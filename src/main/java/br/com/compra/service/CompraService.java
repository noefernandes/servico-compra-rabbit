package br.com.compra.service;

import br.com.compra.model.Compra;
import br.com.compra.repository.CompraRepository;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CompraService {
    private final CompraRepository compraRepository;

    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    private final Exchange exchange;

    public CompraService(CompraRepository compraRepository,
                         RabbitTemplate rabbitTemplate,
                         Queue queue, Exchange exchange) {
        this.compraRepository = compraRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.exchange = exchange;
    }

    public void salvarCompra(Compra compra) {
        compraRepository.save(compra);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void solicitarQuantidadeEPrecoDoProduto() {
        String mensagem = (String) rabbitTemplate.convertSendAndReceive(exchange.getName(), "chave_de_rota_recebimento", "Olá mundo");
        System.out.println(mensagem);
    }

    @RabbitListener(queues = "fila_externa")
    public String pegarQuantidadeEPrecoDoInventario(String objeto) {
        return objeto;
    }
}
