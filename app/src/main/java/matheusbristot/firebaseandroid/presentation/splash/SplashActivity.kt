package matheusbristot.firebaseandroid.presentation.splash

import android.content.Intent
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import matheusbristot.firebaseandroid.presentation.base.view.BaseActivity
import matheusbristot.firebaseandroid.presentation.dashboard.DashboardActivity
import matheusbristot.firebaseandroid.presentation.main.MainActivity

class SplashActivity : BaseActivity(true) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)?.let { firApp ->
            FirebaseAuth.getInstance(firApp)?.let { firAuth ->
                if (firAuth.currentUser != null) {
                    startActivity(Intent(this, DashboardActivity::class.java).addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    finish()
                } else {
                    startActivity(Intent(this, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
                    finish()
                }
            }
        }
    }
}