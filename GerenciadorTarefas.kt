import java.sql.DriverManager
import java.util.Scanner

// Modelo da Tarefa (agora com ID para o Banco de Dados saber quem é quem)
data class Tarefa(val id: Int, val nome: String, val descricao: String, val concluida: Boolean)

fun main() {

    val scanner = Scanner(System.`in`)

    // O resto do teu código continua igual aqui para baixo...
    criarTabelaNoBanco()

    var escolhaUsuario = 0

    while (escolhaUsuario != 4) {
        println("\n--- TAREFAS 5.0 (SQL EDITION) ---")
        println("1. Adicionar Tarefa (INSERT)")
        println("2. Listar Tarefas (SELECT)")
        println("3. Concluir Tarefa (UPDATE)")
        println("4. Sair")
        print("Escolha uma opção: ")

        if (scanner.hasNextInt()) {
            escolhaUsuario = scanner.nextInt()
            scanner.nextLine() // Limpar o enter
        } else {
            scanner.next()
            continue
        }

        when (escolhaUsuario) {
            1 -> {
                print("Nome: ")
                val nome = scanner.nextLine()
                print("Descrição: ")
                val desc = scanner.nextLine()
                adicionarTarefa(nome, desc)
            }
            2 -> {
                listarTarefas()
            }
            3 -> {
                listarTarefas() // Mostra a lista para a pessoa saber o ID
                print("Digite o ID da tarefa para concluir: ")
                if (scanner.hasNextInt()) {
                    val id = scanner.nextInt()
                    concluirTarefa(id)
                }
            }
            4 -> println("Saindo do sistema...")
            else -> println("Opção inválida.")
        }
    }
}

// --- FUNÇÕES DE BANCO DE DADOS (SQL) ---

// Função auxiliar para conectar (evita repetir código)
fun conectar(): java.sql.Connection {
    // Atenção: O nome do arquivo deve ser igual ao do DBeaver
    val url = "jdbc:sqlite:banco_tarefas.db"
    return DriverManager.getConnection(url)
}

fun criarTabelaNoBanco() {
    val sql = """
        CREATE TABLE IF NOT EXISTS Tarefas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT,
            descricao TEXT,
            concluida INTEGER
        );
    """
    // Conecta, executa o comando e fecha a conexão automaticamente (.use)
    conectar().use { conexao ->
        val statement = conexao.createStatement()
        statement.execute(sql)
    }
}

fun adicionarTarefa(nome: String, descricao: String) {
    val sql = "INSERT INTO Tarefas (nome, descricao, concluida) VALUES (?, ?, 0)"

    conectar().use { conexao ->
        val prepare = conexao.prepareStatement(sql)
        prepare.setString(1, nome)
        prepare.setString(2, descricao)
        prepare.executeUpdate()
        println("✅ Tarefa salva no Banco de Dados com sucesso!")
    }
}

fun listarTarefas() {
    val sql = "SELECT * FROM Tarefas"

    conectar().use { conexao ->
        val statement = conexao.createStatement()
        val resultado = statement.executeQuery(sql)

        println("\n--- LISTA DO BANCO DE DADOS ---")
        while (resultado.next()) {
            val id = resultado.getInt("id")
            val nome = resultado.getString("nome")
            val desc = resultado.getString("descricao")
            val concluida = resultado.getInt("concluida") == 1 // 1 é true, 0 é false

            val check = if (concluida) "[x]" else "[ ]"
            println("ID $id - $check $nome ($desc)")
        }
    }
}

fun concluirTarefa(id: Int) {
    val sql = "UPDATE Tarefas SET concluida = 1 WHERE id = ?"

    conectar().use { conexao ->
        val prepare = conexao.prepareStatement(sql)
        prepare.setInt(1, id)
        val linhasAfetadas = prepare.executeUpdate()

        if (linhasAfetadas > 0) {
            println("✅ Tarefa $id atualizada para CONCLUÍDA!")
        } else {
            println("❌ ID não encontrado.")
        }
    }
}