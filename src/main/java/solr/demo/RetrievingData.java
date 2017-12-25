package solr.demo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * 检索solr数据
 * 
 * @author hury
 * @version 2017-11-01 10:47:54
 */
public class RetrievingData {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	String urlString = "http://localhost:8983/solr/mpi";
	SolrClient client;

	public static void main(String args[]) throws SolrServerException, IOException {
		(new RetrievingData()).query();

	}

	public void query() throws SolrServerException, IOException {
		this.init();
		// this.queryAll();
		this.queryByCondition();
	}

	public void init() {
		// Preparing the Solr client
		// String urlString = "http://localhost:8983/solr/my_core";
		client = new HttpSolrClient.Builder(urlString).build();

	}

	/**
	 * 按条件查找
	 * 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void queryByCondition() throws SolrServerException, IOException {

		// Preparing Solr query
		SolrQuery query = new SolrQuery("*:*");

		// 设置返回哪些的列
		query.addField("*");
		// 设定开始序号
		query.setStart(0);
		// 设定返回的行数
		query.setRows(5);
		// 设置排序
		query.setSort(new SortClause("BIRTHDAY", "desc"));

		// 等价sql： SEXCODE=2
		query.addFilterQuery("SEXCODE:2");
		// 等价sql： PERSONNAME like '王%'
		query.addFilterQuery("PERSONNAME:王*");
		// 等价sql：SL >= 20
		query.addFilterQuery("OPT_RECORD:[20 TO *]");
		// 等价sql：SL > 20
		query.addFilterQuery("OPT_RECORD:{20 TO *]");
		// 等价sql：SL <= 100
		query.addFilterQuery("OPT_RECORD:[* TO 100]");
		// 等价sql：SL < 100
		query.addFilterQuery("OPT_RECORD:[* TO 100}");
		try {
			// 等价sql：BIRTHDAY="1965-06-14"
			// 日期类型数据解析
			// query.addFilterQuery("BIRTHDAY:\"1965-06-14T00:00:00Z\"");
			query.addFilterQuery("BIRTHDAY:\"" + sdfIn.format(sdf.parse("1965-06-14")) + "\"");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// Executing the query
		QueryResponse solrRes = client.query(query);

		System.out.println("Response:" + solrRes.getResponse());
		System.out.println("ResponseHeader:" + solrRes.getResponseHeader());

		// Storing the results of the query
		SolrDocumentList docs = solrRes.getResults();

		// 返回匹配的结果总数
		System.out.println("numFound:" + docs.getNumFound());
		// 返回当前的结果数
		System.out.println("docs.size():" + docs.size());
		for (SolrDocument doc : docs) {
			System.out.print(doc.get("IDCARD") + "\t");
			System.out.print(doc.get("PERSONNAME") + "\t");
			System.out.print(sdf.format(doc.get("BIRTHDAY")) + "\t");
			System.out.println(doc.get("OPT_RECORD") + "\t");
		}
	}

	public void queryAll() throws SolrServerException, IOException {

		// Preparing Solr query
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");

		// Adding the field to be retrieved
		query.addField("*");

		SortClause sortClause = new SortClause("id", "desc");
		query.addSort(sortClause);

		// Executing the query
		QueryResponse queryResponse = client.query(query);

		// Storing the results of the query
		SolrDocumentList docs = queryResponse.getResults();
		System.out.println(docs.size());
		System.out.println(docs);
		System.out.println(docs.get(0));
		System.out.println(docs.get(1));
		System.out.println(docs.get(2));
	}

}
