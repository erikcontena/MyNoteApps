package com.example.mynoteapps.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mynoteapps.database.Note
import com.example.mynoteapps.repository.Repository

class MainViewModel(application: Application): ViewModel() {
    private val mRepository = Repository(application)

    fun getAllNotes(sort: String): LiveData<PagedList<Note>> =LivePagedListBuilder(mRepository.getAllNotes(sort),20).build()
}