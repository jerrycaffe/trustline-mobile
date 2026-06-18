package com.example.trustline.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import kotlin.reflect.KClass

class ApiServiceImpl(val apiService: ApiService, val gson: Gson) {
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
                val result: BaseResponse<R>? = gson.fromJson(responseString, type)

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
                    ApiResult.Error(
                        "Response body was empty or invalid",
                        apiResponse.code().toString()
                    )
                }
            } else {
                val error = parseError(apiResponse.errorBody(), gson)
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

    suspend inline fun <reified T : Any> makeGetCall(
        url: String,
        accessToken: String
    ): ApiResult<T> {
        try {
            Log.d("ApiServiceImpl::makeGetCall", "GET request to: $url with token: Bearer ${accessToken.take(20)}...")
            val apiResponse = apiService.get(url, "Bearer $accessToken")
            Log.d("ApiServiceImpl::makeGetCall", "Response code: ${apiResponse.code()}, isSuccessful: ${apiResponse.isSuccessful}")

            if (apiResponse.isSuccessful) {
                val responseString = apiResponse.body()?.string()
                val type = object : TypeToken<BaseResponse<T>>() {}.type
                val result: BaseResponse<T>? = gson.fromJson(responseString, type)
                Log.i("ApiServiceImpl::makeGetCall", "response body: $result")
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
                    ApiResult.Error(
                        "Response body was empty or invalid",
                        apiResponse.code().toString()
                    )
                }
            } else {
                val errorBodyRaw = apiResponse.errorBody()?.string()
                Log.e("ApiServiceImpl::makeGetCall", "Error response (HTTP ${apiResponse.code()}): $errorBodyRaw")
                val error = parseError(apiResponse.errorBody(), gson)
                Log.e("ApiServiceImpl::makeGetCall", "Parsed error: $error")
                return ApiResult.Error(
                    message = error?.message ?: "Access denied (HTTP ${apiResponse.code()})",
                    code = error?.code ?: apiResponse.code().toString()
                )
            }
        } catch (e: IOException) {
            Log.e("Network::IOException", "Message: ${e.message}", e)
        } catch (e: HttpException) {
            Log.e("Network::HttpException", "Code: ${e.code()} - ${e.message()}", e)
        } catch (e: Exception) {
            Log.e("Network::Exception", "Unexpected error: ${e.message}", e)
        }
        return ApiResult.Error("Network error occurred")
    }

    suspend inline fun <reified R : Any> makePublicGetCall(
        url: String
    ): ApiResult<R> {
        try {
            val apiResponse = apiService.getWithoutAuth(url)

            if (apiResponse.isSuccessful) {
                val responseString = apiResponse.body()?.string()
                val type = object : TypeToken<BaseResponse<R>>() {}.type
                val result: BaseResponse<R>? = gson.fromJson(responseString, type)
                Log.i("ApiServiceImpl::makePublicGetCall", "response body: $result")
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
                    ApiResult.Error(
                        "Response body was empty or invalid",
                        apiResponse.code().toString()
                    )
                }
            } else {
                val error = parseError(apiResponse.errorBody(), gson)
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
        return ApiResult.Error("Network error occurred")
    }

    suspend fun makeMultipartPostCall(
        accessToken: String,
        caseBody: RequestBody,
        files: List<MultipartBody.Part>
    ): ApiResult<Unit> {
        try {
            val apiResponse = apiService.submitCase(
                authorization = "Bearer $accessToken",
                caseBody = caseBody,
                files = files
            )

            if (apiResponse.isSuccessful) {
                // Multipart success responses are often empty/plain text; avoid parsing to prevent false negatives.
                return ApiResult.Success(
                    data = Unit,
                    message = "Case submitted successfully",
                    success = true,
                    code = apiResponse.code().toString()
                )
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

        return ApiResult.Error("Network error occurred")
    }

    suspend fun makeMyCasesGetCall(
        accessToken: String
    ): ApiResult<MyCaseListResponse> {
        try {
            Log.d("ApiServiceImpl::makeMyCasesGetCall", "GET /api/v1/cases/my-cases with token: Bearer ${accessToken.take(20)}...")
            val apiResponse = apiService.getMyCases("Bearer $accessToken")
            Log.d("ApiServiceImpl::makeMyCasesGetCall", "Response code: ${apiResponse.code()}, isSuccessful: ${apiResponse.isSuccessful}")

            if (apiResponse.isSuccessful) {
                val responseString = apiResponse.body()?.string()
                val type = object : TypeToken<BaseResponse<MyCaseListResponse>>() {}.type
                val result: BaseResponse<MyCaseListResponse>? = gson.fromJson(responseString, type)
                Log.i("ApiServiceImpl::makeMyCasesGetCall", "response body: $result")
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
                val errorBodyRaw = apiResponse.errorBody()?.string()
                Log.e("ApiServiceImpl::makeMyCasesGetCall", "Error response (HTTP ${apiResponse.code()}): $errorBodyRaw")
                val error = parseError(apiResponse.errorBody(), gson)
                return ApiResult.Error(
                    message = error?.message ?: "Access denied (HTTP ${apiResponse.code()})",
                    code = error?.code ?: apiResponse.code().toString()
                )
            }
        } catch (e: Exception) {
            Log.e("Network::makeMyCasesGetCall", "Unexpected error: ${e.message}", e)
        }
        return ApiResult.Error("Network error occurred")
    }
}