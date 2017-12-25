package solr.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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
public class UpdatingDocument {

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

		System.out.println("solr 更新方式测试");
		doc = new SolrInputDocument();
		// Adding fields to the document
		doc.addField("id", "1");

		String opt = readDataFromConsole("请选择更新方式：0-按字段更新,1-先删除后更新,请输入：");
		if (opt.equals("0")) {// 按字段更新
			Map<String, String> m = new HashMap<String, String>();
			m.put("set", "hury888");
			doc.addField("name", m);
			doc.addField("_version_", 0);
		} else { // 先删除后更新
			doc.addField("name", "hury999");
			doc.addField("_version_", 0);
		}
		solrClient.add(doc);
		solrClient.commit();

	}

	/**
	 * Use InputStreamReader and System.in to read data from console
	 * 
	 * @param prompt
	 * 
	 * @return input string
	 */
	public static String readDataFromConsole(String prompt) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		try {
			System.out.print(prompt);
			str = br.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
