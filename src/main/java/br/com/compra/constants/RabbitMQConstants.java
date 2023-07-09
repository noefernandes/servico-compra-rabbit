package br.com.compra.constants;

public class RabbitMQConstants {
    //Minhas filas
    public static final String FILA_ESTOQUE = "ESTOQUE";
    public static final String FILA_NOTIFICACAO = "NOTIFICACAO";
    public static final String FILA_PAGAMENTO_REQUEST = "PAGAMENTO_REQUEST";

    //Filas para consumir
    public static final String FILA_PAGAMENTO_RESPONSE = "PAGAMENTO_RESPONSE";
}
