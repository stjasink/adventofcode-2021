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
            if (line.startsWith("forward")) {
                position += line.substringAfter("forward ").toLong()
            } else if (line.startsWith("down")) {
                depth += line.substringAfter("down ").toLong()
            } else if (line.startsWith("up")) {
                depth -= line.substringAfter("up ").toLong()
            }
        }
        return position * depth
    }

    override fun part2(input: List<String>): Long {
        var position = 0L
        var depth = 0L
        var aim = 0L
        input.forEach { line ->
            if (line.startsWith("forward")) {
                val amount = line.substringAfter("forward ").toLong()
                position += amount
                depth += amount * aim
            } else if (line.startsWith("down")) {
                aim += line.substringAfter("down ").toLong()
            } else if (line.startsWith("up")) {
                aim -= line.substringAfter("up ").toLong()
            }
        }
        return position * depth
    }
}
