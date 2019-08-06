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

    val tweets = TwitterClient.getHomeTweets(50)

    println("total tweets: ${tweets.size}")
    println()

    val ranking = getWordRanking(tweets)

    ranking
        .subList(0, max(50, tweets.size))
        .forEachIndexed { index, (word, count) ->
            println("Rank ${index+1}: '$word' written $count times")
        }

    println()
    topTweet(tweets.map { it to it.favoriteCount })
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
