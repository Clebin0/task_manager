import java.sql.DriverManager
import java.util.Scanner

data class Tarefa(
    val id: Int,
    val nome: String,
    val descricao: String,
    val concluida: Boolean,
)

class TarefaRepository(
    private val jdbcUrl: String = defaultDatabaseUrl(),
) {
    fun criarTabela() {
        val sql = """
            CREATE TABLE IF NOT EXISTS Tarefas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL CHECK(length(trim(nome)) BETWEEN 1 AND 100),
                descricao TEXT NOT NULL DEFAULT '' CHECK(length(descricao) <= 500),
                concluida INTEGER NOT NULL DEFAULT 0 CHECK(concluida IN (0, 1))
            )
        """.trimIndent()
        conectar().use { conexao ->
            conexao.createStatement().use { statement ->
                statement.execute(sql)
            }
        }
    }

    fun adicionar(nome: String, descricao: String) {
        val nomeNormalizado = nome.trim()
        val descricaoNormalizada = descricao.trim()

        require(nomeNormalizado.isNotEmpty()) { "O nome da tarefa é obrigatório." }
        require(nomeNormalizado.length <= 100) {
            "O nome deve ter no máximo 100 caracteres."
        }
        require(descricaoNormalizada.length <= 500) {
            "A descrição deve ter no máximo 500 caracteres."
        }

        val sql = "INSERT INTO Tarefas (nome, descricao, concluida) VALUES (?, ?, 0)"
        conectar().use { conexao ->
            conexao.prepareStatement(sql).use { statement ->
                statement.setString(1, nomeNormalizado)
                statement.setString(2, descricaoNormalizada)
                statement.executeUpdate()
            }
        }
    }

    fun listar(): List<Tarefa> {
        val tarefas = mutableListOf<Tarefa>()
        val sql = "SELECT id, nome, descricao, concluida FROM Tarefas ORDER BY id"

        conectar().use { conexao ->
            conexao.createStatement().use { statement ->
                statement.executeQuery(sql).use { resultado ->
                    while (resultado.next()) {
                        tarefas += Tarefa(
                            id = resultado.getInt("id"),
                            nome = resultado.getString("nome"),
                            descricao = resultado.getString("descricao"),
                            concluida = resultado.getInt("concluida") == 1,
                        )
                    }
                }
            }
        }

        return tarefas
    }

    fun concluir(id: Int): Boolean {
        require(id > 0) { "O ID da tarefa deve ser positivo." }

        val sql = "UPDATE Tarefas SET concluida = 1 WHERE id = ?"
        return conectar().use { conexao ->
            conexao.prepareStatement(sql).use { statement ->
                statement.setInt(1, id)
                statement.executeUpdate() > 0
            }
        }
    }

    private fun conectar() = DriverManager.getConnection(jdbcUrl)

    companion object {
        private fun defaultDatabaseUrl(): String {
            val path = System.getProperty("task.database.path")
                ?: System.getenv("TASK_DATABASE_PATH")
                ?: "banco_tarefas.db"
            return "jdbc:sqlite:$path"
        }
    }
}

fun main() {
    val repository = TarefaRepository()
    repository.criarTabela()

    Scanner(System.`in`).use { scanner ->
        var escolhaUsuario: Int? = null

        while (escolhaUsuario != 4) {
            println("\n--- TASK MANAGER ---")
            println("1. Adicionar tarefa")
            println("2. Listar tarefas")
            println("3. Concluir tarefa")
            println("4. Sair")
            print("Escolha uma opção: ")

            val entradaMenu = if (scanner.hasNextLine()) scanner.nextLine() else break
            escolhaUsuario = entradaMenu.trim().toIntOrNull()
            if (escolhaUsuario == null) {
                println("Informe uma opção numérica válida.")
                continue
            }

            when (escolhaUsuario) {
                1 -> {
                    print("Nome: ")
                    val nome = if (scanner.hasNextLine()) scanner.nextLine() else break
                    print("Descrição: ")
                    val descricao = if (scanner.hasNextLine()) scanner.nextLine() else break

                    runCatching { repository.adicionar(nome, descricao) }
                        .onSuccess { println("Tarefa salva com sucesso.") }
                        .onFailure { println("Não foi possível salvar: ${it.message}") }
                }

                2 -> {
                    val tarefas = repository.listar()
                    if (tarefas.isEmpty()) {
                        println("Nenhuma tarefa cadastrada.")
                    } else {
                        tarefas.forEach { tarefa ->
                            val status = if (tarefa.concluida) "[x]" else "[ ]"
                            println("ID ${tarefa.id} - $status ${tarefa.nome} (${tarefa.descricao})")
                        }
                    }
                }

                3 -> {
                    repository.listar().forEach { tarefa ->
                        val status = if (tarefa.concluida) "[x]" else "[ ]"
                        println("ID ${tarefa.id} - $status ${tarefa.nome}")
                    }
                    print("ID da tarefa para concluir: ")
                    val entradaId = if (scanner.hasNextLine()) scanner.nextLine() else break
                    val id = entradaId.trim().toIntOrNull()
                    if (id == null) {
                        println("Informe um ID numérico válido.")
                    } else if (repository.concluir(id)) {
                        println("Tarefa $id marcada como concluída.")
                    } else {
                        println("Tarefa não encontrada.")
                    }
                }

                4 -> println("Encerrando o Task Manager.")
                else -> println("Opção inválida.")
            }
        }
    }
}
