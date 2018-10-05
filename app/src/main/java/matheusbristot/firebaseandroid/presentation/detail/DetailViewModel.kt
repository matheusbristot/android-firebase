package matheusbristot.firebaseandroid.presentation.detail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import matheusbristot.firebaseandroid.presentation.base.lifecycle.FlexibleLiveData
import matheusbristot.firebaseandroid.presentation.base.view.BaseViewModel

class DetailViewModel(private val firebaseDatabase: FirebaseDatabase) : BaseViewModel() {

    val pathImage: LiveData<String> get() = pathImageLiveData
    private val pathImageLiveData: FlexibleLiveData<String> = FlexibleLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        firebaseDatabase.getReference("image").child("path").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(data: DataSnapshot) {
                (data.value as? String)?.let(pathImageLiveData::setValue)
            }
        })
    }

}