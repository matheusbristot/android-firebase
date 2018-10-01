package matheusbristot.firebaseandroid.presentation.authentication.register

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.authentication.AuthenticationViewModel
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private var authenticationViewModel: AuthenticationViewModel? = null

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        FirebaseApp.initializeApp(this)?.let { firApp ->
            FirebaseAuth.getInstance(firApp)?.let { firAuth ->
                AuthenticationViewModel(firAuth).let { model ->
                    authenticationViewModel = model
                    authenticationViewModel?.let {
                        lifecycle.addObserver(it)
                        it.userRegistered.observe(this, ::onUserRegistered)
                        it.error.observe(this, ::onGetError)
                        it.shouldProgress.observe(this, ::onVisible)
                    }
                }
            }
        }
        initListenerLogInButton()
    }

    private fun initListenerLogInButton() {
        authenticationViewModel?.let { authModel ->
            binding.logInButton.setOnClickListener {
                (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).apply { toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0) }
                authModel.createUser(binding.emailTextInputEditText.text.toString(), binding.passwordTextInputEditText.text.toString(), binding.confirmPasswordTextInputEditText.text.toString())
            }
        }
    }

    private fun onVisible(visible: Boolean?) {
        visible?.let {
            binding.statusProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun onUserRegistered(firUser: FirebaseUser?) {
        firUser?.let { Log.e("onGetUser", it.email) }
    }

    private fun onGetError(error: String?) {
        error?.let { description ->
            Snackbar.make(binding.root, description, Snackbar.LENGTH_LONG).show()
        }
    }
}