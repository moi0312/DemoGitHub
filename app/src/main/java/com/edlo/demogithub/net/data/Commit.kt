package com.edlo.demogithub.net.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * https://developer.github.com/v3/repos/commits/
 * */

data class Commit (
    var url: String,
    var sha: String,
    @SerializedName("node_id") var nodeId: String?,
    @SerializedName("comments_url") var commentsUrl: String?,
    var commit: CommitDetail?,
    var committer: User?,
    var parents: List<Commit>?
): Serializable

data class CommitDetail (
    var url: String,
    @SerializedName("html_url") var htmlUrl: String,
    var author: Committer,
    var committer: Committer,
    var message: String,
    var verification: VerificationInfo
): Serializable

data class Committer (
    var name: String,
    var email: String,
    var date: String
): Serializable

data class VerificationInfo (
    var verified: Boolean,
    var reason: String,
    var signature: String,
    var payload: String
): Serializable

/** {
"url": "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
"sha": "6dcb09b5b57875f334f61aebed695e2e4193db5e",
"node_id": "MDY6Q29tbWl0NmRjYjA5YjViNTc4NzVmMzM0ZjYxYWViZWQ2OTVlMmU0MTkzZGI1ZQ==",
"html_url": "https://github.com/octocat/Hello-World/commit/6dcb09b5b57875f334f61aebed695e2e4193db5e",
"comments_url": "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e/comments",
"commit": {
    "url": "https://api.github.com/repos/octocat/Hello-World/git/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
    "author": { "name": "Monalisa Octocat", "email": "support@github.com", "date": "2011-04-14T16:00:49Z" },
    "committer": { "name": "Monalisa Octocat", "email": "support@github.com", "date": "2011-04-14T16:00:49Z" },
    "message": "Fix all the bugs",
    "tree": { "url": "https://api.github.com/repos/octocat/Hello-World/tree/6dcb09b5b57875f334f61aebed695e2e4193db5e",
        "sha": "6dcb09b5b57875f334f61aebed695e2e4193db5e" },
    "comment_count": 0,
    "verification": { "verified": false, "reason": "unsigned", "signature": null, "payload": null }
},
"author": { "login": "octocat", "id": 1, "node_id": "MDQ6VXNlcjE=",
    "avatar_url": "https://github.com/images/error/octocat_happy.gif", "gravatar_id": "",
    "type": "User", "site_admin": false },
"committer": { "login": "octocat", "id": 1, "node_id": "MDQ6VXNlcjE=",
    "avatar_url": "https://github.com/images/error/octocat_happy.gif", "gravatar_id": "",
    "url": "https://api.github.com/users/octocat", "type": "User", "site_admin": false },
"parents": [
    { "url": "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
        "sha": "6dcb09b5b57875f334f61aebed695e2e4193db5e" }
]
} */