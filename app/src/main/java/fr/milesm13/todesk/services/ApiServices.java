package fr.milesm13.todesk.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("API/getDevoirs.php")
    Call<List<Devoir>> getDevoirs();
}
