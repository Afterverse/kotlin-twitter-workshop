package com.playkids.workshop

import com.playkids.workshop.adapter.TwitterClient
import com.playkids.workshop.logic.countWords
import com.playkids.workshop.logic.getWordRanking
import com.playkids.workshop.logic.topTweet
import com.playkids.workshop.model.Tweet

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
fun main() {
    println("everything wired up!!")

    val user = TwitterClient.getLoggedUser()

    println(user)

    val tweets = TwitterClient.getHomeTweets()

    println("total tweets: ${tweets.size}")
    println()

    val ranking = getWordRanking(tweets)

    ranking
        .subList(0, 50)
        .forEachIndexed { index, (word, count) ->
            println("Rank ${index+1}: '$word' written $count times")
        }

    println()
    topTweet(tweets.map { it to it.favoriteCount })
        ?.run {
            println("Top tweet: '$text' from '@${relatedUser.screenName}' with $favoriteCount likes")
        }
}
