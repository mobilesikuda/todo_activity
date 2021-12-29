package ru.sikuda.mobile.todo_activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class UpdateActivity : AppCompatActivity() {

    //var title_input: EditText? = null
    //var author_input: EditText? = null
    //var pages_input: EditText? = null
    var id: String? = null
    //var title: String? = null
    //var author: String? = null
    //var pages: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val title_input: TextView = findViewById(R.id.title_input2)
        val author_input: TextView  = findViewById(R.id.author_input2)
        val pages_input: TextView  = findViewById(R.id.pages_input2)
        val update_button: Button = findViewById(R.id.update_button)
        val delete_button: Button = findViewById(R.id.delete_button)

        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("author") && intent.hasExtra("pages")
        ) {
            //Getting Data from Intent
            id = intent.getStringExtra("id")
            val title = intent.getStringExtra("title")
            val author = intent.getStringExtra("author")
            val pages = intent.getStringExtra("pages")

            //Setting Intent Data
            title_input.setText(title)
            author_input.setText(author)
            pages_input.setText(pages)
            Log.d("stev", "$title $author $pages")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }

        //Set actionbar title after getAndSetIntentData method
        val ab = supportActionBar
        ab?.setTitle(title)
        update_button.setOnClickListener(View.OnClickListener { //And only then we call this
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            val title = title_input.getText().toString().trim { it <= ' ' }
            val author = author_input.getText().toString().trim { it <= ' ' }
            val pages = pages_input.getText().toString().trim { it <= ' ' }
            myDB.updateData(id!!, title, author, pages)
        })
        delete_button.setOnClickListener(View.OnClickListener { confirmDialog() })
    }

    fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton(
            "Yes"
        ) { dialogInterface, i ->
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            myDB.deleteOneRow(id!!)
            finish()
        }
        builder.setNegativeButton(
            "No"
        ) { dialogInterface, i -> }
        builder.create().show()
    }
}
