package matheusbristot.firebaseandroid.presentation.login

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import matheusbristot.firebaseandroid.presentation.BuildConfig
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.base.REMOTE_APP_VERSION
import matheusbristot.firebaseandroid.presentation.base.REMOTE_DAILY_MESSAGE
import matheusbristot.firebaseandroid.presentation.base.REMOTE_FORCE_CHECK_VERSION
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        // Inicializa o FirebaseApp e FirebaseAuth
        FirebaseApp.initializeApp(this)?.let { firApp ->
            FirebaseAuth.getInstance(firApp)?.let { firAuth ->
                LoginViewModel(firAuth).let { model ->
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
        initListenerTextInput()
        initListenerLogInButton()
        initVerifyBaseVersions()
        getDailyMessage()
    }

    private fun getDailyMessage() {
        FirebaseRemoteConfig.getInstance()?.let { remoteConfig ->
            remoteConfig.getString(REMOTE_DAILY_MESSAGE)?.let { message ->
                binding.dailyMessageTextView.visibility = View.VISIBLE
                binding.dailyMessageTextView.text = message
            }
        }
    }

    private fun initListenerTextInput() {
        binding.emailTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel?.getTextInput(s.toString(), false)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Nothing to do here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Nothing to do here
            }
        })
        binding.passwordTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel?.getTextInput(s.toString(), true)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Nothing to do here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Nothing to do here
            }
        })
    }

    private fun initListenerLogInButton() {
        viewModel?.let { model ->
            binding.logInButton.setOnClickListener {
                (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
                    toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0)
                }
                model.logIn()
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