package com.RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredTesting {
	
	public static void Main(String[] args) {
		String url = "https://dummy.restapiexample.com/employees";
		
		
		Response res = RestAssured.get(url);
		
		System.out.println("Status Code :" +res.getStatusCode());
		
		System.out.println("Complete response");
		System.out.println("Resonse"+res.asPrettyString());
		
		System.out.println("Respo" +res.getBody().asPrettyString());
		
		System.out.println("Status line");
		System.out.println("Status"+res.getStatusLine());
		
		
		
		
	}

}
