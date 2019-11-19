package com.abdelrahman.footballleague.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdelrahman.footballleague.databinding.SquadItemBinding;
import com.abdelrahman.footballleague.models.Squad;

import java.util.List;

/**
 * Football League
 *
 * @author: Abdel-Rahman El-Shikh :) on 11/18/2019
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private List<Squad> players;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SquadItemBinding binding = SquadItemBinding.inflate(layoutInflater, parent, false);
        return new PlayerAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Squad player = players.get(position);
        holder.binding.setPlayer(player);
    }

    @Override
    public int getItemCount() {
        return (players == null) ? 0 : players.size();
    }

    public void setPlayers(List<Squad> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SquadItemBinding binding;

        ViewHolder(@NonNull SquadItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
