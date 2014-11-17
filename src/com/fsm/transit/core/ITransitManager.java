package com.fsm.transit.core;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import com.fsm.transit.bridge.FragmentAnimation;
import com.fsm.transit.core.model.FragmentArgs;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 12/3/13
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITransitManager<E> {
    void setCurrentContainer(int currentBranch);

    <T extends Fragment> T switchBranch(Class<T> fragmentClass);

    void setActivity(Activity activity);


    /**
     * standart logic switch fragments
     *
     * @param fragmentClass
     */
    <T extends Fragment> T switchFragment(Class<T> fragmentClass);

    <T extends Fragment> T switchFragment(Class<T> fragmentClass, Bundle bundle);

    <T extends Fragment> T switchFragment(Class<T> fragmentClass, Bundle bundle, boolean addToBackStack);

    <T extends Fragment> T switchFragment(Class<T> fragmentClass, FragmentAnimation transitAnimation, boolean addToBackStack);

    <T extends Fragment> T switchFragment(Class<T> fragmentClass, FragmentAnimation transitAnimation);

    <T extends Fragment> T switchFragment(Class<T> fragmentClass, Bundle bundle, FragmentAnimation transitAnimation);

    <T extends Fragment> T switchFragment(Class<T> fragmentClass, Bundle bundle, FragmentAnimation transitAnimation, boolean addToBackStack);

    <T extends Fragment> T switchFragment(Class<T> fragmentClass, FragmentArgs fragmentArgs, FragmentAnimation transitAnimation, boolean addToBackStack);

    /**
     * FSM logic method
     *
     * @param currentFragment - instance for current active fragment
     * @param action          - action what do in this moment
     * @param bundle          - storage for next fragment
     */
    Fragment switchFragment(Fragment currentFragment, E action, Bundle bundle);


    /**
     * FSM logic method
     *
     * @param currentFragment - instance for current active fragment
     * @param action          - action what do in this moment
     */
    Fragment switchFragment(Fragment currentFragment, E action);


    Fragment switchFragment(Fragment currentFragment, E action, FragmentArgs fragmentArgs);

    /**
     * FSM switch fragment use action and known current fragment
     *
     * @param action
     */
    Fragment switchBy(E action);

    /**
     * FSM switch fragmnet use action and know current fragment
     *
     * @param action
     * @param bundle - bundle for new fragment
     */
    Fragment switchBy(E action, Bundle bundle);

    /**
     * FSM switch fragmnet use action and know current fragment
     *
     * @param action
     * @param fragmentArgs - data for new fragment
     */
    Fragment switchBy(E action, FragmentArgs fragmentArgs);

    void addSwitchListener(FragmentSwitchListener listener);

    void removeSwitchListener(FragmentSwitchListener listener);

    void setBackPressListener(BackPressListener listener);

    Fragment getCurrentFragment();

    boolean back();
}
