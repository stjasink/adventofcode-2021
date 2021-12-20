import common.Solver
import common.runAndTime
import common.loadInput
import kotlin.random.Random

fun main() {
    val input = loadInput("day-18.txt")
    val solver = Day18()
    runAndTime(solver, input)
}

class Day18 : Solver {

    override fun part1(input: List<String>): Long {
        val total = addAll(input)
        return total.magnitude().toLong()
    }


    override fun part2(input: List<String>): Long {
        return 0L
    }


    fun addAll(input: List<String>): SnailNumber {
        val numbers = input.map { SnailNumber.from(it) }
        val total = numbers.drop(1).fold(numbers.first()) { acc, num ->
//            println("  ${acc.toSnailString()}")
//            println("+ ${num.toSnailString()}")
            val sum = acc.add(num)
//            println("= ${sum.toSnailString()}")
//            println()
            sum
        }
        return total
    }

}

data class SnailNumber(
    val leftVal: Int?,
    val left: SnailNumber?,
    val rightVal: Int?,
    val right: SnailNumber?,
    val depth: Int,
    val id: Int = Random.nextInt()
) {
    companion object {
        fun from(input: String) = numberFromString(input, 1)
    }

    fun add(other: SnailNumber): SnailNumber {
//        println(this.toSnailString())
//        println(this.toDepthString())
//
//        println(other.toSnailString())
//        println(other.toDepthString())
//

        val thisOneDeeper = this.addDepth()
//        println(thisOneDeeper.toSnailString())
//        println(thisOneDeeper.toDepthString())
        val otherOneDeeper = other.addDepth()
//        println(otherOneDeeper.toSnailString())
//        println(otherOneDeeper.toDepthString())
        val addedNumber = SnailNumber(null, thisOneDeeper, null, otherOneDeeper, 1)
//        println(addedNumber.toSnailString())
//        println(addedNumber.toDepthString())
        val reducedAddedNumber = addedNumber.reduce()
//        println(reducedAddedNumber.toSnailString())
//        println(reducedAddedNumber.toDepthString())
        return reducedAddedNumber
    }

    fun explode(): SnailNumber {
        var leftRegularNumber: Pair<SnailNumber, Char>? = null
        var rightRegularNumber: Pair<SnailNumber, Char>? = null
        var exploder: SnailNumber? = null

        // [[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]
        fun findExploder(number: SnailNumber) {
            if (exploder == null && (number.leftVal != null)) {
                leftRegularNumber = Pair(number, 'L')
            }

            if (number.depth == 4 && exploder == null) { // only find one exploder per turn
                if (number.left != null) {
                    exploder = number.left
                } else if (number.right != null) {
                    exploder = number.right
                }
            }

            number.left?.let { if (it != exploder) findExploder(it) }

            if (exploder == null && (number.rightVal != null)) {
                leftRegularNumber = Pair(number, 'R')
            }

            if (exploder != null && number.right != exploder && rightRegularNumber == null && number.leftVal != null) {
                rightRegularNumber = Pair(number, 'L')
            }

            number.right?.let { if (it != exploder) findExploder(it) }

            if (exploder != null && number != exploder && rightRegularNumber == null && number.rightVal != null) {
                rightRegularNumber = Pair(number, 'R')
            }
        }

        // [[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]
        fun doExploding(number: SnailNumber): SnailNumber {
            val newLeftVal = if (number == leftRegularNumber?.first && leftRegularNumber?.second == 'L') {
                number.leftVal!! + exploder!!.leftVal!!
            } else if (number == rightRegularNumber?.first && rightRegularNumber?.second == 'L') {
                number.leftVal!! + exploder!!.rightVal!!
            } else if (number.left == exploder) {
                0
            } else number.leftVal

            val newLeft = if (number.left == exploder) null else number.left?.let { doExploding(it) }

            val newRightVal = if (number == leftRegularNumber?.first && leftRegularNumber?.second == 'R') {
                number.rightVal!! + exploder!!.leftVal!!
            } else if (number == rightRegularNumber?.first && rightRegularNumber?.second == 'R') {
                number.rightVal!! + exploder!!.rightVal!!
            } else if (number.right == exploder) {
                0
            } else number.rightVal

            val newRight = if (number.right == exploder) null else number.right?.let { doExploding(it) }

            return SnailNumber(newLeftVal, newLeft, newRightVal, newRight, number.depth)
        }

        findExploder(this)
        return if (exploder != null) {
            doExploding(this)
        } else {
            this
        }
    }

    fun split(): SnailNumber {
        var splitter: SnailNumber? = null

        // [[[[7,7],[7,8]],[[9,5],[8,0]]],[[[9,10],20],[8,[9,0]]]]
        fun findSplitter(number: SnailNumber) {
            if (splitter == null) { // only bother looking if not already found
                number.left?.let { findSplitter(it) }
                // might have been found on left branch
                if (splitter == null && number.leftVal != null && number.leftVal > 9) {
                    splitter = number
                }
                if (splitter == null && number.rightVal != null && number.rightVal > 9) {
                    splitter = number
                }
                number.right?.let { findSplitter(it) }

            }
        }

        var doneSplit = false
        fun doSplitting(number: SnailNumber): SnailNumber {
            if (number == splitter) {
                // always try left first
                if (number.leftVal != null && number.leftVal > 9) {
                    val splitLeftVal = number.leftVal / 2
                    val splitRightVal = number.leftVal / 2 + (if (number.leftVal % 2 == 1) 1 else 0)
                    val newLeft = SnailNumber(splitLeftVal, null, splitRightVal, null, number.depth+1)
                    doneSplit = true
                    return SnailNumber(null, newLeft, number.rightVal, number.right, number.depth)
                } else  {
                    if (!(number.rightVal != null && number.rightVal > 9)) throw IllegalStateException("Expected rightVal to be splittable")
                    val splitLeftVal = number.rightVal / 2
                    val splitRightVal = number.rightVal / 2 + (if (number.rightVal % 2 == 1) 1 else 0)
                    val newRight = SnailNumber(splitLeftVal, null, splitRightVal, null, number.depth+1)
                    doneSplit = true
                    return SnailNumber(number.leftVal, number.left, null, newRight, number.depth)
                }
            } else {
                val newLeft = if (doneSplit) number.left else number.left?.let { doSplitting(it) }
                val newRight = if (doneSplit) number.right else number.right?.let { doSplitting(it) }
                return SnailNumber(number.leftVal, newLeft, number.rightVal, newRight, number.depth)
            }
        }

        findSplitter(this)
        return if (splitter != null) {
            doSplitting(this)
        } else {
            this
        }
    }

    private fun addDepth(): SnailNumber {
        return SnailNumber(leftVal, left?.addDepth(), rightVal, right?.addDepth(), depth + 1)
    }

    fun magnitude(): Int {
        val leftMag = leftVal ?: left!!.magnitude()
        val rightMag = rightVal ?: right!!.magnitude()
        return leftMag * 3 + rightMag * 2
    }

    private fun explodeOrSplit(): SnailNumber {
        val exploded = explode()
        if (exploded != this) {
            return exploded
        }
        return split()
    }

    fun reduce(): SnailNumber {
        var previousReduction = this
//        println(this.toSnailString())
//        println(this.toDepthString())
//        println()
        do {
            val thisReduction = previousReduction.explodeOrSplit()
//            println(thisReduction.toSnailString())
//            println(thisReduction.toDepthString())
//            println()
            if (thisReduction == previousReduction) {
                return thisReduction
            }
            previousReduction = thisReduction
        } while (true)
    }

    fun toSnailString(): String {
        val leftString = leftVal ?: left!!.toSnailString()
        val rightString = rightVal ?: right!!.toSnailString()
        return "[$leftString,$rightString]"
    }

    fun toDepthString(): String {
        val output = StringBuilder()
        output.append("[")
        if (left != null) output.append(left.toDepthString()) else if (leftVal!! > 9) output.append("  ") else output.append(" ")
        output.append(depth)
        if (right != null) output.append(right.toDepthString()) else if (rightVal!! > 9) output.append("  ") else output.append(" ")
        output.append("]")
        return output.toString()
    }
}

