package com.kk.testapp

import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule

class PokeDetailTest : MockBase() {

    @JvmField
    @Rule
    val rule = ActivityTestRule(PokeDetailActivity::class.java, true, false)

    fun jump() {
        rule.launchActivity(null)
    }

    override fun dispatch(request: RecordedRequest?): MockResponse {
        return MockResponse().setBody(body)
    }

    private val body = "{\"name\":\"Poke\",\"url\":\"www.google.com\",\"type\":[\"type1\",\"type2\"]}"
}