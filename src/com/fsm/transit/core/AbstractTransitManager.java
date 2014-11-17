package com.fsm.transit.core;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import com.fsm.transit.bridge.FragmentAnimation;
import com.fsm.transit.core.model.FragmentArgs;

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
public abstract class AbstractTransitManager<E> implements ITransitManager<E> {
    public static final String TAG = "FSM";
    protected Activity activity;
    protected int currentContainer;
    protected List<FragmentSwitchListener> fragmentSwitchListeners = new ArrayList<FragmentSwitchListener>();
    protected BackPressListener backPressListener;
    protected Map<TransitData<E>, TransitResultData<E>> transitionsMap = new HashMap<TransitData<E>, TransitResultData<E>>();

    @Override
    public <T extends Fragment> T switchBranch(Class<T> fragmentClass) {
        clearBackStack(0);
        return switchFragment(fragmentClass);
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
     * Pass {@link Activity} in args
     *
     * @param activity {@link Activity}
     */
    public AbstractTransitManager(Activity activity) {
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
     * @param activity {@link Activity}
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Remove from fragment's stack N fragments
     *
     * @param count N fragments that should be keep.
     */
    protected void clearBackStack(int count) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > count) {
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(count);
            fragmentManager.popBackStack(backStackEntry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass Class for instantiation fragment.
     */
    @Override
    public <T extends Fragment> T switchFragment(Class<T> fragmentClass) {
        return switchFragment(fragmentClass, (Bundle) null);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass Class for instantiation fragment.
     * @param bundle        arguments, passing between fragments
     */
    @Override
    public <T extends Fragment> T switchFragment(Class<T> fragmentClass, Bundle bundle) {
        return switchFragment(fragmentClass, bundle, true);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass  Class for instantiation fragment.
     * @param bundle         arguments, passing between fragments
     * @param addToBackStack if true, current transaction would be adding to the backstack. Perform back press invoked rollback.
     */
    @Override
    public <T extends Fragment> T switchFragment(Class<T> fragmentClass, Bundle bundle, boolean addToBackStack) {
        return switchFragment(fragmentClass, bundle, FragmentAnimation.NONE, addToBackStack);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass    Class for instantiation fragment.
     * @param bundle           arguments, passing between fragments
     * @param transitAnimation override animation between current fragment and fragmentClass instance.
     */
    @Override
    public <T extends Fragment> T switchFragment(Class<T> fragmentClass, Bundle bundle, FragmentAnimation transitAnimation) {
        return switchFragment(fragmentClass, bundle, transitAnimation, false);
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
    public <T extends Fragment> T switchFragment(Class<T> fragmentClass, Bundle bundle, FragmentAnimation transitAnimation, boolean addToBackStack) {
        return switchFragment(fragmentClass, new FragmentArgs(bundle, null), transitAnimation, addToBackStack);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass    - generic defined class for instantiation
     * @param fragmentArgs     - arguments
     * @param transitAnimation - animation for fragment transition
     * @param addToBackStack   - back stack management
     * @param <T>              - dynamic defined fragment
     * @return - generic defined fragment
     */
    @Override
    public <T extends Fragment> T switchFragment(Class<T> fragmentClass, FragmentArgs fragmentArgs, FragmentAnimation transitAnimation, boolean addToBackStack) {
        if (fragmentArgs == null) fragmentArgs = new FragmentArgs();
        deleteCycle(fragmentClass);
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        if (transitAnimation != FragmentAnimation.NONE) {
            fragmentTransaction.setCustomAnimations(transitAnimation.getInAnimation(), transitAnimation.getOutAnimation());
        }
        T fragment = (T) Fragment.instantiate(activity.getBaseContext(), fragmentClass.getName(), fragmentArgs.getBundle());

        fragmentTransaction.replace(currentContainer, fragment, getBackStackName(fragmentClass, fragmentArgs.getBundle()));
//        if (addToBackStack) {
//            fragmentTransaction.addToBackStack(getBackStackName(fragmentClass, bundle));
//        }

        fragmentTransaction.addToBackStack(getBackStackName(fragmentClass, fragmentArgs.getBundle()));
        if (!addToBackStack) activity.getFragmentManager().popBackStack();

        fragmentTransaction.commitAllowingStateLoss();
        fireSwitchFragment(fragment);
        return fragment;
    }

    protected String getBackStackName(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        return fragmentClass.getName();

    }


    protected void deleteCycle(Class<? extends Fragment> fragmentClass) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag(fragmentClass.getName()) != null) {
            int backID = -1;
            for (int i = fragmentManager.getBackStackEntryCount() - 1; i >= 0; i--) {
                if (fragmentManager.getBackStackEntryAt(i).getName().equals(fragmentClass.getName())) {
                    backID = i;
                }
            }
            if (backID != -1) {
                fragmentManager.popBackStack(backID, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
    public <T extends Fragment> T switchFragment(Class<T> fragmentClass, FragmentAnimation transitAnimation, boolean addToBackStack) {
        return switchFragment(fragmentClass, (FragmentArgs) null, transitAnimation, addToBackStack);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param fragmentClass
     * @param transitAnimation
     */
    @Override
    public <T extends Fragment> T switchFragment(Class<T> fragmentClass, FragmentAnimation transitAnimation) {
        return switchFragment(fragmentClass, null, transitAnimation);
    }

    /**
     * Replace current fragment with Fragment with {@link Fragment} defined by argument.
     *
     * @param currentFragment - instance for current active fragment
     * @param action          - action what do in this moment
     */
    @Override
    public Fragment switchFragment(Fragment currentFragment, E action) {
        return switchFragment(currentFragment, action, (Bundle) null);
    }

    @Override
    public Fragment switchFragment(Fragment currentFragment, E action, FragmentArgs fragmentArgs) {
        TransitResultData transitResultData = transitionsMap.get(new TransitData<E>(currentFragment.getClass(), action));
        if (transitResultData != null) {
            return switchFragment(transitResultData.getClazz(), fragmentArgs.getBundle(), transitResultData.getAnimation(), transitResultData.isAddToBack());
        } else {
            Log.e(TAG, "Not fragment found with name " + currentFragment.getClass().getSimpleName() + " for switch");
            return null;
        }
    }

    @Override
    public Fragment switchFragment(Fragment currentFragment, E action, Bundle bundle) {
        return switchFragment(currentFragment, action, new FragmentArgs(bundle, null));
    }

    @Override
    public Fragment switchBy(E action) {
        return switchFragment(getCurrentFragment(), action);
    }

    @Override
    public Fragment switchBy(E action, Bundle bundle) {
        return switchFragment(getCurrentFragment(), action, bundle);
    }

    @Override
    public Fragment switchBy(E action, FragmentArgs fragmentArgs) {
        return switchFragment(getCurrentFragment(), action, fragmentArgs);
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
            result = activity.getFragmentManager().getBackStackEntryCount() >= 1;
            if (result) {
                activity.getFragmentManager().popBackStack();
            }
        }
        return result;
    }

    public Fragment getCurrentFragment() {
        return activity.getFragmentManager().findFragmentById(currentContainer);
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
