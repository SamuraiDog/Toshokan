package com.dog.samurai.toshokan.view.helper

class Fraction(numerator: Int, denominator: Int) {
    private var numerator = -1
    private var denominator = -1

    init {
        this.numerator = numerator
        this.denominator = denominator
    }

    private fun reduction() {
        val greatestCommonDivisor = culcDivisor(numerator, denominator)
        numerator /= greatestCommonDivisor
        denominator /= greatestCommonDivisor
    }

    private fun addition(fraction: Fraction) {
        numerator = fraction.denominator * numerator + fraction.numerator * denominator
        denominator *= fraction.denominator
        reduction()
    }

    fun subtraction(fraction: Fraction) {
        numerator = fraction.denominator * numerator - fraction.numerator * denominator
        denominator *= fraction.denominator
        reduction()

    }

    fun multiplication(fraction: Fraction) {
        denominator *= fraction.denominator
        numerator *= fraction.numerator
        reduction()
    }

    fun division(fraction: Fraction) {
        denominator *= fraction.numerator
        numerator *= fraction.denominator
        reduction()
    }

    fun getNumerator(): Int {
        return numerator
    }

    fun getDenominator(): Int {
        return denominator
    }


    private fun culcDivisor(a: Int, b: Int): Int {
        var aa: Int = a
        var bb = b
        while (bb > 0) {
            val c = aa
            aa = bb
            bb = c % bb

        }
        return aa
    }

}