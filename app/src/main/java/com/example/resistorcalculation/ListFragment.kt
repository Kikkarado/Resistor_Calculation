package com.example.resistorcalculation

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(), View.OnClickListener {

    companion object{
        lateinit var dbHelper: DBHelper
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inf = inflater.inflate(R.layout.fragment_recycler, container, false)
        // Inflate the layout for this fragment
        val toolbar1 = inf.findViewById<Toolbar>(R.id.toolbar1)
        val recyclerView = inf.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context as Context)
        (activity as AppCompatActivity).setSupportActionBar(toolbar1)
        setHasOptionsMenu(true)

        dbHelper = DBHelper(context as Context)

        val historyList = dbHelper.getHistory(context as Context)
        val adapter = MyAdapter(historyList)
        recyclerView.layoutManager = LinearLayoutManager(context as Context, LinearLayout.VERTICAL, false)
        recyclerView.adapter = adapter

        return inf
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.back ->{
                fragmentManager!!.beginTransaction().replace(R.id.mainCon, CalculateFragment()).commit()
            }
            R.id.delete ->{
                val database: SQLiteDatabase = dbHelper.writableDatabase
                database.delete(TABLE_DATA, null, null)
                fragmentManager!!.beginTransaction().replace(R.id.mainCon, ListFragment()).commit()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.back_delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onClick(v: View?) {}

}
