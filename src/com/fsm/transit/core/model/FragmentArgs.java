package com.fsm.transit.core.model;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by elvis on 11/10/14.
 */
public class FragmentArgs {
    private Bundle bundle;
    private Fragment targetFragment;

    public FragmentArgs() {
    }

    public FragmentArgs(Bundle bundle, Fragment targetFragment) {
        this.bundle = bundle;
        this.targetFragment = targetFragment;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Fragment getTargetFragment() {
        return targetFragment;
    }

    public void setTargetFragment(Fragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
