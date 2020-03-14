package com.edlo.demogithub.net.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccessToken(
    @SerializedName("access_token") var accessToken: String,
    @SerializedName("token_type") var tokenType: String,
    var scope: String
): Serializable {

//    fun toJsonString(): String {
//        return Gson().toJson(this)
//    }

}