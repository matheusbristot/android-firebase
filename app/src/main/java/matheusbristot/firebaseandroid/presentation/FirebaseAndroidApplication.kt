package matheusbristot.firebaseandroid.presentation

import android.support.multidex.MultiDexApplication

class FirebaseAndroidApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
//        startActivity(Intent(this, MainActivity::class.java))
    }
}