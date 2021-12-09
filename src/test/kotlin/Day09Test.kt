import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09Test {

    @Test
    fun part1Test() {
        val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent().split('\n')

        val answer = Day09().part1(input)
        assertEquals(15L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent().split('\n')

        val answer = Day09().part2(input)
        assertEquals(1134L, answer)
    }
}
