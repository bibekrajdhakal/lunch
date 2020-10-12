# the first stage of the build will use a maven 3.6.1 parent image
FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD

# copy the pom and src code to the container
COPY ./ ./

# package the application code
RUN mvn clean package

# the second stage of the build will use open jdk 8 on alpine 3.9
FROM openjdk:8-jre-alpine3.9

# configure tomcat server
ARG TOMCAT_VERSION=8.5.40
RUN wget http://archive.apache.org/dist/tomcat/tomcat-8/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz -O /tmp/tomcat.tar.gz
RUN cd /tmp && tar -zxvf tomcat.tar.gz -C /opt/
RUN mv /opt/apache-tomcat-${TOMCAT_VERSION} /opt/tomcat
ADD server.xml /opt/tomcat/conf/server.xml
# copy only the artifacts needed from the first stage and discard the rest
COPY --from=MAVEN_BUILD /target/api.war /opt/tomcat/webapps

EXPOSE 8080

CMD /opt/tomcat/bin/catalina.sh jpda run