/*
 * Copyright (c) 2017 Klaus Galler.
 *
 * This file is part of The Devil's Game and therefore licensed under the MIT License. A license file with more information is included with ths software. If used, modified, or distributed, a license and copyright notice has to be included with the software.
 */

package at.coala.games.thedevilsgame.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import at.coala.games.thedevilsgame.data.user.User;

/**
 * Created by Klaus Galler on 02.07.2017.
 *
 * Provides methods to access the user database, extending the SQLiteOpenHelper.
 */
@SuppressWarnings({"HardCodedStringLiteral", "DuplicateStringLiteralInspection"})
class SQLiteDataStorageHelper extends SQLiteOpenHelper {

    @SuppressWarnings("HardCodedStringLiteral")
    private static final String CLASS_NAME = "StructuredGameDataSt...";

    /**
     * If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Database file name.
     */
    private static final String DATABASE_NAME = "game.db";

    /**
     * This class defines the database table storing all quests played in the last or active game.
     *
     * @see BaseColumns provides the _ID
     */
    private static class TablePlayedQuests implements BaseColumns {
        private static final String TABLE_NAME = "played_quests";
        private static final String COLUMN_NAME_USER_ID = "user_id";
        private static final String COLUMN_NAME_QUEST_ID = "quest_id";
    }

    /**
     * This class defines the user database table.
     *
     * @see BaseColumns provides the _ID
     */
    private static class TableUser implements BaseColumns {
        private static final String TABLE_NAME = "user";
        private static final String COLUMN_NAME_ACTIVE = "active";
        private static final String COLUMN_NAME_CLOTHES = "clothes";
        private static final String COLUMN_NAME_LEVEL = "level";
        private static final String COLUMN_NAME_MAX_POINTS = "max_points";
        private static final String COLUMN_NAME_POINTS = "points";
        private static final String COLUMN_NAME_SEX = "sex";
        private static final String COLUMN_NAME_USERNAME = "username";
    }

    /**
     * This statement creates the played_quests database table.
     */
    private static final String SQL_CREATE_TABLE_PLAYED_QUESTS =
            "CREATE TABLE " + TablePlayedQuests.TABLE_NAME + " (" +
                    TablePlayedQuests._ID + " INTEGER PRIMARY KEY," +
                    TablePlayedQuests.COLUMN_NAME_USER_ID + " INTEGER NOT NULL, " +
                    TablePlayedQuests.COLUMN_NAME_QUEST_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + TablePlayedQuests.COLUMN_NAME_USER_ID +
                        ") REFERENCES " + TableUser.TABLE_NAME + '(' + TableUser._ID + "))";

    /**
     * This statement creates the user database table.
     */
    private static final String SQL_CREATE_TABLE_USER =
            "CREATE TABLE " + TableUser.TABLE_NAME + " (" +
                    TableUser._ID + " INTEGER PRIMARY KEY," +
                    TableUser.COLUMN_NAME_ACTIVE + " INTEGER NOT NULL DEFAULT 1," +
                    TableUser.COLUMN_NAME_CLOTHES + " INTEGER NOT NULL DEFAULT 0," +
                    TableUser.COLUMN_NAME_LEVEL + " INTEGER NOT NULL DEFAULT 1," +
                    TableUser.COLUMN_NAME_MAX_POINTS + " DOUBLE NOT NULL DEFAULT 0.0," +
                    TableUser.COLUMN_NAME_POINTS + " DOUBLE NOT NULL DEFAULT 0.0," +
                    TableUser.COLUMN_NAME_SEX + " INTEGER NOT NULL," +
                    TableUser.COLUMN_NAME_USERNAME + " INTEGER NOT NULL, " +
                    "CONSTRAINT name_unique UNIQUE (" + TableUser.COLUMN_NAME_USERNAME + "))";

    /**
     * This statement deletes the played_quests database table.
     */
    private static final String SQL_DELETE_TABLE_PLAYED_QUESTS =
            "DROP TABLE IF EXISTS " + TableUser.TABLE_NAME;

