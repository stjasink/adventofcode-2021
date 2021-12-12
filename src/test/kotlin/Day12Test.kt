import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun part1Test1() {
        val input = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent().split('\n')

        val answer = Day12().part1(input)
        assertEquals(10L, answer)
    }

    @Test
    fun part1Test2() {
        val input = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
        """.trimIndent().split('\n')

        val answer = Day12().part1(input)
        assertEquals(19L, answer)
    }

    @Test
    fun part2Test1() {
        val input = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent().split('\n')

        val answer = Day12().part2(input)
        assertEquals(36L, answer)
    }

    @Test
    fun part2Test2() {
        val input = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
        """.trimIndent().split('\n')

        val answer = Day12().part2(input)
        assertEquals(103L, answer)
    }
}
