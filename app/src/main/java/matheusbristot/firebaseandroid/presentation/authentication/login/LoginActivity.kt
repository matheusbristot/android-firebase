package matheusbristot.firebaseandroid.presentation.authentication.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import matheusbristot.firebaseandroid.presentation.BuildConfig
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.authentication.AuthenticationViewModel
import matheusbristot.firebaseandroid.presentation.base.REMOTE_APP_VERSION
import matheusbristot.firebaseandroid.presentation.base.REMOTE_DAILY_MESSAGE
import matheusbristot.firebaseandroid.presentation.base.REMOTE_FORCE_CHECK_VERSION
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.base.view.BaseActivity
import matheusbristot.firebaseandroid.presentation.databinding.ActivityLoginBinding


class LoginActivity : BaseActivity(true) {

    private lateinit var binding: ActivityLoginBinding

    private var viewModel: AuthenticationViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        // Inicializa o FirebaseApp e FirebaseAuth
        FirebaseApp.initializeApp(this)?.let { firApp ->
            FirebaseAuth.getInstance(firApp)?.let { firAuth ->
                AuthenticationViewModel(firAuth).let { model ->
                    viewModel = model
                    viewModel?.let {
                        lifecycle.addObserver(it)
                        it.userLogged.observe(this, ::onGetUser)
                        it.error.observe(this, ::onGetError)
                        it.shouldProgress.observe(this, ::onVisible)
                    }
                }
            }
        }
        initListenerLogInButton()
        initVerifyBaseVersions()
        getDailyMessage()
        setToolbar(binding.toolbar)
    }

    private fun getDailyMessage() {
        FirebaseRemoteConfig.getInstance()?.let { remoteConfig ->
            remoteConfig.getString(REMOTE_DAILY_MESSAGE)?.let { message ->
                binding.dailyMessageTextView.visibility = View.VISIBLE
                binding.dailyMessageTextView.text = message
            }
        }
    }

    private fun initListenerLogInButton() {
        viewModel?.let { authModel ->
            binding.logInButton.setOnClickListener {
                hideSoftKeyboard()
                authModel.signIn(binding.emailTextInputEditText.text.toString(), binding.passwordTextInputEditText.text.toString())
            }
        }
    }

    private fun onGetError(error: String?) {
        error?.let { description ->
            Snackbar.make(binding.root, description, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun onVisible(visible: Boolean?) {
        visible?.let {
            binding.statusProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun onGetUser(firUser: FirebaseUser?) {
        firUser?.let { Log.e("onGetUser", it.email) }
    }

    private fun initVerifyBaseVersions() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        if (remoteConfig.getBoolean(REMOTE_FORCE_CHECK_VERSION)) {
            val latestVersion = remoteConfig.getString(REMOTE_APP_VERSION)
            val currentAppVersion = BuildConfig.VERSION_NAME
            if (!TextUtils.equals(latestVersion, currentAppVersion))
                Toast.makeText(this, "Olá! Existe uma atualização pendente", Toast.LENGTH_LONG).show()
        }
    }
}