package com.edlo.demogithub.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edlo.demogithub.net.data.Commit
import com.edlo.demogithub.net.data.User
import com.edlo.demogithub.util.Log
import com.example.testcoroutines.net.ApiGutHubHelper
import com.example.testcoroutines.net.data.Repository
import kotlinx.coroutines.launch

class RepositoryViewModel : ViewModel() {

    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getIsLoading(): LiveData<Boolean> { return isLoading }

    private var repository: MutableLiveData<Repository> = MutableLiveData()
    fun getRepo(): LiveData<Repository> { return repository }
    fun setRepo(repo: Repository) { repository.value = repo }

    private var collaborators: MutableLiveData<ArrayList<User>> = MutableLiveData(ArrayList())
    fun getCollaborators(): LiveData<ArrayList<User>> { return collaborators }

    private var commits: MutableLiveData<ArrayList<Commit>> = MutableLiveData(ArrayList())
    fun getCommits(): LiveData<ArrayList<Commit>> { return commits }

    fun reqListCollaborators() {
        repository.value?.let {
            isLoading.value = true
            viewModelScope.launch {
                val listCollaboratorsOfRepo = ApiGutHubHelper.INSTANCE.listCollaboratorsOfRepo(it.owner.loginName, it.name)
                if(listCollaboratorsOfRepo != null) {
                    collaborators.value = listCollaboratorsOfRepo
                } else {
                    Log.d("listCollaboratorsOfRepo fail: " )
                    collaborators.value = ArrayList()
                }
                isLoading.value = false
            }
        }
    }

    fun reqListCommits() {
        repository.value?.let {
            isLoading.value = true
            viewModelScope.launch {
                val listCommitsOfRepo = ApiGutHubHelper.INSTANCE.listCommitsOfRepo(it.owner.loginName, it.name)
                if(listCommitsOfRepo != null) {
                    commits.value = listCommitsOfRepo
                } else {
                    Log.e("listCommitsOfRepo fail: " )
                    commits.value = ArrayList()
                }
                isLoading.value = false
            }
        }
    }
}
