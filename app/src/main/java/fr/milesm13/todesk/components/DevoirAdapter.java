package fr.milesm13.todesk.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.milesm13.todesk.R;
import fr.milesm13.todesk.services.Devoir;

public class DevoirAdapter extends RecyclerView.Adapter<DevoirAdapter.ViewHolder> {

    private List<Devoir> devoirs;

    private int lastPosition = -1;

    public DevoirAdapter(List<Devoir> devoirs) {
        this.devoirs = devoirs;
    }

    @Override
    public int getItemCount() {
        return devoirs.size();
    }

    public void resetAnimation() {
        lastPosition = -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, date;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_devoir, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Devoir d = devoirs.get(position);
        holder.title.setText(d.getTitle());
        holder.content.setText(d.getDescription());
        holder.date.setText(d.getDate());

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.cards_anim);
            animation.setStartOffset(position * 100L);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }



    }


