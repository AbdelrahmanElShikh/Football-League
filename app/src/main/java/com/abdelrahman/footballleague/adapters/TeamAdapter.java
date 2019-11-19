package com.abdelrahman.footballleague.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.abdelrahman.footballleague.databinding.TeamItemBinding;
import com.abdelrahman.footballleague.models.Team;

import java.util.Objects;


/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 */
public class TeamAdapter extends PagedListAdapter<Team, TeamAdapter.ViewHolder> {
    private static final String TAG = "TeamAdapter";
    private OnTeamClick listener;

    public TeamAdapter(OnTeamClick listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static DiffUtil.ItemCallback<Team> DIFF_CALLBACK = new DiffUtil.ItemCallback<Team>() {
        @Override
        public boolean areItemsTheSame(@NonNull Team oldItem, @NonNull Team newItem) {
            return oldItem.getTeamId() == newItem.getTeamId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Team oldItem, @NonNull Team newItem) {
            return oldItem.equals(newItem);
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TeamItemBinding binding = TeamItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team team = getItem(position);
        if (team != null) {
            holder.binding.setTeam(team);
        } else {
            Log.e(TAG, "onBindViewHolder: Item Null");
        }

    }

    public interface OnTeamClick {
        void onTeamClick(Integer teamId);

        void onWebsiteClick(String teamWebsite);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TeamItemBinding binding;

        ViewHolder(TeamItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> listener.onTeamClick(Objects.requireNonNull(getItem(getAdapterPosition())).getTeamId()));
            binding.txtWebsite.setOnClickListener(view -> listener.onWebsiteClick(Objects.requireNonNull(getItem(getAdapterPosition())).getWebsite()));
        }
    }

}
