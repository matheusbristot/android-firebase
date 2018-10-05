package matheusbristot.firebaseandroid.presentation

import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import matheusbristot.firebaseandroid.presentation.base.REMOTE_APP_VERSION
import matheusbristot.firebaseandroid.presentation.base.REMOTE_DAILY_MESSAGE
import matheusbristot.firebaseandroid.presentation.base.REMOTE_FORCE_CHECK_VERSION

class FirebaseAndroidApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startRemoteConfig()
    }

    private fun startRemoteConfig() {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        // Aqui você pode colocar itens default antes de ser publicado no Remote Config do Firebase
        val remoteConfigDefaults = mutableMapOf<String, Any>()
        remoteConfigDefaults[REMOTE_APP_VERSION] = "1.0.0"
        remoteConfigDefaults[REMOTE_FORCE_CHECK_VERSION] = false
        remoteConfigDefaults[REMOTE_DAILY_MESSAGE] = ""

        firebaseRemoteConfig.fetch(60) // a cada minuto vou buscar
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) firebaseRemoteConfig.activateFetched()
                }.addOnFailureListener { exception -> Crashlytics.logException(exception) }
    }
}