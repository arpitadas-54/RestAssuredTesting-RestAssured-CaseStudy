package com.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class BDDValidation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Response res = (Response) given().when().get("https://dummy.restapiexample.com/api/v1/employees").then().statusCode(201)
				.statusLine("HTTP/1.1 200 OK").log().all();

		System.out.println("Response: " + res.asPrettyString());

	}

}
