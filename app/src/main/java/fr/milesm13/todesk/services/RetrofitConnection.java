package fr.milesm13.todesk.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection
{
        private static final String BASE_URL = "http://milesm.fr/";
        private static Retrofit retrofit = null;

        public static Retrofit getClient() {

            if (retrofit == null) {


                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(chain -> {
                            Request request = chain.request().newBuilder()
                                    .header("User-Agent", "Mozilla/5.0")
                                    .build();
                            return chain.proceed(request);
                        })
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

            return retrofit;
        }




}
