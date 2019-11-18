package com.abdelrahman.footballleague.ui.destinations.teamDetails;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.abdelrahman.footballleague.R;
import com.abdelrahman.footballleague.adapters.PlayerAdapter;
import com.abdelrahman.footballleague.databinding.TeamDetailsBinding;
import com.abdelrahman.footballleague.ui.MainActivity;

import java.util.Objects;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 */
public class TeamDetailsFragment extends Fragment {
    private static final String TAG = "TeamDetailsFragment";
    private TeamDetailsBinding binding;
    private TeamDetailsViewModel mViewModel;
    private PlayerAdapter mAdapter;
    private int id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.team_details,container,false);
        assert getArguments() != null;
        id = TeamDetailsFragmentArgs.fromBundle(getArguments()).getTeamId();
        setBackButton();
        mViewModel = ViewModelProviders.of(this).get(TeamDetailsViewModel.class);
        mViewModel.init();
        mAdapter = new PlayerAdapter();
        binding.recyclerViewSquad.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewSquad.setHasFixedSize(true);
        binding.recyclerViewSquad.setNestedScrollingEnabled(false);
        binding.recyclerViewSquad.setAdapter(mAdapter);
        getTeamDetails();
        return binding.getRoot();
    }

    private void setBackButton() {
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void getTeamDetails() {
        mViewModel.getTeamById(id).observe(getViewLifecycleOwner(),team -> {
            switch (team.getStatus()){
                case SUCCESS:
                    binding.setTeam(team.getData());
                    mAdapter.setPlayers(team.getData().getSquad());
                    break;
                case ERROR:
                    Log.e(TAG, "getTeamDetails: Error "+team.getApiError().getMessage() );
                    Toast.makeText(getContext(), team.getApiError().getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                case Failure:
                    Toast.makeText(getActivity(), team.getApiException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "getTeams: Failure" + team.getApiException().getLocalizedMessage());
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }



}
