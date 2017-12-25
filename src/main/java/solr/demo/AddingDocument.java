package solr.demo;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

/**
 * 添加solr数据
 * 
 * @author hury
 * @version 2017-11-01 10:48:40
 */
public class AddingDocument {

	public static void main(String[] args) throws SolrServerException, IOException {
		// Preparing the Solr client
		String urlString = "http://localhost:8983/solr/my_core";
		SolrClient solrClient = new HttpSolrClient.Builder(urlString).build();

		// Preparing the Solr document
		SolrInputDocument doc = new SolrInputDocument();

		// Adding fields to the document
		doc.addField("id", "1");
		doc.addField("name", "hury2");
		doc.addField("age", 32);
		doc.addField("addr", "中国-北京");

		// doc.addField("_version_", 0);
		// Adding the document to Solr
		solrClient.add(doc);

		// Saving the changes
		solrClient.commit();
		System.out.println("Documents added");
	}
}
