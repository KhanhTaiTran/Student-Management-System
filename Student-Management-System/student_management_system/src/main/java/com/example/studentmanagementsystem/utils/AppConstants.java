package com.example.studentmanagementsystem.utils;

public class AppConstants {

    // --- 1. PAGINATION ---
    // default page number 0 (first page)
    public static final String DEFAULT_PAGE_NUMBER = "0";

    // default take 10 records per page
    public static final String DEFAULT_PAGE_SIZE = "10";

    // default sorting 'id'
    public static final String DEFAULT_SORT_BY = "id";
    // asc
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    // --- 2. SECURITY & JWT ---
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    // --- 3. BUSINESS LOGIC ---
    public static final String MIN_BALANCE_TO_ENROLL = "500000";

    private AppConstants() {
    }
}