package solr.demo;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * 删除solr数据
 * 
 * @author hury
 * @version 2017-11-01 10:48:16
 */
public class DeletingAllDocuments {
	public static void main(String args[]) throws SolrServerException, IOException {
		// Preparing the Solr client
		String urlString = "http://localhost:8983/solr/my_core";
		SolrClient Solr = new HttpSolrClient.Builder(urlString).build();

		// Deleting the documents from Solr
		Solr.deleteByQuery("*");

		// Saving the document
		Solr.commit();
		System.out.println("Documents deleted");
	}
}
