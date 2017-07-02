/*
 * Copyright (c) 2017 Klaus Galler.
 *
 * This file is part of The Devil's Game and therefore licensed under the MIT License. A license file with more information is included with ths software. If used, modified, or distributed, a license and copyright notice has to be included with the software.
 */

package at.coala.games.thedevilsgame.game;

import android.content.Context;

import at.coala.games.thedevilsgame.persistence.DataAccess;

/**
 * Created by Klaus Galler on 02.07.2017.
 */
public class GameDataManager {

    public static GameState firstGameState() {
        //TODO ask if resuming a game is possible
        return true ? new GameStateResumeGame() : nextGameState();
    }

    private static GameState nextGameState() {
        //TODO return gamestate matching the nex quest
        return new GameStateError();
    }
}
