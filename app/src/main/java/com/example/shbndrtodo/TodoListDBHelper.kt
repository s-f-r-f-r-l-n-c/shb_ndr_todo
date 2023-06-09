package com.example.shbndrtodo
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper;

class TodoListDBHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "AppDatabase"
        private val TABLE_TODOS = "TodosTable"
        private val COL_ID = "id"
        private val COL_TITLE = "title"
        private val COL_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val SQL = ("CREATE TABLE " + TABLE_TODOS + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " TEXT," + COL_DESCRIPTION + " TEXT" + ")")
        db!!.execSQL(SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val SQL = ("DROP TABLE IF EXISTS " + TABLE_TODOS)
        db!!.execSQL(SQL)
        onCreate(db)
    }

    fun addTodo(todo: TodoModel):Long{
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(COL_TITLE, todo.title)
        cv.put(COL_DESCRIPTION, todo.description)

        val ret = db.insert(TABLE_TODOS, null, cv)

        db.close()
        return ret
    }

    fun readAllTodos():List<TodoModel>{
        val todoList: ArrayList<TodoModel> = ArrayList<TodoModel>()
        val SQL = "SELECT  * FROM ${TABLE_TODOS}"

        var cursor: Cursor? = null
        val db = this.readableDatabase

        try{
            cursor = db.rawQuery(SQL, null)
        }catch (e: SQLiteException) {
            return ArrayList()
        }

        var id: Int
        var title: String
        var description: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0)
                title = cursor.getString(1)
                description = cursor.getString(2)

                val todo= TodoModel(id = id, title = title, description = description)
                todoList.add(todo)
            } while (cursor.moveToNext())
        }

        return todoList
    }

    fun updateTodo(todo: TodoModel):Int{
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(COL_ID, todo.id)
        cv.put(COL_TITLE, todo.title)
        cv.put(COL_DESCRIPTION,todo.description )

        val ret = db.update(TABLE_TODOS, cv,"id=" + todo.id,null)

        db.close()

        return ret
    }

    fun deleteTodo(todo: TodoModel):Int{
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(COL_ID, todo.id)

        val ret = db.delete(TABLE_TODOS,"id=" + todo.id,null)

        db.close()

        return ret
    }
}