package matheusbristot.firebaseandroid.presentation.login

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import matheusbristot.firebaseandroid.presentation.R
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
}