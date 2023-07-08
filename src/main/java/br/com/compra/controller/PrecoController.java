package br.com.compra.controller;

import br.com.compra.constants.RabbitMQConstants;
import br.com.compra.dto.PrecoDto;
import br.com.compra.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/preco")
public class PrecoController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity alteraPreco(@RequestBody PrecoDto precoDto){
        Object o = this.rabbitMQService.enviaMensagem(RabbitMQConstants.FILA_PRECO, precoDto);
        return new ResponseEntity(o, HttpStatus.OK);
    }
}