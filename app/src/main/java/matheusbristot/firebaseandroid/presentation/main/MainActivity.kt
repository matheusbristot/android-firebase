package matheusbristot.firebaseandroid.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import matheusbristot.firebaseandroid.presentation.R
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
    }

    private fun onText(text: String?) {
        text?.let {
            binding.firstTextView.text = it
        }
    }

    //Devemos nos atentar, que este método não pode ser privado, se não o binding dará falha de compilação.
    //E tbm, como estamos passando main::onClickTextView no xml, devemos deixar view: View como parametro,
    //por conta do setOnClickListener, passar a view
    fun onClickText(view: View) {
        Toast.makeText(this, "[onClickText]", Toast.LENGTH_LONG).show()
    }
}