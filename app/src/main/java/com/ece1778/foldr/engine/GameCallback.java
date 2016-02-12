package com.ece1778.foldr.engine;

import com.ece1778.foldr.game.GameCode;

/**
 * Created by Ding on 3/18/2015.
 */
public interface GameCallback {
    public void gameFinished(GameCode code, int points);

    public void showBlockedToast();

    public void showMenu();

    public void showHelp();

    public void updateScoreAndMoves(int points, int moves);
}
