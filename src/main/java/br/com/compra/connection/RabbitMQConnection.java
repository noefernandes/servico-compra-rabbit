package br.com.compra.connection;

import br.com.compra.constants.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMQConnection {

    private static final String NOME_EXCHANGE = "amq.direct";
    private final AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange troca) {
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adiciona(){
        Queue filaEstoque = this.fila(RabbitMQConstants.FILA_ESTOQUE);
        Queue filaNotificacao = this.fila(RabbitMQConstants.FILA_NOTIFICACAO);
        Queue filaPagamentoRequest = this.fila(RabbitMQConstants.FILA_PAGAMENTO_REQUEST);
        Queue filaPagamentoResponse = this.fila(RabbitMQConstants.FILA_PAGAMENTO_RESPONSE);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoEstoque = this.relacionamento(filaEstoque, troca);
        Binding ligacaoNotificacao = this.relacionamento(filaNotificacao, troca);
        Binding ligacaoPagamentoRequest = this.relacionamento(filaPagamentoRequest, troca);
        Binding ligacaoPagamentoResponse = this.relacionamento(filaPagamentoResponse, troca);

        this.amqpAdmin.declareQueue(filaEstoque);
        this.amqpAdmin.declareQueue(filaNotificacao);
        this.amqpAdmin.declareQueue(filaPagamentoRequest);
        this.amqpAdmin.declareQueue(filaPagamentoResponse);

        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoEstoque);
        this.amqpAdmin.declareBinding(ligacaoNotificacao);
        this.amqpAdmin.declareBinding(ligacaoPagamentoRequest);
        this.amqpAdmin.declareBinding(ligacaoPagamentoResponse);
    }
}
