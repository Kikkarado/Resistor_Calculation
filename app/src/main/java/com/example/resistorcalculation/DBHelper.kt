package com.example.resistorcalculation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

const val DB_VERSION = 1
const val DB_NAME = "Calculation_data"
const val TABLE_DATA = "Data"

const val KEY_ID = "_id"
const val VOLTAGE = "voltage"
const val F_VOLTAGE = "forward_voltage"
const val CURRENT = "current"
const val N_O_LEDS = "number_of_LEDs"
const val RESISTENCE = "resistance"
const val MIN_POWER = "min_power"


class DBHelper(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_DATA($KEY_ID integer primary key, $VOLTAGE double, \" +\n" +
                "                \"$F_VOLTAGE double, $CURRENT double, $N_O_LEDS double, $RESISTENCE double, $MIN_POWER double)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE_DATA")
        onCreate(db)
    }

    fun getHistory(context: Context): ArrayList<AllHistory>{
        val qry = "select * from $TABLE_DATA order by $KEY_ID desc"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val allh = ArrayList<AllHistory>()

        if (cursor.count == 0)
            Toast.makeText(context, "Записей не найдено.", Toast.LENGTH_SHORT).show() else
        {while (cursor.moveToNext()){
            val his = AllHistory()
            his.hid = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            his.voltage = cursor.getDouble(cursor.getColumnIndex(VOLTAGE))
            his.f_voltage = cursor.getDouble(cursor.getColumnIndex(F_VOLTAGE))
            his.current = cursor.getDouble(cursor.getColumnIndex(CURRENT))
            his.n_o_leds = cursor.getDouble(cursor.getColumnIndex(N_O_LEDS))
            his.resistence = cursor.getDouble(cursor.getColumnIndex(RESISTENCE))
            his.min_power = cursor.getDouble(cursor.getColumnIndex(MIN_POWER))
            allh.add(his)
        }
            if (cursor.count == 1) Toast.makeText(context, "Найдена ${cursor.count} запись", Toast.LENGTH_SHORT).show() else
                if(cursor.count >= 2 || cursor.count <= 4) {Toast.makeText(context, "Найдено ${cursor.count} записи", Toast.LENGTH_SHORT).show()}
                    if (cursor.count > 4) {Toast.makeText(context, "Найдено ${cursor.count} записей", Toast.LENGTH_SHORT).show()}
        }
        cursor.close()
        db.close()
        return allh
    }
}