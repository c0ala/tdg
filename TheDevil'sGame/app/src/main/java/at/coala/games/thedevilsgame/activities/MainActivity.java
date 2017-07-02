/*
 * Copyright (c) 2017 Klaus Galler.
 *
 * This file is part of The Devil's Game and therefore licensed under the MIT License. A license file with more information is included with ths software. If used, modified, or distributed, a license and copyright notice has to be included with the software.
 */

package at.coala.games.thedevilsgame.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import at.coala.games.thedevilsgame.R;

/**
 * Created by Klaus Galler on 02.07.2017.
 *
 * This Activity will be the main game activity. With the MainActivity all gameplay will presented to the user.
 */
public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("HardCodedStringLiteral")
    private static final String MAIN_ACTIVITY = "MainActivity";

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection HardCodedStringLiteral
        Log.i(MAIN_ACTIVITY, "Activity created successfully.");
    }
}