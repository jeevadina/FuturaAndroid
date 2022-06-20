package com.futuraeducation.network

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.BuildConfig
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.futuraeducation.utils.Define
import com.futuraeducation.utils.MyPreferences
import com.futuraeducation.utils.Utils
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class NetworkHelper(context: Context) {

    var GET: Int = 1
    var POST: Int = 2
    var RESTYPE_OBJECT: Int = 101
    var RESTYPE_ARRAY: Int = 102
    private val MY_SOCKET_TIMEOUT_MS = 60 * 1000
    var TAG = NetworkHelper::class.java.simpleName

    var responseSuccess = 0
    var responseFailed = 1
    var responseNoInternet = 2

    lateinit var context: Context

    lateinit var cd: ConnectionDetector
    lateinit var gson: Gson

    init {
        if (context != null) {
            cd = ConnectionDetector(context)
            gson = Gson()
            this.context = context

            AndroidNetworking.enableLogging()

        }
    }

    fun call(
        callType: Int,
        type: Int,
        url: String,
        params: Map<String, String>,
        priority: Priority,
        tag: String,
        onNetworkResponse: OnNetworkResponse
    ) {
        Utils.log(TAG, "url $url")
        Utils.log(TAG, "params $params")
        Utils.log(TAG, "TAG $tag")
        if (cd.isConnectingToInternet()) {

            val header = HashMap<String, String>()
            header.put("Accept", "application/json")
            header.put("Accept-Encoding", "gzip, deflate, br")
            header.put("Connection", "keep-alive")
            header["Content-Type"] = "application/json; charset=utf-8"
            header["access_token"] = MyPreferences(context).getString(Define.ACCESS_TOKEN)!!
            header["Accept-Language"] = "en-US,en;q=0.9"
            header["User-Agent"] =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.106 Safari/537.36"


            Utils.log("Url", url)
            Utils.log("params", params.toString())
            Utils.log("headers", header.toString())

            if (callType == GET && type == RESTYPE_OBJECT) {
                getCallQueryParamsObjet(url, params, priority, tag, header, onNetworkResponse)
            } else if (callType == GET && type == RESTYPE_ARRAY) {
                getCallQueryParamsArray(url, params, priority, tag, header, onNetworkResponse)
            } else if (callType == POST && type == RESTYPE_OBJECT) {
                postCallBodyParamsObject(url, params, priority, tag, header, onNetworkResponse)
            } else if (callType == POST && type == RESTYPE_ARRAY) {
                postCallBodyParamsArray(url, params, priority, tag, header, onNetworkResponse)
            }

        } else {
            onNetworkResponse.onNetworkResponse(responseNoInternet, "No Internet Connection..", tag)
        }
    }

    fun getCallQueryParamsObjet(
        url: String,
        params: Map<String, String>,
        priority: Priority,
        tag: String,
        header: Map<String, String>,
        onNetworkResponse: OnNetworkResponse
    ) {

        AndroidNetworking.get(url)
            .addPathParameter(params)
            .addHeaders(header)
            .setTag(tag)
            .doNotCacheResponse()
            .setPriority(priority)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Utils.log(TAG, "response -$tag  $response")
                    if (context != null)
                        onNetworkResponse.onNetworkResponse(
                            responseSuccess,
                            response.toString(),
                            tag
                        )
                }

                override fun onError(error: ANError) {
                    try {
                        Utils.log(TAG, "NetworkError -$tag ${error.errorBody!!} ${error.errorCode}")
                        if (BuildConfig.DEBUG) {
                            val response =
                                "URL :" + url + "\nError Code : " + error.errorCode + "response : \n" + error.errorDetail
                            onNetworkResponse.onNetworkResponse(responseFailed, response, tag)
                        } else {

                            if (context != null)
                                onNetworkResponse.onNetworkResponse(
                                    responseFailed,
                                    error.errorDetail,
                                    tag
                                )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })


    }

    fun getCallQueryParamsArray(
        url: String,
        params: Map<String, String>,
        priority: Priority,
        tag: String,
        header: Map<String, String>,
        onNetworkResponse: OnNetworkResponse
    ) {

        AndroidNetworking.get(url)
            .addQueryParameter(params)
            .addHeaders(header)
            .setTag(tag)
            .doNotCacheResponse()
            .setPriority(priority)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    // do anything with response
                    Log.e(TAG, "response -$tag  $response")
                    if (context != null)
                        onNetworkResponse.onNetworkResponse(
                            responseSuccess,
                            response.toString(),
                            tag
                        )
                }

                override fun onError(error: ANError) {
                    Utils.log("NetworkError", error.errorDetail.toString())
                    val response = "Error Code : " + error.errorCode + " " + error.errorDetail

                    if (error.errorCode == 0) {
                        onNetworkResponse.onNetworkResponse(responseFailed, response, tag)
                    } else {
                        onNetworkResponse.onNetworkResponse(responseFailed, response, tag)
                    }
                }
            })


    }

    fun postCallBodyParamsObject(
        url: String,
        params: Map<String, String>,
        priority: Priority,
        tag: String,
        header: Map<String, String>,
        onNetworkResponse: OnNetworkResponse
    ) {

        AndroidNetworking.post(url)
            .addBodyParameter(params)
            .addHeaders(header)
            .setContentType("application/json; charset=utf-8")
            .setTag(tag)
            .setPriority(priority)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if (context != null)
                        onNetworkResponse.onNetworkResponse(
                            responseSuccess,
                            response.toString(),
                            tag
                        )
                    Utils.log(TAG, "response -$tag  $response")
                }

                override fun onError(error: ANError) {
                    Utils.log(TAG, "NetworkError -$tag $error ${error.errorCode}")
                    if (context != null)
                        if (error.errorDetail.equals("connectionError")) {
                            onNetworkResponse.onNetworkResponse(
                                responseNoInternet,
                                "No Internet Connection..",
                                tag
                            )
                        } else {
                            onNetworkResponse.onNetworkResponse(
                                responseFailed,
                                error.errorDetail,
                                tag
                            )
                        }
                }
            })

    }

    fun postCallBodyParamsArray(
        url: String,
        params: Map<String, String>,
        priority: Priority,
        tag: String,
        header: Map<String, String>,
        onNetworkResponse: OnNetworkResponse
    ) {

        AndroidNetworking.post(url)

            .addQueryParameter(params)
            //.addPathParameter(pathParams)
            .addHeaders(header)
            .setTag(tag)
            .doNotCacheResponse()
            .setPriority(priority)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    Log.e(TAG, "response -$tag  $response")
                    if (context != null)
                        onNetworkResponse.onNetworkResponse(
                            responseSuccess,
                            response.toString(),
                            tag
                        )
                }

                override fun onError(error: ANError) {
                    Utils.log("NetworkError", error.errorDetail.toString())
                    val response = "Error Code : " + error.errorCode + " " + error.errorDetail

                    if (error.errorCode == 0) {
                        onNetworkResponse.onNetworkResponse(responseFailed, response, tag)
                    } else {
                        onNetworkResponse.onNetworkResponse(responseFailed, response, tag)
                    }
                }
            })
    }

    fun postCall(
        url: String,
        params: JSONObject,
        tag: String,
        headers: HashMap<String, String>,
        onNetworkResponse: OnNetworkResponse,
    ) {
        val queue = Volley.newRequestQueue(context)
        if (cd.isConnectingToInternet()) {
            Utils.log(TAG, "url $url")
            Utils.log(TAG, "params $params")
            Utils.log(TAG, "params $params")
            Utils.log(TAG, "headers $headers")

            val jsonObjReq: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, url, params,
                Response.Listener { response: JSONObject ->
                    Utils.log(TAG, "response -$tag $response")
                    if (response.optBoolean("error")) {
                        if (response.optString("message")
                                .equals("Unauthorized", ignoreCase = true)
                        ) {

                        } else {
                            onNetworkResponse.onNetworkResponse(
                                responseSuccess, response.toString(),
                                tag
                            )
                        }
                    } else {
                        onNetworkResponse.onNetworkResponse(
                            responseSuccess,
                            response.toString(),
                            tag
                        )
                    }

                },
                Response.ErrorListener { error: VolleyError ->
                    try{
                        Utils.log(
                            TAG,
                            "ErrorListener -$tag ${error.message} ${error.networkResponse.statusCode}"
                        )
                    }catch (e: Exception){}

                    if (error is TimeoutError || error is NoConnectionError) {
                        onNetworkResponse.onNetworkResponse(
                            responseNoInternet,
                            "No Internet Connection..",
                            tag
                        )
                    } else {
                        onNetworkResponse.onNetworkResponse(
                            responseFailed,
                            "Something went wrong!, Please try again..",
                            tag
                        )
                    }
                }) {
                override fun getHeaders(): Map<String, String> {
                    return headers
                }
            }
            jsonObjReq.retryPolicy = DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(jsonObjReq).tag = tag
        } else {
            onNetworkResponse.onNetworkResponse(responseNoInternet, "No Internet Connection..", tag)
        }
    }

    fun postCallResponseArray(
        url: String,
        params: JSONObject,
        tag: String,
        headers: HashMap<String, String>,
        onNetworkResponse: OnNetworkResponse,
    ) {
        Utils.log(TAG, "url $url")
        Utils.log(TAG, "params $params")
        Utils.log(TAG, "headers $headers")

        AndroidNetworking.post(url)
            .addBodyParameter(params)
            .addHeaders(headers)
            .setTag(tag)
            .doNotCacheResponse()
            .build()
            .getAsJSONArray( object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    Log.e(TAG, "response1 -$tag  $response")
                    if (context != null)
                        onNetworkResponse.onNetworkResponse(
                            responseSuccess,
                            response.toString(),
                            tag
                        )
                }

                override fun onError(error: ANError) {
                    Utils.log("NetworkError1", error.errorDetail.toString())
                    val response = "Error Code2 : " + error.errorCode + " " + error.errorDetail

                    if (error.errorCode == 0) {
                        onNetworkResponse.onNetworkResponse(responseFailed, response, tag)
                    } else {
                        onNetworkResponse.onNetworkResponse(responseFailed, response, tag)
                    }
                }
            })
    }


    fun getCall(
        url: String,
        tag: String,
        headers: HashMap<String, String>,
        onNetworkResponse: OnNetworkResponse,
    ) {
        val queue = Volley.newRequestQueue(context)
        if (cd.isConnectingToInternet()) {
            Utils.log(TAG, "url $url")
            Utils.log(TAG, "headers $headers")

            val jsonObjReq: JsonObjectRequest = object : JsonObjectRequest(
                Method.GET, url, null,
                Response.Listener { response: JSONObject ->
                    Utils.log(TAG, "response -$tag $response")
                    if (response.optBoolean("error")) {
                        if (response.optString("message")
                                .equals("Unauthorized", ignoreCase = true)
                        ) {

                        } else {
                            onNetworkResponse.onNetworkResponse(
                                responseSuccess, response.toString(),
                                tag
                            )
                        }
                    } else {
                        onNetworkResponse.onNetworkResponse(
                            responseSuccess,
                            response.toString(),
                            tag
                        )
                    }

                },
                Response.ErrorListener { error: VolleyError ->
                    try {
                        Utils.log(
                            TAG,
                            "ErrorListener -$tag ${error.message} ${error.networkResponse.statusCode}"
                        )
                        if (error is TimeoutError || error is NoConnectionError) {
                            onNetworkResponse.onNetworkResponse(
                                responseNoInternet,
                                "No Internet Connection..",
                                tag
                            )
                        } else {
                            onNetworkResponse.onNetworkResponse(
                                responseFailed,
                                "Something went wrong!, Please try again..",
                                tag
                            )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }) {
                override fun getHeaders(): Map<String, String> {
                    return headers
                }
            }
            jsonObjReq.retryPolicy = DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(jsonObjReq).tag = tag
        } else {
            onNetworkResponse.onNetworkResponse(responseNoInternet, "No Internet Connection..", tag)
        }
    }

    fun getArrayCall(
        url: String,
        tag: String,
        headers: HashMap<String, String>,
        onNetworkResponse: OnNetworkResponse,
    ) {

        val queue = Volley.newRequestQueue(context)
        if (cd.isConnectingToInternet()) {
            Utils.log(TAG, "url $url")
            Utils.log(TAG, "headers $headers")

            try {
                val jsonArrayRequest = object : JsonArrayRequest(
                    Method.GET,
                    url,
                    null,
                    Response.Listener { response ->
                        Utils.log(TAG, "response -$tag $response")
                        onNetworkResponse.onNetworkResponse(
                            responseSuccess,
                            response.toString(),
                            tag
                        )
                    },
                    Response.ErrorListener { error: VolleyError ->
                        Utils.log(
                            TAG,
                            "ErrorListener -$tag ${error.message} ${error.networkResponse}"
                        )
                        if (error is TimeoutError || error is NoConnectionError) {
                            onNetworkResponse.onNetworkResponse(
                                responseNoInternet,
                                "No Internet Connection..",
                                tag
                            )
                        } else {
                            onNetworkResponse.onNetworkResponse(
                                responseFailed,
                                "Something went wrong!, Please try again..",
                                tag
                            )
                        }
                    }
                ) {
                    override fun getHeaders(): Map<String, String> {
                        return headers
                    }
                }
                jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue.add(jsonArrayRequest).tag = tag

            }catch (e:Exception){}


        } else {
            onNetworkResponse.onNetworkResponse(responseNoInternet, "No Internet Connection..", tag)
        }
    }

    fun cancelAll() {
        AndroidNetworking.cancelAll()
    }

    fun cancel(tag: String) {
        AndroidNetworking.cancel(tag)
    }

}