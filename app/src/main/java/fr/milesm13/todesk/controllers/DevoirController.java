package fr.milesm13.todesk.controllers;

import android.util.Log;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import fr.milesm13.todesk.components.DevoirAdapter;
import fr.milesm13.todesk.services.Devoir;
import fr.milesm13.todesk.services.DevoirsUpdater;

public class DevoirController {

    private List<Devoir> devoirs;
    private DevoirAdapter adapter;
    private DevoirsUpdater updater;
    private SwipeRefreshLayout swipeRefresh;

    public DevoirController(List<Devoir> devoirs, DevoirAdapter adapter, SwipeRefreshLayout swipeRefresh) {
        this.devoirs = devoirs;
        this.adapter = adapter;
        this.swipeRefresh = swipeRefresh;
        this.updater = new DevoirsUpdater();
    }

    public void refreshDevoirs() {

        swipeRefresh.setRefreshing(true); // start spinner

        updater.updateDevoirs(new DevoirsUpdater.CallbackDevoirs() {

            @Override
            public void onSuccess(List<Devoir> newDevoirs) {
                devoirs.clear();
                devoirs.addAll(newDevoirs);
                adapter.notifyDataSetChanged();
                adapter.resetAnimation(); // 👈 on reset
                swipeRefresh.setRefreshing(false); // stop spinner
            }

            @Override
            public void onError(String error) {
                swipeRefresh.setRefreshing(false); // stop spinner même en erreur
            }
        });
    }
}