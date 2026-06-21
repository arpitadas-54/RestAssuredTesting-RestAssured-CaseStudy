package com.TestNGFramework;

import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BDDQuestion {
	@BeforeClass
	public void Setup() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
	}
	
	@Test(priority = 1)

	public void TraditionalApproch() {
		
		JSONObject ob = new JSONObject();
		ob.put("title", "my name devansh");
		ob.put("body", "i am from kanpur");
		ob.put("userId", 1);
		
		RequestSpecification request = given();
		request.header("Content-Type","application/json");
		request.body(ob.toString());
		Response response = request.post("/posts");
		
		printResponseDetails(response,"Traditional");

	}
	@Test(priority = 2)
	public void BDDApproch() {
		JSONObject ob = new JSONObject();
		ob.put("title", "my name de bajpai");
		ob.put("body", "i am from kanpur up");
		ob.put("userId", 2);
		
		given().header("Content-Type","application/json").body(ob.toString()).when().post("/posts").then().statusCode(201).statusLine(containsString("201")).body("title", equalTo("my name de bajpai")).body("userId",equalTo(2)).time(lessThan(5000L)).contentType("application/json").log().all();


	}
	@Test(priority = 3)
	public void testBDDAproachWithExatraction() {
		JSONObject ob = new JSONObject();
		ob.put("title", "my name devh bajpai");
		ob.put("body", "i am from kanpur up");
		ob.put("userId", 3);
		
		Response response = given().header("Content-Type","application/json").body(ob.toString()).when().post("/posts").then().statusCode(201).extract().response();
		
		printResponseDetails(response,"BDDAproachWithExatraction");
	}
	
	public void printResponseDetails(Response response, String approch ) {
		System.out.println("The approch is:" +approch);
		System.out.println("Status Line" +response.statusLine());
		System.out.println("Response Time" +response.getTime());
		System.out.println("ContentT-Type" +response.contentType());
		
		System.out.println("Status code" +response.statusCode());
		System.out.println("Response body" +response.getBody().toString());
		System.out.println("Complete response" +response.asString());
	}



}
