package com.playkids.workshop

import com.playkids.workshop.adapter.TwitterClient

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
fun main() {
    println("everything wired up!!")
    println("Hello 🌎!!!")

    TwitterClient.doOauth().also { println(it) }

    val user = TwitterClient.getLoggedUser()

    println(user)
}
