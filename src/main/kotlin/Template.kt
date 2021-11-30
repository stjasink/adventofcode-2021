import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("template.txt")
    val solver = Template()
    runAndTime(solver, input)
}

class Template : Solver {

    override fun part1(input: List<String>): Long {
        return 0L
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }
}
