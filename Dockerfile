FROM amazoncorretto:11-alpine-jdk

COPY build/libs/philosopherTSV2LimelightXML.jar  /usr/local/bin/philosopherTSV2LimelightXML.jar

ENTRYPOINT ["java", "-jar", "/usr/local/bin/philosopherTSV2LimelightXML.jar"]