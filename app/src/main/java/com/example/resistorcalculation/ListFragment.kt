package com.example.resistorcalculation


import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.*
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(), View.OnClickListener {

    private var cursor: Cursor? = null
    private var database: SQLiteDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf = inflater.inflate(R.layout.fragment_list, container, false)
        // Inflate the layout for this fragment
        val toolbar1 = inf.findViewById<Toolbar>(R.id.toolbar1)
        (activity as AppCompatActivity).setSupportActionBar(toolbar1)
        setHasOptionsMenu(true)
        database = DBHelper(context as Context).readableDatabase
        cursor = database?.rawQuery("select * from $TABLE_DATA order by $KEY_ID desc", null)
        return inf
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.back ->{
                fragmentManager!!.beginTransaction().replace(R.id.mainCon, CalculateFragment()).commit()
            }
            R.id.delete ->{
                database?.delete(TABLE_DATA, null, null)
                fragmentManager!!.beginTransaction().replace(R.id.mainCon, ListFragment()).commit()
            }
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.back_delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        val dataDB = arrayOf(KEY_ID ,VOLTAGE, F_VOLTAGE, CURRENT, N_O_LEDS, RESISTENCE, MIN_POWER)
        if(cursor != null && cursor?.count!! > 0){
            val dataAdapter = SimpleCursorAdapter(context as Context, R.layout.list,
                cursor, dataDB, intArrayOf(R.id.number, R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6), 0)
            listview.adapter = dataAdapter}
        else {
            val toast = Toast.makeText(context as Context, "Вы не проводили расчётов.", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()}
    }

    override fun onDestroy() {
        super.onDestroy()
        database?.close()
        cursor?.close()
    }

    override fun onClick(v: View?) {}


}
