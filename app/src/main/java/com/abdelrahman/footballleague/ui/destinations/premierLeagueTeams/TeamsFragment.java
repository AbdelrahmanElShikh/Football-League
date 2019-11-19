package com.abdelrahman.footballleague.ui.destinations.premierLeagueTeams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdelrahman.footballleague.MyApplication;
import com.abdelrahman.footballleague.R;
import com.abdelrahman.footballleague.adapters.TeamAdapter;
import com.abdelrahman.footballleague.databinding.PremierLeagueFragmentBinding;
import com.abdelrahman.footballleague.ui.MainActivity;

import java.util.Objects;


/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 */
public class TeamsFragment extends Fragment implements TeamAdapter.OnTeamClick {
    private static final String TAG = "TeamsFragment";
    private PremierLeagueFragmentBinding binding;
    private TeamsViewModel mViewModel;
    private TeamAdapter mAdapter;
    private boolean isTeamsUpdated;
    private SharedPreferences sharedPreferences;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isTeamsUpdated = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.premier_league_fragment, container, false);
        Objects.requireNonNull(((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        mViewModel = ViewModelProviders.of(this).get(TeamsViewModel.class);
        mViewModel.init();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        binding.recyclerViewTeams.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewTeams.setHasFixedSize(true);
        mAdapter = new TeamAdapter(this);
        binding.recyclerViewTeams.setAdapter(mAdapter);
        if (MyApplication.hasNetwork() && !isTeamsUpdated)
            getTeamsFromApi();
        else
            getTeamsFromDb();
        binding.btnRetry.setOnClickListener(view -> reConnect());
        return binding.getRoot();
    }

    private void reConnect() {
        binding.progressBar.setVisibility(View.VISIBLE);
        getTeamsFromApi();
    }

    private void getTeamsFromDb() {
        Log.i(TAG, "getTeams: From Local Database");
        //so if the user (open the app for the first time and no data in room and no internet to store data)
        // Or
        // user cleared the app storage and opened the app with no internet
        //we handle that
        boolean isDbEmpty = sharedPreferences.getBoolean(getString(R.string.isDbEmpty), true);
        if (!isDbEmpty)
            mViewModel.getTeamsLiveData().observe(getViewLifecycleOwner(), teams -> {
                        binding.txtNoTeams.setVisibility(View.GONE);
                        binding.btnRetry.setVisibility(View.GONE);
                        mAdapter.submitList(teams);
                        mAdapter.notifyDataSetChanged();
                        binding.progressBar.setVisibility(View.GONE);
                    }
            );
        else {
            binding.txtNoTeams.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            binding.btnRetry.setVisibility(View.VISIBLE);

        }

    }

    private void getTeamsFromApi() {
        Log.i(TAG, "getTeams: Updated");
        mViewModel.getPremierLeagueTeams().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    mViewModel.deleteAndInsertNewTeams(response.getData().getTeams());
                    sharedPreferences.edit()
                            .putBoolean(getString(R.string.isDbEmpty), false)
                            .apply();
                    isTeamsUpdated = true;
                    getTeamsFromDb();
                    break;
                case ERROR:
                    Toast.makeText(getActivity(), response.getApiError().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "getTeams: Error " + response.getApiError().getMessage());
                    break;
                case Failure:
                    Log.e(TAG, "getTeams: Failure" + response.getApiException().getLocalizedMessage());
                    getTeamsFromDb();
            }
            binding.progressBar.setVisibility(View.GONE);
        });


    }

    @Override
    public void onTeamClick(Integer teamId) {
        controller().navigate(TeamsFragmentDirections.actionTeamsFragmentToTeamDetailsFragment(teamId));
    }

    @Override
    public void onWebsiteClick(String teamWebsite) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(teamWebsite));
        startActivity(browserIntent);
    }

    private NavController controller() {
        return NavHostFragment.findNavController(this);
    }
}
