FROM gradle:8.14.3-jdk21@sha256:47ba472786e0a68921ff212a1d6fcfd1c74da9ca3b1c0e7993ea541a36055567 AS build
WORKDIR /home/gradle/src
COPY build.gradle settings.gradle gradle.properties ./
COPY gradle ./gradle
COPY gradlew gradlew.bat ./
COPY src ./src
RUN gradle clean build -x test

FROM eclipse-temurin:21-jre-alpine@sha256:990397e0495ac088ab6ee3d949a2e97b715a134d8b96c561c5d130b3786a489d
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]
