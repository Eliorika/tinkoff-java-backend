FROM openjdk:17-oracle
WORKDIR /usr/src/app
ARG JAR_FILE=./scrapper/target/*SNAPSHOT.jar
COPY ${JAR_FILE} bot.jar
ENTRYPOINT ["java","-jar","scrapper.jar"]
