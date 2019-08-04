package com.playkids.workshop

import com.playkids.workshop.adapter.TwitterClient
import twitter4j.Status

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
suspend fun main() {
    println("everything wired up!!")

    val user = TwitterClient.getLoggedUser()

    println(user)

    val statuses = TwitterClient.getHomeTweets()

    println(statuses.size)
    statuses.forEach {
        it.toTweet()
    }
}
