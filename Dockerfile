FROM openjdk:17
EXPOSE 8080
ADD target/servico-compra-rabbit.jar servico-compra-rabbit.jar
ENTRYPOINT ["java", "-jar", "/servico-compra-rabbit.jar"]