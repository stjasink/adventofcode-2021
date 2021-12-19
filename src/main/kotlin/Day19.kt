import common.Solver
import common.runAndTime
import common.loadInput

fun main() {
    val input = loadInput("day-19.txt")
    val solver = Day19()
    runAndTime(solver, input)
}

class Day19 : Solver {

    override fun part1(input: List<String>): Long {
        val scanners = parseInput(input)
        val orientedScanners = MutableList<Scanner?>(scanners.size) { null }
        orientedScanners[0] = scanners[0]

        while (orientedScanners.filterNotNull().size < scanners.size) {
            println(orientedScanners.filterNotNull().size.toString() + " / " + scanners.size)
            orientedScanners.filterNotNull().forEach { scanner1 ->
                scanners.forEach { scanner2 ->
                    if (scanner1.num != scanner2.num) {
                        val match = compareScanners(scanner1, scanner2)
                        if (match != null) {
                            orientedScanners[match.num] = match
                        }
                    }
                }
            }
        }

        val allBeacons = orientedScanners.flatMap { it!!.beacons }.toSet()
        return allBeacons.size.toLong()
    }

    override fun part2(input: List<String>): Long {
        return 0L
    }

    fun compareScanners(scanner1: Scanner, scanner2: Scanner): Scanner? {
        for (orientation in 0..23) {
            val scanner2TestOrientation = scanner2.orient(orientation)
            val match = scanner1.beaconsOverlap(scanner2TestOrientation)
            if (match.first) {
                return scanner2TestOrientation.offset(match.second!!)
            }
        }
        return null
    }

    fun parseInput(input: List<String>): List<Scanner> {
        val inputPerScanner: MutableList<MutableList<String>> = mutableListOf()
        input.forEach { line ->
            if (line.startsWith("--- scanner ")) {
                inputPerScanner.add(mutableListOf())
            }
            if (line.isNotBlank()) {
                inputPerScanner.last().add(line)
            }
        }

        val scanners = inputPerScanner.map { scannerInput ->
            Scanner.from(scannerInput)
        }

        return scanners
    }

    data class Scanner (
        val num: Int,
        val beacons: Set<Point>) {

        fun beaconsOverlap(other: Scanner): Pair<Boolean, Vector?> {
            val relativeSpacingAndPoints1 = beaconRelativeSpacingsAndPoints()
            val relativeSpacingAndPoints2 = other.beaconRelativeSpacingsAndPoints()
            val relativeSpacing1 = relativeSpacingAndPoints1.map { it.first }
            val relativeSpacing2 = relativeSpacingAndPoints2.map { it.first }
            val overlapping = relativeSpacing1.intersect(relativeSpacing2)
            if (overlapping.size >= 12) {
                val firstOverlap = overlapping.first()
                val point1 = relativeSpacingAndPoints1.find { it.first == firstOverlap }!!.second
                val point2 = relativeSpacingAndPoints2.find { it.first == firstOverlap }!!.second
                return Pair(true, point1.distanceTo(point2))
            } else {
                return Pair(false, null)
            }
        }

//        fun rotX(times: Int): Scanner {
//            return if (times == 0) this else (0 until times).fold(this) { acc, _ -> acc.rotX() }
//        }
//
//        fun rotY(times: Int): Scanner {
//            return if (times == 0) this else (0 until times).fold(this) { acc, _ -> acc.rotY() }
//        }
//
//        fun rotZ(times: Int): Scanner {
//            return if (times == 0) this else (0 until times).fold(this) { acc, _ -> acc.rotZ() }
//        }
//
//        fun rotX(): Scanner {
//            // (x, y, z) -> (x, z, -y)
//            val rotatedBeacons = beacons.map { beacon ->
//                beacon.copy(y = beacon.z, z = -beacon.y)
//            }
//            return Scanner(num, rotatedBeacons.toSet())
//        }
//
//        fun rotY(): Scanner {
//            // (x, y, z) -> (-z, y, x)
//            val rotatedBeacons = beacons.map { beacon ->
//                beacon.copy(x = -beacon.z, z = beacon.x)
//            }
//            return Scanner(num, rotatedBeacons.toSet())
//        }
//
//        fun rotZ(): Scanner {
//            // (x, y, z) -> (y, -x, z)
//            val rotatedBeacons = beacons.map { beacon ->
//                beacon.copy(x = beacon.y, y = -beacon.x)
//            }
//            return Scanner(num, rotatedBeacons.toSet())
//        }
//
//        fun flipX(): Scanner {
//            val flippedBeacons = beacons.map { beacon ->
//                beacon.copy(y = -beacon.y, z = -beacon.z)
//            }
//            return Scanner(num, flippedBeacons.toSet())
//        }
//
//        fun flipY(): Scanner {
//            val flippedBeacons = beacons.map { beacon ->
//                beacon.copy(x = -beacon.x, z = -beacon.z)
//            }
//            return Scanner(num, flippedBeacons.toSet())
//        }
//
//        fun flipZ(): Scanner {
//            val flippedBeacons = beacons.map { beacon ->
//                beacon.copy(x = -beacon.x, y = -beacon.y)
//            }
//            return Scanner(num, flippedBeacons.toSet())
//        }

        companion object {
            fun from(lines: List<String>): Scanner {
                val scannerNum = lines.first().split(" ")[2].toInt()
                val beacons = lines.drop(1).map { beaconLine ->
                    val (x, y, z) = beaconLine.split(",").map { it.toInt() }
                    Point(x, y, z)
                }
                return Scanner(scannerNum, beacons.toSet())
            }
        }

        fun orient(orientation: Int) = Scanner(num, beacons.map { it.orient(orientation) }.toSet())

        fun beaconRelativeSpacingsAndPoints(): List<Pair<Vector, Point>> {
            return beacons.flatMap { beacon1 ->
                beacons.filterNot { it == beacon1 }.map { beacon2 ->
                    beacon1.distanceTo(beacon2) to beacon1
                }
            }
        }

        fun offset(by: Vector): Scanner {
            return Scanner(num, beacons.map { it.offset(by) }.toSet())
        }

        fun findOffset(): Set<Vector> {
            return beacons.flatMap { beacon1 ->
                beacons.filterNot { it == beacon1 }.map { beacon2 ->
                    beacon1.distanceTo(beacon2)
                }
            }.toSet()
        }

        fun print() {
            println("--- scanner $num ---")
            beacons.forEach {
                println("${it.x},${it.y},${it.z}")
            }
            println()
        }

    }

