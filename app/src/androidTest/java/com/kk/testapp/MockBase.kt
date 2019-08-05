package com.kk.testapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class MockBase : Dispatcher() {

    open val mockWebServer = MockWebServer()

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