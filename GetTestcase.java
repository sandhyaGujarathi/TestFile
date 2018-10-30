package com.qa.TestCases;
import java.io.IOException;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.qa.RestClient.RestClient;
import com.qa.TestBase.TestBase;
import com.qa.TestUtil.GetvalueByPath;
import com.qa.TestUtil.Utility;
public class GetTestcase extends TestBase {
public String Url;
TestBase testbase;
 public RestClient restclient;
 public  CloseableHttpResponse closeablehttpresponse;
 
 public JSONObject jsonobject;
	public GetTestcase() throws IOException
	{
		 super();
	} 
		@BeforeTest
		public void Setup() throws IOException
		{ 
			  Url=apiurl  + ServiceUrl;
			System.out.println("URL=>"+ Url);
		}
		@Test  (priority=2)
		public void GetMethodWithHeaders() throws ClientProtocolException, IOException, JSONException {
			HashMap<String,String> hashmap=new HashMap<String,String>();
			 			hashmap.put("Access-Control-Allow-Origin ","*");
			hashmap.put("CF-RAY","460f27ff5d499da9-ORD");
			hashmap.put("Connection","keep-alive");
			hashmap.put("Content-Encoding","gzip");
			hashmap.put("Content-Type","application/json; charset=utf-8");
			hashmap.put("Date","Thu, 27 Sep 2018 15:55:43 GMT");
			hashmap.put("ETag","W/\"1bb-wM9a6JWbwt3JpLNfwoQwxnqaC3Y\"");
		hashmap.put("Expect-CT ","max-age=604800, report-uri=\"https://report-uri.cloudflare.com/cdn-cgi/beacon/expect-ct\"\r\n" + 
				"");
		hashmap.put("Server","cloudflare");
		hashmap.put("Transfer-Encoding ","chunked");
		hashmap.put("X-Powered-By" ,"Express");
				 restclient=new RestClient();
				closeablehttpresponse= restclient.GetMethod(Url, hashmap);
				 
				Assert.assertEquals(closeablehttpresponse.containsHeader("Content-Type"),true,"Header doesnt exist--Failed");
				 System.out.println("Passed -Content-Type Header Exists");
					 
				
		}

		@Test (priority=1)
		public  void GetMethodwithoutHeaders() throws Throwable, IOException, JSONException
		{
			restclient=new RestClient( );
			 closeablehttpresponse =restclient.GetMethod(Url);
			 Header[] AllHeaders=closeablehttpresponse.getAllHeaders() ;
				int StatusCode= closeablehttpresponse.getStatusLine().getStatusCode();
				String responseString= EntityUtils.toString(closeablehttpresponse.getEntity(),"UTF-8");
				  jsonobject=new JSONObject(responseString);
				System.out.println("JSONObject==>"+jsonobject);
				System.out.println("Status Code--->"+StatusCode);
							 
				Assert.assertEquals(StatusCode, 200,"Status Code not 200,Not Ok");
				System.out.println("Status Code Ok -200");
				String	last_Name=Utility.Getpathvalue(jsonobject,"/data[0]/last_name");
				Assert.assertEquals(last_Name,"Holt");
		// GetvalueByPath class1=new GetvalueByPath();
		String First_name =class1.getvalueByPath(jsonobject,"/data[1]/first_name");
		 Assert.assertEquals(First_name, "Charles");
		 System.out.println("First Name of data[1]==>"+First_name+"-Passed");
				String per_page=Utility.Getpathvalue(jsonobject, "/per_page");
				Assert.assertEquals(Integer.parseInt(per_page),  3);
				String total=Utility.Getpathvalue(jsonobject,"/total");
				Assert.assertEquals(total, "12");
				HashMap<String,String> hashmap=new HashMap<String,String>();
				for( Header header:AllHeaders) {
					String Headername=header.getName();
					String Value=header.getValue();
					hashmap.put(Headername, Value);
				}
				System.out.println("Header Information==>"+hashmap);
		}
		 
	 


}
+