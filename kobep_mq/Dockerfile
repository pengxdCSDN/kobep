FROM anapsix/alpine-java:8_server-jre_unlimited
ARG JAR_FILE
ADD  ${JAR_FILE} app.jar
ENTRYPOINT ["java","${JAVA_OPTS}","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
