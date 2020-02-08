package com.example.resistorcalculation

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_db.*

class DBActivity : AppCompatActivity() {

    private var cursor: Cursor? = null
    private var database: SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)

        database = DBHelper(this).readableDatabase
        cursor = database?.rawQuery("select * from $TABLE_DATA", null)

    }

    override fun onResume() {
        super.onResume()
        val dataDB = arrayOf(RESISTENCE, MIN_POWER)
        val dataAdapter = SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
            cursor, dataDB, intArrayOf(android.R.id.text1, android.R.id.text2), 0)
        listview.adapter = dataAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        database?.close()
        cursor?.close()
    }
}
