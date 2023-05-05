package ru.tinkoff.edu.java.scrapper.configuration;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost", 5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        var jsonRabbitTemplate = new RabbitTemplate(connectionFactory);
        jsonRabbitTemplate.setMessageConverter(jsonMessageConverter);
        return jsonRabbitTemplate;
    }

    @Bean
    public Queue qScrapper(ApplicationConfig config) {
        return QueueBuilder.durable(config.queue())
                .withArgument("x-dead-letter-exchange", config.queue().concat(".dlx"))
                .build();
    }

    @Bean
    public DirectExchange exScrapper(ApplicationConfig config) {
        return new DirectExchange(config.exchange());
    }

    @Bean
    public Binding bindingScrapper(Queue qScrapper, DirectExchange exScrapper) {
        return BindingBuilder.bind(qScrapper).to(exScrapper).withQueueName();
    }

    @Bean
    public Queue deadLetterQueue(ApplicationConfig config) {
        return QueueBuilder.durable(config.queue().concat(".dlq")).build();
    }


    @Bean
    public FanoutExchange deadLetterExchange(ApplicationConfig config) {
        return new FanoutExchange(config.queue() + ".dlx");
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
    }
}
