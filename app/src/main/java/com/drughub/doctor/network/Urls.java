package com.drughub.doctor.network;

public class Urls {

    public static String BASE_URL = "https://qa.drughub.in/vachub/service/";

    public static String SIGN_UP = BASE_URL + "serviceprovider/1.0/signup";
    public static String SIGN_IN = BASE_URL + "login/loginAuthentication";
    public static String FORGET_PASSWORD = BASE_URL + "login/forgotPassword";

    public static String SERVICE_PROVIDER = BASE_URL + "serviceprovider/1.0/spProfile/";
    public static String COUNTRY = BASE_URL + "common/1.0/country";
    public static String STATE = BASE_URL + "common/1.0/state/";
    public static String CITY = BASE_URL + "common/1.0/city/";

    public static String CLINIC = BASE_URL + "serviceprovider/1.0/clinics";

    public static String CALENDAR = "/clinicCalendars";
    public static String CHANGE_PASSWORD = BASE_URL + "user/changePassword";
    public static String GET_SPECIALIZATION = BASE_URL+"common/1.0/specialization";
    public static String GET_QUALIFICATION = BASE_URL+"common/1.0/qualification";

    public static String DOCTOR_HOLIDAYS = BASE_URL + "serviceprovider/1.0/doctorHolidays";
}
