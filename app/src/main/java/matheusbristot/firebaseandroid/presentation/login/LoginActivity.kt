package matheusbristot.firebaseandroid.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp

class LoginActivity : AppCompatActivity() {

//    private lateinit var binding

    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Init FirebaseApp
        FirebaseApp.initializeApp(this)?.let {
            viewModel = LoginViewModel(it)
            viewModel?.let {
                lifecycle.addObserver(it)
            }
        }
    }
}