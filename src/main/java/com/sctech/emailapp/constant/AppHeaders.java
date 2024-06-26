package com.sctech.emailapp.constant;

import org.springframework.stereotype.Component;

@Component
public class AppHeaders {
    public static String API_HEADER = "x-apikey";
    public static String REQUEST_ID = "App-Request-Id";
    public static String ENTITY_ID = "companyId";
    public static String ENTITY_TYPE = "entityType";
    public static String ENTITY_CREDITS = "entityCredits";
    public static String ENTITY_CHANNEL_NAME = "entityChannelName";
}
