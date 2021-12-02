import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
    fun part1Test() {
        val input = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent().split('\n')

        val answer = Day02().part1(input)
        assertEquals(150L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent().split('\n')

        val answer = Day02().part2(input)
        assertEquals(900L, answer)
    }
}
