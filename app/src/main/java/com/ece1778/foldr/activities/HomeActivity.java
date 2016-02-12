package com.ece1778.foldr.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.ece1778.foldr.R;
import com.ece1778.foldr.fragments.LevelFragment;
import com.ece1778.foldr.fragments.MainMenuFragment;
import com.ece1778.foldr.game.MediaManager;

public class HomeActivity extends BaseOpenGLActivity {

    private FragmentManager fragmentManager;
    private final MainMenuFragment mainMenuFragment;
    private final LevelFragment levelFragment;
    private final MediaManager mediaManager;

    public HomeActivity(){
        this.mainMenuFragment = new MainMenuFragment();
        this.levelFragment = new LevelFragment();
        this.fragmentManager = this.getFragmentManager();
        this.mediaManager = MediaManager.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showMenu();
    }

    public void showMenu(){
        FragmentTransaction transaction = this.getTransaction();
        transaction.replace(R.id.mainContainer, this.mainMenuFragment);
        transaction.commit();
    }

    public void showLevels(){
        FragmentTransaction transaction = this.getTransaction();
        transaction.replace(R.id.mainContainer, this.levelFragment);
        transaction.commit();
    }

    private FragmentTransaction getTransaction(){
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();

        transaction.setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
        return transaction;
    }

    @Override
    public void onResume(){
        super.onResume();
        this.mediaManager.playTitleSound();
    }

    @Override
    public void onPause(){
        super.onPause();
        this.mediaManager.stopTitleSound();
    }
}
