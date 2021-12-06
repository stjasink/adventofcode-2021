import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun part1Test() {
        val input = """
            3,4,3,1,2
        """.trimIndent().split('\n')

        val answer = Day06().part1(input)
        assertEquals(5934L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            3,4,3,1,2
        """.trimIndent().split('\n')

        val answer = Day06().part2(input)
        assertEquals(26984457539L, answer)
    }
}
