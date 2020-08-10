FROM maven:3-jdk-11 AS builder
WORKDIR /usr/src/mdifhirmapping
ADD . .
RUN mvn clean install -DskipTests -f /usr/src/mdifhirmapping/VRDR_javalib/
RUN mvn clean install -DskipTests -f /usr/src/mdifhirmapping/

FROM tomcat:latest
#move the WAR for contesa to the webapps directory
COPY --from=builder /usr/src/mdifhirmapping/target/ForteFhirMapper-0.0.2-SNAPSHOT.jar /usr/local/tomcat/webapps/ForteFhirMapper.war
COPY --from=builder /usr/src/mdifhirmapping/src/main/resources/* /usr/local/tomcat/src/main/resources/