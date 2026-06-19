class Produto(
    val nome : String,
    private var preco : Double,
     val quantidadeEstoque : Int

) {

    fun getPreco() : Double = preco
    fun setPreco(preco: Double) {}

    fun aplicarDesconto(percentual: Double){
        if (percentual < 0 || percentual > 100) {
            println("percentua não valido")
        }else {
            preco = preco - (preco * percentual)
        }
    }

}




fun main (){
    val produto1 = Produto(
        nome = "Arroz",
        preco = 15.0,
        quantidadeEstoque = 100
    )
    val produto2 = Produto(
        nome = "Feijão",
        preco = 7.0,
        quantidadeEstoque = 87
    )
    val produto3 = Produto(
        nome = "Macarrão",
        preco = 3.50,
        quantidadeEstoque = 37
    )

    produto1.aplicarDesconto(0.10)
    println("PRODUTO1: ${produto1.nome} | " + "PREÇO: R$ ${"%.2f".format(produto1.preco)}  |" + "ESTOQUE: ${produto1.quantidadeEstoque} "  )
    println("_________")
    produto2.aplicarDesconto(0.08)

    println("PRODUTO2: ${produto2.nome} | " + "PREÇO:  R$ ${"%.2f".format(produto2.preco)}  |" + "ESTOQUE: ${produto2.quantidadeEstoque} "  )
    println("_________")
    produto3.aplicarDesconto(101.0)

    println("PRODUTO2: ${produto3.nome} | " + "PREÇO:  R$ ${"%.2f".format(produto3.preco)}  |" + "ESTOQUE: ${produto3.quantidadeEstoque} "  )

}




