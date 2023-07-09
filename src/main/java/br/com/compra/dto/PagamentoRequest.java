package br.com.compra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoRequest {
    private String idCompra;
    private String numeroCartao;
    private String mes;
    private String ano;
    private Integer numeroParcelas;
    private Double totalCompra;

    public PagamentoRequest(String idCompra, String numeroCartao, String mes, String ano, Integer numeroParcelas,
                            Double totalCompra) {
        this.idCompra = idCompra;
        this.numeroCartao = numeroCartao;
        this.mes = mes;
        this.ano = ano;
        this.numeroParcelas = numeroParcelas;
        this.totalCompra = totalCompra;
    }
}
