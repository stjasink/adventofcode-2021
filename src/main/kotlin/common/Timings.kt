package common

import java.time.Duration
import java.time.LocalDateTime

fun timeCode(code: () -> Unit): Duration {
    for (i in 1..10) {
        code()
    }
    val timingsInNanos = mutableListOf<Long>()
    for (i in 1..10) {
        val start1 = LocalDateTime.now()
        code()
        timingsInNanos.add(Duration.between(start1, LocalDateTime.now()).toNanos())
    }
    return Duration.ofNanos(timingsInNanos.average().toLong())
}

fun runAndTime(solver: Solver, input: List<String>) {
    val part1Answer = solver.part1(input)
    println("Part 1 answer: $part1Answer")
    val part2Answer = solver.part2(input)
    println("Part 2 answer: $part2Answer")

//    println("Timings")
//    val part1Time = timeCode { solver.part1(input) }
//    println("Part 1: ${part1Time} ${part1Time.toMillis()}ms / ${part1Time.toNanos() / 1000}μs")
//    val part2Time = timeCode { solver.part2(input) }
//    println("Part 2: $part2Time ${part2Time.toMillis()}ms / ${part2Time.toNanos() / 1000}μs")
}
