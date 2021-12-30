package ru.sikuda.mobile.todo_activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var empty_imageview: ImageView? = null
    var no_data: TextView? = null

    private var myDB: MyDatabaseHelper? = null
    var book_id = ArrayList<String>()
    var book_title = ArrayList<String>()
    var book_author = ArrayList<String>()
    var book_pages = ArrayList<String>()
    var customAdapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView  = findViewById(R.id.recyclerView)
        val add_button: FloatingActionButton = findViewById(R.id.add_button)
        empty_imageview = findViewById(R.id.empty_imageview)
        no_data = findViewById(R.id.no_data)
        add_button.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        })
        myDB = MyDatabaseHelper(this@MainActivity)
        storeDataInArrays()
        customAdapter = CustomAdapter(
            this@MainActivity, this, book_id, book_title, book_author, book_pages)
        recyclerView.setAdapter(customAdapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this@MainActivity))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }

    fun storeDataInArrays() {
        val cursor = myDB?.readAllData()
        if (cursor?.count == 0) {
            empty_imageview!!.visibility = View.VISIBLE
            no_data!!.visibility = View.VISIBLE
        } else {
            while (cursor!!.moveToNext()) {
                book_id.add(cursor.getString(0))
                book_title.add(cursor.getString(1))
                book_author.add(cursor.getString(2))
                book_pages.add(cursor.getString(3))
            }
            empty_imageview!!.visibility = View.GONE
            no_data!!.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            confirmDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all Data?")
        builder.setPositiveButton(
            "Yes"
        ) { dialogInterface, i ->
            val myDB = MyDatabaseHelper(this@MainActivity)
            myDB.deleteAllData()
            //Refresh Activity
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(
            "No"
        ) { dialogInterface, i -> }
        builder.create().show()
    }
}

