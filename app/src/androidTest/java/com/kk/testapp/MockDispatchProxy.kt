package com.kk.testapp

import android.content.Context
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.nio.charset.Charset

// we can dispatch all request here,
// besides activity can override addPageResponse() method to config additional request
fun dispatchImpl(request: RecordedRequest?, requestMap : MutableList<Pair<String, MockResponse>>): MockResponse {
    for (pair in requestMap) {
        if (request?.path == pair.first) {
            return pair.second
        }
    }
    // we can mock all request here
    return when (request?.path) {
        "/v1/pokemon" -> MockResponse().setBody(pokemon)
        "/v2/pokemon" -> MockResponse().setBody(getJson("json/pokemon_list.json")) // can use json.file in androidTest
        "/v2/msg" -> MockResponse().setBody(getJson("json/pokemon_msg.json"))
        else -> MockResponse().setStatus("404")
    }
}


fun dispatchImplBase(request: RecordedRequest?): MockResponse {
    return when (request?.path) {
        "/v1/pokemon" -> MockResponse().setBody(pokemon)
        "/v2/pokemon" -> MockResponse().setBody(getJson("json/pokemon_list.json")) // can use json.file in androidTest
        "/v2/msg" -> MockResponse().setBody(getJson("json/pokemon_msg.json"))
        else -> MockResponse().setStatus("404")
    }
}

fun dispatch(request: RecordedRequest?, context: Context): MockResponse {
    return when (request?.path) {
        "/v1/pokemon" -> MockResponse().setBody(pokemon)
        "/v2/pokemon" -> MockResponse().setBody(
            getJson(
                "json/pokemon_list.json",
                context
            )
        ) // can use json.file in androidTest
        "/v2/msg" -> MockResponse().setBody(getJson("json/pokemon_msg.json", context))
        else -> MockResponse().setStatus("404")
    }
}

fun dispatch(request: RecordedRequest?, loader: ClassLoader): MockResponse {
    return when (request?.path) {
        "/v1/pokemon" -> MockResponse().setBody(pokemon)
        "/v2/pokemon" -> MockResponse().setBody(
            getJson(
                "json/pokemon_list.json",
                loader
            )
        ) // can use json.file in androidTest
        "/v2/msg" -> MockResponse().setBody(getJson("json/pokemon_msg.json", loader))
        else -> MockResponse().setStatus("404")
    }
}

private val pokemon =
    "{\"count\":1,\"next\":\"Next\",\"previous\":\"Previous\",\"result\":[\"name\":\"Poke\",\"url\":\"www.google.com\",\"type\":[\"type1\",\"type2\"]]}"

val success = "{\"status\":\"00000\",\"msg\":\"操作成功\",\"errorMsg\":\"\",\"success\":true}"

private fun getJson(path: String, context: Context): String {
    val stream = context.javaClass.classLoader.getResource(path)
    return stream.readBytes().toString(Charset.defaultCharset())
}

private fun getJson(path: String, loader: ClassLoader): String {
    val stream = loader.getResource(path)
    return stream.readBytes().toString(Charset.defaultCharset())
}

fun getJson(path: String): String {
    val stream = ProxyObject::class.java.classLoader!!.getResource(path)
    return stream.readBytes().toString(Charset.defaultCharset())
}

class ProxyObject