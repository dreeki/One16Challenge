FROM maven:3.8.6-openjdk-18 AS MAVEN_BUILD

MAINTAINER Andreas De Witte

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

# Since I'm using testcontainers for tests, I need to run docker in docker during build fase,
# which is not supported by docker itself, hence -DskipTests during docker build
RUN mvn -B clean package -DskipTests


FROM openjdk:oraclelinux8

WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/One16Challenge-1.0.0.jar /app/

ENTRYPOINT ["java", "-jar", "One16Challenge-1.0.0.jar"]
