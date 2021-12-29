package ru.sikuda.mobile.todo_activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val title_input: TextView = findViewById(R.id.title_input)
        val author_input: TextView = findViewById(R.id.author_input)
        val pages_input: TextView = findViewById(R.id.pages_input)
        val add_button: Button = findViewById(R.id.add_button)

        add_button.setOnClickListener(View.OnClickListener {
            val myDB = MyDatabaseHelper(this@AddActivity)
            myDB.addBook(
                title_input.getText().toString().trim { it <= ' ' },
                author_input.getText().toString().trim { it <= ' ' },
                Integer.valueOf(pages_input.getText().toString().trim { it <= ' ' })
            )
        })
    }
}