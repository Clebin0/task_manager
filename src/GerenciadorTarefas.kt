package src

import java.util.Scanner

// Agora tem 'descricao'
data class Tarefa(val nome: String, val descricao: String, var concluida: Boolean = false)

fun main() {
    val listaDeTarefas = mutableListOf<Tarefa>()
    val scanner = Scanner(System.`in`)
    var escolhaUsuario = 0

    while (escolhaUsuario != 4) {
        println("\n--- TAREFAS (Com Detalhes) ---")
        println("1. Adicionar Tarefa")
        println("2. Listar Tarefas")
        println("3. Concluir Tarefa")
        println("4. Sair")
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
                print("Nome da tarefa: ")
                val nomeDigitado = scanner.nextLine()

                // PEDIR A DESCRIÇÃO
                print("Descrição (detalhes): ")
                val descricaoDigitada = scanner.nextLine()

                // CRIAR O OBJETO COMPLETO
                val novaTarefa = Tarefa(nome = nomeDigitado, descricao = descricaoDigitada)
                listaDeTarefas.add(novaTarefa)
                println("Tarefa criada!")
            }
            2 -> {
                println("\n--- SUAS TAREFAS ---")
                if (listaDeTarefas.isEmpty()) {
                    println("Lista vazia.")
                } else {
                    listaDeTarefas.forEachIndexed { index, tarefa ->
                        val check = if (tarefa.concluida) "[x]" else "[ ]"
                        // MOSTRAR A DESCRIÇÃO NA TELA
                        println("${index + 1}. $check ${tarefa.nome} - (${tarefa.descricao})")
                    }
                }
            }
            3 -> {
                println("\n--- CONCLUIR TAREFA ---")
                listaDeTarefas.forEachIndexed { i, t ->
                    println("${i + 1}. ${if(t.concluida) "[x]" else "[ ]"} ${t.nome}")
                }
                print("Número da tarefa: ")
                if (scanner.hasNextInt()) {
                    val indice = scanner.nextInt()
                    if (indice > 0 && indice <= listaDeTarefas.size) {
                        listaDeTarefas[indice - 1].concluida = true
                        println("Feito!")
                    } else {
                        println("Inválido.")
                    }
                }
            }
            4 -> println("Saindo...")
            else -> println("Opção inválida.")
        }
    }
}