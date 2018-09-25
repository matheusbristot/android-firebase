package matheusbristot.firebaseandroid.presentation.base.view

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    private val disposables = CompositeDisposable()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        disposables.clear()
    }
}