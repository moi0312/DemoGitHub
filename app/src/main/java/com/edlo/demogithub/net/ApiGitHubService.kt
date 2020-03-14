package com.example.testcoroutines.net

import com.edlo.demogithub.net.data.AccessToken
import com.edlo.demogithub.net.data.Commit
import com.edlo.demogithub.net.data.User
import com.example.testcoroutines.net.data.Repository
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.http.*

enum class Type { all, owner, member }
enum class Sort { created, updated, pushed, full_name }
enum class Direction { asc, desc }

interface ApiGitHubService {

    @GET(ApiGitHub.GET_REPOS_OF_USER)
    fun listRepositoriesForUser(
        @Path("username") username: String,
        @Query("type") type: String = Type.all.name,
        @Query("sort") sort: String = Sort.full_name.name,
        @Query("direction") direction: String = Direction.asc.name
    ): Deferred<ArrayList<Repository>>?

    @GET(ApiGitHub.GET_COLLABORATORS_OF_REPO)
    fun listCollaboratorsOfRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Header("Authorization") token: String?
    ): Deferred<ArrayList<User>>?

    @GET(ApiGitHub.GET_COMMITS_OF_REPO)
    fun listCommitsOfRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Deferred<ArrayList<Commit>>?

    @FormUrlEncoded
    @POST(ApiGitHub.POST_OAUTH_ACCESS_TOKEN)
    fun exchangeAccessTokenByCode(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Deferred<AccessToken>?
}