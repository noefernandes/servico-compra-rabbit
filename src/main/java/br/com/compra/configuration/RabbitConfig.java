package br.com.compra.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("fila_externa");
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("exchange_teste");
    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("chave_de_rota_recebimento");
    }
}