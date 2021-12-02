import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-02.txt")
    val solver = Day02()
    runAndTime(solver, input)
}

class Day02 : Solver {

    override fun part1(input: List<String>): Long {
        var position = 0L
        var depth = 0L
        input.forEach { line ->
            val (instruction, value) = line.split(' ')
            when (instruction) {
                "forward" -> position += value.toLong()
                "down" -> depth += value.toLong()
                "up" -> depth -= value.toLong()
            }
        }
        return position * depth
    }

    override fun part2(input: List<String>): Long {
        var position = 0L
        var depth = 0L
        var aim = 0L
        input.forEach { line ->
            val (instruction, value) = line.split(' ')
            when (instruction) {
                "forward" -> {
                    position += value.toLong()
                    depth += value.toLong() * aim
                }
                "down" -> aim += value.toLong()
                "up" -> aim -= value.toLong()
            }
        }
        return position * depth
    }
}
