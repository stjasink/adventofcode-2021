import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day24Test {

    @Test
    fun part1Test1() {
        val input = """
            inp z
            inp x
            mul z 3
            eql z x
        """.trimIndent().split('\n')

        val answer1 = Day24().runProgramOnData(input, "26")
        assertEquals(1L, answer1['z'])

        val answer2 = Day24().runProgramOnData(input, "24")
        assertEquals(0L, answer2['z'])
    }

    @Test
    fun part1Test2() {
        val input = """
            inp w
            add z w
            mod z 2
            div w 2
            add y w
            mod y 2
            div w 2
            add x w
            mod x 2
            div w 2
            mod w 2
        """.trimIndent().split('\n')

        val answer1 = Day24().runProgramOnData(input, "9")
        assertEquals(1L, answer1['w'])
        assertEquals(0L, answer1['x'])
        assertEquals(0L, answer1['y'])
        assertEquals(1L, answer1['z'])
    }

    @Test
    fun part2Test() {
        val input = """
        """.trimIndent().split('\n')

        val answer = Day24().part2(input)
        assertEquals(0L, answer)
    }
}
