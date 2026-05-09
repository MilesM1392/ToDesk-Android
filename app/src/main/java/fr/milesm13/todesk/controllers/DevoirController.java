package fr.milesm13.todesk.controllers;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.milesm13.todesk.components.DevoirAdapter;
import fr.milesm13.todesk.services.Devoir;
import fr.milesm13.todesk.services.DevoirsUpdater;

public class DevoirController {

    private List<Devoir> devoirs;
    private DevoirAdapter adapter;
    private DevoirsUpdater updater;
    private SwipeRefreshLayout swipeRefresh;

    private View rootView;

    private DevoirView view;

    public DevoirController(List<Devoir> devoirs, DevoirAdapter adapter, SwipeRefreshLayout swipeRefresh, View rootView, DevoirView view) {
        this.devoirs = devoirs;
        this.adapter = adapter;
        this.swipeRefresh = swipeRefresh;
        this.updater = new DevoirsUpdater();
        this.rootView = rootView;
        this.view = view;
    }
    public interface DevoirView {
        void showDevoirs(List<Devoir> devoirs);
        void showError(String message);
    }
    public void refreshDevoirs() {

        swipeRefresh.setRefreshing(true); // start spinner

        updater.updateDevoirs(new DevoirsUpdater.CallbackDevoirs() {

            @Override
            public void onSuccess(List<Devoir> newDevoirs) {
                view.showDevoirs(newDevoirs);
                devoirs.clear();
                devoirs.addAll(newDevoirs);
                adapter.notifyDataSetChanged();
                adapter.resetAnimation(); // on reset
                swipeRefresh.setRefreshing(false); // stop spinner
            }

            @Override
            public void onError(String error) {
                view.showError(error);
                swipeRefresh.setRefreshing(false); // stop spinner même en erreur

                Log.d("SNACKBAR", "Erreur reçue : " + error);
                Snackbar snackbar = Snackbar.make(rootView, error, Snackbar.LENGTH_LONG);
                snackbar.show();


            }
        });
    }
}