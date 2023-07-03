package com.example.eduputinf151873

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


data class Game(
    val name: String,
    val yearPublished: String,
    val objectId: String,
    val image: String
)
data class Extension(
    val name: String,
    val objectId: String,
    val image: String
)
public var DATABASE_VERSION_GLOBAL = 1

class ProfileDataBase(
    context: Context,
    name: String?, factory: SQLiteDatabase.CursorFactory?, version:Int ):SQLiteOpenHelper(context,
    DATABASE_NAME,null, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = DATABASE_VERSION_GLOBAL
        val DATABASE_NAME = "profileDB"
        val TABLE_NAME = "profiles"
        val COLUMN_ID = "_id"
        val COLUMN_NICKNAME = "nickname"
        val COLUMN_AMOUNT_OF_GAMES = "amount_of_games"
        val COLUMN_AMOUNT_OF_EXTENSIONS = "amount_of_extensions"
        val COLUMN_LAST_SYNCHRONIZED = "last_synchronized"
    }


    override fun onCreate(db: SQLiteDatabase) {
        DATABASE_VERSION_GLOBAL += 1
        val CREATE_PROFILE_TABLE = ("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NICKNAME + " TEXT, " + COLUMN_AMOUNT_OF_GAMES + " INTEGER, " + COLUMN_AMOUNT_OF_EXTENSIONS + " INTEGER, " + COLUMN_LAST_SYNCHRONIZED + " TEXT)")
        db.execSQL(CREATE_PROFILE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        DATABASE_VERSION_GLOBAL += 1
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addProfile(nickname:String,amount_of_games:Int,amount_of_extensions:Int,last_synchronized:String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NICKNAME,nickname)
        values.put(COLUMN_AMOUNT_OF_GAMES,amount_of_games)
        values.put(COLUMN_AMOUNT_OF_EXTENSIONS,amount_of_extensions)
        values.put(COLUMN_LAST_SYNCHRONIZED,last_synchronized)
        db.insert(TABLE_NAME,null,values)
        //db.close()
    }

    fun getSynchroDate():String{
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)
        var lastSynchro = ""
        if(cursor.moveToFirst()){
            cursor.moveToFirst()
            lastSynchro = cursor.getString(4)
            cursor.close()
        }
        //db.close()
        return lastSynchro
    }


}
class GamesDataBase(

    context: Context,
    name: String?, factory: SQLiteDatabase.CursorFactory?, version:Int ):SQLiteOpenHelper(context,
    DATABASE_NAME,null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = DATABASE_VERSION_GLOBAL + 1
        val DATABASE_NAME = "gamesDB"
        val TABLE_NAME = "Games"
        val COLUMN_ID = "_id"
        val COLUMN_ORIGINAL_TITLE = "original_title"
        val COLUMN_YEAR_OF_RELEASE = "year_of_release"
        val COLUMN_BGG_ID = "bgg_id"
        val COLUMN_IMAGE_URL = "image_url"
    }

    override fun onCreate(db: SQLiteDatabase) {
        DATABASE_VERSION_GLOBAL += 1
        val CREATE_GAME_TABLE =
            ("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_ORIGINAL_TITLE + " TEXT, " + COLUMN_YEAR_OF_RELEASE + " INTEGER, " + COLUMN_BGG_ID + " INTEGER, " + COLUMN_IMAGE_URL + " TEXT)")
        db.execSQL(CREATE_GAME_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        DATABASE_VERSION_GLOBAL += 1
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addGame(original_title: String, year_of_release: String, bgg_id: Int, image_url: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ORIGINAL_TITLE, original_title)
        values.put(COLUMN_YEAR_OF_RELEASE, year_of_release)
        values.put(COLUMN_BGG_ID, bgg_id)
        values.put(COLUMN_IMAGE_URL, image_url)
        db.insert(TABLE_NAME, null, values)
        //db.close()
    }

    fun getAllGames(): List<Game> {
        val gameList = mutableListOf<Game>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORIGINAL_TITLE))
            val yearPublished =
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_YEAR_OF_RELEASE))
            val objectId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BGG_ID))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL))

            val game = Game(name, yearPublished, objectId, image)
            gameList.add(game)
        }
        cursor.close()
        return gameList
    }
}

class ExtensionsDataBase(
    context: Context,
    name: String?, factory: SQLiteDatabase.CursorFactory?, version:Int ):SQLiteOpenHelper(context,
    DATABASE_NAME,null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = DATABASE_VERSION_GLOBAL + 2
        val DATABASE_NAME = "extendsionsDB"
        val TABLE_NAME = "Extensions"
        val COLUMN_ID = "_id"
        val COLUMN_ORIGINAL_TITLE = "original_title"
        val COLUMN_BGG_ID = "bgg_id"
        val COLUMN_IMAGE_URL = "image_url"
    }

    override fun onCreate(db: SQLiteDatabase) {
        DATABASE_VERSION_GLOBAL += 1
        val CREATE_EXTENSIONS_TABLE =
            ("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_ORIGINAL_TITLE + " TEXT, " + COLUMN_BGG_ID + " INTEGER, " + COLUMN_IMAGE_URL + " TEXT)")
        db.execSQL(CREATE_EXTENSIONS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        DATABASE_VERSION_GLOBAL += 1
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addExtension(original_title: String, bgg_id: Int, image_url: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ORIGINAL_TITLE, original_title)
        values.put(COLUMN_BGG_ID, bgg_id)
        values.put(COLUMN_IMAGE_URL, image_url)
        db.insert(TABLE_NAME, null, values)
        //db.close()
    }

    fun getAllExtensions(): List<Extension> {
        val extensionList = mutableListOf<Extension>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORIGINAL_TITLE))
            val objectId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BGG_ID))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL))

            val extension = Extension(name, objectId, image)
            extensionList.add(extension)
        }
        cursor.close()
        return extensionList
    }

}
