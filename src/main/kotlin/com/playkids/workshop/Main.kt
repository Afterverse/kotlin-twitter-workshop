package com.playkids.workshop

import com.playkids.workshop.adapter.TwitterClient

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
fun main() {
    println("everything wired up!!")

    val user = TwitterClient.getLoggedUser()

    println(user)

    val statuses = TwitterClient.getHomeTweets()

    println(statuses.size)
    statuses.forEach {
        println(it)
    }
}
