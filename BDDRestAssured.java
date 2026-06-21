package com.RestAssured;

import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

public class BDDRestAssured {
	@BeforeClass
	public void Setup() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
		
	}
	
	@Test
	public void POSTRequest() {
		JSONObject obj = new JSONObject();
		obj.put("title", "My anme is devanhs");
		obj.put("body", "i am nice guy");
		obj.put("userId", 2);
		
		System.out.println("Request Body :" +obj.toString());
		
		given().log().all().header("Content-Type","application/json").when().get("/posts").then().log().all().statusCode(201).contentType("application/json").body("title", equalTo("My anme is devanhs")).body("body", equalTo("i am nice guy")).body("userId", equalTo(2)).body("id", notNullValue()).time(lessThan(50L));
	}
	

	

}
