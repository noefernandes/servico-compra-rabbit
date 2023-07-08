package br.com.compra.controller;

import br.com.compra.constants.RabbitMQConstants;
import br.com.compra.dto.EstoqueDto;
import br.com.compra.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "estoque")
public class EstoqueController {

    @Autowired
    private RabbitMQService rabbitmqService;

    @PutMapping
    private ResponseEntity alteraEstoque(@RequestBody EstoqueDto estoqueDto){
        System.out.println(estoqueDto.codigoproduto);

        this.rabbitmqService.enviaMensagem(RabbitMQConstants.FILA_ESTOQUE, estoqueDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
