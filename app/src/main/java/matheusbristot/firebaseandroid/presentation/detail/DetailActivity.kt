package matheusbristot.firebaseandroid.presentation.detail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import matheusbristot.firebaseandroid.data.entity.Event
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.base.view.BaseActivity
import matheusbristot.firebaseandroid.presentation.databinding.ActivityDetailsBinding

class DetailActivity : BaseActivity(true) {

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        FirebaseApp.initializeApp(this)?.let { firApp ->
            FirebaseDatabase.getInstance(firApp).let { firDatabase ->
                (intent?.extras?.get(EVENT_EXTRA) as? Event)?.let { event ->
                    binding.title.text = event.title
                    binding.description.text = event.description
                    DetailViewModel(firDatabase).let { dashViewModel ->
                        viewModel = dashViewModel
                        lifecycle.addObserver(viewModel)
                        dashViewModel.pathImage.observe(this, ::onGetImage)
                    }
                }
            }
        }
        setToolbar(binding.toolbar)
    }

    private fun onGetImage(path: String?) {
        val requestOptions = RequestOptions()
        val rounded = false
        val centerCrop = false
        val usePlaceholder = true
        when {
            rounded == true -> requestOptions.circleCrop()
            centerCrop == true -> requestOptions.centerCrop()
//            else -> requestOptions.centerInside()
        }
        if (usePlaceholder) {
            requestOptions.placeholder(getDrawable(R.drawable.ic_download_image)).error(getDrawable(R.drawable.ic_problem))
        }
        path?.let {
            Glide.with(this).load(it).apply(requestOptions).into(binding.pathImageView)
        }
    }

    companion object {

        val EVENT_EXTRA = "EVENT_EXTRA"

        fun start(context: Context, event: Event) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(EVENT_EXTRA, event)
            })
        }
    }
}