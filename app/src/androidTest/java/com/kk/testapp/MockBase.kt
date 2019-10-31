package com.kk.testapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
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
import java.io.Serializable
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

    open fun jump(vararg params: Pair<String, Any?>?) {
        activityTestRule.launchInternal(params)
        val countdown = CountDownLatch(1)  //取消自动退出
        countdown.await()
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


    private fun fillIntentArguments(params: Array<out Pair<String, Any?>?>) : Intent {
        val intent = Intent()
        params.forEach {
            if (it == null)  return@forEach
            val value = it.second
            when (value) {
                null -> intent.putExtra(it.first, null as Serializable?)
                is Int -> intent.putExtra(it.first, value)
                is Long -> intent.putExtra(it.first, value)
                is CharSequence -> intent.putExtra(it.first, value)
                is String -> intent.putExtra(it.first, value)
                is Float -> intent.putExtra(it.first, value)
                is Double -> intent.putExtra(it.first, value)
                is Char -> intent.putExtra(it.first, value)
                is Short -> intent.putExtra(it.first, value)
                is Boolean -> intent.putExtra(it.first, value)
                is Serializable -> intent.putExtra(it.first, value)
                is Bundle -> intent.putExtra(it.first, value)
                is Parcelable -> intent.putExtra(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                    else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
                }
                is IntArray -> intent.putExtra(it.first, value)
                is LongArray -> intent.putExtra(it.first, value)
                is FloatArray -> intent.putExtra(it.first, value)
                is DoubleArray -> intent.putExtra(it.first, value)
                is CharArray -> intent.putExtra(it.first, value)
                is ShortArray -> intent.putExtra(it.first, value)
                is BooleanArray -> intent.putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            return@forEach
        }
        return intent
    }

    open fun <T : Activity> ActivityTestRule<T>.launch(vararg params: Pair<String, Any?>?) {
        launchActivity(fillIntentArguments(params))
    }

    private fun <T : Activity> ActivityTestRule<T>.launchInternal(params: Array<out Pair<String, Any?>?>) {
        launchActivity(fillIntentArguments(params))
    }

}