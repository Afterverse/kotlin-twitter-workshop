package com.playkids.workshop

import com.playkids.workshop.adapter.TwitterClient
import com.playkids.workshop.adapter.UserAuthProperties
import com.playkids.workshop.model.Tweet

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
fun main() {
    println("everything wired up!!")
    println("Hello 🌎!!!")

    TwitterClient.doOauth().also {
        userProperties: UserAuthProperties ->
            println(userProperties)
    }

    val user = TwitterClient.getLoggedUser()

    val tweets = TwitterClient.getHomeTweets(300)

    var hashTags = mutableListOf<String>()
    for (tweet in tweets) {
        if (tweet.text.contains("#")) {
            val palavras = tweet.text.split(" ", "\n")
            for (palavra in palavras) {
                if (palavra.startsWith("#")) {
                    hashTags.add(palavra)
                }
            }
        }
    }

    val hashTags2 = tweets
        .map { tweet -> tweet.text }
        .map { text -> text.split(" ", "\n") }
        .flatten()
        .filter { palavra -> palavra.startsWith("#") }

    println("Feio: ${hashTags}")
    println("Bonito: ${hashTags2}")

    val dict = mutableMapOf<String, Int>()
    for (tweet in tweets) {
        val palavras = tweet.text.split(" ", "\n")
        for (palavra in palavras) {
            if (dict[palavra] != null) {
                dict[palavra] = dict[palavra]!! + 1
            } else {
                dict[palavra] = 1
            }
        }
    }

    val grupos = tweets
        .map { it.text }
        .flatMap { text -> text.split(" ", "\n") }
        .groupBy { palavra -> palavra }
        .map { (palavra, lista) -> palavra to lista.size }
        .sortedByDescending { (palavra, qtas) -> qtas }

    println(grupos)
    println(dict)

    //println(user)
}
