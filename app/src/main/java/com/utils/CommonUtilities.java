package com.utils;

import java.util.ArrayList;

public class CommonUtilities {
    public static final String TOLLURL = "https://fleet.api.here.com/2/calculateroute.json?app_id=";
    /*
     * Every other URL in this class is derived from SERVER, so this one line
     * is the single place the backend is configured. It now reads BuildConfig
     * instead of a hard-coded vendor domain: the value comes from
     * -PSERVER_BASE_URL on the command line and defaults to the opss backend.
     * BuildConfig.SERVER_BASE_URL exists in every build type.
     */
    public static final String SERVER = com.alaadcin.user.BuildConfig.SERVER_BASE_URL;
    public static final String SERVER_FOLDER_PATH = "";
    public static final String WEBSERVICE = "webservice_shark.php";
    public static final String SERVER_WEBSERVICE_PATH = SERVER_FOLDER_PATH + WEBSERVICE + "?";
    public static final String SERVER_URL = SERVER + SERVER_FOLDER_PATH;
    public static final String SERVER_URL_WEBSERVICE = SERVER + SERVER_WEBSERVICE_PATH + "?";
    public static final String SERVER_URL_PHOTOS = SERVER_URL + "webimages/";
    public static final String LINKEDINLOGINLINK = SERVER + "linkedin-login/linkedin-app.php";
    public static final String PAYMENTLINK = SERVER + "assets/libraries/webview/payment_configuration_trip.php?";
    public static final String USER_PHOTO_PATH = CommonUtilities.SERVER_URL_PHOTOS + "upload/Passenger/";
    public static final String PROVIDER_PHOTO_PATH = CommonUtilities.SERVER_URL_PHOTOS + "upload/Driver/";
    public static final String STORE_PHOTO_PATH = CommonUtilities.SERVER_URL_PHOTOS + "upload/Company/";
    public static String OriginalDateFormate = "dd MMM, yyyy (EEE)";
    public static String OriginalTimeFormate = "hh:mm aa";
    public static ArrayList<String> ageRestrictServices = new ArrayList<>();
}