    data class Point (
        val x: Int,
        val y: Int,
        val z: Int): Comparable<Point> {
        fun orient0() = Point(x, y, z)
        fun orient1() = Point(-z, y, x)
        fun orient2() = Point(-x, y, -z)
        fun orient3() = Point(z, y, -x)
        fun orient4() = Point(-x, -y, z)
        fun orient5() = Point(z, -y, x)
        fun orient6() = Point(x, -y, -z)
        fun orient7() = Point(-z, -y, -x)
        fun orient8() = Point(-y, x, z)
        fun orient9() = Point(-z, x, -y)
        fun orient10() = Point(y, x, -z)
        fun orient11() = Point(z, x, y)
        fun orient12() = Point(y, -x, z)
        fun orient13() = Point(z, -x, -y)
        fun orient14() = Point(-y, -x, -z)
        fun orient15() = Point(-z, -x, y)
        fun orient16() = Point(y, z, x)
        fun orient17() = Point(-x, z, y)
        fun orient18() = Point(-y, z, -x)
        fun orient19() = Point(x, z, -y)
        fun orient20() = Point(-y, -z, x)
        fun orient21() = Point(x, -z, y)
        fun orient22() = Point(y, -z, -x)
        fun orient23() = Point(-x, -z, -y)

        val orientators = mapOf(
            0 to { p: Point -> p.orient0() },
            1 to { p: Point -> p.orient1() },
            2 to { p: Point -> p.orient2() },
            3 to { p: Point -> p.orient3() },
            4 to { p: Point -> p.orient4() },
            5 to { p: Point -> p.orient5() },
            6 to { p: Point -> p.orient6() },
            7 to { p: Point -> p.orient7() },
            8 to { p: Point -> p.orient8() },
            9 to { p: Point -> p.orient9() },
            10 to { p: Point -> p.orient10() },
            11 to { p: Point -> p.orient11() },
            12 to { p: Point -> p.orient12() },
            13 to { p: Point -> p.orient13() },
            14 to { p: Point -> p.orient14() },
            15 to { p: Point -> p.orient15() },
            16 to { p: Point -> p.orient16() },
            17 to { p: Point -> p.orient17() },
            18 to { p: Point -> p.orient18() },
            19 to { p: Point -> p.orient19() },
            20 to { p: Point -> p.orient20() },
            21 to { p: Point -> p.orient21() },
            22 to { p: Point -> p.orient22() },
            23 to { p: Point -> p.orient23() }
        )

        fun orient(orientation: Int) = orientators[orientation]!!(this)

        fun distanceTo(other: Point) = Vector(x - other.x, y - other.y, z - other.z)

        fun offset(by: Vector) = Point(x + by.x, y + by.y, z + by.z)

        override fun compareTo(other: Point): Int {
            val xCompare = x.compareTo(other.x)
            return if (xCompare == 0) {
                val yCompare = y.compareTo(other.y)
                if (yCompare == 0) {
                    z.compareTo(other.z)
                } else yCompare
            } else xCompare
        }
    }

    // same structure as Point but used for different things
    data class Vector (
        val x: Int,
        val y: Int,
        val z: Int)
}
