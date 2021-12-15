import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15Test {

    @Test
    fun part1Test() {
        val input = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent().split('\n')

        val answer = Day15().part1(input)
        assertEquals(40L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent().split('\n')

        val answer = Day15().part2(input)
        assertEquals(315L, answer)
    }
}
