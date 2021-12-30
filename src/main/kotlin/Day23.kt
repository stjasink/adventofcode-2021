import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = loadInput("day-23.txt")
    val solver = Day23()
    runAndTime(solver, input)
}

class Day23 : Solver {

    override fun part1(input: List<String>): Long {
        val startState = parseStartState(input)
        val energyUsed = findEndStateWithLowestEnergy(startState)
        return energyUsed.toLong()
    }

    override fun part2(input: List<String>): Long {
        val extraLine1 = "  #D#C#B#A#"
        val extraLine2 = "  #D#B#A#C#"
        val unfoldedInput = input.take(3) + extraLine1 + extraLine2 + input.takeLast(2)
        val startState = parseStartState(unfoldedInput)
        val energyUsed = findEndStateWithLowestEnergy(startState)
        return energyUsed.toLong()
    }

    fun findEndStateWithLowestEnergy(startState: Set<Amphipod>): Int {
        var lowestEnergyFound = Int.MAX_VALUE

        fun doMoves(state: Set<Amphipod>) {
//            state.print()
            val moves = state.allPossibleMoves()
            moves.forEach { (pod, newPos) ->
                val newPod = pod.moveTo(newPos)
                val newState = (state - pod) + newPod
                // no point continuing if we have already used more energy than a previous end state
                val newStateEnergy = newState.totalEnergyUsed()
                if (newStateEnergy >= lowestEnergyFound) {
                    return
                }
                if (newState.allInDestRooms()) {
                    lowestEnergyFound = newStateEnergy
                    println("Found end state with energy $newStateEnergy")
                } else {
                    doMoves(newState)
                }
            }
        }

        doMoves(startState)

        return lowestEnergyFound
    }

    fun Set<Amphipod>.allInDestRooms(): Boolean {
        val eachInDestRoom = this.map { it.isInDestRoom() }
        return !eachInDestRoom.contains(false)
    }

    fun Set<Amphipod>.totalEnergyUsed(): Int {
        return this.sumOf { it.energyUsed() }
    }

    fun Set<Amphipod>.allPossibleMoves(): List<Pair<Amphipod, Position>> {
        return this.flatMap { pod ->
            pod.possibleMoves(this).map { move ->
                Pair(pod, move)
            }
        }
    }

    fun Set<Amphipod>.print() {
        println("#############")
        print("#")
        for (x in 0 .. 10) {
            print(firstOrNull { it.pos == Position(x, 0) }?.type ?: ".")
        }
        println("#")

        print("###")
        print(firstOrNull { it.pos == Position(2, 1) }?.type ?: ".")
        print("#")
        print(firstOrNull { it.pos == Position(4, 1) }?.type ?: ".")
        print("#")
        print(firstOrNull { it.pos == Position(6, 1) }?.type ?: ".")
        print("#")
        print(firstOrNull { it.pos == Position(8, 1) }?.type ?: ".")
        println("###")

        print("  #")
        print(firstOrNull { it.pos == Position(2, 2) }?.type ?: ".")
        print("#")
        print(firstOrNull { it.pos == Position(4, 2) }?.type ?: ".")
        print("#")
        print(firstOrNull { it.pos == Position(6, 2) }?.type ?: ".")
        print("#")
        print(firstOrNull { it.pos == Position(8, 2) }?.type ?: ".")
        println("#")

        println("  #########")
        println()
    }

