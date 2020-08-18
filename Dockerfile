FROM maven:3-jdk-11 AS builder
WORKDIR /usr/src/fortefhirmapping
ADD . .
RUN mvn clean install -DskipTests -f /usr/src/fortefhirmapping/VRDR_javalib/
RUN mvn clean install -DskipTests -f /usr/src/fortefhirmapping/

FROM tomcat:latest
#move the WAR for contesa to the webapps directory
COPY --from=builder /usr/src/fortefhirmapping/target/ForteFhirMapper-0.0.2-SNAPSHOT.war /usr/local/tomcat/webapps/ForteFhirMapper.war
COPY --from=builder /usr/src/fortefhirmapping/src/main/resources/* /usr/local/tomcat/src/main/resources/
