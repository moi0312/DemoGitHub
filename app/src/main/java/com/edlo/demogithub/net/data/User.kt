package com.edlo.demogithub.net.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
    @SerializedName("login") var loginName: String,
    @SerializedName("avatar_url") var avatarUrl: String,
    var type: String
): Serializable
/** {
"login": "octocat",
"id": 1,
"node_id": "MDQ6VXNlcjE=",
"avatar_url": "https://github.com/images/error/octocat_happy.gif",
"gravatar_id": "",
"url": "https://api.github.com/users/octocat",
"type": "User",
"site_admin": false,
"permissions": { "pull": true, "push": true, "admin": false }
} */