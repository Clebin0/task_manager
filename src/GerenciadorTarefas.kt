import java.io.File
import java.util.Scanner

// 1. O MOLDE (Continua igual)
data class Tarefa(val nome: String, val descricao: String, var concluida: Boolean = false)

fun main() {
    val scanner = Scanner(System.`in`)
    // 2. O ARQUIVO: Onde os dados vão morar
    val arquivoBancoDeDados = File("banco_de_tarefas.txt")

    // 3. CARREGAR (LOAD): Ao iniciar, tentamos ler o arquivo
    val listaDeTarefas = lerTarefasDoArquivo(arquivoBancoDeDados)

    var escolhaUsuario = 0

    while (escolhaUsuario != 4) {
        println("\n--- TAREFAS 4.0 (Com Banco de Dados) ---")
        println("1. Adicionar Tarefa")
        println("2. Listar Tarefas")
        println("3. Concluir Tarefa")
        println("4. Sair e Salvar") // Mudamos o texto aqui
        print("Escolha uma opção: ")

        if (scanner.hasNextInt()) {
            escolhaUsuario = scanner.nextInt()
            scanner.nextLine()
        } else {
            println("Digite um número válido.")
            scanner.next()
            continue
        }

        when (escolhaUsuario) {
            1 -> {
                print("Nome: ")
                val nome = scanner.nextLine()
                print("Descrição: ")
                val desc = scanner.nextLine()

                listaDeTarefas.add(Tarefa(nome, desc))
                println("Tarefa criada!")

                // SALVAMENTO AUTOMÁTICO: Salvamos sempre que muda algo
                salvarTarefasNoArquivo(arquivoBancoDeDados, listaDeTarefas)
            }
            2 -> {
                println("\n--- SUAS TAREFAS ---")
                if (listaDeTarefas.isEmpty()) {
                    println("Nenhuma tarefa cadastrada.")
                } else {
                    listaDeTarefas.forEachIndexed { index, tarefa ->
                        val check = if (tarefa.concluida) "[x]" else "[ ]"
                        println("${index + 1}. $check ${tarefa.nome} (${tarefa.descricao})")
                    }
                }
            }
            3 -> {
                println("\n--- CONCLUIR ---")
                listaDeTarefas.forEachIndexed { i, t -> println("${i+1}. ${t.nome}") }
                print("Número: ")
                if (scanner.hasNextInt()) {
                    val idx = scanner.nextInt()
                    if (idx > 0 && idx <= listaDeTarefas.size) {
                        listaDeTarefas[idx - 1].concluida = true
                        println("Concluído!")
                        // SALVAMENTO AUTOMÁTICO
                        salvarTarefasNoArquivo(arquivoBancoDeDados, listaDeTarefas)
                    } else {
                        println("Inválido.")
                    }
                }
            }
            4 -> {
                println("Salvando dados e saindo...")
                // Garantia final de salvamento
                salvarTarefasNoArquivo(arquivoBancoDeDados, listaDeTarefas)
            }
            else -> println("Opção inválida.")
        }
    }
}

// --- FUNÇÕES DE ENGENHARIA DE DADOS (IO) ---

// Função para ESCREVER no HD
fun salvarTarefasNoArquivo(arquivo: File, lista: List<Tarefa>) {
    // Transformamos a lista em um textoão. Ex: "Estudar;Sim|Comprar Pão;Não"
    var textoParaSalvar = ""

    for (tarefa in lista) {
        // Usamos ";" para separar as colunas da nossa "planilha" de texto
        textoParaSalvar += "${tarefa.nome};${tarefa.descricao};${tarefa.concluida}\n"
    }

    // Escreve tudo no arquivo (substituindo o antigo)
    arquivo.writeText(textoParaSalvar)
}

// Função para LER do HD
fun lerTarefasDoArquivo(arquivo: File): MutableList<Tarefa> {
    val listaNova = mutableListOf<Tarefa>()

    if (arquivo.exists()) {
        // Lê cada linha do arquivo
        val linhas = arquivo.readLines()
        for (linha in linhas) {
            // A linha vem assim: "Estudar Java;Revisar POO;true"
            // O split(";") corta a linha em 3 pedaços
            val pedacos = linha.split(";")

            if (pedacos.size == 3) {
                val nomeRecuperado = pedacos[0]
                val descRecuperada = pedacos[1]
                val statusRecuperado = pedacos[2].toBoolean() // Converte texto "true" para Boolean

                val tarefaRecuperada = Tarefa(nomeRecuperado, descRecuperada, statusRecuperado)
                listaNova.add(tarefaRecuperada)
            }
        }
        println("Banco de dados carregado: ${listaNova.size} tarefas encontradas.")
    }

    return listaNova
}