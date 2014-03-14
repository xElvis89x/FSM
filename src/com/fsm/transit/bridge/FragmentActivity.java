package com.fsm.transit.bridge;


import android.content.Context;
import android.support.v4.app.FragmentManager;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 3/14/14
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FragmentActivity {
    public FragmentManager getSupportFragmentManager();

    public Context getBaseContext();
}
