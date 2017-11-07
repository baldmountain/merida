FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/merida-0.0.1-SNAPSHOT-standalone.jar /merida/app.jar

EXPOSE 4000

CMD ["java", "-jar", "/merida/app.jar"]
