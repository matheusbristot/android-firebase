package matheusbristot.firebaseandroid.presentation.main

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.databinding.ActivityMainBinding
import matheusbristot.firebaseandroid.presentation.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel("Você esta na MainActivity")
        mainViewModel?.let {
            lifecycle.addObserver(it)
            it.text.observe(this, ::onText)
            it.output.observe(this, ::onInput)
        }
        binding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mainViewModel?.getInputText(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nothing to do here
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Nothing to do here
            }
        })
        binding.goToLoginButton.setOnClickListener { goToLogin() }
    }


    private fun onText(text: String?) {
        text?.let {
            binding.firstTextView.text = it
        }
    }

    private fun onInput(text: String?) {
        text?.let {
            binding.resultTextView.text = it
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java)
                .apply { addFlags(FLAG_ACTIVITY_CLEAR_TASK) })
    }
}