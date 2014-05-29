package com.fsm.transit.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.fsm.transit.bridge.FragmentActivity;
import com.fsm.transit.bridge.FragmentAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 12/4/13
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class is responsible for the overall logic of the transition and the transition between fragments.
 * Such as: switching fragments, adding / deleting the stack processing pressing the back.
 */
public abstract class AbstractTransitManger<E> implements ITransitManager<E> {
    public static final String TAG = "FSM";
    protected FragmentActivity activity;
    protected int currentContainer;
    protected List<FragmentSwitchListener> fragmentSwitchListeners = new ArrayList<FragmentSwitchListener>();
    protected BackPressListener backPressListener;
    protected Map<TransitData<E>, TransitResultData<E>> transitionsMap = new HashMap<TransitData<E>, TransitResultData<E>>();

    @Override
    public void switchBranch(Class<? extends Fragment> fragmentClass) {
        if (!activity.getSupportFragmentManager().getBackStackEntryAt(activity.getSupportFragmentManager().getBackStackEntryCount() - 1).getName().equals(fragmentClass.getName())) {
            clearBackStack(0);
            switchFragment(fragmentClass);
        }
    }

    /**
     * Setup backpress listener {@link BackPressListener}
     *
     * @param listener instance of the {@link BackPressListener}
     */
    @Override
    public void setBackPressListener(BackPressListener listener) {
        backPressListener = listener;
    }

    /**
     * Pass {@link FragmentActivity} in args
     *
     * @param activity {@link FragmentActivity}
     */
    public AbstractTransitManger(FragmentActivity activity) {
        this.activity = activity;
    }

    /**
     * Setup current container.
     * This container use as container placeholder.
     *
     * @param currentBranch Placeholder resource
     */
    public void setCurrentContainer(int currentBranch) {
        this.currentContainer = currentBranch;
    }

    /**
     * Set current context
     *
     * @param activity {@link FragmentActivity}
     */
    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    /**
     * Remove from fragment's stack N fragments
     *
     * @param count N fragments that should be removed.
     */
    protected void clearBackStack(int count) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        for (int i = count; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass Class for instantiation fragment.
     */
    @Override
    public void switchFragment(Class<? extends Fragment> fragmentClass) {
        switchFragment(fragmentClass, (Bundle) null);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass Class for instantiation fragment.
     * @param bundle        arguments, passing between fragments
     */
    @Override
    public void switchFragment(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        switchFragment(fragmentClass, bundle, true);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass  Class for instantiation fragment.
     * @param bundle         arguments, passing between fragments
     * @param addToBackStack if true, current transaction would be adding to the backstack. Perform back press invoked rollback.
     */
    @Override
    public void switchFragment(Class<? extends Fragment> fragmentClass, Bundle bundle, boolean addToBackStack) {
        switchFragment(fragmentClass, bundle, FragmentAnimation.NONE, addToBackStack);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass    Class for instantiation fragment.
     * @param bundle           arguments, passing between fragments
     * @param transitAnimation override animation between current fragment and fragmentClass instance.
     */
    @Override
    public void switchFragment(Class<? extends Fragment> fragmentClass, Bundle bundle, FragmentAnimation transitAnimation) {
        switchFragment(fragmentClass, bundle, transitAnimation, false);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass    Class for instantiation fragment.
     * @param bundle           arguments, passing between fragments
     * @param transitAnimation override animation between current fragment and fragmentClass instance.
     * @param addToBackStack   if true, current transaction would be adding to the backstack. Perform back press invoked rollback.
     */
    @Override
    public void switchFragment(Class<? extends Fragment> fragmentClass, Bundle bundle, FragmentAnimation transitAnimation, boolean addToBackStack) {
        deleteCycle(fragmentClass);
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (transitAnimation != FragmentAnimation.NONE) {
            fragmentTransaction.setCustomAnimations(transitAnimation.getEnter(), transitAnimation.getExit(),transitAnimation.getPopEnter(),transitAnimation.getPopExit());
        }
        Fragment fragment = Fragment.instantiate(activity.getBaseContext(), fragmentClass.getName());
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(currentContainer, fragment, getBackStackName(fragmentClass, bundle));
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(getBackStackName(fragmentClass, bundle));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fireSwitchFragment(fragment);
    }

    protected String getBackStackName(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        return fragmentClass.getName();

    }


    protected void deleteCycle(Class<? extends Fragment> fragmentClass) {
        if (activity.getSupportFragmentManager().findFragmentByTag(fragmentClass.getName()) != null) {
            int count = 0;
            for (int i = activity.getSupportFragmentManager().getBackStackEntryCount() - 1; i >= 0; i--) {
                if (activity.getSupportFragmentManager().getBackStackEntryAt(i).getName().equals(fragmentClass.getName())) {
                    count++;
                }
            }
            for (int i = 0; i < count + 1; i++) {
                activity.getSupportFragmentManager().popBackStack();
            }
        }
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass
     * @param transitAnimation
     * @param addToBackStack
     */
    @Override
    public void switchFragment(Class<? extends Fragment> fragmentClass, FragmentAnimation transitAnimation, boolean addToBackStack) {
        switchFragment(fragmentClass, null, transitAnimation, addToBackStack);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass
     * @param transitAnimation
     */
    @Override
    public void switchFragment(Class<? extends Fragment> fragmentClass, FragmentAnimation transitAnimation) {
        switchFragment(fragmentClass, null, transitAnimation);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param currentFragment - instance for current active fragment
     * @param action          - action what do in this moment
     */
    @Override
    public void switchFragment(Fragment currentFragment, E action) {
        switchFragment(currentFragment, action, null);
    }

    @Override
    public void switchFragment(Fragment currentFragment, E action, Bundle bundle) {
        TransitResultData transitResultData = transitionsMap.get(new TransitData(currentFragment.getClass(), action));
        if (transitResultData != null) {
            switchFragment(transitResultData.getClazz(), bundle, transitResultData.getAnimation(), transitResultData.isAddToBack());
        } else {
            Log.e(TAG, "Not fragment found with name " + currentFragment.getClass().getSimpleName() + " for switch");
        }
    }

    /**
     * Perform back press.
     *
     * @return true if fragments should be changes, false - otherwise.
     */
    @Override
    public boolean back() {
        boolean result = fireBackPress();
        if (!result) {
            result = activity.getSupportFragmentManager().getBackStackEntryCount() >= 1;
            if (result) {
                activity.getSupportFragmentManager().popBackStack();
            }
        }
        return result;
    }

    @Override
    public void addSwitchListener(FragmentSwitchListener listener) {
        if (listener != null) {
            fragmentSwitchListeners.add(listener);
        }
    }

    @Override
    public void removeSwitchListener(FragmentSwitchListener listener) {
        fragmentSwitchListeners.remove(listener);
    }

    protected void fireSwitchFragment(Fragment newFragment) {
        for (FragmentSwitchListener fragmentSwitchListener : fragmentSwitchListeners) {
            fragmentSwitchListener.fragmentSwitched(newFragment);
        }
    }

    protected boolean fireBackPress() {
        return backPressListener != null && backPressListener.backPressed();
    }
}
