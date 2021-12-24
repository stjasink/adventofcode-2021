import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-21.txt")
    val solver = Day21()
    runAndTime(solver, input)
}

class Day21 : Solver {

    override fun part1(input: List<String>): Long {
        val player1Start = input[0].last().digitToInt()
        val player2Start = input[1].last().digitToInt()
        val game = Game(player1Start, player2Start)

        do {
            if (game.player1Move()) {
                return game.player2Total.toLong() * game.dieRollCount.toLong()
            }
            if (game.player2Move()) {
                return game.player1Total.toLong() * game.dieRollCount.toLong()
            }
        } while (true)

    }

    override fun part2(input: List<String>): Long {
        val player1Start = input[0].last().digitToInt()
        val player2Start = input[1].last().digitToInt()

        val player1Sequence = sequenceFrom(player1Start)
        val player2Sequence = sequenceFrom(player2Start)

        var player1Pos = 1
        var player2Pos = 1

        var player1ValueCounts = mapOf<Int, Long>()
        var player2ValueCounts = mapOf<Int, Long>()

        var player1WinCount = 0L
        var player2WinCount = 0L

        val rollingChances = listOf(
            3 to 1,
            4 to 3,
            5 to 6,
            6 to 7,
            7 to 6,
            8 to 3,
            9 to 1)

        do {
            // player 1
            val newPlayer1ValueCounts = mutableMapOf<Int, Long>()
            rollingChances.forEach {
                val (roll, chance) = it
                val num = player1Sequence[roll]
                player1ValueCounts.forEach { currentNum, currentCount ->
                    newPlayer1ValueCounts[currentNum + num] = chance * currentCount
                }
            }
            val player1Won = newPlayer1ValueCounts.filter { it.key >= 21 }
            player1WinCount += player1Won.values.sum()
            player1ValueCounts = newPlayer1ValueCounts.filter { it.key < 21 }

            // player 2
            val newPlayer2ValueCounts = mutableMapOf<Int, Long>()
            rollingChances.forEach {
                val (roll, chance) = it
                val num = player2Sequence[roll]
                player2ValueCounts.forEach { currentNum, currentCount ->
                    newPlayer2ValueCounts[currentNum + num] = chance * currentCount
                }
            }
            val player2Won = newPlayer2ValueCounts.filter { it.key >= 21 }
            player2WinCount += player2Won.values.sum()
            player2ValueCounts = newPlayer2ValueCounts.filter { it.key < 21 }
        } while (player1ValueCounts.isNotEmpty() && player2ValueCounts.isNotEmpty())

        return maxOf(player1WinCount, player2WinCount)
    }

    fun sequenceFrom(start: Int): List<Int> {
        return (-1..10_000).map {
            (it + start) % 10 + 1
        }
    }

    class Game(player1Start: Int, player2Start: Int) {
        var dieVal = 1
        var dieRollCount = 0
        var player1Pos = player1Start
        var player1Total = 0
        var player2Pos = player2Start
        var player2Total = 0

        fun player1Move(): Boolean {
            val dice = rollThree()
            player1Pos += dice.sum()
            if (player1Pos > 10) {
                player1Pos %= 10
            }
            player1Total += player1Pos
//            println("Player 1 rolls ${dice[0]}+${dice[1]}+${dice[2]} and moves to space $player1Pos for a total score of $player1Total.")
            return player1Total >= 1000
        }

        fun player2Move(): Boolean {
            val dice = rollThree()
            player2Pos += dice.sum()
            if (player2Pos > 10) {
                player2Pos %= 10
            }
            player2Total += player2Pos
//            println("Player 2 rolls ${dice[0]}+${dice[1]}+${dice[2]} and moves to space $player2Pos for a total score of $player2Total.")
            return player2Total >= 1000
        }

        fun rollThree(): List<Int> {
            return listOf(rollOne(), rollOne(), rollOne())
        }

        fun rollOne(): Int {
            val rolledVal = dieVal
            dieVal += 1
            dieRollCount += 1
            if (dieVal == 101) {
                dieVal = 1
            }
            return rolledVal
        }
    }
}
