package com.abdelrahman.footballleague.ui.destinations;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abdelrahman.footballleague.MyApplication;
import com.abdelrahman.footballleague.R;
import com.abdelrahman.footballleague.adapters.TeamAdapter;
import com.abdelrahman.footballleague.databinding.PremierLeagueFragmentBinding;

/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 */
public class TeamsFragment extends Fragment implements TeamAdapter.OnTeamClick {
    private static final String TAG = "TeamsFragment";
    private PremierLeagueFragmentBinding binding;
    private TeamsViewModel mViewModel;
    private TeamAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.premier_league_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(TeamsViewModel.class);
        mViewModel.init();
        binding.recyclerViewTeams.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TeamAdapter(this);
        binding.recyclerViewTeams.setAdapter(mAdapter);
        if(MyApplication.hasNetwork())
        getTeamsFromApi();
        else getTeamsFromDb();
        return binding.getRoot();
    }

    private void getTeamsFromDb() {
        Log.i(TAG, "getTeams: From Local Database");
        mViewModel.getTeamsLiveData().observe(getViewLifecycleOwner(), teams -> {
                   // mAdapter.setTeams(teams);
                    binding.progressBar.setVisibility(View.GONE);
                }
        );
    }

    private void getTeamsFromApi() {
            Log.i(TAG, "getTeams: Updated");
            mViewModel.getPremierLeagueTeams().observe(getViewLifecycleOwner(), response -> {
                switch (response.getStatus()) {
                    case SUCCESS:
                        mViewModel.deleteAndInsertNewTeams(response.getData().getTeams());
                        getTeamsFromDb();
                        break;
                    case ERROR:
                        Toast.makeText(getActivity(), response.getApiError().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "getTeams: Error " + response.getApiError().getMessage());
                        break;
                    case Failure:
                        Toast.makeText(getActivity(), "Server Error , Data from database", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "getTeams: Failure" + response.getApiException().getLocalizedMessage());
                }
                binding.progressBar.setVisibility(View.GONE);
            });





    }

    @Override
    public void onTeamClick(Integer teamId) {
        Toast.makeText(getActivity(), teamId.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWebsiteClick() {

    }
}
