package com.beytullaharslan.notesapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.fragment_todo.untiledText
import java.lang.Exception


class NotesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savenotButton.setOnClickListener {
            saveNote(it)
        }
        arguments?.let {
            val choice = NotesFragmentArgs.fromBundle(it).id
            var information = TodoFragmentArgs.fromBundle(it).information

            if (information.equals(false)){
                //yeni bir yemek eklemeye geldi
                untiledText.setText("")
                noteText.setText("")
                savenotButton.visibility = View.VISIBLE
                deleteNoteButton.visibility = View.INVISIBLE



            } else {
                //daha önce oluşturulan yemeği görmeye geldi
                savenotButton.visibility = View.INVISIBLE
                deleteNoteButton.visibility = View.VISIBLE
                deleteNoteButton.setOnClickListener(){
                    context?.let {
                        val db = it.openOrCreateDatabase("ToNotes",Context.MODE_PRIVATE,null)
                        val cursor = db.execSQL("DELETE  FROM Notes WHERE id = $choice")
                    }
                }




                context?.let {

                    try {
                        val db = it.openOrCreateDatabase("ToNotes",Context.MODE_PRIVATE,null)
                        val cursor = db.rawQuery("SELECT * FROM Notes WHERE id = ?", arrayOf(choice.toString()))
                        val noteUntiledIndex = cursor.getColumnIndex("untiled")
                        val noteExplainIndex = cursor.getColumnIndex("explains")

                        while(cursor.moveToNext()){
                            untiledText.setText(cursor.getString(noteUntiledIndex))
                            noteText.setText(cursor.getString(noteExplainIndex))
                        }

                        cursor.close()

                    } catch (e: Exception){
                        e.printStackTrace()
                    }

                }

            }

        }
    }

    fun saveNote(view: View) {
        println("fonksiyon çagırıldııı")
        //SQLite'a Kaydetme
        val untiled = untiledText.text.toString()
        val explain = noteText.text.toString()
        println("fonksiyon çagırıldııı")
        try{
            context?.let {
                val database = it.openOrCreateDatabase("ToNotes", Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS Notes (id INTEGER PRIMARY KEY, untiled VARCHAR, explains VARCHAR)")
                println("tablo oluşturuldu")
                val sqlString = "INSERT INTO Notes (untiled, explains) VALUES (?, ?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,untiled)
                statement.bindString(2,explain)

                statement.execute()
                println("kayıt yapıldı")
            }

        } catch (e: Exception){
            e.printStackTrace()
        }
        // val action = TodoFragmentDirections.actionTodoToMainMenu()
        //view?.let { Navigation.findNavController(it).navigate(action) }
    }

}