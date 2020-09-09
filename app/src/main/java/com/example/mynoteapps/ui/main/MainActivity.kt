package com.example.mynoteapps.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynoteapps.R
import com.example.mynoteapps.database.Note
import com.example.mynoteapps.helper.SortUtils
import com.example.mynoteapps.helper.ViewModelFactory
import com.example.mynoteapps.ui.insert.NoteUpdateActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NotePagedListAdapter
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllNotes(SortUtils.NEWEST).observe(this, noteObserver)

        adapter = NotePagedListAdapter(this@MainActivity)
        rv_notes.layoutManager = LinearLayoutManager(this)
        rv_notes.setHasFixedSize(true)
        rv_notes.adapter = adapter

        fab_add.setOnClickListener { view ->
            if (view.id == R.id.fab_add) {
                val intent = Intent(this@MainActivity, NoteUpdateActivity::class.java)
                startActivityForResult(intent, NoteUpdateActivity.REQUEST_ADD)
            }
        }
    }

    private val noteObserver = Observer<PagedList<Note>> { noteList ->
        if (noteList != null) {
            adapter.submitList(noteList)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == NoteUpdateActivity.REQUEST_ADD) {
                if (resultCode == NoteUpdateActivity.RESULT_ADD) {
                    showSnackbarMessage(getString(R.string.added))
                }
            } else if (requestCode == NoteUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == NoteUpdateActivity.RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed))
                } else if (resultCode == NoteUpdateActivity.RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted))
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.getItemId()) {
            R.id.action_newest -> sort = SortUtils.NEWEST
            R.id.action_oldest -> sort = SortUtils.OLDEST
            R.id.action_random -> sort = SortUtils.RANDOM
        }
        mainViewModel.getAllNotes(sort).observe(this, noteObserver)
        item.setChecked(true)
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_notes, message, Snackbar.LENGTH_SHORT).show()
    }


}
