package fr.milesm13.todesk;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import fr.milesm13.todesk.components.DevoirAdapter;
import fr.milesm13.todesk.controllers.DevoirController;
import fr.milesm13.todesk.services.ApiServices;
import fr.milesm13.todesk.services.Devoir;
import fr.milesm13.todesk.services.RetrofitConnection;

public class DevoirsActivity extends AppCompatActivity implements DevoirController.DevoirView {

    public ApiServices apiServices;

    DevoirAdapter adapter;

    Button refreshButton;

    public SwipeRefreshLayout swipeRefresh;

    RecyclerView recyclerView;
    View errorLayout;

    public DevoirController ControllerDevoir;
    List<Devoir> devoirs = new ArrayList<>();

    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //Layout
        setContentView(R.layout.activity_devoirs);

        // WTF is this ?
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.devoirs_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // RecyclerView et error layout
        errorLayout = findViewById(R.id.errorLayout);
        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Adapter du recycler view
        adapter = new DevoirAdapter(devoirs);
        recyclerView.setAdapter(adapter);

        // API
        apiServices = RetrofitConnection.getClient().create(ApiServices.class);


        //Ajout du controller de refresh et du refresh quand on swipe
        swipeRefresh = findViewById(R.id.swipeRefresh);
        rootView = findViewById(R.id.devoirs_activity);
        ControllerDevoir = new DevoirController(devoirs, adapter, swipeRefresh, rootView, this);

        swipeRefresh.setOnRefreshListener(() -> {
            ControllerDevoir.refreshDevoirs();
            Log.d("ctrl", "devoirs rechargés");
        });
        //Chargement initial du recyclerView via refresh
        ControllerDevoir.refreshDevoirs();

        //Component

        // Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Bouton refresh
        refreshButton = findViewById(R.id.btnRefresh);
        refreshButton.setOnClickListener(v -> {
            ControllerDevoir.refreshDevoirs();
            Log.d("ctrl", "devoirs rechargés");
        });

        //Rend le logo ToDesk visible
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);
        }
    }
    //Ajout des composants du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void showDevoirs(List<Devoir> devoirs) {
        errorLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);

    }
}