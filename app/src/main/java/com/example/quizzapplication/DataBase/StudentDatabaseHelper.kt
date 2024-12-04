package com.example.quizzapplication.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentDetails.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_CLASS = "class"
        private const val COLUMN_SCHOOL = "school"
        private const val COLUMN_CITY = "city"
        private const val COLUMN_CONTACT = "contact"


        private const val table = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_CLASS TEXT,
                $COLUMN_SCHOOL TEXT,
                $COLUMN_CITY TEXT,
                $COLUMN_CONTACT TEXT
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(table)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    fun insertStudent(
        name: String,
        className: String,
        school: String,
        city: String,
        contact: String
    ): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_CLASS, className)
            put(COLUMN_SCHOOL, school)
            put(COLUMN_CITY, city)
            put(COLUMN_CONTACT, contact)
        }
        return db.insert(TABLE_NAME, null, values)
    }
}



