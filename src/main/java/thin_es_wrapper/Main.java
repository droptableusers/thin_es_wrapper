package thin_es_wrapper;

import thin_es_wrapper.daos.ElasticsearchDao;
import thin_es_wrapper.resources.QueryResource;
import io.dropwizard.Application;
import io.dropwizard.elasticsearch.health.EsClusterHealthCheck;
import io.dropwizard.elasticsearch.managed.ManagedEsClient;
import io.dropwizard.setup.Environment;

public class Main extends Application<EsWrapperConfiguration> {

	public static void main(String[] args) throws Exception {
		new Main().run(args);
	}

	@Override
	public void run(EsWrapperConfiguration configuration, Environment environment) throws Exception {
		final ManagedEsClient managedClient = new ManagedEsClient(configuration.getElasticsearch());
		environment.lifecycle().manage(managedClient);
		environment.healthChecks().register("Es cluster health", new EsClusterHealthCheck(managedClient.getClient()));
		final ElasticsearchDao elasticsearchDao = new ElasticsearchDao(managedClient.getClient());
		final QueryResource queryResource = QueryResource
				.builder()
				.dao(elasticsearchDao)
				.indexName(configuration.getIndex())
				.type(configuration.getType())
				.build();
		environment.jersey().register(queryResource);
	}

}
