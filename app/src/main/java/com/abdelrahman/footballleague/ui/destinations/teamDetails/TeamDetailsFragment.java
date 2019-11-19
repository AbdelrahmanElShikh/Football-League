package com.abdelrahman.footballleague.ui.destinations.teamDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdelrahman.footballleague.R;
import com.abdelrahman.footballleague.adapters.PlayerAdapter;
import com.abdelrahman.footballleague.databinding.TeamDetailsBinding;
import com.abdelrahman.footballleague.ui.MainActivity;

import java.util.Objects;

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
                    binding.txtSquad.setVisibility(View.VISIBLE);
                    binding.setTeam(team.getData());
                    mAdapter.setPlayers(team.getData().getSquad());
                    break;
                case ERROR:
                    binding.txtSquad.setVisibility(View.GONE);
                    Log.e(TAG, "getTeamDetails: Error "+team.getApiError().getMessage() );
                    Toast.makeText(getContext(), team.getApiError().getMessage(), Toast.LENGTH_SHORT).show();
                    break;
                case Failure:
                    binding.txtSquad.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), team.getApiException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "getTeams: Failure" + team.getApiException().getLocalizedMessage());
                    showFailureDialog();
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }
private void showFailureDialog(){
    new AlertDialog.Builder(Objects.requireNonNull(getContext()))
            .setTitle(getString(R.string.app_name))
            .setMessage("we are sorry we couldn't establish connection please check internet connection")
            .setPositiveButton(R.string.retry, (dialog, which) -> {
                binding.progressBar.setVisibility(View.VISIBLE);
                getTeamDetails();
            })
            .setNegativeButton(R.string.back, (dialogInterface, i) ->
                Objects.requireNonNull(getActivity()).onBackPressed()
            )
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
}


}
