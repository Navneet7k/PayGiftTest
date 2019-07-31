package navneet.com.paygifttest;

import navneet.com.paygifttest.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Navneet Krishna on 30/07/19.
 */
interface RequestInterface {
    @GET("v2/top-headlines?country=us&apiKey=9bce785607fc41c3b24cb48efe043d2f")
    Call<NewsResponse> getNewsFeed(@Query("pageSize")int pageSize,@Query("page")int page);
}
