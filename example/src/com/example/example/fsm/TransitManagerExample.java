package com.example.example.fsm;

import com.example.example.fragment.Test2Fragment;
import com.example.example.fragment.TestFragment;
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
public class TransitManagerExample extends AbstractTransitManger<FragmentAction> {

    /**
     * Pass {@link com.fsm.transit.bridge.FragmentActivity} in args
     *
     * @param activity {@link com.fsm.transit.bridge.FragmentActivity}
     */
    public TransitManagerExample(com.fsm.transit.bridge.FragmentActivity activity) {
        super(activity);
    }

    {
        transitionsMap.put(new TransitData<FragmentAction>(TestFragment.class, FragmentAction.ACTION_CARD_BUTTON), new TransitResultData<FragmentAction>(Test2Fragment.class));
    }


}