fun numberFromString(input: String, depth: Int): SnailNumber {

    fun splitOnTopLevelComma(input: String): Pair<String, String> {
        var openBracketCount = 0
        input.forEachIndexed { index, c ->
            when (c) {
                '[' -> openBracketCount += 1
                ']' -> openBracketCount -= 1
                ',' -> {
                    if (openBracketCount == 0) {
                        return Pair(input.substring(0, index), input.substring(index+1))
                    }
                }
            }
        }
        throw IllegalStateException("Did not find match")
    }

    val twoDigits = Regex("^\\[(\\d+),(\\d+)]")
    val firstDigit = Regex("^\\[(\\d+),(\\[.*)]\$")
    val lastDigit = Regex("^\\[(.*),(\\d+)]\$")

    if (twoDigits.matches(input)) {
        val matches = twoDigits.matchEntire(input)!!.groupValues
        return SnailNumber(matches[1].toInt(), null, matches[2].toInt(), null, depth)
    } else if (firstDigit.matches(input)) {
        val matches = firstDigit.matchEntire(input)!!.groupValues
        return SnailNumber(matches[1].toInt(), null, null, numberFromString(matches[2], depth+1), depth)
    } else if (lastDigit.matches(input)) {
        val matches = lastDigit.matchEntire(input)!!.groupValues
        return SnailNumber(null, numberFromString(matches[1], depth+1), matches[2].toInt(), null, depth)
    } else if (input.startsWith("[[")) {
        val inputWithoutBrackets = input.drop(1).dropLast(1)
        val (leftString, rightString) = splitOnTopLevelComma(inputWithoutBrackets)
        return SnailNumber(null, numberFromString(leftString, depth+1), null, numberFromString(rightString, depth+1), depth)
    } else {
        throw IllegalStateException("Not sure what is happening here: $input")
    }

}

