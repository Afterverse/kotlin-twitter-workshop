package com.playkids.workshop

import com.playkids.workshop.adapter.TwitterClient
import com.playkids.workshop.logic.getWordRanking
import com.playkids.workshop.logic.topTweet
import kotlin.math.max

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
fun main() {
    println("everything wired up!!")

    val user = TwitterClient.getLoggedUser()

    println(user)

    val tweets = TwitterClient.getHomeTweets(300)

    val filteredTweets = tweets.filter { it.retweetedTweet == null }
        .filter { it.quoteInformation == null }
        .filter { it.replyInfo == null }
        .toList()

    println("total tweets: ${filteredTweets.size}")
    println()

    val ranking = getWordRanking(filteredTweets)

    ranking
        .filter { it.first.isNotBlank() }
        .filter { !it.first.isConnective() }
        .subList(0, max(50, filteredTweets.size))
        .forEachIndexed { index, (word, count) ->
            println("Rank ${index+1}: '$word' written $count times")
        }

    println()
    topTweet(filteredTweets.map { it to it.favoriteCount })
        ?.run {
            println("Top tweet: '$text' from '@${relatedUser.screenName}' with $favoriteCount likes")
        }

    val userTweets = TwitterClient.getUserTweets("JulioMoreira_", 40)
    println("Tweets from @juliomoreira_: size: ${userTweets.size} | ${
        userTweets
            .map { it.text }
            .joinToString("\n\n\n")
    }")
}

private fun String.isConnective(): Boolean {
    return setOf( "de", "que", "no", "pra","na", "do", "da", "to", "and",
        "the", "com", "for", "um", "in", "of", "em", "your", "with", "te",
        "se", "as", "meu", "por", "para", "tambm", "tem", "muito", "ele",
        "sendo","sobre", "nas", "minha", "vc", "by", "os", "our", "its")
        .contains(this.toLowerCase()) || this.length <= 1
}
