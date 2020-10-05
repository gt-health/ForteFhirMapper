FROM maven:3-jdk-11 AS builder
WORKDIR /usr/src/raven_mapper_api
ADD . .
RUN mvn clean install -DskipTests -f /usr/src/raven_mapper_api/VRDR_javalib/
RUN mvn clean install -DskipTests -f /usr/src/raven_mapper_api/

FROM tomcat:latest
#move the WAR for contesa to the webapps directory
COPY --from=builder /usr/src/raven_mapper_api/target/raven-mapper-api-0.0.3-SNAPSHOT.war /usr/local/tomcat/webapps/raven-mapper-api.war
COPY --from=builder /usr/src/raven_mapper_api/src/main/resources/* /usr/local/tomcat/src/main/resources/
