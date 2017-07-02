/*
 * Copyright (c) 2017 Klaus Galler.
 *
 * This file is part of The Devil's Game and therefore licensed under the MIT License. A license file with more information is included with ths software. If used, modified, or distributed, a license and copyright notice has to be included with the software.
 */

package at.coala.games.thedevilsgame.data.user;

import java.util.List;

/**
 * Created by Klaus Galler on 02.07.2017.
 */
public class User {

    /**
     * Enumeration for the sex of the user.
     */
    public enum Sex {
        FEMALE, MALE, OTHER
    }

    private final int userId;

    /**
     * true if the user is in the active game; false if not
     */
    private boolean active;

    /**
     * number of clothes the user has lost or put on. This count starts with zero and decrements with every loss (and increments with every additional robe to wear)
     */
    private int clothes;

    /**
     * current player level
     */
    private int level;

    /**
     * max points the player has ever reached - this is shown in the highscore
     */
    private double max_points;

    /**
     * current points in active game
     */
    private double points;

    /**
     * the users sex
     */
    private Sex sex;

    /**
     * Username shown in the game.
     */
    private String username;

    /**
     * List of all quests the user has participated in the current game.
     */
    private List<Integer> playedQuests;

    public User(int userId, boolean active, int clothes, int level, double max_points, double points, Sex sex, String username, List<Integer> playedQuests) {
        this.userId = userId;
        this.active = active;
        this.clothes = clothes;
        this.level = level;
        this.max_points = max_points;
        this.points = points;
        this.sex = sex;
        this.username = username;
        this.playedQuests = playedQuests;
    }
}
