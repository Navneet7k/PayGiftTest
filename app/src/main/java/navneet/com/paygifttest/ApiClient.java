package navneet.com.paygifttest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Navneet Krishna on 31/07/19.
 */
public class ApiClient {
    //use of this pattern helps us to maintain only a single instance of retrofit
    private static final String base_url="http://newsapi.org/";
    private static Retrofit retrofit=null;

    public static Retrofit getApiClient() {
        if (retrofit==null) {
            retrofit=new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();  //creating a retrofit object and loading it with gson converter factory for json to pojo conversions easy

            // retrofit also helps us to pass an okhttp client instance which we may use for setting network timeouts, create trust mangers or enabling tlsv1.2 which is disabled by default on and 19 and below
        }
        return retrofit;
    }
}
