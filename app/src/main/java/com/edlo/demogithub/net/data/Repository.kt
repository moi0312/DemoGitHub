package com.example.testcoroutines.net.data

import com.edlo.demogithub.net.data.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * https://developer.github.com/v3/search/#search-repositories
 */
data class Repository(
    var id: String,
    @SerializedName("node_id") var nodeId: String,
    var name: String,
    @SerializedName("full_name") var fullName: String,
    var private: Boolean,
    var owner: User,
    @SerializedName("html_url") var htmlUrl: String,
    var description: String,
    var url: String,
    @SerializedName("updated_at") var updatedAt: String,
    var language: String
): Serializable
