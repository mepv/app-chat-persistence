FROM openjdk:8-jdk-alpine
EXPOSE 8020
 ARG JAR_FILE=target/ms-user-0.0.1-SNAPSHOT.jar
 # cd /opt/app
 WORKDIR /opt/app
 # cp target/{jarfileName}.jar /opt/app/app.jar
 COPY ${JAR_FILE} app.jar
 # java -jar /opt/app/app.jar
 ENTRYPOINT ["java","-jar","app.jar"]