package com.gabrielspassos.service

import org.opensearch.client.opensearch.OpenSearchClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SearchService @Autowired()(private val openSearchClient: OpenSearchClient) {

}
