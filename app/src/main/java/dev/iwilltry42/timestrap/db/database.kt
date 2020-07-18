package dev.iwilltry42.timestrap.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.iwilltry42.timestrap.content.tasks.Task
import dev.iwilltry42.timestrap.content.tasks.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Task class
@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
abstract class TimestrapRoomDB : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    private class TimestrapDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.taskDao())
                }
            }
        }

        suspend fun populateDatabase(taskDao: TaskDao) {
            // Delete all content here.
            taskDao.deleteAll()

            // Add sample words.
            val task = Task(0, "test-task","0.00", "https://m.timestrap.com/api/tasks/0/")
            taskDao.insert(task)

            // TODO: Add your own words!
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TimestrapRoomDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TimestrapRoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimestrapRoomDB::class.java,
                    "timestrap_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(TimestrapDBCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
