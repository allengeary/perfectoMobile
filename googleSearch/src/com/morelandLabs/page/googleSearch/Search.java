package com.morelandLabs.page.googleSearch;

import com.perfectoMobile.page.Page;

public interface Search extends Page
{
    @ElementDefinition
    public String SEARCH = "SEARCH";
    
    @ElementDefinition
    public String SEARCH_BUTTON = "SEARCH_BUTTON";
    
    @ElementDefinition
    public String RESULTS = "RESULT_HEADER";
}
