package com.qa.TestCases;
import java.io.File;
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
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.RestClient.RestClient;
import com.qa.TestBase.TestBase;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import comqa.Testdata.Users;
public class PostTestCase extends TestBase{
	public RestClient restclient;
	public String Url;
	public TestBase testbase;
	public JSONObject jsonObject;
	public CloseableHttpResponse closeablehttpresponse;
	public   PostTestCase () throws IOException {
		super();
	}
	@BeforeTest
	public void Setup() throws IOException
	{ 
		  Url= apiurl+ path;
		System.out.println("URL=>"+ Url);
	}
	 
	@Test  
public void PostRequestTest() throws ClientProtocolException, IOException, JSONException
{   
		restclient=new RestClient();
		HashMap<String,String> hashmap=new 	HashMap<String,String>();
		hashmap.put("Content-Type ","application/json; charset=utf-8");
		 hashmap.put("Server" ,"cloudflare");
		//Jackson bind API to convert POJO(Plain Old Java Object to JsonObject
		 Users usersobj=new Users("morpheus","leader");
	 ObjectMapper mapper=new ObjectMapper();
 mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
 //object to json file
	 mapper.writeValue(new File("C:\\Users\\vedgu\\eclipse-workspace\\RestAPI1\\src\\main\\java\\comqa\\Testdata\\users.json"),usersobj );
	 //Object to json in string
	 String userjsonString= mapper.writeValueAsString(usersobj);
		closeablehttpresponse=restclient.PostMethodWithHeaders(Url, userjsonString ,hashmap);
		System.out.println("User JSONString ==>"+userjsonString);
		//testbase=new TestBase();
		//Status code 
		Assert.assertEquals(Status_Code_201_Created, closeablehttpresponse.getStatusLine().getStatusCode());
		System.out.println("User got Created--Passed POST");
		//Conversion to JSON String
	String responseString=	EntityUtils.toString(closeablehttpresponse.getEntity(),"UTF-8");
	//Conversion to JSON Object 
		jsonObject=new JSONObject(responseString);
	System.out.println("JSON String----->>>"+jsonObject.toString());
	Header[] AllHeaders=	closeablehttpresponse.getAllHeaders();
	HashMap <String,String>Headermap=new HashMap<String,String>();
	for(Header header:AllHeaders) {
		 Headermap.put(header.getName(), header.getValue());
	}
		System.out.println("All Headers in POST Call===>>>"+Headermap);
	//Converting JsonString to Java Code (Pojo)
	Users Userresobject=mapper.readValue(userjsonString,Users.class);
	System.out.println(usersobj.GetName());
	System.out.println(usersobj.GetJob());
	System.out.println(Userresobject.getId() );
	System.out.println(Userresobject.GetName() );
	System.out.println(Userresobject.GetJob());
	System.out.println(Userresobject.getCreatedAt() );
	Assert.assertEquals( Userresobject.GetJob() ,usersobj.GetJob()) ;
	
	Assert.assertTrue((usersobj.GetName().equals(Userresobject.GetName())),"Name mismatch");
	System.out.println(usersobj.GetName().equals(Userresobject.GetName()));
	Assert.assertTrue((usersobj.GetJob().equals(Userresobject.GetJob())),"Job mismatch");
	System.out.println(usersobj.GetJob().equals(Userresobject.GetJob()));
	
}
}
