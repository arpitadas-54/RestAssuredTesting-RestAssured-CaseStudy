package com.RestAssured;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class BDDGETRequest {

	public static void main(String[] args) {
		Response res = given().when().get("https://dummy.restapiexample.com/api/v1/employees").then().extract().response();
		System.out.println("Status Code :" +res.getStatusCode());
		
		System.out.println("Complete response");
		System.out.println("Resonse"+res.asPrettyString());
		System.out.println("Resonse time"+res.getTime());
		
		System.out.println("Respo" +res.getBody().asPrettyString());
		
		System.out.println("Status line");
		System.out.println("Status"+res.getStatusLine());
		
		System.out.println("Time" +res.getTime());
		System.out.println("Content Type" +res.contentType());

	}

}
