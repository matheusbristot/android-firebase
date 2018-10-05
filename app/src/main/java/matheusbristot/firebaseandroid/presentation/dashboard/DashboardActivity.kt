package matheusbristot.firebaseandroid.presentation.dashboard

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import matheusbristot.firebaseandroid.data.entity.Event
import matheusbristot.firebaseandroid.presentation.R
import matheusbristot.firebaseandroid.presentation.adapter.EventAdapter
import matheusbristot.firebaseandroid.presentation.base.lifecycle.observe
import matheusbristot.firebaseandroid.presentation.base.view.BaseActivity
import matheusbristot.firebaseandroid.presentation.databinding.ActivityDashboardBinding
import matheusbristot.firebaseandroid.presentation.detail.DetailActivity


class DashboardActivity : BaseActivity(true) {

    private lateinit var viewModel: DashboardViewModel

    private lateinit var binding: ActivityDashboardBinding

    private lateinit var progressDialog: ProgressDialog

    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        progressDialog = ProgressDialog(this)
        // Inicializa o FirebaseApp e FirebaseDatabase
        FirebaseApp.initializeApp(this)?.let { firApp ->
            FirebaseDatabase.getInstance(firApp).let { firDatabase ->
                DashboardViewModel(firDatabase).let { dashViewModel ->
                    viewModel = dashViewModel
                    lifecycle.addObserver(viewModel)
                    viewModel.events.observe(this, ::onEvents)
                    viewModel.errorDatabaseMessage.observe(this, ::onDatabaseError)
                    viewModel.shouldShowLoading.observe(this, ::onShowLoading)
                }
            }
        }
        setToolbar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        adapter = EventAdapter()
        binding.eventRecyclerView.adapter = adapter
        binding.eventRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun onEvents(events: List<Event>?) {
        events?.let { adapter.setEvents(it, ::openDetail) }
    }

    private fun openDetail(event: Event) {
        DetailActivity.start(this, event)
    }

    private fun onShowLoading(isShow: Boolean?) {
        isShow?.let {
            if (it) {
                progressDialog.setMessage(getString(R.string.global_wait))
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }

        }
    }

    private fun onDatabaseError(error: String?) {
        error?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).setAction("Tentar novamente") { viewModel.retry() }
        }
    }
}