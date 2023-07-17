package br.com.compra.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PagamentoRequest {
    private String idCompra;
    private String numeroCartao;
    private String mes;
    private String ano;
    private String cvv;
    private Integer numeroParcelas;
    private Double totalCompra;
}
