package com.example.testcoroutines.net

object ApiGitHub {
    const val BASE_URL = "https://api.github.com"

    const val GET_REPOS_OF_USER = "/users/{username}/repos"
    const val GET_COLLABORATORS_OF_REPO = "/repos/{owner}/{repo}/collaborators"
    const val GET_COMMITS_OF_REPO = "/repos/{owner}/{repo}/commits"

    const val POST_OAUTH_AUTHORIZE = "https://github.com/login/oauth/authorize"//+"?client_id={clientID}&scope=user&login={loginName}"
    const val POST_OAUTH_ACCESS_TOKEN = "https://github.com/login/oauth/access_token"

}