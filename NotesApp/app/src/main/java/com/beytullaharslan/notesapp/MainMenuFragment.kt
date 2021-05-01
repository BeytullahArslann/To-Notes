package com.beytullaharslan.notesapp

import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main_menu.*
import java.lang.Exception


class MainMenuFragment : Fragment() {
    var todoUntiledList = ArrayList<String>()
    var todoIdList = ArrayList<Int>()
    var noteUntiledList = ArrayList<String>()
    var noteIdList = ArrayList<Int>()
    private lateinit var todoListAdapter : TodoRecyclerAdapter
    private lateinit var noteListAdapter: NoteRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoListAdapter = TodoRecyclerAdapter(todoUntiledList,todoIdList)
        todoView.layoutManager = LinearLayoutManager(context)
        todoView.adapter = todoListAdapter
        noteListAdapter = NoteRecyclerAdapter(noteUntiledList,noteIdList)
        notesView.layoutManager = LinearLayoutManager(context)
        notesView.adapter = noteListAdapter

        println("fonksiyondan önce")
        sqlVeriAlma()
        println("fonksiyondan sonra")
    }
    fun sqlVeriAlma(){
        println("fonksiyonun başı")
        try {
            println("try başı")
            activity?.let {

                val database = it.openOrCreateDatabase("ToNotes", Context.MODE_PRIVATE,null)

                val todocursor = database.rawQuery("SELECT * FROM todos",null)
                val notecursor = database.rawQuery("SELECT * FROM Notes",null)

                println("letten sonra")
                val todoUntiledIndex = todocursor.getColumnIndex("untiled")
                val todoIdIndex = todocursor.getColumnIndex("id")
                val noteUntiledIndex = notecursor.getColumnIndex("untiled")
                val noteIdIndex = notecursor.getColumnIndex("id")


                todoUntiledList.clear()
                todoIdList.clear()
                noteUntiledList.clear()
                noteUntiledList.clear()
                println("while dan önce")
                while(todocursor.moveToNext()){
                    todoUntiledList.add(todocursor.getString(todoUntiledIndex))
                    todoIdList.add(todocursor.getInt(todoIdIndex))
                }
                while (notecursor.moveToNext()){
                    noteIdList.add(notecursor.getInt(noteIdIndex))
                    noteUntiledList.add(notecursor.getString(noteUntiledIndex))
                }
                println("while dan sonra")

                todoListAdapter.notifyDataSetChanged()
                noteListAdapter.notifyDataSetChanged()

                todocursor.close()
                notecursor.close()
            }




        } catch (e: Exception){
        println("catch")
        }
    }
}