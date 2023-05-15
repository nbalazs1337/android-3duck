import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.balazs.project.persistence.dao.WorkerDao
import com.balazs.project.persistence.model.WorkerDB

@Database(entities = [WorkerDB::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workerDao(): WorkerDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val migration1to2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Define your migration logic here
                        // For example, you can execute SQL statements to alter tables, add columns, etc.
                        database.execSQL("ALTER TABLE workers ADD COLUMN new_column INTEGER DEFAULT 0 NOT NULL")
                    }
                }

                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my-database"
                )
                    .addMigrations(migration1to2)
                    .build()

                instance = newInstance
                newInstance
            }
        }
    }
}
