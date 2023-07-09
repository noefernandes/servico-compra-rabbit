package br.com.compra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueResponse {
    private Integer quantidade;
    private Double preco;
}
