#FROM ubuntu:latest
#LABEL authors="Enes"
#
##ENTRYPOINT ["top", "-b"]
#
#FROM eclipse-temurin:17-jdk-alpine AS base
#
#LABEL maintainer="Enes <enestopal.053@gmail.com>" \
#      description="Docker image for SynClock database"
#
#COPY target/SynClock-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8080
##ENTRYPOINT ["java","-jar","/app.jar"]
#
#
#CMD ["java", "-jar", "/app.jar"]



FROM openjdk:17-jdk-slim

LABEL authors="Enes"

# JAR dosyasını konteynere kopyala
COPY target/SynClock-0.0.1-SNAPSHOT.jar /usr/app/

# Çalışma dizinini belirle
WORKDIR /usr/app

# Uygulama portunu belirle
EXPOSE 8080

# Uygulamayı başlat
CMD ["java", "-jar", "SynClock-0.0.1-SNAPSHOT.jar"]
