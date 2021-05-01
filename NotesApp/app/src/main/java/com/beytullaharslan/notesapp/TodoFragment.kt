package com.beytullaharslan.notesapp

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.fragment_todo.saveTodoButton
import kotlinx.android.synthetic.main.fragment_todo.untiledText
import java.lang.Exception


@Suppress("UNREACHABLE_CODE")
class TodoFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*saveButton.setOnClickListener() {
            println("butona tıklandı")
            saveTodo() }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_todo, container, false)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveTodoButton.setOnClickListener {
            saveTodo(it)
        }
        arguments?.let {
            val choice = TodoFragmentArgs.fromBundle(it).id
            var gelenBilgi = TodoFragmentArgs.fromBundle(it).information

            if (gelenBilgi.equals(false)){
                //yeni bir yemek eklemeye geldi
                untiledText.setText("")
                explainText.setText("")
                timeText.setText("")
                dateText.setText("")
                saveTodoButton.visibility = View.VISIBLE
                deleteTodoButton.visibility = View.INVISIBLE



            } else {
                //daha önce oluşturulan yemeği görmeye geldi
                saveTodoButton.visibility = View.INVISIBLE
                deleteTodoButton.visibility = View.VISIBLE
                deleteTodoButton.setOnClickListener(){
                    context?.let {
                        val db = it.openOrCreateDatabase("ToNotes",Context.MODE_PRIVATE,null)
                        val cursor = db.execSQL("DELETE  FROM Todos WHERE id = $choice")
                    }
                }



                context?.let {

                    try {

                        val db = it.openOrCreateDatabase("ToNotes",Context.MODE_PRIVATE,null)
                        val cursor = db.rawQuery("SELECT * FROM Todos WHERE id = ?", arrayOf(choice.toString()))

                        val todoUntiledIndex = cursor.getColumnIndex("untiled")
                        val todoExplainIndex = cursor.getColumnIndex("explains")
                        val todoTimeIndex = cursor.getColumnIndex("time")
                        val todoDateIndex = cursor.getColumnIndex("date")

                        while(cursor.moveToNext()){
                            untiledText.setText(cursor.getString(todoUntiledIndex))
                            explainText.setText(cursor.getString(todoExplainIndex))
                            timeText.setText(cursor.getString(todoTimeIndex))
                            dateText.setText(cursor.getString(todoDateIndex))
                        }

                        cursor.close()

                    } catch (e: Exception){
                        e.printStackTrace()
                    }

                }

            }

        }

    }

    public fun saveTodo(view: View) {
        println("fonksiyon çagırıldı")
        //SQLite'a Kaydetme
        val untiled = untiledText.text.toString()
        val explain = explainText.text.toString()
        val time = timeText.text.toString()
        val date = dateText.text.toString()



        try{
            context?.let {
                val database = it.openOrCreateDatabase("ToNotes", Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS Todos (id INTEGER PRIMARY KEY, untiled VARCHAR, explains VARCHAR, time VARCHAR, date VARCHAR)")
                println("tablo oluşturuldu")
                val sqlString = "INSERT INTO todos (untiled, explains, time , date) VALUES (?, ?, ?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,untiled)
                statement.bindString(2,explain)
                statement.bindString(3,time)
                statement.bindString(4,date)
                statement.execute()
                println("kayıt yapıldı")
            }

        } catch (e: Exception){
            e.printStackTrace()
        }
        // val action = TodoFragmentDirections.actionTodoToMainMenu()
        //view?.let { Navigation.findNavController(it).navigate(action) }
        }
    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_note_item){
            val action = TodoDirections.actionTodoToNotes()
            activity?.let { Navigation.findNavController(it,R.id.fragment).navigate(action) }
        }
        else if (item.itemId == R.id.add_todo_item){
            val action = TodoDirections.actionTodoSelf()
            activity?.let { Navigation.findNavController(it,R.id.fragment).navigate(action) }
        }
        return super.onOptionsItemSelected(item)
    }*/



    }


