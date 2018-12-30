package hr.smilebacksmile.codewars.kthbit

import kotlin.math.pow

fun main(args : Array<String>) {
    val result = killKthBit(0, 3)
    println("$result")

}

fun killKthBit(n: Int, k: Int): Int {

    return  n.toString(2)
            .toMutableList()
            .let{chs -> chs.take((chs.size - k).takeIf{it >= 0} ?: 0  )  + "0" + chs.takeLast((k-1).takeIf{it >= 0} ?:0)}
            .asReversed().mapIndexed{i, v -> v.toString().toInt() * 2f.pow(i).toInt()}
            .fold(0) { sum, next -> sum + next }
}

fun killKthBit2(n: Int, k: Int): Int {
    // this works only on positive integers (but by specification, n is a positive integer)
    // notice also this String is immutable (just like in Java)
    // and remember that in Kotlin there are no implicit conversions
    val b:String = n.toString(2)
    // convert immutable string to mutable collection
    val chars = b.toMutableList()
    // we could use check of indexes
    val firstPart = chars.take(chars.size - k)
    val secondPart = chars.takeLast(k-1)
    val full = firstPart + "0" + secondPart
    var sum = 0
    full.reversed().forEachIndexed {
        i, v -> sum += v.toString().toInt() * 2f.pow(i).toInt()
    }
    return sum
}