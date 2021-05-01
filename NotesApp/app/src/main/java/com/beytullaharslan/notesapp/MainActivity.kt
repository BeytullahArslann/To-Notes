package com.beytullaharslan.notesapp

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var status = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (status == 0) {

            if (item.itemId == R.id.add_note_item) {
                val action = MainMenuFragmentDirections.actionMainMenuToNotes()
                Navigation.findNavController(this, R.id.fragment).navigate(action)
                status = 1
            } else if (item.itemId == R.id.add_todo_item) {
                val action = MainMenuFragmentDirections.actionMainMenuToTodo(0,false)
                Navigation.findNavController(this, R.id.fragment).navigate(action)
                status = 2
            }

        }
        if (status == 1) {
            if (item.itemId == R.id.add_note_item) {
                val action = NotesFragmentDirections.actionNotesSelf()
                Navigation.findNavController(this, R.id.fragment).navigate(action)
            }
            else if (item.itemId == R.id.add_todo_item) {
            val action = NotesFragmentDirections.actionNotesToTodo(0,false)
            Navigation.findNavController(this, R.id.fragment).navigate(action)
            status = 2
        }
        }
        if (status == 2){
            if(item.itemId == R.id.add_note_item){
                val action = TodoFragmentDirections.actionTodoToNotes()
                Navigation.findNavController(this, R.id.fragment).navigate(action)
                status = 1
            }

            else if (item.itemId == R.id.add_todo_item){
                val action = TodoFragmentDirections.actionTodoSelf(0,false)
                Navigation.findNavController(this, R.id.fragment).navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
        }
        return  false
    }

    override fun onBackPressed() {
        status = 0
        super.onBackPressed()
    }

}