package matheusbristot.firebaseandroid.presentation.main

import android.os.Bundle
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
        mainViewModel = MainViewModel("Você esta na MainActivity")
        mainViewModel?.let {
            lifecycle.addObserver(it)
            it.text.observe(this, ::onText)
        }
    }


    private fun onText(text: String?) {
        text?.let {
            binding.firstTextView.text = it
        }
    }
}