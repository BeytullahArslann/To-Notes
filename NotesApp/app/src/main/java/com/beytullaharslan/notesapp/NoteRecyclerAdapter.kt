package com.beytullaharslan.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_recycler_view.view.*
import kotlinx.android.synthetic.main.todo_recycler_view.view.*
import kotlinx.android.synthetic.main.todo_recycler_view.view.todo_recycler_row_text

class NoteRecyclerAdapter(val notesList: ArrayList<String>, val idList : ArrayList<Int>) : RecyclerView.Adapter<NoteRecyclerAdapter.NoteHolder>() {
    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRecyclerAdapter.NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.note_recycler_view,parent,false)
        return NoteRecyclerAdapter.NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteRecyclerAdapter.NoteHolder, position: Int) {
        holder.itemView.note_recycler_row_text.text = notesList[position]
        holder.itemView.setOnClickListener {
            val action = MainMenuFragmentDirections.actionMainMenuToNotes(idList[position],true)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}