package com.example.trustline.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.io.IOException
import kotlin.reflect.KClass

class ApiServiceImpl(val apiService: ApiService) {
    suspend inline fun <T : Any, reified R : Any> makePostCall(
        url: String,
        requestBody: T,
        responseBody: KClass<R>
    ): ApiResult<R> {

        try {

            Log.d("ApiServiceImpl", "request body: $requestBody")
            val apiResponse = apiService.post(url, requestBody)
            Log.i("ApiServiceImpl", "response body: $apiResponse")
            if (apiResponse.isSuccessful) {
                val responseString = apiResponse.body()?.string()
                val type = object : TypeToken<BaseResponse<R>>() {}.type
                val result: BaseResponse<R>? = Gson().fromJson(responseString, type)
                
                return if (result != null) {
                    if (result.success) {
                        ApiResult.Success(
                            data = result.data,
                            message = result.message,
                            success = result.success,
                            code = result.code
                        )
                    } else {
                        ApiResult.Error(
                            message = result.message ?: "Request was not successful",
                            code = result.code
                        )
                    }
                } else {
                    ApiResult.Error("Response body was empty or invalid", apiResponse.code().toString())
                }
            } else {
                val error = parseError(apiResponse.errorBody())
                return ApiResult.Error(
                    message = error?.message ?: "Unknown error",
                    code = error?.code ?: apiResponse.code().toString()
                )
            }
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