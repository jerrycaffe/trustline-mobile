package com.example.trustline.data

import android.util.Log
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class ApiServiceImpl(val apiService: ApiService) {
    suspend inline fun <reified R> makePostCall(
        url: String,
        requestBody: Any,
        responseBody: Any
    ): ApiResult<R> {

        try {

            Log.d("ApiServiceImpl", "request body: $requestBody")
            val apiResponse = apiService.post(url, requestBody)
            Log.i("ApiServiceImpl", "response body: $apiResponse")
            return if (apiResponse.isSuccessful) ApiResult.Success(
                Gson().fromJson(
                    apiResponse.body()?.string(),
                    R::class.java
                )
            )
            else ApiResult.Error(parseError(apiResponse.errorBody())!!.message)
        } catch (e: IOException) {
            Log.e("Network", "IOException: ${e.message}", e)
        } catch (e: HttpException) {
            Log.e("Network", "HttpException: ${e.code()} - ${e.message()}", e)
        } catch (e: Exception) {
            Log.e("Network", "Unexpected exception", e)
        }
        Log.d("ApiServiceImpl", ": $responseBody")
        return ApiResult.Error("Network error occurred")

    }
}