package com.fsm.transit.core;

import android.support.v4.app.Fragment;
import com.fsm.transit.FragmentAction;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 12/9/13
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransitData {
    private Class<? extends Fragment> stateClass;
    private FragmentAction action;

    public TransitData(Class<? extends Fragment> stateClass, FragmentAction action) {
        this.stateClass = stateClass;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransitData that = (TransitData) o;

        if (action != that.action) return false;
        if (!stateClass.equals(that.stateClass)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stateClass.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }
}
