FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD amitravel-0.0.1-SNAPSHOT.jar amitravel.jar
ENTRYPOINT ["java", "-jar","/amitravel.jar"]
 