package dev.iwilltry42.timestrap.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.iwilltry42.timestrap.data.network.response.TasksResponse

class TimestrapRemoteSourceImpl(
    private val timestrapApiService: TimestrapApiService
) : TimestrapRemoteSource {

    private val _remoteTasks = MutableLiveData<TasksResponse>()
    override val remoteTasks: LiveData<TasksResponse>
        get() = _remoteTasks

    override suspend fun fetchTasks() {
        try {
            val fetchedTasks = timestrapApiService
                .getTasks()
                .await()
            _remoteTasks.postValue(fetchedTasks)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Network Connection", e)
        }
    }

}