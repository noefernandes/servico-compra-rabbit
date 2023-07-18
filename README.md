# servico-compra-rabbit

Para realizar requisição a aplicação use o ip listado no application.properties na porta 3100.
Por exemplo: http://{endereco_ip}:3100/health

Para realizar uma requisição para testar o microsserviço de compra, tente mandar uma requisição POST para a url
http://{endereco_ip}:3100/compra como a seguir:

{
	"idProduto": "14",
	"idUsuario": 1,
	"preco": 193.99,
	"totalCompra": 0.0,
	"quantidade": 2,
	"numeroParcelas": 3
}
