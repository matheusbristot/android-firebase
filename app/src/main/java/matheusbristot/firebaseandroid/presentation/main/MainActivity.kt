package matheusbristot.firebaseandroid.presentation.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.authentication.login.LoginActivity
import matheusbristot.firebaseandroid.presentation.authentication.register.RegisterActivity
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.databinding.ActivityMainBinding

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
            it.text.observe(this, ::onText) // usamos uma extensions para reduzir o código em toda Activity
        }
        binding.goToLoginButton.setOnClickListener(::goToLogin)
        binding.goToRegisterButton.setOnClickListener(::goToSignUp)
    }

    private fun onText(text: String?) {
        text?.let {
            binding.firstTextView.text = it
        }
    }

    private fun goToLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun goToSignUp(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    //Devemos nos atentar, que este método não pode ser privado, se não o binding dará falha de compilação.
    //E tbm, como estamos passando main::onClickTextView no xml, devemos deixar view: View como parametro,
    //por conta do setOnClickListener, passar a view
    fun onClickText(view: View) {
        Toast.makeText(this, "[onClickText]", Toast.LENGTH_LONG).show()
    }
}