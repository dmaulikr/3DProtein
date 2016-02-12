package com.ece1778.foldr.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.ece1778.foldr.R;
import com.ece1778.foldr.activities.GameActivity;
import com.ece1778.foldr.activities.HomeActivity;
import com.ece1778.foldr.data.Protein;
import com.ece1778.foldr.game.MediaManager;
import com.ece1778.foldr.game.ProteinManager;
import com.ece1778.foldr.utils.ProteinArrayAdapter;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LevelFragment extends Fragment implements AdapterView.OnItemClickListener {

    @InjectView(R.id.back)
    ImageView backButton;

    @InjectView(R.id.grid)
    GridView grid;

    private final MediaManager mediaManager;
    private ProteinArrayAdapter adapter;

    public LevelFragment(){
        this.mediaManager = MediaManager.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        ButterKnife.inject(this, view);
        this.adapter = new ProteinArrayAdapter(this.getActivity());
        this.grid.setAdapter(this.adapter);
        this.grid.setOnItemClickListener(this);
        this.grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Protein protein = ProteinManager.getInstance().getProteinByIndex(position);
                if(protein != null){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rcsb.org/pdb/explore/explore.do?structureId=" + protein.getCode()));
                    startActivity(browserIntent);
                }
                return true;
            }
        });
        this.bindButtons();
        this.tryLoadLevels();

        return view;
    }

    private void bindButtons(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LevelFragment.this.mediaManager.playMoveBlockSound();
                ((HomeActivity)LevelFragment.this.getActivity()).showMenu();
            }
        });

    }

    private void tryLoadLevels(){
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            private ProgressDialog dialog = null;

            @Override
            protected void onPreExecute(){
            //    this.dialog = ProgressDialog.show(LevelFragment.this.getActivity(), "Loading...", null);
            }

            @Override
            protected Void doInBackground(Void... params) {
                ProteinManager.getInstance().loadProteins();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
               Collection<Protein> proteins = ProteinManager.getInstance().getProteins();
               LevelFragment.this.adapter.addProteins(proteins);
            //    this.dialog.dismiss();
            }
        };

        task.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.mediaManager.playMenuClickSound();
        Intent intent = new Intent(this.getActivity(), GameActivity.class);
        intent.putExtra("ProteinId", position);
        this.getActivity().startActivity(intent);
    }
}
