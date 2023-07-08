FROM azul/zulu-openjdk-alpine:20.0.1-20.30.11

ENV ROOT_FILE cooperative-assembly-manager-api-1.0.0.jar
ENV DESTINY_HOME /home/project/app.jar

EXPOSE 8080

COPY target/$ROOT_FILE $DESTINY_HOME

ENTRYPOINT ["java", "-Xss512k", "-Xms512M", "-Xmx1024M", "-XX:MaxGCPauseMillis=500", "-XX:+UseG1GC", "-XX:+DisableExplicitGC", "-XX:SurvivorRatio=6", "-XX:MaxMetaspaceSize=256m", "-XX:+ParallelRefProcEnabled", "-jar", "/home/project/app.jar"]