import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day24Test {



    @Test
    fun part2Test() {
        val input = """
        """.trimIndent().split('\n')

        val answer = Day24().part2(input)
        assertEquals(0L, answer)
    }
}
