FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/discover-server-0.0.1-SNAPSHOT.jar discover-server.jar
ENTRYPOINT ["java","-jar","/discover-server.jar"]
