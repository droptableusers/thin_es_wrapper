package thin_es_wrapper.daos;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import thin_es_wrapper.model.SearchResult;

@AllArgsConstructor
@Builder
public class ElasticsearchDao {

	private final Client esClient;
	
	public SearchResult<String> search(String indexName, String type, int from, int pageSize) {
		SearchResponse response = esClient.prepareSearch()
				.setIndices(indexName)
				.setTypes(type)
				.setQuery(matchAllQuery())
				.setFrom(from)
				.setSize(pageSize)
				.addSort("@timestamp", SortOrder.ASC)
				.addField("_source")
				.get();
		List<String> result = new ArrayList<>(pageSize);
		for (SearchHit hit : response.getHits()) {
			result.add(hit.getSourceAsString());
		}
		long totalHits = response.getHits().getTotalHits();
		return new SearchResult<String>(totalHits, result);
	}
}
