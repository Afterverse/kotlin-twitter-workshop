package com.playkids.workshop.adapter

import com.playkids.workshop.model.Tweet

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
object TwitterClient {
    fun getTweets(): List<Tweet> {
        return listOf(Tweet(postedBy = "julio.barros"))
    }
}