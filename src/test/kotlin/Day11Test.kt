import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun part1Test() {
        val input = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent().split('\n')

        val answer = Day11().part1(input)
        assertEquals(1656L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent().split('\n')

        val answer = Day11().part2(input)
        assertEquals(195L, answer)
    }
}
