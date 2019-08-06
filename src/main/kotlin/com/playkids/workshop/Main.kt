package com.playkids.workshop

import com.playkids.workshop.adapter.TwitterClient
import com.playkids.workshop.logic.getWordRanking
import com.playkids.workshop.logic.topTweet

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
fun main() {
    println("Everything wired up!!")

    val user = TwitterClient.getLoggedUser()

    println(user)

    val tweets = TwitterClient
        .getHomeTweets(300)
        .toList()

    val filteredTweets = tweets
        .filter { it.retweetedTweet == null }
        .filter { it.quoteInformation == null }
        .filter { it.replyInfo == null }

    println("Total tweets: ${filteredTweets.size}")
    println()

    val ranking = getWordRanking(filteredTweets)

    ranking
        .filter { rankedWord -> rankedWord.first.isNotBlank() }
        .filter { (word, _) -> !word.isConnective() }
        .take(50)
        .forEachIndexed { index, (word, count) ->
            println("Rank ${index + 1}: '$word' written $count times")
        }

    println()
    val tweetsWithFavoriteCount = filteredTweets.map { it to it.favoriteCount }
    val topTweet = topTweet(tweetsWithFavoriteCount)

    println(
        "Top tweet: '${topTweet?.text}' from '@${topTweet?.relatedUser?.screenName}' " +
                "with ${topTweet?.favoriteCount} likes"
    )

    val userTweets = TwitterClient.getUserTweets("JulioMoreira_", 40)
    println("Tweets from @juliomoreira_: size: ${userTweets.size}")
    println(userTweets.joinToString("\n\n\n") { it.text })
}

private fun String.isConnective(): Boolean {
    return setOf(
        "de", "que", "no", "pra", "na", "do", "da", "to", "and",
        "the", "com", "for", "um", "in", "of", "em", "your", "with", "te",
        "se", "as", "meu", "por", "para", "tambm", "tem", "muito", "ele",
        "sendo", "sobre", "nas", "minha", "vc", "by", "os", "our", "its"
    )
        .contains(this.toLowerCase()) || this.length <= 1
}
