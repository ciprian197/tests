FROM adoptopenjdk/openjdk11:alpine-slim
EXPOSE 8080
COPY build/libs/tests-* tests.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tests.jar"]