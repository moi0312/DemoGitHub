package com.edlo.demogithub.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcoroutines.net.ApiGutHubHelper
import com.example.testcoroutines.net.data.Repository
import kotlinx.coroutines.launch

class GitHubRepositoriesViewModel : ViewModel() {

    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getIsLoading(): LiveData<Boolean> { return isLoading }

    private var queryUserName: MutableLiveData<String> = MutableLiveData("")
    fun getQueryUserName(): LiveData<String> { return queryUserName }
    fun setQueryUserName(value: String) {
        queryUserName.value = value
        reqListRepositoriesForUser(queryUserName.value!!)
    }

    private var repositories: MutableLiveData<ArrayList<Repository>> = MutableLiveData(ArrayList())
    fun getRepositories(): LiveData<ArrayList<Repository>> { return repositories }

    fun reqListRepositoriesForUser(username: String) {
        isLoading.value = true
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val listRepositoriesForUser = ApiGutHubHelper.INSTANCE.listRepositoriesForUser(username)
            if(listRepositoriesForUser != null) {
                Log.e("ddddddddd", "result: " + listRepositoriesForUser.toString())
                repositories.value = listRepositoriesForUser
            } else {
                Log.e("ddddddddd", "fail: " )
            }
            isLoading.value = false
        }
    }
}
