package com.sssakib.mysqlitecrudapplication.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_NONE
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DATABASE_NAME = "userDatabase"
val TABLE_NAME = "userDetails"
val DATABASE_VERSION = 2
val ID = "Id"
val NAME = "Name"
val PHONE = "Phone"
val GENDER = "Gender"
val LOCATION = "Location"
val IMAGE = "Image"



class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_TABLE=
            ("CREATE TABLE $TABLE_NAME ( $ID INTEGER PRIMARY KEY AUTOINCREMENT,$NAME TEXT,$PHONE TEXT, $GENDER TEXT,$LOCATION TEXT, $IMAGE TEXT )")
        db.execSQL(CREATE_TABLE)

    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

            db.execSQL(DROP_TABLE)
            onCreate(db)

    }

    fun insertData(user: User) {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, user.name)
        contentValues.put(PHONE, user.phone)
        contentValues.put(GENDER, user.gender)
        contentValues.put(LOCATION, user.location)
        contentValues.put(IMAGE, user.image)
         db.insert(TABLE_NAME, null, contentValues)
        // var result =
//        if(result == -1.toLong())
//            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
//        else
//            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()

        db.close()
    }

    fun updateUser(id: Int, user: User): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, user.name)
        contentValues.put(PHONE, user.phone)
        contentValues.put(GENDER, user.gender)
        contentValues.put(LOCATION, user.location)
        contentValues.put(IMAGE, user.image)
        return db.update(
            TABLE_NAME,
            contentValues,
            "$ID = ?",
            arrayOf(id.toString())
        )
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(
            TABLE_NAME,
            "$ID = ?",
            arrayOf(id.toString())
        )
    }
    fun readData() : MutableList<User>{
        var list : MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var user = User()
                user.id = result.getString(result.getColumnIndex(ID)).toInt()
                user.name = result.getString(result.getColumnIndex(NAME))
                user.phone = result.getString(result.getColumnIndex(PHONE))
                user.gender = result.getString(result.getColumnIndex(GENDER))
                user.location = result.getString(result.getColumnIndex(LOCATION))
                user.image = result.getString(result.getColumnIndex(IMAGE))


                list.add(user)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

}