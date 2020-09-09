package com.example.mynoteapps.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mynoteapps.database.Note
import com.example.mynoteapps.repository.Repository

class NoteUpdateViewModel(application: Application): ViewModel() {

    private val mRepository: Repository = Repository(application)

    fun insert(note: Note){
        mRepository.insert(note)
    }
    fun update(note: Note) {
        mRepository.update(note)
    }
    fun delete(note: Note) {
        mRepository.delete(note)
    }

}