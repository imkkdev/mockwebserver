package com.kk.testapp

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        Constants.URL = "http://127.0.0.1:8089/"
        mockWebServer.start(8089)
        mockWebServer.setDispatcher(dispatcher)
    }

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testApi() {
        activityTestRule.launchActivity(null)
        val request = mockWebServer.takeRequest()
        println("okhttp >>>> " + request.path)
    }

    private val dispatcher: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            println("okhttp path = " + request.path)
            Log.e("okhttp", "path = " + request.path)
            return when (request.path) {
                "/404" -> MockResponse().setResponseCode(404)
                else -> MockResponse().setResponseCode(200).throttleBody(895, 1, TimeUnit.SECONDS).setBody("{\"status\":\"00000\",\"msg\":\"操作成功\",\"errorMsg\":\"\",\"success\":true}")
            }
        }
    }

}