import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day17Test {

    @Test
    fun part1TestPlot1() {
        assertTrue(Day17().plotLaunch(7, 2, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part1TestPlot2() {
        assertTrue(Day17().plotLaunch(6, 3, Day17.Target(20..30, -10..-5)).first)
    }
    @Test
    fun part1TestPlot3() {
        assertTrue(Day17().plotLaunch(9, 0, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part1TestPlot4() {
        assertFalse(Day17().plotLaunch(17, -4, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part2TestPlot1() {
        assertTrue(Day17().plotLaunch(7, 6, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part2TestPlot2() {
        assertTrue(Day17().plotLaunch(7, 7, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part2TestPlot3() {
        assertTrue(Day17().plotLaunch(7, 8, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part2TestPlot4() {
        assertTrue(Day17().plotLaunch(7, 9, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part2TestPlot5() {
        assertFalse(Day17().plotLaunch(5, 7, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part2TestPlot6() {
        assertFalse(Day17().plotLaunch(5, 8, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part2TestPlot7() {
        assertFalse(Day17().plotLaunch(5, 9, Day17.Target(20..30, -10..-5)).first)
    }

    @Test
    fun part1Test() {
        val input = """
            target area: x=20..30, y=-10..-5
        """.trimIndent().split('\n')

        val answer = Day17().part1(input)
        assertEquals(45L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            target area: x=20..30, y=-10..-5
        """.trimIndent().split('\n')

        val answer = Day17().part2(input)
        assertEquals(112L, answer)
    }
}
