package br.com.compra.controller;

import br.com.compra.model.Compra;
import br.com.compra.service.CompraService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RabbitListener(queues = "teste")
public class CompraController {

    private final CompraService compraService;
    private final RabbitTemplate rabbitTemplate;

    public CompraController(CompraService compraService, RabbitTemplate rabbitTemplate) {
        this.compraService = compraService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping(value = "/compra/{id}")
    public ResponseEntity<?> saveCompra(@PathVariable("id") Long id) {
        Compra compra = new Compra();
        compra.setIdProduto(0L);
        compra.setQuantidade(2);
        compra.setPreco(3.50);
        compra.setTotalCompra(3.50 * 2);
        compraService.salvarCompra(compra);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void receberMensagem(String in) {
        System.out.println(in);
    }
}
