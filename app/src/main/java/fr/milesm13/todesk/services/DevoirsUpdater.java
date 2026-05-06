package fr.milesm13.todesk.services;

import android.util.Log;

import java.util.List;

import fr.milesm13.todesk.components.DevoirAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DevoirsUpdater {

    public interface CallbackDevoirs {
        void onSuccess(List<Devoir> devoirs);
        void onError(String error);
    }

    public void updateDevoirs(CallbackDevoirs callback) {

        ApiServices apiServices = RetrofitConnection.getClient().create(ApiServices.class);

        apiServices.getDevoirs().enqueue(new Callback<List<Devoir>>() {

            @Override
            public void onResponse(Call<List<Devoir>> call, Response<List<Devoir>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erreur API : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Devoir>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}