package com.fsm.transit.core;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 1/24/14
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BackPressListener {
    /**
     * if return true
     * then caller cancel self default behavior
     * else caller call default method call
     *
     * @return
     */
    public boolean backPressed();
}
