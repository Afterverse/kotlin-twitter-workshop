package com.playkids.workshop.model

import twitter4j.*
import java.time.Instant

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 8/3/19
 */
typealias TweetId = Long

data class Tweet(
    val createdAt: Instant,
    val id: TweetId,
    val text: String,
    val source: TweetSource,
    val truncationInfo: TruncationInfo? = null,
    val replyInfo: ReplyInfo? = null,
    val geoLocation: GeoLocation? = null,
    val place: Place? = null,
    val isFavorited: Boolean,
    val favoriteCount: Int,
    val retweetCount: Int,
    val relatedUser: SimpleUser,
    val retweetedTweet: Tweet? = null,
    val isRetweetedByCurrentUser: Boolean,
    val currentUserRetweetId: TweetId? = null,
    val isPossiblySensitive: Boolean,
    val language: String,
    val scopes: Scopes? = null,
    val quoteInformation: QuoteInformation? = null
)

data class TruncationInfo(
    val displayTextRangeStart: Int,
    val displayTextRangeEnd: Int
)

data class ReplyInfo(
    val inReplyToTweetId: TweetId,
    val inReplyToUserId: Long,
    val inReplyToScreenName: String
)

data class QuoteInformation(
    val quotedTweetId: TweetId,
    val quotedTweet: Tweet? = null,
    val quotedTweetPermalink: URLEntity
)

sealed class TweetSource
object TwitterWebClient: TweetSource()
object TwitterMobileWebApp: TweetSource()
object TwitterForIphone: TweetSource()
object TwitterForAndroid: TweetSource()
object TweetDeck: TweetSource()
data class UnknownTweetSource(val sourceName: String): TweetSource()


fun Status.toTweet(): Tweet =
    Tweet(
        createdAt = createdAt.toInstant(),
        id = id,
        text = text,
        source = getTweetSource(source),
        truncationInfo = if (isTruncated) TruncationInfo(
            displayTextRangeStart,
            displayTextRangeEnd
        ) else null,
        replyInfo = inReplyToStatusId.takeIf { it != -1L }?.let {
            ReplyInfo(
                inReplyToStatusId,
                inReplyToUserId,
                inReplyToScreenName
            )
        },
        geoLocation = geoLocation,
        place = place,
        isFavorited = isFavorited,
        favoriteCount = favoriteCount,
        retweetCount = retweetCount,
        relatedUser = user.toSimpleUser(),
        retweetedTweet = retweetedStatus?.toTweet(),
        isRetweetedByCurrentUser = isRetweetedByMe,
        currentUserRetweetId = currentUserRetweetId,
        isPossiblySensitive = isPossiblySensitive,
        language = lang,
        scopes = scopes,
        quoteInformation = quotedStatusId.takeIf { it != -1L }?.let {
            QuoteInformation(
                quotedTweetId = quotedStatusId,
                quotedTweet = quotedStatus?.toTweet(),
                quotedTweetPermalink = quotedStatusPermalink
            )
        }
    )

fun getTweetSource(source: String): TweetSource =
    when {
        source.contains("Twitter for Android") -> TwitterForAndroid
        source.contains("TweetDeck") -> TweetDeck
        source.contains("Twitter for iPhone") -> TwitterForIphone
        source.contains("Twitter Web App") -> TwitterMobileWebApp
        source.contains("Twitter Web Client") -> TwitterWebClient
        else -> UnknownTweetSource(source)
    }
