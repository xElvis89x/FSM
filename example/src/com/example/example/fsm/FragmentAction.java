package com.example.example.fsm;

/**
 * Created with IntelliJ IDEA.
 * User: elvis
 * Date: 12/6/13
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * Common and special actions for every fragment in application
 */
public enum FragmentAction {
    /**
     * common fragment actions
     */
    SELECT_ITEM,

    NEXT_PAGE_BUTTON,
    ERROR_PAGE,

    /**
     * special action for fragments
     */

    //MainFragment actions
    GAS_STATION_BUTTON,
    SHARES_BUTTON,
    HELPFUL_BUTTON,
    MAP_BUTTON,
    INTERTAINMENT_BUTTON,
    PROFILE_BUTTON,
    CONTACT_BUTTON,
    HELP_BUTTON,
    RULES_BUTTON,

    //UserProfileFragment action
    ACTION_CARD_BUTTON,
    GET_CARD_BUTTON,
    RESTORE_CARD_BUTTON,

    //HelpfulFragment actions
    CAR_EXP_BUTTON,
    ECTO_STYLE_BUTTON,
    FIND_CAR_BUTTON,
    SPEED_BUTTON,

    //CarExpensesListFragment actions
    CAR_EXP_FEED_BUTTON,
    REPORTS_BUTTON,

    //EctoStyleDetailFeedFragment actions
    CAR_DETAILS_BUTTON,
    SPEED_SETTINGS_BUTTON,

    //GetCardFragment action
    SAVE_BUTTON,
    ACTIVATION_BUTTON,
    EARN_POINTS_BUTTON,
    HISTORY_BUTTON,
    ADD_PHOTO_CAR_BUTTON,
    EDIT_CAR_BUTTON,

    SHARES_FEED_BUTTON,

    //MapFragment action
    FILTER_GAS_STATION_BUTTON,
    GAS_STATION_INFO,

    //GasStationInfoFragment
    HOW_TO_FIND_BUTTON, GAS_STATION_INFORMATION_BUTTON,
    CAR_SETTINGS_BUTTON,
    GAS_INFO_STATION_BUTTON, SPEED_ANALOG_BUTTON, SPEED_SETTINGS_ANALOG_BUTTON, SPEED_DIGITAL_SETTINGS_BUTTON,
}
