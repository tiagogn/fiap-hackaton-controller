FROM gradle:8-jdk17 AS build

WORKDIR /src
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
RUN gradle build -x test

FROM openjdk:17-slim
RUN addgroup --system app && adduser --system --group app
USER app
WORKDIR /app
COPY --from=build /src/build/libs/*.jar /app/controller.jar
EXPOSE 8080
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:MaxMetaspaceSize=256m -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+ExitOnOutOfMemoryError"
ENTRYPOINT ["java", "-jar", "/app/controller.jar"]