package thin_es_wrapper.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResult<T> {
	private final long totalHits;
	private final List<T> hits;
}
