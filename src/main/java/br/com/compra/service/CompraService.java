package br.com.compra.service;

import br.com.compra.constants.RabbitMQConstants;
import br.com.compra.dto.*;
import br.com.compra.model.Compra;
import br.com.compra.repository.CompraRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompraService {

    private final String PAGAMENTO_REALIZADO = "PAGAMENTO_REALIZADO";

    private final String PAGAMENTO_PENDENTE = "PAGAMENTO_PENDENTE";

    private final CompraRepository compraRepository;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public CompraService(CompraRepository compraRepository, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.compraRepository = compraRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public Compra salvarCompra(Compra compra) throws JsonProcessingException {
        EstoqueRequest estoqueRequest = new EstoqueRequest();
        estoqueRequest.setIdProduto(compra.getIdProduto());
        estoqueRequest.setQuantidade(compra.getQuantidade());

        if(estoqueRequest.getQuantidade() < 1) {
            throw new RuntimeException("Por favor, solicite uma quantidade de itens maior do que 0.");
        }

        EstoqueResponse estoqueResponse = acessarEstoque(estoqueRequest);

        if(estoqueResponse.getQuantidade() < estoqueRequest.getQuantidade()){
            throw new RuntimeException("O produto está faltando no estoque.");
        }

        compra.setTotalCompra(compra.getQuantidade().doubleValue() * estoqueResponse.getPreco());
        compra.setPreco(estoqueResponse.getPreco());
        compra.setStatus(PAGAMENTO_PENDENTE);
        Compra compraSalva = compraRepository.save(compra);

        enviarNotificacao(PAGAMENTO_PENDENTE);

        PagamentoRequest pagamentoRequest = new PagamentoRequest(compraSalva.getId(),"1111222233334444",
                "06", "2028", "501", compra.getNumeroParcelas(), compra.getTotalCompra());
        solicitarPagamento(pagamentoRequest);

        return compraSalva;
    }

    private EstoqueResponse acessarEstoque(EstoqueRequest estoqueRequest) throws JsonProcessingException {
        rabbitTemplate.setReplyTimeout(90000);

        String requestJson = objectMapper.writeValueAsString(estoqueRequest);
        String responseJson = (String) rabbitTemplate.convertSendAndReceive(RabbitMQConstants.FILA_ESTOQUE, requestJson);

        System.out.println(responseJson);

        return objectMapper.readValue(responseJson, EstoqueResponse.class);
    }

    private void enviarNotificacao(String status) throws JsonProcessingException {
        NotificacaoRequest notificacaoRequest = new NotificacaoRequest();
        notificacaoRequest.setEmail("noepessoa@outlook.com");

        switch (status) {
            case PAGAMENTO_PENDENTE ->
                notificacaoRequest.setText("Compra efetuada com sucesso. Aguardando pagamento.");
            case PAGAMENTO_REALIZADO ->
                notificacaoRequest.setText("Pagamento efetuado com sucesso.");
            default -> {
            }
        }

        String requestJson = objectMapper.writeValueAsString(notificacaoRequest);
        rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_NOTIFICACAO, requestJson);
    }

    private void solicitarPagamento(PagamentoRequest pagamentoRequest) throws JsonProcessingException {
        String requestJson = objectMapper.writeValueAsString(pagamentoRequest);
        rabbitTemplate.convertAndSend(RabbitMQConstants.FILA_PAGAMENTO_REQUEST, requestJson);
    }

    @RabbitListener(queues = RabbitMQConstants.FILA_PAGAMENTO_RESPONSE)
    private void aguardarPagamento(String pagamento) throws JsonProcessingException {
        PagamentoResponse pagamentoResponse = objectMapper.readValue(pagamento, PagamentoResponse.class);

        Optional<Compra> compraOp = compraRepository.findById(pagamentoResponse.getIdCompra());

        Compra compra;

        if(compraOp.isPresent()){
            compra = compraOp.get();
            compra.setStatus(PAGAMENTO_REALIZADO);
            enviarNotificacao(PAGAMENTO_REALIZADO);
            compraRepository.save(compra);
            //System.out.println("Entrou");
        }
        System.out.println(pagamento);

    }
}
