package solr.demo;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * solr分组
 * 
 * @author hury
 * @version 2017-11-01 10:48:52
 */
public class HitHighlighting {
	public static void main(String args[]) throws SolrServerException, IOException {
		// Preparing the Solr client
		String urlString = "http://localhost:8983/solr/my_core";
		SolrClient client = new HttpSolrClient.Builder(urlString).build();

		// String query = request.query;
		SolrQuery query = new SolrQuery();

		// Setting the query string
		query.setQuery("*:*");

		// Setting the no.of rows
		query.setRows(0);

		// Adding the facet field
		query.addFacetField("author");

		// Creating the query request
		QueryRequest qryReq = new QueryRequest(query);

		// Creating the query response
		QueryResponse resp = qryReq.process(client);

		// Retrieving the response fields
		System.out.println(resp.getFacetFields());

		List<FacetField> facetFields = resp.getFacetFields();
		System.out.println("facetFields.size():" + facetFields.size());

		for (int i = 0; i < facetFields.size(); i++) {
			FacetField facetField = facetFields.get(i);
			List<Count> facetInfo = facetField.getValues();

			for (FacetField.Count facetInstance : facetInfo) {
				System.out.println(facetInstance.getName() + " : " + facetInstance.getCount() + " [drilldown qry:"
						+ facetInstance.getAsFilterQuery());
			}
		}
		System.out.println("complete");
	}
}
