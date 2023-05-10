import com.balazs.project.MyApplication
import com.balazs.project.WorkerViewModel
import com.balazs.project.presentation.HomeActivity
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {


    fun inject(activity: HomeActivity)
    fun inject(workerViewModel: WorkerViewModel)
}