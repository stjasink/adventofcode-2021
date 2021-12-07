import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day07Test {

    @Test
    fun part1Test() {
        val input = """
            16,1,2,0,4,2,7,1,2,14
        """.trimIndent().split('\n')

        val answer = Day07().part1(input)
        assertEquals(37L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            16,1,2,0,4,2,7,1,2,14
        """.trimIndent().split('\n')

        val answer = Day07().part2(input)
        assertEquals(168L, answer)
    }
}
