package br.com.compra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    @Id
    private String id;

    private Long idProduto;

    private Double preco;

    private Double totalCompra;

    private Integer quantidade;
}
