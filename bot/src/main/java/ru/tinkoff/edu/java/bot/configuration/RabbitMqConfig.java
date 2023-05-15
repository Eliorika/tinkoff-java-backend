package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter messageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter=new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }

    @Bean
    public ConnectionFactory rabbit() {
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
    public Queue deadLetterQueue(ApplicationConfig config) {
        return QueueBuilder.durable(config.queue().concat(".dlq")).build();
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
    public FanoutExchange deadLetterExchange(ApplicationConfig config) {
        return new FanoutExchange(config.queue() + ".dlx");
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
    }


    @Bean
    public ClassMapper classMapper(){
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.request.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }


}
