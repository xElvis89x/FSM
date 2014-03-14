package com.fsm.transit;


/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 1/20/14
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class FragmentAnimation {
    public static final FragmentAnimation NONE = new FragmentAnimation(-1, -1);

    private int inAnim;
    private int outAnim;

    public FragmentAnimation(int outAnimation, int inAnimation) {
        this.outAnim = outAnimation;
        this.inAnim = inAnimation;
    }

    public int getInAnimation() {
        return inAnim;
    }

    public int getOutAnimation() {
        return outAnim;
    }
}
