package thin_es_wrapper.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import lombok.Builder;
import thin_es_wrapper.daos.ElasticsearchDao;
import thin_es_wrapper.model.SearchResult;

@Path("/messages")
@Builder
public class QueryResource {
	private final ElasticsearchDao dao;
	private final String indexName;
	private final String type;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResult<String> getAll (
			@DefaultValue("0") @QueryParam("from") int from,
			@DefaultValue("10") @QueryParam("pageSize") int pageSize) {
		return dao.search(indexName, type, from, pageSize);
	}

	@GET
	@Path("/{position}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOne (@PathParam("position") int position){
		if (position < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		SearchResult<String> result = dao.search(indexName, type, position, 1);
		if (result.getTotalHits() <= position) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(result.getHits().get(0)).build();
		}
	}
}
