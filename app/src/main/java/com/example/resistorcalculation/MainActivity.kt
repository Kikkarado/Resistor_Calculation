package com.example.resistorcalculation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
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
                    LN8.visibility = View.GONE
                    imageView.setImageResource(R.drawable.shema1)
                    KolSv.setText("1")
                }

                R.id.PosSoed -> {
                    LN8.visibility = View.VISIBLE
                    imageView.setImageResource(R.drawable.shema3)
                }

                R.id.ParSoed -> {
                    LN8.visibility = View.GONE
                    imageView.setImageResource(R.drawable.shema2)
                    KolSv.setText("1")
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.history)
            Toast.makeText(this, "History", Toast.LENGTH_SHORT).show()

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
}

fun EditText.getDouble() = text.toString().toDouble()