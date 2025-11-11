package com.gabrielspassos.contracts.v2;

import com.gabrielspassos.contracts.v2.response.TagsResponse;

import java.util.List;

public interface TagsContract {
    
    TagsResponse upsertTag(String id, boolean isEnabled);

    TagsResponse getTag(String id);
    
    List<TagsResponse> getTags(List<String> ids);

    TagsResponse deleteTag(String id);
    
}
