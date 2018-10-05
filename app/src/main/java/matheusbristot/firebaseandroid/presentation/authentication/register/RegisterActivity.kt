package matheusbristot.firebaseandroid.presentation.authentication.register

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.authentication.AuthenticationViewModel
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.base.view.BaseActivity
import matheusbristot.firebaseandroid.presentation.dashboard.DashboardActivity
import matheusbristot.firebaseandroid.presentation.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {

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
                        it.userLogged.observe(this, ::onUserRegistered)
                        it.error.observe(this, ::onGetError)
                        it.shouldProgress.observe(this, ::onVisible)
                    }
                }
            }
        }
        initListenerLogInButton()
        setToolbar(binding.toolbar)
    }

    private fun initListenerLogInButton() {
        authenticationViewModel?.let { authModel ->
            binding.logInButton.setOnClickListener {
                hideSoftKeyboard()
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
        firUser?.let {
            startActivity(Intent(this, DashboardActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            finish()
        }
    }

    private fun onGetError(error: String?) {
        error?.let { description ->
            Snackbar.make(binding.root, description, Snackbar.LENGTH_LONG).show()
        }
    }
}