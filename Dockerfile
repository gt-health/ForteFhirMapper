FROM maven:3-jdk-11 AS builder
WORKDIR /usr/src/mdifhirmapping
ADD . .
RUN mvn clean install -DskipTests -f /usr/src/mdifhirmapping/VRDR_javalib/
RUN mvn clean install -DskipTests -f /usr/src/mdifhirmapping/

FROM tomcat:latest
#move the WAR for contesa to the webapps directory
COPY --from=builder /usr/src/mdifhirmapping/target/OpenMDIFhirMapping-0.0.1-SNAPSHOT.jar /usr/local/tomcat/webapps/MDIFhirMapping.war
COPY --from=builder /usr/src/mdifhirmapping/src/main/resources/* /usr/local/tomcat/src/main/resources/