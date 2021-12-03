import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun part1Test() {
        val input = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
        """.trimIndent().split('\n')

        val answer = Day03().part1(input)
        assertEquals(198L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
        """.trimIndent().split('\n')

        val answer = Day03().part2(input)
        assertEquals(230L, answer)
    }
}
