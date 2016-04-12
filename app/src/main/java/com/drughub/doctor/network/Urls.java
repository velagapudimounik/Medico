package com.drughub.doctor.network;

public class Urls {
    public static String BASE_URL = "http://dev.drughub.in:8080/vachub/service/";

    public static String SIGN_UP = BASE_URL + "serviceprovider/1.0/signup";
    public static String SIGN_IN = BASE_URL + "login/loginAuthentication";
    public static String FORGET_PASSWORD = BASE_URL + "login/forgotPassword";

    public static String SERVICE_PROVIDER = BASE_URL + "serviceprovider/1.0/spProfile/";
    public static String COUNTRY = BASE_URL + "1.0/common/country";
    public static String STATE = BASE_URL + "1.0/common/state/";
    public static String CITY = BASE_URL + "1.0/common/city/";

    public static String ADD_CLINIC = BASE_URL + "1.0/sp/clinics/{userId}";
    public static String UPDATE_CLINIC = BASE_URL + "1.0/sp/clinic/{userId}";

    public static String CREATE_CALANDER = BASE_URL + "1.0/sp/clinic";


}
