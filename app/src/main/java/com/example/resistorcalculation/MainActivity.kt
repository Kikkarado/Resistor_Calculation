package com.example.resistorcalculation

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        calculate.setOnClickListener {
            clickButton()
        }
        rGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.OneSv -> {
                    textView9.visibility = View.GONE
                    KolSv.visibility = View.GONE
                    textView10.visibility = View.GONE
                    imageView.setImageResource(R.drawable.shema1)
                    KolSv.setText("1")
                }

                R.id.ParSoed -> {
                    textView9.visibility = View.VISIBLE
                    KolSv.visibility = View.VISIBLE
                    textView10.visibility = View.VISIBLE
                    imageView.setImageResource(R.drawable.shema3)
                }

                R.id.PosSoed -> {
                    textView9.visibility = View.GONE
                    KolSv.visibility = View.GONE
                    textView10.visibility = View.GONE
                    imageView.setImageResource(R.drawable.shema2)
                    KolSv.setText("1")
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.history)
            startActivity(Intent(this, DBActivity::class.java))

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history, menu)
        return true
    }

    data class Result(

        val r: Double,
        val p: Double

    )

    private fun calculate(): Result {
        val u1 = Nap.getDouble()
        val i = Tok.getDouble() * 0.001
        val sd = KolSv.getDouble()
        val u2 = NapSv.getDouble() * sd
        val u = u1 - u2

        return Result(
            r = u / i,
            p = u * i
        )
    }

    private fun clickButton() {
        val (r, p) = calculate()
        showDialog(r,p)
        addData(r, p)
    }

    private fun showDialog(r: Double, p: Double) {
        AlertDialog.Builder(this)
            .setTitle("Answer")
            .setMessage("Сопротивление: ${"%.3f".format(r)} Ом\nМин. мощность: ${"%.3f".format(p)} Вт")
            //.setIcon(R.drawable.icon5)
            .setCancelable(false)
            .setNegativeButton("Close") { dialog, _ ->
                dialog.cancel()
            }.create()
            .show()
    }

    private fun addData(r: Double, p: Double){
        val database = DBHelper(this).writableDatabase
        val contentValues = ContentValues()
        val cursor = database.query(TABLE_DATA, null, null, null, null, null, null)

        contentValues.put(VOLTAGE, Nap.text.toString())
        contentValues.put(F_VOLTAGE, NapSv.text.toString())
        contentValues.put(CURRENT, Tok.text.toString())
        contentValues.put(N_O_LEDS, KolSv.text.toString())
        contentValues.put(RESISTENCE, r)
        contentValues.put(MIN_POWER, p)

        database.insert(TABLE_DATA, null, contentValues)

        if (cursor.moveToFirst()){
            val idIndex = cursor.getColumnIndex(KEY_ID)
            val idName = cursor.getColumnIndex(VOLTAGE)
            val idSurname = cursor.getColumnIndex(F_VOLTAGE)
            val idDate = cursor.getColumnIndex(CURRENT)
            val idGender = cursor.getColumnIndex(N_O_LEDS)
            val idHeight = cursor.getColumnIndex(RESISTENCE)
            val idWeight = cursor.getColumnIndex(MIN_POWER)

            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) + "; voltage = " + cursor.getString(idName)
                        + "; forward_voltage = " + cursor.getString(idSurname) +
                        "; current = " + cursor.getString(idDate) + "; number_of_LEDs = " + cursor.getString(idGender)
                        + "; resistance = " + cursor.getString(idHeight) + "; min_power = " + cursor.getString(idWeight))
            } while (cursor.moveToNext())
        }else Log.d("mLog", "0 rows")
        cursor.close()

        DBHelper(this).close()
    }
}

fun EditText.getDouble() = text.toString().toDouble()