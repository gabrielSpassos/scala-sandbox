package com.gabrielspassos.contracts.v3;

import com.gabrielspassos.contracts.v3.response.TagsResponse;

import java.util.List;

public interface TagsContract {

    TagsResponse upsertTag(String id, String value);

    TagsResponse getTag(String id);

    List<TagsResponse> getTags(List<String> ids);

    TagsResponse deleteTag(String id);

}
