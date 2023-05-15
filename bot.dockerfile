FROM openjdk:17-oracle
WORKDIR /usr/src/app
ARG JAR_FILE=./bot/target/*SNAPSHOT.jar
COPY ${JAR_FILE} bot.jar
ENTRYPOINT ["java","-jar","bot.jar"]
