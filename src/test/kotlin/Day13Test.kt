import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day13Test {

    @Test
    fun part1Test() {
        val input = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0

            fold along y=7
            fold along x=5
        """.trimIndent().split('\n')

        val answer = Day13().part1(input)
        assertEquals(17L, answer)
    }

    @Disabled
    @Test
    fun part2Test() {
        val input = """
        """.trimIndent().split('\n')

        val answer = Day13().part2(input)
        assertEquals(0L, answer)
    }
}
