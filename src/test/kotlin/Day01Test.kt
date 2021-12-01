import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun part1Test() {
        val input = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent().split('\n')

        val answer = Day01().part1(input)
        assertEquals(7L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            199
            200
            208
            210
            200
            207
            240
            269
            260
            263
        """.trimIndent().split('\n')

        val answer = Day01().part2(input)
        assertEquals(5L, answer)
    }
}
