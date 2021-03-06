package matheusbristot.firebaseandroid.presentation.main

import android.arch.lifecycle.*
import matheusbristot.firebaseandroid.presentation.base.lifecycle.FlexibleLiveData

class MainViewModel(
        private val name: String
) : ViewModel(), LifecycleObserver {

    val text: LiveData<String> get() = textLiveData
    private val textLiveData: FlexibleLiveData<String> = FlexibleLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        textLiveData.value = name
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        textLiveData.value = "Ciclo de vida: ON_START"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        textLiveData.value = "Ciclo de vida: ON_RESUME"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        textLiveData.value = "Ciclo de vida: ON_PAUSE"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        textLiveData.value = "Ciclo de vida: ON_DESTROY"
    }
}