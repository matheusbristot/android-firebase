package matheusbristot.firebaseandroid.presentation.main

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.databinding.ActivityMainBinding
import matheusbristot.firebaseandroid.presentation.authentication.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this // inicialização da main no xml
        mainViewModel = MainViewModel("Você esta na MainActivity")
        mainViewModel?.let {
            lifecycle.addObserver(it)
            it.output.observe(this, ::onInput)
            it.text.observe(this, ::onText) // usamos uma extensions para reduzir o código em toda Activity
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

    //Devemos nos atentar, que este método não pode ser privado, se não o binding dará falha de compilação.
    //E tbm, como estamos passando main::onClickTextView no xml, devemos deixar view: View como parametro,
    //por conta do setOnClickListener, passar a view
    fun onClickText(view: View) {
        Toast.makeText(this, "[onClickText]", Toast.LENGTH_LONG).show()
    }
}