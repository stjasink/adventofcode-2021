package common

fun loadInput(filename: String): List<String> {
    return ClassLoader.getSystemResourceAsStream(filename)
        .bufferedReader()
        .lineSequence()
        .toList()
}
