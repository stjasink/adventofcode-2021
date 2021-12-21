import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day21Test {

    @Test
    fun part1Test() {
        val input = """
            Player 1 starting position: 4
            Player 2 starting position: 8
        """.trimIndent().split('\n')

        val answer = Day21().part1(input)
        assertEquals(739785L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
        """.trimIndent().split('\n')

        val answer = Day21().part2(input)
        assertEquals(0L, answer)
    }
}
