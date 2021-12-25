import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-24.txt")
    val solver = Day24()
    runAndTime(solver, input)
}

class Day24 : Solver {

    override fun part1(input: List<String>): Long {
        val triples = parse(input)
        val mappings = createMapping(triples)
        val bits = calculateMax(triples, mappings)
        println(bits.joinToString(""))
        return 0L
    }

    override fun part2(input: List<String>): Long {
        val triples = parse(input)
        val mappings = createMapping(triples)
        val bits = calculateMin(triples, mappings)
        println(bits.joinToString(""))
        return 0L
    }

    private fun calculateMin(triples: List<Triple>, mapping: List<Mapping>): List<Int> {
        val bits = mutableListOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0)

        for (map in mapping) {
            val index1 = map.a
            val index2 = map.b
            val c = triples[index1].c
            val b = triples[index2].b

            var x = 1
            while (x <= 9) {
                if ((1..9).contains(x + c + b)) {
                    break
                }
                x += 1
            }
            bits[index1] = x
            bits[index2] = x + c + b
        }
        return bits
    }

    private fun calculateMax(triples: List<Triple>, mapping: List<Mapping>): List<Int> {
        val bits = mutableListOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0)

        for (map in mapping) {
            val index1 = map.a
            val index2 = map.b
            val c = triples[index1].c
            val b = triples[index2].b

            var x = 9
            while (x > 0) {
                if ((1..9).contains(x + c + b)) {
                    break
                }
                x -= 1
            }
            bits[index1] = x
            bits[index2] = x + c + b
        }

        return bits
    }

    private fun parse(input: List<String>): List<Triple> {
        val triples = mutableListOf<Triple>()
        for (i in input.indices step 18) {
            val a = input[i+4].split(" ").last().toInt()
            val b = input[i+5].split(" ").last().toInt()
            val c = input[i+15].split(" ").last().toInt()
            triples.add(Triple(a, b, c))
        }
        return triples
    }

    private fun createMapping(triples: List<Triple>): List<Mapping> {
        val doubles = mutableListOf<Mapping>()
        val indexes = mutableListOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13)

        while (indexes.isNotEmpty()) {
            for (i in indexes.indices) {
                val a = triples[indexes[i]].a
                val b = triples[indexes[i+1]].a
                if (a == 1 && b == 26) {
                    doubles.add(Mapping(indexes[i], indexes[i+1]))
                    indexes.removeAt(i)
                    indexes.removeAt(i)
                    break
                }
            }
        }

        return doubles
    }

    data class Triple (
        val a: Int,
        val b: Int,
        val c: Int
    )

    data class Mapping (
        val a: Int,
        val b: Int
    )
}
