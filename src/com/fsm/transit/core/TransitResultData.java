package com.fsm.transit.core;

import android.support.v4.app.Fragment;
import com.fsm.transit.bridge.FragmentAnimation;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 1/14/14
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransitResultData<E> {
    private Class<? extends Fragment> clazz;
    private boolean addToBack;
    private FragmentAnimation animation;

    public TransitResultData(Class<? extends Fragment> aClass, boolean addToBack) {
        this(aClass, FragmentAnimation.NONE, addToBack);
    }

    public TransitResultData(Class<? extends Fragment> clazz, FragmentAnimation animation, boolean addToBack) {
        this.clazz = clazz;
        this.addToBack = addToBack;
        this.animation = animation;
    }

    public TransitResultData(Class<? extends Fragment> clazz, FragmentAnimation animation) {
        this(clazz, animation, false);
    }

    public TransitResultData(Class<? extends Fragment> clazz) {
        this(clazz, false);

    }

    public Class<? extends Fragment> getClazz() {
        return clazz;
    }

    public boolean isAddToBack() {
        return addToBack;
    }

    public FragmentAnimation getAnimation() {
        return animation;
    }
}
