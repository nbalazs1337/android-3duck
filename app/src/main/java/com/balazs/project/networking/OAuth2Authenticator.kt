/*
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class OAuth2Authenticator(private val apiKey: String, private val secret: String) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val credentials = Credentials.basic(apiKey, secret)
        return response.request.newBuilder()
            .header("Authorization", credentials)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .method(response.request.method, response.request.body)
            .build()
    }
}
*/
