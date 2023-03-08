FROM maven:3.5.0-jdk-8-alpine
WORKDIR /usr/src/app
COPY ./pom.xml .
RUN mvn -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY ./ .
RUN mvn -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml package
EXPOSE 8011
CMD ["java", "-jar", "./target/discovery-service-0.0.1-SNAPSHOT.jar"]