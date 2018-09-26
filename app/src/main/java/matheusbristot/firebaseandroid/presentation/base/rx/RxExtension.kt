package matheusbristot.firebaseandroid.presentation.base.rx

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.with(): Single<T> = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())