    /**
     * This statement deletes the user database table.
     */
    private static final String SQL_DELETE_TABLE_USER =
            "DROP TABLE IF EXISTS " + TableUser.TABLE_NAME;

    /**
     * Create a new database access.
     *
     * @param context to use to open or create the database.
     * @see Context
     */
    SQLiteDataStorageHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where
     * the creation of tables and the initial population of the tables happen.
     *
     * @param db the database.
     * @see SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_PLAYED_QUESTS);
        Log.i(CLASS_NAME, "SQLDatabase created successfully.");
    }

    /**
     * Called when the database needs to be upgraded. If the database is older
     * than the current version, tables are dropped and onCreate() is called.
     *
     * @param db the database.
     * @param oldVersion the old database version.
     * @param newVersion the new database version.
     * @see SQLiteDatabase
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL(SQL_DELETE_TABLE_PLAYED_QUESTS);
            db.execSQL(SQL_DELETE_TABLE_USER);
            onCreate(db);
            Log.i(CLASS_NAME, "SQLDatabase deleted successfully.");
        }
    }

    /**
     * Returning a list with all played quests stored to a user.
     *
     * @param db the database.
     * @param userId a database user id
     * @return a list with all played quests to a certain user
     * @see List
     * @see SQLiteDatabase
     */
    private static List<Integer> getPlayedQuests(SQLiteDatabase db, int userId) {
        List<Integer> playedQuests = new LinkedList<>();

        try (Cursor cursor = db.query(
                TablePlayedQuests.TABLE_NAME,
                new String[]{TablePlayedQuests.COLUMN_NAME_QUEST_ID},
                TablePlayedQuests.COLUMN_NAME_USER_ID + " = ?",
                new String[]{Integer.toString(userId)},
                null, null, null)) {
            while (cursor.moveToNext()) {
                playedQuests.add(cursor.getInt(cursor.getColumnIndexOrThrow(TablePlayedQuests.COLUMN_NAME_QUEST_ID)));
            }
        }
        return playedQuests;
    }

    /**
     * Returning a list with all users stored in the database.
     *
     * @param db the database.
     * @return a list with all users.
     * @see List
     * @see SQLiteDatabase
     * @see User
     */
    static List<User> getUserList(SQLiteDatabase db) {
        List<User> userList = new LinkedList<>();

        try (Cursor cursor = db.query(
                TableUser.TABLE_NAME,
                new String[]{
                        TableUser._ID,
                        TableUser.COLUMN_NAME_ACTIVE,
                        TableUser.COLUMN_NAME_CLOTHES,
                        TableUser.COLUMN_NAME_LEVEL,
                        TableUser.COLUMN_NAME_MAX_POINTS,
                        TableUser.COLUMN_NAME_POINTS,
                        TableUser.COLUMN_NAME_SEX,
                        TableUser.COLUMN_NAME_USERNAME},
                null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                userList.add(new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(TableUser._ID)),
                        1 == cursor.getInt(cursor.getColumnIndexOrThrow(TableUser.COLUMN_NAME_ACTIVE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(TableUser.COLUMN_NAME_CLOTHES)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(TableUser.COLUMN_NAME_LEVEL)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(TableUser.COLUMN_NAME_MAX_POINTS)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(TableUser.COLUMN_NAME_POINTS)),
                        User.Sex.values()[cursor.getInt(cursor.getColumnIndexOrThrow(TableUser.COLUMN_NAME_SEX))],
                        cursor.getString(cursor.getColumnIndexOrThrow(TableUser.COLUMN_NAME_USERNAME)),
                        getPlayedQuests(db, cursor.getInt(cursor.getColumnIndexOrThrow(TableUser._ID)))));
            }
        }
        Log.i(CLASS_NAME, userList.size() + " user fetched from database.");
        return userList;
    }
}
