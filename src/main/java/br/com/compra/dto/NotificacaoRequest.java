package br.com.compra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacaoRequest {
    private String email;
    private String text;
}
