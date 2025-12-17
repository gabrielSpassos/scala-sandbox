package com.gabrielspassos.contracts.v5;

import com.gabrielspassos.contracts.v5.response.TagsValueResponse;

import java.util.Map;

public interface TagsContract {
    
    Map<String, TagsValueResponse> upsertTag(Map<String, String> requestBody);
    
}
