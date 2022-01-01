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

    private fun findEndStateWithLowestEnergy(startState: Set<Amphipod>): Int {
        var lowestEnergyFound = Int.MAX_VALUE
        val statesSeenWithEnergy = mutableMapOf<Set<Amphipod>, Int>()

        fun doMoves(state: Set<Amphipod>, depth: Int) {
            statesSeenWithEnergy.put(state, state.totalEnergyUsed())
//            state.print()
            val moves = state.allPossibleMoves()
                // prioritise by how close a pod moves to its destination room
                .sortedBy { (it.first.type.destRoomX - it.second.x).absoluteValue }
                // prioritise more expensive first
//                .sortedByDescending { it.first.type.energyPerMove }
                // prioritise those that go into a room
//                .sortedByDescending { it.second.y }
            moves.forEach { (pod, newPos) ->
                val newPod = pod.moveTo(newPos)
                val newState = (state - pod) + newPod
                // no point continuing if we have already used more energy than the current lowest end state
                val newStateEnergy = newState.totalEnergyUsed()
                if (newStateEnergy >= lowestEnergyFound) {
                    return@forEach
                }
                // no point continuing if we have previously been in this same arrangement with lower energy
                statesSeenWithEnergy[newState]?.apply {
                    if (newStateEnergy >= this) {
                        return@forEach
                    }
                }
                if (newState.allInDestRooms()) {
                    lowestEnergyFound = newStateEnergy
                    println("Found end state with energy $newStateEnergy and $depth moves")
                } else {
                    doMoves(newState, depth + 1)
                }
            }
        }

        doMoves(startState, 1)

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

        val maxY = size / 4
        for (y in 1 .. maxY) {
            print (if (y == 1) "###" else "  #")
            print(firstOrNull { it.pos == Position(2, y) }?.type ?: ".")
            print("#")
            print(firstOrNull { it.pos == Position(4, y) }?.type ?: ".")
            print("#")
            print(firstOrNull { it.pos == Position(6, y) }?.type ?: ".")
            print("#")
            print(firstOrNull { it.pos == Position(8, y) }?.type ?: ".")
            println (if (y == 1) "###" else "#")
        }

        println("  #########")
        println()
    }

    class Amphipod (
        val type: AmphipodType,
        val pos: Position,
        val moveCount: Int) {

        fun isInDestRoom(): Boolean {
            return pos.y > 0 && pos.x == type.destRoomX
        }

        fun moveTo(newPos: Position): Amphipod {
            val numMoves = (pos.x - newPos.x).absoluteValue + (pos.y - newPos.y).absoluteValue
            return Amphipod(type = type, pos = newPos, moveCount = moveCount + numMoves)
        }

        fun energyUsed(): Int {
            return moveCount * type.energyPerMove
        }

        private fun isInCorridor(): Boolean {
            return pos.y == 0
        }

        fun possibleMoves(state: Set<Amphipod>): List<Position> {
            // types of moves to consider, of which only one will apply:
            // 1) move from starting room into corridor, to any location that is not blocked or outside a room
            // 2) move from corridor into destination room, to the deepest possible location
            // 3) move from destination room into corridor, if there is a different type trapped below

            val maxY = state.size / 4
            return if (isInCorridor()) {
                // check if destination room contains no pods of other types
                val podsInDestRoom = state.filter { it.pos.x == type.destRoomX }
                if (podsInDestRoom.none { it.type != type }) {
                    // check if there is a route to the room entrance
                    val xBetweenPodAndRoom = min(pos.x, type.destRoomX) .. max(pos.x, type.destRoomX)
                    val podsInTheWay = state.filter { it.pos.y == 0 && it.pos.x in xBetweenPodAndRoom } - this
                    if (podsInTheWay.isEmpty()) {
                        // assume that pods have filled up from the bottom, so find the lowest space we can go to
                        val destY = podsInDestRoom.minOfOrNull { it.pos.y - 1 } ?: maxY
                        listOf(Position(type.destRoomX, destY))
                    } else {
                        emptyList()
                    }
                } else {
                    // otherwise it can't move into the destination room
                    emptyList()
                }
            } else if (isInDestRoom()) {
                // if in destination room then move only if another type is below and there is nothing above
                if (state.none { it.pos.x == pos.x && it.pos.y == pos.y - 1 }) {
                    val lowerPodsInDestRoom = state.filter { it.pos.x == pos.x && it.pos.y > pos.y }
                    if (lowerPodsInDestRoom.any { it.type != type }) {
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
                    emptyList()
                }
            } else {
                // if at the bottom and there is another pod on top then can't move
                val higherPodsInDestRoom = state.filter { it.pos.x == pos.x && it.pos.y < pos.y }
                if (higherPodsInDestRoom.isEmpty()) {
                    // possible to move to each spot in the corridor that is not outside a room or blocked by another pod
                    val leftCorridorLimit = state.filter { it.pos.y == 0 && it.pos.x < pos.x }.maxOfOrNull { it.pos.x + 1 } ?: 0
                    val leftCorridorPositions = (leftCorridorLimit .. pos.x).map { Position(it, 0) }
                    val rightCorridorLimit = state.filter { it.pos.y == 0 && it.pos.x > pos.x }.minOfOrNull { it.pos.x - 1 } ?: 10
                    val rightCorridorPositions = (pos.x .. rightCorridorLimit).map { Position(it, 0) }
                    val positionsNotOutsideDoors = (leftCorridorPositions + rightCorridorPositions) - AmphipodType.values().map { Position(it.destRoomX, 0) }
                    positionsNotOutsideDoors
                } else {
                    emptyList()
                }
            }
        }

        override fun hashCode(): Int {
            return type.hashCode() * 63 + pos.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            val otherPod = other as Amphipod
            return type == otherPod.type && pos == otherPod.pos
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
