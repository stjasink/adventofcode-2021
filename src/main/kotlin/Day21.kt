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

        var playerCounts = mapOf(Pair(Player(player1Start, 0), Player(player2Start, 0)) to 1L)

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
            val newPlayer1Counts = mutableMapOf<Pair<Player, Player>, Long>()
            playerCounts.forEach { (players, count) ->
                rollingChances.forEach { (roll, chance) ->
                    val newPos = if (players.first.pos + roll > 10) ((players.first.pos + roll) % 10) else (players.first.pos + roll)
                    val newTotal = players.first.total + newPos
                    val newPlayer = Player(newPos, newTotal)
                    val newPlayerPair = Pair(newPlayer, players.second)
                    if (newPlayer1Counts[newPlayerPair] == null) {
                        newPlayer1Counts[newPlayerPair] = count * chance
                    } else {
                        newPlayer1Counts[newPlayerPair] = newPlayer1Counts[newPlayerPair]!! + (count * chance)
                    }
                }
            }
            val player1NewlyWon = newPlayer1Counts.filter { it.key.first.total >= 21 }
            player1WinCount += player1NewlyWon.values.sum()
            playerCounts = newPlayer1Counts.filter { it.key.first.total < 21 }

            // player 2
            val newPlayer2Counts = mutableMapOf<Pair<Player, Player>, Long>()
            playerCounts.forEach { (players, count) ->
                rollingChances.forEach { (roll, chance) ->
                    val newPos = if (players.second.pos + roll > 10) ((players.second.pos + roll) % 10) else (players.second.pos + roll)
                    val newTotal = players.second.total + newPos
                    val newPlayer = Player(newPos, newTotal)
                    val newPlayerPair = Pair(players.first, newPlayer)
                    if (newPlayer2Counts[newPlayerPair] == null) {
                        newPlayer2Counts[newPlayerPair] = count * chance
                    } else {
                        newPlayer2Counts[newPlayerPair] = newPlayer2Counts[newPlayerPair]!! + (count * chance)
                    }
                }
            }
            val player2NewlyWon = newPlayer2Counts.filter { it.key.second.total >= 21 }
            player2WinCount += player2NewlyWon.values.sum()
            playerCounts = newPlayer2Counts.filter { it.key.second.total < 21 }

        } while (playerCounts.isNotEmpty())

        // 444356092776315

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

    data class Player(
        val pos: Int,
        val total: Int
    )
}
