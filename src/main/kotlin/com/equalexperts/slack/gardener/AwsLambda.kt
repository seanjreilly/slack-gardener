package com.equalexperts.slack.gardener

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.equalexperts.slack.gardener.rest.SlackApi
import com.equalexperts.slack.gardener.rest.SlackBotApi
import com.natpryce.konfig.*
import java.time.Clock
import java.time.Period

class AwsLambda : RequestHandler<Any, Unit> {
    override fun handleRequest(input: Any?, context: Context?): Unit {
        val config = EnvironmentVariables
        val slackUri = config[slack.uri]

        val slackApi = SlackApi.factory(slackUri, config[slack.apiKey])
        val slackBotApi = SlackBotApi.factory(slackUri, config[slack.bot.apiKey])

        val clock = Clock.systemUTC()
        val idlePeriod = Period.ofMonths(3)
        val warningPeriod = Period.ofWeeks(1)

        Gardener(slackApi, slackBotApi, clock, idlePeriod, warningPeriod).process()
    }
}

object slack : PropertyGroup() {
    val uri by uriType
    val apiKey by stringType
    object bot : PropertyGroup() {
        val apiKey by stringType
    }
}