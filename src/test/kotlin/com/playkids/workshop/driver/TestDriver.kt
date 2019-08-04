package com.playkids.workshop.driver

import com.playkids.workshop.logic.*

/**
 * @author Adriano Belfort (adriano.belfort@playkids.com) on 2019-08-03
 */

fun main() {
    val testString = "oi tudo bem? oi tudo legal? ah tudo sim e com vc? tudo ótimo!"

    println(countWords(testString))
    println(splitWords(testString))
    println(cleanWords(splitWords(testString)))
    println(countLetters(testString))
    println(countWords(testString))
    println(
        topTweeters(
            listOf(
                "joao",
                "maria",
                "joao",
                "cleber",
                "pedro",
                "joao",
                "maria",
                "joao"
            )
        )
    )
}