import java.util.Scanner

// --- AQUI ESTÁ A EVOLUÇÃO (POO) ---
// Criamos um "molde" (data class) para as tarefas.
// Agora cada tarefa é um Objeto que tem nome E status (concluída ou não).
data class Tarefa(val nome: String, var concluida: Boolean = false)

fun main() {
    // A lista agora guarda OBJETOS do tipo 'Tarefa', e não mais textos soltos.
    val listaDeTarefas = mutableListOf<Tarefa>()
    
    val scanner = Scanner(System.`in`)
    var escolhaUsuario = 0

    while (escolhaUsuario != 4) {
        println("\n--- TAREFAS 2.0 (Kotlin POO) ---")
        println("1. Adicionar Tarefa")
        println("2. Listar Tarefas")
        println("3. Concluir Tarefa (Novo!)") 
        println("4. Sair")
        print("Escolha uma opção: ")

        if (scanner.hasNextInt()) {
            escolhaUsuario = scanner.nextInt()
            scanner.nextLine() // Limpa o "Enter" que sobra na memória
        } else {
            println("Por favor, digite um número válido.")
            scanner.next()
            continue
        }

        when (escolhaUsuario) {
            1 -> {
                print("Nome da tarefa: ")
                val nomeDigitado = scanner.nextLine()
                
                // CRIA O OBJETO: Aqui nasce a instância da classe Tarefa
                val novaTarefa = Tarefa(nome = nomeDigitado)
                listaDeTarefas.add(novaTarefa)
                println("Tarefa criada com sucesso!")
            }
            2 -> {
                println("\n--- SUAS TAREFAS ---")
                if (listaDeTarefas.isEmpty()) {
                    println("A lista está vazia.")
                } else {
                    // Loop inteligente que desenha a caixinha [x] ou [ ]
                    listaDeTarefas.forEachIndexed { index, tarefa ->
                        val check = if (tarefa.concluida) "[x]" else "[ ]"
                        println("${index + 1}. $check ${tarefa.nome}")
                    }
                }
            }
            3 -> {
                println("\n--- MARCAR COMO FEITA ---")
                // Mostra a lista para o usuário saber o número
                listaDeTarefas.forEachIndexed { i, t -> 
                    val check = if (t.concluida) "[x]" else "[ ]"
                    println("${i + 1}. $check ${t.nome}") 
                }
                
                print("Digite o número da tarefa para concluir: ")
                if (scanner.hasNextInt()) {
                    val indice = scanner.nextInt()
                    
                    if (indice > 0 && indice <= listaDeTarefas.size) {
                        // Acessa o objeto específico na memória e muda o boolean dele
                        val tarefaAlvo = listaDeTarefas[indice - 1]
                        tarefaAlvo.concluida = true
                        println("Parabéns! Tarefa '${tarefaAlvo.nome}' concluída!")
                    } else {
                        println("Número inválido.")
                    }
                }
            }
            4 -> println("Saindo... Até mais!")
            else -> println("Opção inválida.")
        }
    }
}