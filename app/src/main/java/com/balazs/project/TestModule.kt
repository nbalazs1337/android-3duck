/*
import com.balazs.project.networking.ApiServiceImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object TestModule {
    @Provides
    fun provideApiService(apiServiceImplementation: ApiServiceImplementation):ApiService {
        return apiServiceImplementation
    }

    @Provides
    fun provideApiServiceImplementation(
        apiService: ApiService,
        authenticator: OAuth2Authenticator
    ): ApiServiceImplementation {
        return ApiServiceImplementation(apiService, authenticator)
    }
}
*/
