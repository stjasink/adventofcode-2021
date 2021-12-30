import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day23Test {

    @Test
    fun part1Test() {
        val input = """
            #############
            #...........#
            ###B#C#B#D###
              #A#D#C#A#
              #########
        """.trimIndent().split('\n')

        val answer = Day23().part1(input)
        assertEquals(12521L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            #############
            #...........#
            ###B#C#B#D###
              #A#D#C#A#
              #########
        """.trimIndent().split('\n')

        val answer = Day23().part2(input)
        assertEquals(44169L, answer)
    }
}
