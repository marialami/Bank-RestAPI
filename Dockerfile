FROM openjdk

COPY build/libs/usuario-0.0.1-SNAPSHOT.jar Bank-RestAPI.jar
CMD sleep 15 && java -jar Bank-RestAPI.jar