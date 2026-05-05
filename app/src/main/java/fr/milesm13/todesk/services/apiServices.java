package fr.milesm13.todesk.services;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface apiServices {
    @GET("API/getDevoirs.php")
    Call<List<Devoir>> getDevoirs();
}
