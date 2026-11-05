import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TarefaRepositoryTest {
    private lateinit var directory: Path
    private lateinit var repository: TarefaRepository

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        directory = Files.createTempDirectory("task-manager-test-")
        val database = directory.resolve("tasks.db")
        repository = TarefaRepository("jdbc:sqlite:$database")
        repository.criarTabela()
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
        if (Files.exists(directory)) {
            Files.walk(directory).use { paths ->
                paths.sorted(Comparator.reverseOrder()).forEach(Files::deleteIfExists)
            }
        }
    }

    @Test
    fun `creates lists and completes tasks`() {
        repository.adicionar("Estudar Kotlin", "Praticar CRUD com SQLite")

        val inserted = repository.listar()
        assertEquals(1, inserted.size)
        assertEquals("Estudar Kotlin", inserted[0].nome)
        assertEquals("Praticar CRUD com SQLite", inserted[0].descricao)
        assertFalse(inserted[0].concluida)

        assertTrue(repository.concluir(inserted[0].id))
        assertTrue(repository.listar().single().concluida)
        assertFalse(repository.concluir(999))
    }

    @Test
    fun `rejects empty and oversized user input`() {
        assertFailsWith<IllegalArgumentException> { repository.adicionar("  ", "Descrição") }
        assertFailsWith<IllegalArgumentException> { repository.adicionar("x".repeat(101), "") }
        assertFailsWith<IllegalArgumentException> { repository.adicionar("Tarefa", "x".repeat(501)) }
        assertTrue(repository.listar().isEmpty())
    }

    @Test
    fun `stores SQL-looking input as ordinary text`() {
        val input = "Tarefa'); DROP TABLE Tarefas; --"
        repository.adicionar(input, "Entrada de teste")

        assertEquals(input, repository.listar().single().nome)
        repository.adicionar("Outra tarefa", "")
        assertEquals(2, repository.listar().size)
    }

    @Test
    fun `rejects non-positive task identifiers`() {
        assertFailsWith<IllegalArgumentException> { repository.concluir(0) }
        assertFailsWith<IllegalArgumentException> { repository.concluir(-1) }
    }
}
