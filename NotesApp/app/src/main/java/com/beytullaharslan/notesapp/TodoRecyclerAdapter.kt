package com.beytullaharslan.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_recycler_view.view.*

class TodoRecyclerAdapter (val todoList: ArrayList<String>, val idList : ArrayList<Int>) : RecyclerView.Adapter<TodoRecyclerAdapter.TodoHolder>(){

    class TodoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_recycler_view,parent,false)
        return TodoHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoRecyclerAdapter.TodoHolder, position: Int) {
        holder.itemView.todo_recycler_row_text.text = todoList[position]
        holder.itemView.setOnClickListener {
            val action = MainMenuFragmentDirections.actionMainMenuToTodo(idList[position],true)
            Navigation.findNavController(it).navigate(action)
        }
    }

}