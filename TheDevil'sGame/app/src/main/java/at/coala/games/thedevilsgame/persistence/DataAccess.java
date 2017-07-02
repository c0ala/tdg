/*
 * Copyright (c) 2017 Klaus Galler.
 *
 * This file is part of The Devil's Game and therefore licensed under the MIT License. A license file with more information is included with ths software. If used, modified, or distributed, a license and copyright notice has to be included with the software.
 */

package at.coala.games.thedevilsgame.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import at.coala.games.thedevilsgame.data.user.User;

/**
 * Created by Klaus Galler on 02.07.2017.
 *
 * Provides methods to access persistent data. It can read and write Users into a database and read quests and rules out of XML files.
 */
public class DataAccess {

    @SuppressWarnings("HardCodedStringLiteral")
    private static final String CLASS_NAME = "DataAccess";

    /**
     * Get all users in the database into two different lists depending on if
     * they are selected for the current game or not. Both lists are not
     * connected with the database. Changes on the database or a list will not
     * affect the other parts.
     */
    public static List<User> getUsers(Context context) {
        SQLiteOpenHelper dbHelper = new SQLiteDataStorageHelper(context);
        List<User> userList = SQLiteDataStorageHelper.getUserList(dbHelper.getReadableDatabase());
        dbHelper.close();
        //noinspection HardCodedStringLiteral
        Log.i(CLASS_NAME, "getUsers() executed successfully.");
        return userList;
    }
}
