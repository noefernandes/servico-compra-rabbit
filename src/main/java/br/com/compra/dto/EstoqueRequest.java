package br.com.compra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueRequest {
    private Long idProduto;
    private Integer quantidade;
}
