package com.kk.testapp

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import java.util.concurrent.CountDownLatch

class PokeDetailTest : MockBase(PokeDetailActivity::class.java) {

    @Test
    fun jump1() {
        activityTestRule.launch(
            "name" to "poke",
            "param" to 1
        )
        val countdown = CountDownLatch(1)  //取消自动退出
        countdown.await()
    }

    @Test
    fun jump2() {
        jump(
            "name" to "poke",
            "param" to 1
        )
    }

    override fun dispatch(request: RecordedRequest?): MockResponse {
        return MockResponse().setBody(body)
    }

    private val body =
        "{\"name\":\"Poke\",\"url\":\"www.google.com\",\"type\":[\"type1\",\"type2\"]}"
}