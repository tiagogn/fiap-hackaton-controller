# Etapa de build: constrói o JAR
FROM gradle:8-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon

# Etapa de extração das camadas do layertools
FROM openjdk:17-slim AS extractor
WORKDIR /layers
COPY --from=build /app/build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Etapa final: executável otimizado por camadas
FROM openjdk:17-slim
WORKDIR /app
COPY --from=extractor /layers/dependencies/ ./
COPY --from=extractor /layers/snapshot-dependencies/ ./
COPY --from=extractor /layers/spring-boot-loader/ ./
COPY --from=extractor /layers/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
