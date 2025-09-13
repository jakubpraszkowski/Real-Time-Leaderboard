FROM maven:3.9-eclipse-temurin-17-focal AS builder

WORKDIR /build

COPY pom.xml .

COPY user/pom.xml user/pom.xml
COPY user/src/ user/src/
COPY leaderboard/pom.xml leaderboard/pom.xml
COPY leaderboard/src/ leaderboard/src/
COPY score/pom.xml score/pom.xml
COPY score/src/ score/src/

RUN mvn dependency:go-offline -DskipTests

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-focal

WORKDIR /app

COPY --from=builder /build/user/target/*.jar user-app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","user-app.jar"]