    data class Amphipod (
        val type: AmphipodType,
        val pos: Position,
        val moveCount: Int) {

        fun isInDestRoom(): Boolean {
            return pos.y > 0 && pos.x == type.destRoomX
        }

        fun moveTo(newPos: Position): Amphipod {
            val numMoves = (pos.x - newPos.x).absoluteValue + (pos.y - newPos.y).absoluteValue
            return copy(pos = newPos, moveCount = moveCount + numMoves)
        }

        fun energyUsed(): Int {
            return moveCount * type.energyPerMove
        }

        private fun isInCorridor(): Boolean {
            return pos.y == 0
        }

        fun possibleMoves(state: Set<Amphipod>): List<Position> {
            // two types of moves to consider, of which only one will apply:
            // 1) move from starting room into corridor, to any location that is not blocked or outside a room
            // 2) move from corridor into destination room, to the deepest possible location

            return if (isInCorridor()) {
                // check if destination room is available and is empty or contains only the same type
                val podsInDestRoom = state.filter { it.pos.x == type.destRoomX }
                if (podsInDestRoom.isEmpty()) {
                    // if empty then go to the bottom if there is a route to the room entrance
                    val xBetweenPodAndRoom = min(pos.x, type.destRoomX) .. max(pos.x, type.destRoomX)
                    val podsInTheWay = state.filter { it.pos.y == 0 && it.pos.x in xBetweenPodAndRoom } - this
                    if (podsInTheWay.isEmpty()) {
                        listOf(Position(type.destRoomX, 2))
                    } else {
                        emptyList()
                    }
                } else if (podsInDestRoom.size == 1 && podsInDestRoom.first().type == type) {
                    // if there is one and it's the same type then assume it is at the bottom, so go to the top if there is a route to the room entrance
                    val xBetweenPodAndRoom = min(pos.x, type.destRoomX) .. max(pos.x, type.destRoomX)
                    val podsInTheWay = state.filter { it.pos.y == 0 && it.pos.x in xBetweenPodAndRoom } - this
                    if (podsInTheWay.isEmpty()) {
                        listOf(Position(type.destRoomX, 1))
                    } else {
                        emptyList()
                    }
                } else {
                    // otherwise it can't move into the destination room
                    emptyList()
                }
            } else if (isInDestRoom()) {
                // if in destination room then we don't want to move unless we are at the top and another type is below
                if (pos.y == 1 && state.first { it.pos.x == pos.x && it.pos.y == 2 }.type != type) {
                    val leftCorridorLimit = state.filter { it.pos.y == 0 && it.pos.x < pos.x }.maxOfOrNull { it.pos.x + 1 } ?: 0
                    val leftCorridorPositions = (leftCorridorLimit .. pos.x).map { Position(it, 0) }
                    val rightCorridorLimit = state.filter { it.pos.y == 0 && it.pos.x > pos.x }.minOfOrNull { it.pos.x - 1 } ?: 10
                    val rightCorridorPositions = (pos.x .. rightCorridorLimit).map { Position(it, 0) }
                    val positionsNotOutsideDoors = (leftCorridorPositions + rightCorridorPositions) - AmphipodType.values().map { Position(it.destRoomX, 0) }
                    positionsNotOutsideDoors
                } else {
                    emptyList()
                }
            } else {
                // if at the bottom and there is another pod on top then can't move
                if (pos.y == 2 && state.firstOrNull { it.pos.x == pos.x && it.pos.y == 1 } != null) {
                    emptyList()
                } else {
                    // possible to move to each spot in the corridor that is not outside a room or blocked by another pod
                    val leftCorridorLimit = state.filter { it.pos.y == 0 && it.pos.x < pos.x }.maxOfOrNull { it.pos.x + 1 } ?: 0
                    val leftCorridorPositions = (leftCorridorLimit .. pos.x).map { Position(it, 0) }
                    val rightCorridorLimit = state.filter { it.pos.y == 0 && it.pos.x > pos.x }.minOfOrNull { it.pos.x - 1 } ?: 10
                    val rightCorridorPositions = (pos.x .. rightCorridorLimit).map { Position(it, 0) }
                    val positionsNotOutsideDoors = (leftCorridorPositions + rightCorridorPositions) - AmphipodType.values().map { Position(it.destRoomX, 0) }
                    positionsNotOutsideDoors
                }
            }
        }


    }

    data class Position(
        val x: Int,
        val y: Int
    )

    enum class AmphipodType(val energyPerMove: Int, val destRoomX: Int) {
        A(1, 2),
        B(10, 4),
        C(100, 6),
        D(1000, 8)
    }

    private fun parseStartState(input: List<String>): Set<Amphipod> {
        val startState = mutableSetOf<Amphipod>()

        input.drop(1).forEachIndexed { y, line ->
            line.drop(1).forEachIndexed { x, char ->
                when (char) {
                    'A', 'B', 'C', 'D' -> {
                        val pos = Position(x, y)
                        val pod = Amphipod(AmphipodType.valueOf(char.toString()), pos, 0)
                        startState.add(pod)
                    }
                }
            }
        }
        return startState
    }
}
