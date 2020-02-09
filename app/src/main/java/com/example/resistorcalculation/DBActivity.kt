package com.example.resistorcalculation

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_db.*

class DBActivity : AppCompatActivity() {

    private var cursor: Cursor? = null
    private var database: SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)
        setSupportActionBar(toolbar1)

        database = DBHelper(this).readableDatabase
        cursor = database?.rawQuery("select * from $TABLE_DATA order by $KEY_ID desc", null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.back ->{
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.delete ->{
                database?.delete(TABLE_DATA, null, null)
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_delete, menu)
        return true
    }
    override fun onResume() {
        super.onResume()
        val dataDB = arrayOf(KEY_ID ,VOLTAGE, F_VOLTAGE, CURRENT, N_O_LEDS, RESISTENCE, MIN_POWER)
        if(cursor != null && cursor?.count!! > 0){
        val dataAdapter = SimpleCursorAdapter(this, R.layout.list,
            cursor, dataDB, intArrayOf(R.id.number, R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6), 0)
        listview.adapter = dataAdapter}
        else {
           val toast = Toast.makeText(this, "Вы не проводили расчётов.", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()}
    }

    override fun onDestroy() {
        super.onDestroy()
        database?.close()
        cursor?.close()
    }
}
