package matheusbristot.firebaseandroid.presentation

import android.content.Intent
import androidx.multidex.MultiDexApplication
import matheusbristot.firebaseandroid.presentation.main.MainActivity

class FirebaseAndroidApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
//        startActivity(Intent(this, MainActivity::class.java))
    }
}