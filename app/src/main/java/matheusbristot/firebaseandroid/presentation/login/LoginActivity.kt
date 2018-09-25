package matheusbristot.firebaseandroid.presentation.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        //Init FirebaseApp
        FirebaseApp.initializeApp(this)?.let {
            viewModel = LoginViewModel(it)
            viewModel?.let {
                lifecycle.addObserver(it)
            }
        }
    }
}