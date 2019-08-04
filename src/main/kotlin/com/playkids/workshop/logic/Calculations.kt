package com.playkids.workshop.logic

import com.playkids.workshop.model.Tweet

/**
 * @author Adriano Belfort (adriano.belfort@playkids.com) on 2019-08-03
 */

fun splitWords(string: String): List<String> {
    return string
        .split(" ")
}

fun cleanWordA(word: String) =
    word
        .replace(",", "")
        .replace("!", "")

fun cleanWordsA(words: List<String>) =
    words
        .map { it.replace(",", "") }
        .map { it.replace("!", "") }

fun cleanWordB(word: String) =
    word.replace(Regex("\\W+"), "")

fun cleanWords(words: List<String>) =
    words.map { word -> cleanWordB(word) }

fun cleanWords(tweet: String) = cleanWords(splitWords(tweet))

fun countWords(string: String): Map<String, Int> {
    return cleanWords(splitWords(string))
        .groupBy { it }
        .mapValues { it.value.count() }
}

fun countLetters(string: String): Int =
    cleanWords(splitWords(string))
        .fold(0) { acc, s -> acc + s.length }


fun letters(word: String) = word.toCharArray()

fun topTweeters(users: List<String>): Map<String, Int> {
    return users
        .groupBy { it }
        .mapValues { it.value.count() }
        .entries
        .sortedByDescending { it.value }
        .map { it.key to it.value }
        .toMap()
}

fun <T> topTweet(tweetLikes: List<Pair<T, Int>>): T? {
    return tweetLikes
        .maxBy { it.second }
        ?.first
}

fun getWordRanking(tweets: List<Tweet>): List<Pair<String, Int>> =
    tweets
        .map { countWords(it.text) }
        .flatMap { it.toList() }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, counts) ->
            counts.sum()
        }
        .toList()
        .sortedByDescending { it.second }
