package br.com.compra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueRequest {
    private String idProduto;
    private Integer quantidade;
}
