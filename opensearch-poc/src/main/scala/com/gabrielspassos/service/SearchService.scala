package com.gabrielspassos.service

import com.gabrielspassos.dto.DocumentDTO
import org.opensearch.client.opensearch.OpenSearchClient
import org.opensearch.client.opensearch._types.query_dsl.MatchQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters.*

@Service
class SearchService @Autowired()(private val openSearchClient: OpenSearchClient) {

  private val index = "documents"

  def createIndex(): String = {
    val response = openSearchClient.indices().create(_.index(index))
    response.index()
  }

  def save(doc: DocumentDTO): String = {
    val saved = openSearchClient.index[DocumentDTO](
      _.index(index)
        .id(doc.id)
        .document(doc)
    )
    saved.index()
  }

  def search(text: String): List[DocumentDTO] = {
    val response = openSearchClient.search[DocumentDTO](
      _.index(index).query(q =>
        q.`match`((m: MatchQuery.Builder) =>
          m.field("content")
            .query(f => f.stringValue(text))
        )
      ),
      classOf[DocumentDTO]
    )

    response.hits().hits().asScala
      .flatMap(hit => Option(hit.source()))
      .toList
  }

}
