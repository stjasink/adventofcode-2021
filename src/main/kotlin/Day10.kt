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
        var score = 0L
        input.forEach { line ->
            score += findUnmatchedScore(line)
        }
        return score
    }

    override fun part2(input: List<String>): Long {
        val scores = mutableListOf<Long>()
        val incompleteLines = input.filter { findUnmatchedScore(it) == 0L }

        incompleteLines.forEach { line ->
            val score = findCompletionScore(line)
            scores.add(score)
        }

        val middleIndex = scores.size / 2
        val sortedScores = scores.sorted()
        return sortedScores[middleIndex]
    }

    private fun findCompletionScore(line: String): Long {
        val openers = ArrayDeque<Char>()
        line.toCharArray().forEach { c ->
            when (c) {
                '(', '[', '{', '<' -> openers.addLast(c)
                ')' -> if (openers.removeLast() != '(') { throw IllegalStateException("Oops") }
                ']' -> if (openers.removeLast() != '[') { throw IllegalStateException("Oops") }
                '}' -> if (openers.removeLast() != '{') { throw IllegalStateException("Oops") }
                '>' -> if (openers.removeLast() != '<') { throw IllegalStateException("Oops") }
            }
        }

        var score = 0L
        while (openers.isNotEmpty()) {
            score *= 5
            when (openers.removeLast()) {
                '(' -> score += 1
                '[' -> score += 2
                '{' -> score += 3
                '<' -> score += 4
            }
        }

        return score
    }

    private fun findUnmatchedScore(line: String): Long {
        val openers = ArrayDeque<Char>()
        line.toCharArray().forEach { c ->
            when (c) {
                '(', '[', '{', '<' -> openers.addLast(c)
                ')' -> if (openers.removeLast() != '(') {  return 3L }
                ']' -> if (openers.removeLast() != '[') { return 57L }
                '}' -> if (openers.removeLast() != '{') {   return 1197L }
                '>' -> if (openers.removeLast() != '<') { return 25137L }
            }
        }
        return 0
    }
}
