import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.balazs.project.persistence.dao.WorkerDao
import com.balazs.project.persistence.localApi.WorkerApi
import com.balazs.project.presentation.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        requireNotNull(context) { "Context must not be null" }
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "my-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWorkerDao(appDatabase: AppDatabase): WorkerDao {
        return appDatabase.workerDao()
    }

    @Provides
    @Singleton
    fun provideWorkerApi(workerDao: WorkerDao): WorkerApi {
        return WorkerApi(workerDao)
    }

    @Provides
    fun provideWorkerViewModelFactory(workerApi: WorkerApi): ViewModelProvider.Factory {
        return WorkerViewModelFactory(workerApi)
    }

}
