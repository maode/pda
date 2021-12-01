FROM harbor.pkcfwd.com/library/oracle-jdk:1.8.0_301
MAINTAINER yhwt

WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE} pda.jar

ENTRYPOINT ["java", "-Xmx512m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "pda.jar"]
CMD ["8686"]
