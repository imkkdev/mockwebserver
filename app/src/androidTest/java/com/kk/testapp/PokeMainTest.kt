package com.kk.testapp

import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule

class PokeMainTest : MockBase() {

    @JvmField
    @Rule
    val rule = ActivityTestRule(PokeMainActivity::class.java, true, false)

    fun jump() {
        rule.launchActivity(null)
    }

    override fun dispatch(request: RecordedRequest?): MockResponse {
        return when (request?.path) {
            "v2/pokemon" -> MockResponse().setBody(pokemon)
            else -> MockResponse().setResponseCode(404)
        }
    }

    private val pokemon = "{\"count\":1,\"next\":\"Next\",\"previous\":\"Previous\",\"result\":[\"name\":\"Poke\",\"url\":\"www.google.com\",\"type\":[\"type1\",\"type2\"]]}"
}