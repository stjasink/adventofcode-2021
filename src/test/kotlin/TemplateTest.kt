import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TemplateTest {

    @Test
    fun part1Test() {
        val input = """
        """.trimIndent().split('\n')

        val answer = Template().part1(input)
        assertEquals(0L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
        """.trimIndent().split('\n')

        val answer = Template().part2(input)
        assertEquals(0L, answer)
    }
}
