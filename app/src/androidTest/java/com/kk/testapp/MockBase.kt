package com.kk.testapp

import android.app.Activity
import android.content.Intent
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
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
open class MockBase(activityClass: Class<out Activity>) : Dispatcher() {

    open val mockWebServer = MockWebServer()

    @JvmField
    @Rule
    val activityTestRule = ActivityTestRule(activityClass, true, false)

    @Test
    open fun jump() {
        jumpImpl(null)
    }

    fun jumpImpl(intent: Intent?) {
        activityTestRule.launchActivity(intent)
        val countdown = CountDownLatch(1)  //取消自动退出
        countdown.await()
    }

    @Before
    fun setup() {
        // can use http://localhost:xxxx
        // can get host by #mockWebServer.hostName #mockWebServer.port  #mockWebServer.url("")
        // if mockWebServer get 503 response, check the phone network setting
        Constants.URL = "http://127.0.0.1:8089/"
        mockWebServer.start(8089)
        mockWebServer.setDispatcher(this)
    }

    @After
    override fun shutdown() {
        mockWebServer.shutdown()
    }

    override fun dispatch(request: RecordedRequest?): MockResponse {
        return MockResponse().setBody(success)
    }

    open val success = "{\"status\":\"00000\",\"msg\":\"操作成功\",\"errorMsg\":\"\",\"success\":true}"

}