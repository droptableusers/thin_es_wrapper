package thin_es_wrapper;

import lombok.Getter;
import lombok.Setter;
import io.dropwizard.Configuration;
import io.dropwizard.elasticsearch.config.EsConfiguration;

@Getter
@Setter
public class EsWrapperConfiguration extends Configuration {
	private EsConfiguration elasticsearch;
	private String index;
	private String type;
}
