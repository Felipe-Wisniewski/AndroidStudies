package br.com.felipewisniewski.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import br.com.felipewisniewski.model.NetCat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var mainAdapter: MainAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.bunchOfCats.observe(this, Observer { onResult(it) })
        viewModel.errorMessage.observe(this, Observer { onError(it) })

        button.setOnClickListener {
            button.setOnClickListener { viewModel.getSomeCats() }
        }
    }

    private fun onResult(bunchOfCats: List<NetCat>?) {
        viewManager = GridLayoutManager(this, 2)
        mainAdapter = MainAdapter(bunchOfCats)
        recyclerCats.layoutManager = viewManager
        recyclerCats.adapter = mainAdapter
    }

    private fun onError(error: String?) {
        error.let {
            if (!it!!.isBlank()) {
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show()
            }
        }
    }
}
