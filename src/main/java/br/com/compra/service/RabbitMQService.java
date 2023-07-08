package br.com.compra.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public Object enviaMensagem(String nomeFila, Object mensagem){
        try {
            rabbitTemplate.setReplyTimeout(90000);
            String mensagemJson = this.objectMapper.writeValueAsString(mensagem);
            Object o = this.rabbitTemplate.convertSendAndReceive(nomeFila, mensagemJson);
            System.out.println(o);
            return o;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}