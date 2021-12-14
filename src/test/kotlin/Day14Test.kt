import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun part1Test() {
        val input = """
            NNCB

            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """.trimIndent().split('\n')

        val answer = Day14().part1(input)
        assertEquals(1588L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
            NNCB

            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """.trimIndent().split('\n')

        val answer = Day14().part2(input)
        assertEquals(2188189693529L, answer)
    }
}
