package com.example.resistorcalculation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DB_VERSION = 1
val DB_NAME = "Calculation_data"
val TABLE_DATA = "Data"

val KEY_ID = "_id"
val VOLTAGE = "voltage"
val F_VOLTAGE = "forward_voltage"
val CURRENT = "current"
val N_O_LEDS = "number_of_LEDs"
val RESISTENCE = "resistance"
val MIN_POWER = "min_power"

class DBHelper(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_DATA($KEY_ID integer primary key, $VOLTAGE double, " +
                "$F_VOLTAGE double, $CURRENT double, $N_O_LEDS double, $RESISTENCE double, $MIN_POWER double)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE_DATA")
        onCreate(db)
    }

}