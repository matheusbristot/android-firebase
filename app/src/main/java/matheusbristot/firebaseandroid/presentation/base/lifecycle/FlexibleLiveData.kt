package matheusbristot.firebaseandroid.presentation.base.lifecycle

import androidx.lifecycle.MutableLiveData

class FlexibleLiveData<T> : MutableLiveData<T>() {

    private var onActiveCallback: (() -> Unit)? = null
    private var onInactiveCallback: (() -> Unit)? = null

    companion object {
        fun <T> default(t: T?): FlexibleLiveData<T> {
            val liveData = FlexibleLiveData<T>()
            liveData.value = t
            return liveData
        }
    }

    fun doOnActive(onActive: () -> Unit): FlexibleLiveData<T> {
        this.onActiveCallback = onActive
        return this
    }

    fun doOnInactive(onInactive: () -> Unit): FlexibleLiveData<T> {
        this.onInactiveCallback = onInactive
        return this
    }

    override fun onActive() {
        super.onActive()
        onActiveCallback?.invoke()
    }

    override fun onInactive() {
        super.onInactive()
        onInactiveCallback?.invoke()
    }
}