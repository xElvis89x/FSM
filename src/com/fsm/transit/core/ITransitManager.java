package com.fsm.transit.core;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.fsm.transit.FragmentAction;
import com.fsm.transit.FragmentAnimation;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 12/3/13
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITransitManager {
    void setCurrentContainer(int currentBranch);

    void switchBranch(Class<? extends Fragment> fragmentClass);

    void setActivity(FragmentActivity activity);


    /**
     * standart logic switch fragments
     *
     * @param fragmentClass
     */
    void switchFragment(Class<? extends Fragment> fragmentClass);

    void switchFragment(Class<? extends Fragment> fragmentClass, Bundle bundle);

    void switchFragment(Class<? extends Fragment> fragmentClass, Bundle bundle, boolean addToBackStack);

    void switchFragment(Class<? extends Fragment> fragmentClass, FragmentAnimation transitAnimation, boolean addToBackStack);

    void switchFragment(Class<? extends Fragment> fragmentClass, FragmentAnimation transitAnimation);

    void switchFragment(Class<? extends Fragment> fragmentClass, Bundle bundle, FragmentAnimation transitAnimation);

    void switchFragment(Class<? extends Fragment> fragmentClass, Bundle bundle, FragmentAnimation transitAnimation, boolean addToBackStack);

    /**
     * FSM logic method
     *
     * @param currentFragment - instance for current active fragment
     * @param action          - action what do in this moment
     * @param bundle          - storage for next fragment
     */
    void switchFragment(Fragment currentFragment, FragmentAction action, Bundle bundle);


    /**
     * FSM logic method
     *
     * @param currentFragment - instance for current active fragment
     * @param action          - action what do in this moment
     */
    void switchFragment(Fragment currentFragment, FragmentAction action);

    void addSwitchListener(FragmentSwitchListener listener);

    void removeSwitchListener(FragmentSwitchListener listener);

    void setBackPressListener(BackPressListener listener);

    boolean back();
}
