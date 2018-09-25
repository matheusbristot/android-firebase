package matheusbristot.firebaseandroid.presentation.base.lifecycle

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, android.arch.lifecycle.Observer { observer(it) })
}