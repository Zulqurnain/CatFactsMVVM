package com.jutt.catfactsfeeddemo.viewmodels

import android.content.Context
import androidx.hilt.Assisted
import androidx.lifecycle.*
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.architecture.Event
import com.jutt.catfactsfeeddemo.data.models.AnimalFact
import com.jutt.catfactsfeeddemo.data.repositories.CatsFactsRepository
import com.jutt.catfactsfeeddemo.data.repositories.DatabaseRepository
import com.jutt.catfactsfeeddemo.data.repositories.ResourcesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val resourcesRepository: ResourcesRepository,
    private val catsFactsRepository: CatsFactsRepository,
    val database: DatabaseRepository,
    val savedState: SavedStateHandle
) : ViewModel() {

    object Events {
        const val NAVIGATE_TO_HOME: String = "NAVIGATE_TO_HOME"
    }

    private val _showLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> get() = _showLoader

    private val _navigate = MutableLiveData<Event<String>>()
    val navigate: LiveData<Event<String>> get() = _navigate

    private val _popupMessage = MutableLiveData<Event<String>>()
    val popupMessage: LiveData<Event<String>> get() = _popupMessage

    private val _successMessage = MutableLiveData<Event<String>>()
    val successMessage: LiveData<Event<String>> get() = _successMessage

    val catFactsList: LiveData<List<AnimalFact>> get() = database.getAllFacts()

    private val _toolbarVisible = MutableLiveData<Boolean>()
    val toolbarVisible: LiveData<Boolean> get() = _toolbarVisible

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun start() {
        navigateToHome()
    }

    fun navigateToHome() {
        navigateToSection(Events.NAVIGATE_TO_HOME)
    }

    fun navigateToSection(eventContent: String) {
        _navigate.value = Event.create(content = eventContent)
    }

    fun syncCatFactsFromServer() {
        viewModelScope.launch {
            _showLoader.value = true
            val response = catsFactsRepository.fetchCatFactsFromAPI()
            val listReturned = response.data ?: arrayListOf()
            if (response.success && listReturned.isNotEmpty()) {
                database.clearAllFacts()
                database.upsertCatFact(*listReturned.toTypedArray())
            } else {
                _popupMessage.value = Event.create(
                    content = response.message
                        ?: resourcesRepository.getString(R.string.error_message_server_error)
                )
            }
            _showLoader.value = false
        }
    }

    fun setToolbarVisibility(visible: Boolean) {
        _toolbarVisible.postValue(visible)
    }
}