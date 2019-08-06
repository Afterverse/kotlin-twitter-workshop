package com.playkids.workshop.adapter

import com.playkids.workshop.model.*
import twitter4j.*

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
object TwitterClient {
    val twitter = TwitterFactory.getSingleton()

    fun getLoggedUser() = twitter.verifyCredentials().toSimpleUser()

    fun getHomeTweets(maxTweets: Int): List<Tweet> {
        var page = 1
        var totalTweets = 0

        val tweetSequence = generateSequence {
            if (totalTweets >= maxTweets) null
            else
                twitter.getHomeTimeline(Paging(page++, maxTweets - totalTweets))
                    .toList()
                    .also { totalTweets += it.size }
                    .takeIf { it.isNotEmpty() }
        }

        return tweetSequence.flatten().map { it.toTweet() }.toList()
    }

    fun getUserTweets(screenName: String, maxTweets: Int): List<Tweet> {
        var page = 1
        var totalTweets = 0

        val userSequence = generateSequence {
            if (totalTweets >= maxTweets) null
            else
                twitter.getUserTimeline(screenName, Paging(page++, maxTweets - totalTweets))
                    .toList()
                    .also { totalTweets += it.size }
                    .takeIf { it.isNotEmpty() }
        }

        return userSequence.flatten().map { it.toTweet() }.toList()
    }

    fun getUserFollowers(user: SimpleUser, startingAt: Long = -1): List<SimpleUser> {
        val response = twitter.friendsFollowers().getFollowersIDs(user.id, startingAt)

        return lookupUsers(response)
    }

    fun getUserFollowing(user: SimpleUser, startingAt: Long = -1): List<SimpleUser> {
        val response = twitter.friendsFollowers().getFriendsIDs(user.id, startingAt)

        return lookupUsers(response)
    }

    private fun lookupUsers(response: IDs): List<SimpleUser> {
        return response.iDs.toList()
            .chunked(95)
            .flatMap {
                twitter.users().lookupUsers(*it.toLongArray())
            }
            .map { it.toSimpleUser() }
    }
}