package fr.milesm13.todesk;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import fr.milesm13.todesk.components.DevoirAdapter;
import fr.milesm13.todesk.services.Devoir;
import fr.milesm13.todesk.services.RetrofitConnection;
import fr.milesm13.todesk.services.apiServices;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public apiServices apiServices;

    RecyclerView recyclerView;
    DevoirAdapter adapter;
    List<Devoir> devoirs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Utilisation de retrofit et appel de l'API
        apiServices = RetrofitConnection.getClient().create(apiServices.class);
        chargerDevoirs();

        //Header de l'appli
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        //Mise en place du recyclerView

        // Layout manager (OBLIGATOIRE)
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fake data pour test
        Devoir d1 = new Devoir("machin", "truc", "15-12-2026", "");
        // ⚠️ ajoute un constructeur ou setters si besoin

        devoirs.add(d1);

        adapter = new DevoirAdapter(devoirs);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void chargerDevoirs() {


        apiServices.getDevoirs().enqueue(new Callback<List<Devoir>>() {
            @Override
            public void onResponse(Call<List<Devoir>> call, Response<List<Devoir>> response) {
                System.out.println(response.body());

                if (response.isSuccessful() && response.body() != null) {
                    devoirs.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    List<Devoir> devoirs = response.body();


                    for (Devoir devoir : devoirs) {
                        Log.d("API_TEST", devoir.getTitle());
                    }
                } else {
                    Log.e("API_TEST", "Réponse vide ou erreur");
                    System.out.println("foiré");
                }
            }
            @Override
            public void onFailure(Call<List<Devoir>> call, Throwable t) {
                Log.e("API_TEST", "Erreur : " + t.getMessage());
            }
        });
    }

}