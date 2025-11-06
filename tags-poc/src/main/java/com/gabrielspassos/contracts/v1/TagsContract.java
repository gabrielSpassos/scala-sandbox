package com.gabrielspassos.contracts.v1;

import com.gabrielspassos.contracts.v1.response.TagsResponse;

public interface TagsContract {
    
    TagsResponse upsertTag(String id, boolean isEnabled);
    
}
