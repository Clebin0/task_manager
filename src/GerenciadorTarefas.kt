package src

import java.util.Scanner // O Kotlin usa muitas ferramentas do Java

fun main() {
    // Em Kotlin, usamos 'val' para coisas que não mudam e 'var' para o que muda.
    // mutableListOf é uma lista que podemos alterar (adicionar/remover)
    val listaDeTarefas = mutableListOf<String>()
    
    val scanner = Scanner(System.`in`)
    var escolhaUsuario = 0 // Olha a variável renomeada aqui!

    // O while funciona igual ao Java
    while (escolhaUsuario != 4) {
        println("\n--- MENU DE TAREFAS (KOTLIN) ---")
        println("1. Adicionar Tarefa")
        println("2. Listar Tarefas")
        println("3. Remover Tarefa")
        println("4. Sair")
        print("Escolha uma opção: ")

        // Validação de entrada
        if (scanner.hasNextInt()) {
            escolhaUsuario = scanner.nextInt()
            scanner.nextLine() // Limpar buffer
        } else {
            println("Por favor, digite um número válido.")
            scanner.next()
            continue
        }

        // No lugar do 'switch', o Kotlin usa o 'when', que é muito mais poderoso
        when (escolhaUsuario) {
            1 -> {
                print("Digite a descrição da tarefa: ")
                val novaTarefa = scanner.nextLine()
                listaDeTarefas.add(novaTarefa)
                println("Tarefa adicionada!")
            }
            2 -> {
                println("\n--- LISTA ---")
                if (listaDeTarefas.isEmpty()) {
                    println("Nenhuma tarefa cadastrada.")
                } else {
                    // O 'forEachIndexed' percorre a lista e já te dá o índice (i) e a tarefa
                    listaDeTarefas.forEachIndexed { index, tarefa ->
                        println("${index + 1}. $tarefa")
                    }
                }
            }
            3 -> {
                println("\n--- REMOVER ---")
                if (listaDeTarefas.isEmpty()) {
                    println("Lista vazia.")
                } else {
                    // Mostra a lista novamente
                    listaDeTarefas.forEachIndexed { index, tarefa ->
                        println("${index + 1}. $tarefa")
                    }
                    
                    print("Digite o número para remover: ")
                    if (scanner.hasNextInt()) {
                        val indiceParaRemover = scanner.nextInt()
                        
                        if (indiceParaRemover > 0 && indiceParaRemover <= listaDeTarefas.size) {
                            val removida = listaDeTarefas.removeAt(indiceParaRemover - 1)
                            println("Tarefa '$removida' removida!")
                        } else {
                            println("Número inválido.")
                        }
                    } else {
                        println("Entrada inválida.")
                        scanner.next()
                    }
                }
            }
            4 -> println("Saindo... Até mais!") // Se for só uma linha, nem precisa de chaves {}
            else -> println("Opção inválida.")
        }
    }
}