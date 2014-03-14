package com.fsm.transit.example;

import android.support.v4.app.Fragment;
import com.fsm.transit.core.AbstractTransitManger;
import com.fsm.transit.core.TransitData;
import com.fsm.transit.core.TransitResultData;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 3/14/14
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZExample extends AbstractTransitManger<FragmentAction> {

    /**
     * Pass {@link com.fsm.transit.bridge.FragmentActivity} in args
     *
     * @param activity {@link com.fsm.transit.bridge.FragmentActivity}
     */
    public ZExample(com.fsm.transit.bridge.FragmentActivity activity) {
        super(activity);
    }

    {
        transitionsMap.put(new TransitData<FragmentAction>(F1.class, FragmentAction.ACTION_CARD_BUTTON), new TransitResultData<FragmentAction>(F1.class));
    }

    public static class F1 extends Fragment {

    }

}
