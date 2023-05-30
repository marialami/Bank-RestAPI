FROM openjdk

COPY build/libs/bank-0.0.1-SNAPSHOT.jar bank-rest-api.jar
CMD sleep 15 && java -jar bank-rest-api.jar