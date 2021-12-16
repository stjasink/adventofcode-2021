import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-16.txt")
    val solver = Day16()
    runAndTime(solver, input)
}

class Day16 : Solver {

    override fun part1(input: List<String>): Long {
        val bits = input.first().toCharArray().map { hex ->
            hex.toString().toInt(16).toString(2).padStart(4, '0')
        }.joinToString("")

        val allPackets = mutableListOf<Packet>()
        var startPos = 0
        // could have up to 3 zeroes at the end for padding
        while (startPos < (bits.length - 7)) {
            val packet = parsePacket(bits, startPos)
            allPackets.add(packet)
            startPos += packet.size
        }

        var sumOfVersions = 0
        fun addVersions(packets: List<Packet>) {
            packets.forEach { packet ->
                sumOfVersions += packet.version
                addVersions(packet.subPackets)
            }
        }
        addVersions(allPackets)

        return sumOfVersions.toLong()

    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    fun parsePacket(bits: String, startBit: Int): Packet  {
        val version = bits.substring(startBit, startBit + 3).toInt(2)
        val type = bits.substring(startBit + 3, startBit + 6).toInt(2)
        val restOfBits = bits.substring(startBit + 6)

//        println("$version $type $restOfBits")

        if (type == 4) {
            val (number, length) = parseNumberFromBits(restOfBits)
            return Packet(version, type, number, 6 + length, emptyList())
        } else {
            val lengthType = restOfBits.first()
            if (lengthType == '0') {
                val lengthBits = restOfBits.drop(1).take(15)
                val length = lengthBits.toInt(2)
                var subPacketLengths = 0
                val subPackets = mutableListOf<Packet>()
                while (subPacketLengths < length) {
                    val subPacket = parsePacket(restOfBits, 16 + subPacketLengths)
                    subPackets.add(subPacket)
                    subPacketLengths += subPacket.size
                }
                val thisPacketLength = 6 + 16 + subPacketLengths
                return Packet(version, type, null, thisPacketLength, subPackets)
            } else {
                val numSubPacketBits = restOfBits.drop(1).take(11)
                val numSubPackets = numSubPacketBits.toInt(2)
                var subPacketLengths = 0
                val subPackets = mutableListOf<Packet>()
                for (subPacketNum in 0 until numSubPackets) {
                    val subPacket = parsePacket(restOfBits, 12 + subPacketLengths)
                    subPackets.add(subPacket)
                    subPacketLengths += subPacket.size
                }
                val thisPacketLength = 6 + 12 + subPacketLengths
                return Packet(version, type, null, thisPacketLength, subPackets)
            }

        }

    }

    private fun parseNumberFromBits(bits: String): Pair<Long, Int> {
        val numberBits = StringBuilder()
        var bitsUsed = 0
        bits.windowed(5, 5).forEach {
            val leadingBit = it.first()
            val digitBits = it.substring(1)
            numberBits.append(digitBits)
            bitsUsed += 5
            if (leadingBit == '0') {
                return Pair(numberBits.toString().toLong(2), bitsUsed)
            }
        }
        throw IllegalStateException("Did not find end of number in $bits")
    }

    data class Packet(
        val version: Int,
        val type: Int,
        val number: Long?,
        val size: Int,
        val subPackets: List<Packet>
    )
}
