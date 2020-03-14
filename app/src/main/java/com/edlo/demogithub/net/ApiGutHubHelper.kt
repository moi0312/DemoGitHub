package com.example.testcoroutines.net

import com.edlo.demogithub.BuildConfig
import com.edlo.demogithub.DemoGitHubApplication
import com.edlo.demogithub.net.data.AccessToken
import com.edlo.demogithub.net.data.Commit
import com.edlo.demogithub.net.data.User
import com.edlo.demogithub.util.Log
import com.edlo.demogithub.util.SharedPreferencesHelper
import com.example.testcoroutines.net.data.Repository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGutHubHelper private constructor() {

    companion object {
        val INSTANCE = ApiGutHubHelper()
    }

    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit
    private var service: ApiGitHubService

    init{
        val okHttpClientBuilder = OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                var reqBuilder = chain.request().newBuilder()
                    .url(chain.request().url())
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", "DemoGitHub")
//                if(){
//                    reqBuilder.addHeader("Authorization", "bearer " + BuildConfig.GIT_HUB_PERSONAL_ACCESS_TOKEN)
//                }
                chain.proceed(reqBuilder.build())
            }
        if( BuildConfig.PRINT_LOG ) {
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        okHttpClient = okHttpClientBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(ApiGitHub.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
        service = retrofit.create(ApiGitHubService::class.java)
    }

    suspend fun listRepositoriesForUser(username: String): ArrayList<Repository>? {
        var result: ArrayList<Repository>? = null
        try {
            result = service.listRepositoriesForUser(username)?.await()
        } catch (e: HttpException) {
            Log.e("${e.code()} : ${e.message()}")
            when(e.code()) {
                404 -> result = ArrayList()
                else -> { }
            }
        }
        return result
    }
    suspend fun listCollaboratorsOfRepo(owner: String, repo: String): ArrayList<User>? {
        var result: ArrayList<User>? = null
        try {
            var token = SharedPreferencesHelper.HELPER.getToken()
            result = service.listCollaboratorsOfRepo(owner, repo, token)?.await()
        } catch (e: HttpException) {
            Log.e("${e.code()} : ${e.message()}")
            when(e.code()) {
                401 -> result = ArrayList()
                else -> { }
            }
        }
        return result
    }
    suspend fun listCommitsOfRepo(owner: String, repo: String): ArrayList<Commit>? {
        var result: ArrayList<Commit>? = null
        try {
            result = service.listCommitsOfRepo(owner, repo)?.await()
        } catch (e: HttpException) {
            Log.e("${e.code()} : ${e.message()}")
        }
        return result
    }

    suspend fun exchangeAccessTokenByCode(code: String): AccessToken? {
        var result: AccessToken? = null
        try {
            result = service.exchangeAccessTokenByCode(BuildConfig.GIT_HUB_CLIENT_ID, BuildConfig.GIT_HUB_CLIENT_SECRET, code)?.await()
            result?.let {
                Log.d("exchangeAccessTokenByCode: result: ${it}")
            }
        } catch (e: HttpException) {
            Log.e("${e.code()} : ${e.message()}")
        }
        return result
    }
}