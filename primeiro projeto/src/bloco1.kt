
open class Produto(
    val nome: String,
    private var preco: Double,
    var quantidadeEstoque: Int
) {

    fun getPreco(): Double = preco

    fun setPreco(valor: Double) {
        if (valor < 0) {
            println("Erro: preço não pode ser negativo.")
        } else {
            preco = valor
        }
    }

    fun aplicarDesconto(percentual: Double) {
        if (percentual < 0 || percentual > 100) {
            println("percentual não valido")
        } else {
            setPreco(preco - (preco * percentual / 100))
        }
    }
}

class ProdutoPerecivel(
    nome: String,
    preco: Double,
    quantidadeEstoque: Int,
    val dataValidade: String // formato "DD/MM/AAAA"
) : Produto(nome, preco, quantidadeEstoque) {

    private fun converterData(data: String): String {
        val partes = data.split("/")
        return "${partes[2]}/${partes[1]}/${partes[0]}"
    }

    fun estaVencido(dataHoje: String): Boolean {
        return converterData(dataValidade) < converterData(dataHoje)
    }
}


abstract class FuncionarioBase(
    val nome: String,
    val salarioBase: Double
) {
    abstract fun calcularSalario(): Double
}

class Vendedor(
    nome: String,
    salarioBase: Double,
    var totalVendas: Double = 0.0
) : FuncionarioBase(nome, salarioBase) {

    override fun calcularSalario(): Double {
        return salarioBase + (totalVendas * 0.05)
    }
}

class Gerente(
    nome: String,
    salarioBase: Double,
    val bonusFixo: Double
) : FuncionarioBase(nome, salarioBase) {

    override fun calcularSalario(): Double {
        return salarioBase + bonusFixo
    }
}



fun finalizarVenda(carrinho: List<Produto>, vendedor: Vendedor, dataHoje: String) {
    println("\n========== FINALIZANDO VENDA ==========")

    for (produto in carrinho) {
        if (produto is ProdutoPerecivel && produto.estaVencido(dataHoje)) {
            println("AVISO: '${produto.nome}' está VENCIDO (validade: ${produto.dataValidade})!")
        }
    }

    var totalVenda = 0.0
    for (produto in carrinho) {
        println("  - ${produto.nome}: R$ %.2f".format(produto.getPreco()))
        totalVenda += produto.getPreco()
        produto.quantidadeEstoque -= 1
    }

    vendedor.totalVendas += totalVenda

    println("Total da venda: R$ %.2f".format(totalVenda))
    println("Salário atualizado de ${vendedor.nome}: R$ %.2f".format(vendedor.calcularSalario()))
}


fun main() {

    // --- Exercício 1 e 2 (seu código, ajustado) ---
    val produto1 = Produto(nome = "Arroz", preco = 15.0, quantidadeEstoque = 100)
    val produto2 = Produto(nome = "Feijão", preco = 7.0, quantidadeEstoque = 87)
    val produto3 = Produto(nome = "Macarrão", preco = 3.50, quantidadeEstoque = 37)

    produto1.aplicarDesconto(10.0)
    println("PRODUTO1: ${produto1.nome} | PREÇO: R$ ${"%.2f".format(produto1.getPreco())} | ESTOQUE: ${produto1.quantidadeEstoque}")
    println("_________")

    produto2.aplicarDesconto(8.0)
    println("PRODUTO2: ${produto2.nome} | PREÇO: R$ ${"%.2f".format(produto2.getPreco())} | ESTOQUE: ${produto2.quantidadeEstoque}")
    println("_________")

    produto3.aplicarDesconto(101.0)
    println("PRODUTO3: ${produto3.nome} | PREÇO: R$ ${"%.2f".format(produto3.getPreco())} | ESTOQUE: ${produto3.quantidadeEstoque}")

    // --- Exercício 3 ---
    println("\n===== Exercício 3 — Encapsulamento =====")
    produto1.setPreco(-5.0) // deve rejeitar
    produto1.setPreco(13.50)
    println("Novo preço do Arroz: R$ %.2f".format(produto1.getPreco()))

    // --- Exercício 4 ---
    println("\n===== Exercício 4 — Produto Perecível =====")
    val leite = ProdutoPerecivel("Leite", 5.00, 30, "01/07/2026")
    val iogurte = ProdutoPerecivel("Iogurte", 3.50, 20, "15/06/2026")
    val dataHoje = "30/06/2026"

    println("Leite vencido? ${leite.estaVencido(dataHoje)}")
    println("Iogurte vencido? ${iogurte.estaVencido(dataHoje)}")

    // --- Exercícios 5 e 6 ---
    println("\n===== Exercícios 5 e 6 — Folha de pagamento =====")
    val funcionarios: List<FuncionarioBase> = listOf(
        Vendedor("João", 3000.0, 5000.0),
        Gerente("Maria", 5000.0, 1000.0),
        Vendedor("Carlos", 2800.0, 2000.0)
    )

    var totalFolha = 0.0
    for (f in funcionarios) {
        val tipo = if (f is Vendedor) "Vendedor" else "Gerente"
        println("${f.nome} ($tipo) → R$ %.2f".format(f.calcularSalario()))
        totalFolha += f.calcularSalario()
    }
    println("---")
    println("Total da folha: R$ %.2f".format(totalFolha))

    // --- Exercício 7 ---
    println("\n===== Exercício 7 — Mini-sistema de loja =====")
    val carrinho: List<Produto> = listOf(
        Produto("Café", 12.00, 50),
        ProdutoPerecivel("Queijo", 18.00, 15, "20/06/2026"), // vencido
        ProdutoPerecivel("Manteiga", 9.50, 25, "31/12/2026")
    )
    val vendedor = Vendedor("Ana", 2500.0)

    finalizarVenda(carrinho, vendedor, "30/06/2026")
}