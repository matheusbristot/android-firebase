package matheusbristot.firebaseandroid.presentation.dashboard

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import matheusbristot.firebaseandroid.data.entity.Event
import matheusbristot.firebaseandroid.presentation.base.lifecycle.FlexibleLiveData
import matheusbristot.firebaseandroid.presentation.base.view.BaseViewModel


class DashboardViewModel(
        private val firebaseDatabase: FirebaseDatabase
) : BaseViewModel() {

    val events: LiveData<List<Event>> get() = eventsLiveData
    val errorDatabaseMessage: LiveData<String> get() = errorDatabaseMessageLiveData
    val shouldShowLoading: LiveData<Boolean> get() = shouldShowLoadingLiveData

    private val eventsLiveData: FlexibleLiveData<List<Event>> = FlexibleLiveData()
    private val errorDatabaseMessageLiveData: FlexibleLiveData<String> = FlexibleLiveData()
    private val shouldShowLoadingLiveData: FlexibleLiveData<Boolean> = FlexibleLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        getEvents()
    }

    fun retry() {
        getEvents()
    }

    private fun getEvents() {
        shouldShowLoadingLiveData.value = true
        firebaseDatabase.getReference("events").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                shouldShowLoadingLiveData.value = false
                errorDatabaseMessageLiveData.value = error.message
            }

            override fun onDataChange(data: DataSnapshot) {
                shouldShowLoadingLiveData.value = false
                data.children.mapNotNull { event ->
                    event.getValue<Event>(Event::class.java)
                }.let(eventsLiveData::setValue)
            }
        })
    }
}