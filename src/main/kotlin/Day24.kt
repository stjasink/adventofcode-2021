import common.Solver
import common.runAndTime
import common.loadInput
import java.math.BigDecimal

fun main() {
    val input = loadInput("day-24.txt")
    val solver = Day24()
    runAndTime(solver, input)
}

class Day24 : Solver {

    override fun part1(input: List<String>): Long {
//        compileProgram(input)
//
//        val startNum = 99999999999999L
//        val numString = startNum.toString().toCharArray().map { it.digitToInt().toLong() }
//        for (num in startNum downTo 9999999999999L) {
//            if (num % 1_000_000 == 0L) {
//                println(num)
//            }
////            val numString = num.toString().toCharArray().map { it.digitToInt().toLong() }
//            if (!numString.contains(0L)) {
//                val resultZ = runCompiledProgramOnData(numString.toMutableList())
//                if (resultZ == 0L) {
//                    return num
//                }
//            }
//        }

        val num = calcPart1()
        println(num)

        return 0L
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    fun compileProgram(instructions: List<String>) {
        println("""
            var w = 0L
            var x = 0L
            var y = 0L
            var z = 0L
        """.trimIndent())

        instructions.forEach { ins ->
            when {
                ins.startsWith("inp") -> {
                    val var1 = ins.split(" ")[1].first()
                    println("$var1 = data.removeFirst()")
                }
                ins.startsWith("add") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: params[2].first()
                    println("$var1 += $val2")
                }
                ins.startsWith("mul") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: params[2].first()
                    println("$var1 *= $val2")
                }
                ins.startsWith("div") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: params[2].first()
                    println("$var1 /= $val2")
                }
                ins.startsWith("mod") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: params[2].first()
                    println("$var1 %= $val2")
                }
                ins.startsWith("eql") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: params[2].first()
                    println("$var1 = if ($var1 == $val2) 1L else 0L")
                } else -> {
                throw IllegalStateException("Did not understand $ins")
            }
            }
        }

    }

    fun runCompiledProgramOnDataOptimised(data: MutableList<Long>): Long {
        var w = 0L
        var x = 0L
        var y = 0L
        var z = 0L

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 1
        x += 12
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 4
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 1
        x += 15
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 11
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 1
        x += 11
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 7
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 26
        x += -14
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 2
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 1
        x += 12
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 11
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 26
        x += -10
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 13
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 1
        x += 11
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 9
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 1
        x += 13
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 12
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 26
        x += -7
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 6
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 1
        x += 10
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 2
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 26
        x += -2
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 11
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 26
        x += -1
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 12
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 26
        x += -4
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 3
        y *= x
        z += y

        w = data.removeFirst()
        x *= 0
        x += z
        x %= 26
        z /= 26
        x += -12
        x = if (x == w) 1L else 0L
        x = if (x == 0L) 1L else 0L
        y *= 0
        y += 25
        y *= x
        y += 1
        z *= y
        y *= 0
        y += w
        y += 13
        y *= x
        z += y

        return z
    }

    fun runProgramOnData(instructions: List<String>, dataString: String): Map<Char, Long> {
        val vars = mutableMapOf(
            'w' to 0L,
            'x' to 0L,
            'y' to 0L,
            'z' to 0L
        )
        val data = dataString.toCharArray().toMutableList()


        instructions.forEach { ins ->
            when {
                ins.startsWith("inp") -> {
                    val var1 = ins.split(" ")[1].first()
                    vars[var1] = data.removeFirst().digitToInt().toLong()
                }
                ins.startsWith("add") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: vars[params[2].first()]!!
                    vars[var1] = vars[var1]!! + val2
                }
                ins.startsWith("mul") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: vars[params[2].first()]!!
                    vars[var1] = vars[var1]!! * val2
                }
                ins.startsWith("div") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: vars[params[2].first()]!!
                    vars[var1] = vars[var1]!! / val2
                }
                ins.startsWith("mod") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: vars[params[2].first()]!!
                    vars[var1] = vars[var1]!! % val2
                }
                ins.startsWith("eql") -> {
                    val params = ins.split(" ")
                    val var1 = params[1].first()
                    val val2 = params[2].toLongOrNull() ?: vars[params[2].first()]!!
                    vars[var1] = if (vars[var1]!! == val2) 1L else 0L
                } else -> {
                    throw IllegalStateException("Did not understand $ins")
                }
            }
        }

        return vars
    }



    fun calcPart1(): BigDecimal {

        val stepVars = listOf(
            Triple(1, 12, 4),
            Triple(1, 15, 11),
            Triple(1, 11, 7),
            Triple(26, -14, 2),
            Triple(1, 12, 11),
            Triple(26, -10, 13),
            Triple(1, 11, 9),
            Triple(1, 13, 12),
            Triple(26, -7, 6),
            Triple(1, 10, 2),
            Triple(26, -2, 11),
            Triple(26, -1, 12),
            Triple(26, -4, 3),
            Triple(26, -12, 13)
        )


        fun calcNextZ(var1: Int, var2: Int, var3: Int, prevZ: Int, digit: Int): Int {
            var w = digit
            var x = 0
            var y = 0
            var z = prevZ

            x = z % 26

            z /= var1
            x += var2


//            eql x w
//            eql x 0
//            mul y 0
//            add y 25
//            mul y x
//            add y 1
//            mul z y
//            mul y 0
//            add y w
//            add y 4
//            mul y x
//            add z y



            return z
        }

        val foundNumbers = mutableListOf<BigDecimal>()
        val foundDigits = mutableListOf<Int>()

        fun findMatchingDigitAndPreviousZ(var1: Int, var2: Int, var3: Int, needThisZ: Int): Pair<Int, Int> {
            for (tryDigit in 1..9) {
                for (tryPrevZ in 0 .. 25) {
                    if (calcNextZ(var1, var2, var3, tryPrevZ, tryDigit) == needThisZ) {
                        return Pair(tryDigit, tryPrevZ)
                    }
                }
            }
            throw IllegalStateException("Found no match")
        }

        var needZ = 0
        for (digitNum in 13 downTo 0) {
            val (var1, var2, var3) = stepVars[digitNum]
            val (newDigit, newZ) = findMatchingDigitAndPreviousZ(var1, var2, var3, needZ)
            foundDigits.add(newDigit)
            needZ = newZ
        }

        val num = foundDigits.reversed().joinToString("").toBigDecimal()

        return num
    }
}
