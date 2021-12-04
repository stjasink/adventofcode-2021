import common.Solver
import common.loadInput
import common.runAndTime


fun main() {
    val input = loadInput("day-04.txt")
    val solver = Day04()
    runAndTime(solver, input)
}

class Day04 : Solver {

    override fun part1(input: List<String>): Long {
        val numbersToCall = input.first().split(",").map { it.toInt() }
        val boards = parseBoards(input)

        numbersToCall.forEach { numberToCall ->
            boards.forEach { board ->
                board.markNumber(numberToCall)
                if (board.hasWon()) {
                    return numberToCall.toLong() * board.countUnMarked().toLong()
                }
            }
        }

        throw IllegalStateException("No winner found")
    }

    override fun part2(input: List<String>): Long {
        val numbersToCall = input.first().split(",").map { it.toInt() }
        val boards = parseBoards(input)

        var numWinners = 0
        numbersToCall.forEach { numberToCall ->
            boards.forEach { board ->
                if (!board.hasWon()) {
                    board.markNumber(numberToCall)
                    if (board.hasWon()) {
                        numWinners += 1
                        if (numWinners == boards.size) {
                            return numberToCall.toLong() * board.countUnMarked().toLong()
                        }
                    }
                }
            }
        }

        throw IllegalStateException("No non-winner found")
    }

    private fun parseBoards(input: List<String>): MutableList<Board> {
        val boards = mutableListOf<Board>()
        var currentBoard = Board(mutableListOf())
        (input + "").drop(2).forEach { line ->
            if (line.isBlank()) {
                boards.add(currentBoard)
                currentBoard = Board(mutableListOf())
            } else {
                val numbersFromLineString = line.trim().split(Regex(" +"))
                val numbersFromLine = numbersFromLineString.map { Num(it.toInt(), false) }
                currentBoard.rows.add(numbersFromLine)
            }
        }
        return boards
    }

    data class Board(
        val rows: MutableList<List<Num>>
    ) {
        fun markNumber(numberToMark: Int) {
            rows.forEach { row ->
                row.forEach { num ->
                    if (num.number == numberToMark) {
                        num.marked = true
                    }
                }
            }
        }

        fun hasWon(): Boolean {
            fun allMarked(checkRows: List<List<Num>>): Boolean {
                checkRows.forEach { checkRow ->
                    if (checkRow.filter { it.marked }.size == checkRow.size) {
                        return true
                    }
                }
                return false
            }

            if (allMarked(rows)) {
                return true
            }

            val columns = transpose(rows)
            if (allMarked(columns)) {
                return true
            }

            return false
        }

        fun countUnMarked(): Int {
            var counter = 0
            rows.forEach { row ->
                row.forEach { num ->
                    if (!num.marked) {
                        counter += num.number
                    }
                }
            }
            return counter
        }

    }

    data class Num(
        val number: Int,
        var marked: Boolean
    )

}

fun <T> transpose(table: List<List<T>>): List<List<T>> {
    val ret: MutableList<List<T>> = ArrayList()
    for (i in 0 until table[0].size) {
        val col: MutableList<T> = ArrayList()
        for (row in table) {
            col.add(row[i])
        }
        ret.add(col)
    }
    return ret
}
