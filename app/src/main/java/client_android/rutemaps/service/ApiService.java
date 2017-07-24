package client_android.rutemaps.service;

import client_android.rutemaps.model.DataTersimpanModel;
import client_android.rutemaps.model.DefaultResponseModel;
import client_android.rutemaps.model.DoneResponseModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by wahyuade on 18/07/17.
 */

public class ApiService {
    public static String BASE_URL = "http://192.168.43.10:3000";

    public static PostService service_post = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.PostService.class);

    public static GetService service_get = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.GetService.class);

    public interface GetService{
        @GET("/done")
        Call<DoneResponseModel> getHasil();

        @GET("/data")
        Call<DataTersimpanModel> getData();
    }

    public interface PostService{
        @FormUrlEncoded
        @POST("/kirim_sumber")
        Call<DefaultResponseModel> postSumber(@Field("nama") String nama, @Field("stock") String stock, @Field("lat") Double lat, @Field("lng") Double lng);

        @FormUrlEncoded
        @POST("/kirim_tujuan")
        Call<DefaultResponseModel> postTujuan(@Field("nama") String nama, @Field("stock") String stock, @Field("lat") Double lat, @Field("lng") Double lng);
    }
}
