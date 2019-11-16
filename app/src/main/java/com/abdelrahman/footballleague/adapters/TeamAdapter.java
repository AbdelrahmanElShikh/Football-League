package com.abdelrahman.footballleague.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdelrahman.footballleague.databinding.TeamItemBinding;
import com.abdelrahman.footballleague.models.Team;

import java.util.List;

/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private List<Team> teamList;
    private OnTeamClick listener;

    public TeamAdapter(OnTeamClick listener) {
        this.listener = listener;
    }
    public void setTeams(List<Team> teamList){
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TeamItemBinding binding = TeamItemBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.binding.setTeam(team);
        holder.binding.getRoot().setOnClickListener(view -> listener.onTeamClick(team.getId()));
        holder.binding.txtWebsite.setOnClickListener(view -> listener.onWebsiteClick());
    }

    @Override
    public int getItemCount() {
        if(teamList == null) return 0;
        else return teamList.size();
    }
    public interface OnTeamClick{
        void onTeamClick(Integer teamId);
        void onWebsiteClick();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TeamItemBinding binding;
        public ViewHolder(TeamItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
