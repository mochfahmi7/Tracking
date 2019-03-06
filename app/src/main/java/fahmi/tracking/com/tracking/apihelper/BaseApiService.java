package fahmi.tracking.com.tracking.apihelper;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface BaseApiService {
    @GET("ambil.php")
    Call <List<Data>> getData(
            @Query("item_type") String item_type

    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                              @Field("password") String password);

//    @FormUrlEncoded
//    @POST("data.php")
//    Call<ResponseBody> dataRequest(@Field("email") String email);


    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerRequest(@Field("nama") String nama,
                                       @Field("email") String email,
                                       @Field("password") String password);
    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseBody> insertRequest(@Field("email") String email,
                                     @Field("no1") String no1,
                                     @Field("no2") String no2,
                                     @Field("no3") String no3,
                                     @Field("no4") String no4,
                                     @Field("no5") String no5,
                                     @Field("no6") String no6,
                                     @Field("no7") String no7,
                                     @Field("pesan") String pesan
                                     );

}
