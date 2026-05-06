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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public apiServices apiServices;

    DevoirAdapter adapter;
    List<Devoir> devoirs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //Layout
        setContentView(R.layout.activity_main);

        // WTF is this ?
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);
        }

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //Adapter du recycler view
        adapter = new DevoirAdapter(devoirs);
        recyclerView.setAdapter(adapter);

        // API
        apiServices = RetrofitConnection.getClient().create(apiServices.class);
        chargerDevoirs();
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

                if (response.isSuccessful() && response.body() != null) {
                    devoirs.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("API_TEST", "Réponse vide ou erreur");
                }
            }
            @Override
            public void onFailure(Call<List<Devoir>> call, Throwable t) {
                Log.e("API_TEST", "Erreur : " + t.getMessage());
            }
        });
    }

}