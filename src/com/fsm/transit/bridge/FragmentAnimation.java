package com.fsm.transit.bridge;


/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 1/20/14
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class FragmentAnimation {
    public static final FragmentAnimation NONE = new FragmentAnimation(-1, -1);

    private int enter;
    private int ext;

    private int popEnter;
    private int popExit;

    public FragmentAnimation(int enter, int exit, int popEnter, int popExit) {
        this.enter = enter;
        this.ext = exit;
        this.popEnter = popEnter;
        this.popExit = popExit;
    }


    public FragmentAnimation(int inAnimation,int outAnimation) {
        this.enter = inAnimation;
        this.ext = outAnimation;
    }


    public int getEnter() {
        return enter;
    }

    public int getExit() {
        return ext;
    }


    public int getPopEnter() {
        return popEnter;
    }

    public int getPopExit() {
        return popExit;
    }
}
