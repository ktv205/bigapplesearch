package com.example.krishnateja.bigapplesearch.utils;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by krishnateja on 4/9/2015.
 */
public class RecentSearchContentProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.krishnateja.bigapplesearch.utils.RecentSearchContentProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;
    public RecentSearchContentProvider(){
        setupSuggestions(AUTHORITY,MODE);
    }
}
