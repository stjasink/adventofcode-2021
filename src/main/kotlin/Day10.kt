import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-10.txt")
    val solver = Day10()
    runAndTime(solver, input)
}

class Day10 : Solver {

    override fun part1(input: List<String>): Long {
        return input.map { line ->
            findUnmatchedScore(line)
        }.sumOf { it }
    }

    override fun part2(input: List<String>): Long {
        val incompleteLines = input.filter { findUnmatchedScore(it) == 0L }
        val scores = incompleteLines.map { line ->
            findCompletionScore(line)
        }
        return scores.sorted()[scores.size / 2]
    }

    private fun findCompletionScore(line: String): Long {
        val openers = mutableListOf<Char>()
        line.toCharArray().forEach { c ->
            when (c) {
                '(', '[', '{', '<' -> openers.add(c)
                ')' -> if (openers.removeLast() != '(') { throw IllegalStateException("Oops") }
                ']' -> if (openers.removeLast() != '[') { throw IllegalStateException("Oops") }
                '}' -> if (openers.removeLast() != '{') { throw IllegalStateException("Oops") }
                '>' -> if (openers.removeLast() != '<') { throw IllegalStateException("Oops") }
            }
        }

        return openers
            .reversed()
            .map { c ->
                when (c) {
                    '(' -> 1L
                    '[' -> 2L
                    '{' -> 3L
                    '<' -> 4L
                    else -> throw IllegalStateException("Unexpected char $c")
                }
            }.reduce { acc, i -> (acc * 5) + i }
    }

    private fun findUnmatchedScore(line: String): Long {
        val openers = mutableListOf<Char>()
        line.toCharArray().forEach { c ->
            when (c) {
                '(', '[', '{', '<' -> openers.add(c)
                ')' -> if (openers.removeLast() != '(') return 3L
                ']' -> if (openers.removeLast() != '[') return 57L
                '}' -> if (openers.removeLast() != '{') return 1197L
                '>' -> if (openers.removeLast() != '<') return 25137L
            }
        }
        return 0
    }
}
