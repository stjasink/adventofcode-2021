import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {

    @Test
    fun part1TestLiteralPacket() {
        val input = "110100101111111000101000"

        val answer = Day16().parsePacket(input, 0)
        assertEquals(6, answer.version)
        assertEquals(4, answer.type)
        assertEquals(2021, answer.number!!)
    }

    @Test
    fun part1TestOperatorPacketLength0() {
        val input = "00111000000000000110111101000101001010010001001000000000"

        val answer = Day16().parsePacket(input, 0)
        assertEquals(1, answer.version)
        assertEquals(6, answer.type)
        assertEquals(2, answer.subPackets.size)

        assertEquals(10, answer.subPackets[0].number!!)
        assertEquals(20, answer.subPackets[1].number!!)
    }

    @Test
    fun part1TestOperatorPacketLength1() {
        val input = "11101110000000001101010000001100100000100011000001100000"

        val answer = Day16().parsePacket(input, 0)
        assertEquals(7, answer.version)
        assertEquals(3, answer.type)
        assertEquals(3, answer.subPackets.size)

        assertEquals(1, answer.subPackets[0].number!!)
        assertEquals(2, answer.subPackets[1].number!!)
        assertEquals(3, answer.subPackets[2].number!!)
    }

    @Test
    fun part1Test1() {
        val input = """
            8A004A801A8002F478
        """.trimIndent().split('\n')

        val answer = Day16().part1(input)
        assertEquals(16L, answer)
    }

    @Test
    fun part1Test2() {
        val input = """
            620080001611562C8802118E34
        """.trimIndent().split('\n')

        val answer = Day16().part1(input)
        assertEquals(12L, answer)
    }

    @Test
    fun part1Test3() {
        val input = """
            C0015000016115A2E0802F182340
        """.trimIndent().split('\n')

        val answer = Day16().part1(input)
        assertEquals(23L, answer)
    }

    @Test
    fun part1Test4() {
        val input = """
            A0016C880162017C3686B18A3D4780
        """.trimIndent().split('\n')

        val answer = Day16().part1(input)
        assertEquals(31L, answer)
    }

    @Test
    fun part2Test() {
        val input = """
        """.trimIndent().split('\n')

        val answer = Day16().part2(input)
        assertEquals(0L, answer)
    }
}
