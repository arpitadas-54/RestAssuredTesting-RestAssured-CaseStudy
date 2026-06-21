package com.RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CompletePutPostTest {
	private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
	private JSONObject testUser;
	private int createdUserId;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = BASE_URL;
		// Common test data
		testUser = new JSONObject();
		testUser.put("name", "Test User");
		testUser.put("email", "raghu.astepahead@gmail.com");
		testUser.put("username", "raghavendra");
	}

	@Test(priority = 1)
	public void testPostRequestComplete() {
		System.out.println("\n\n========== POST REQUEST - TRADITIONAL ==========");
		JSONObject user = new JSONObject(testUser.toString());
		user.put("id", 1);
		System.out.println("Request Body: " + user.toString(2));
		Response response = given().header("Content-Type", "application/json").body(user.toString()).post("/users");
		printResponseDetails(response, "POST");
		org.testng.Assert.assertEquals(response.getStatusCode(), 201);
	
		// Store created ID for PUT test
		JSONObject responseJson = new JSONObject(response.getBody().asString());
		if (responseJson.has("id")) {
			createdUserId = responseJson.getInt("id");
			System.out.println("Created User ID: " + createdUserId);
		}
	}

	@Test(priority = 2)
	public void testPutRequestComplete() {
		System.out.println("\n\n========== PUT REQUEST - TRADITIONAL ==========");
		// Update user details
		JSONObject updatedUser = new JSONObject();
		updatedUser.put("id", 1);
		updatedUser.put("name", "Updated Test User");
		updatedUser.put("email", "raghu.astepahead@example.com");
		updatedUser.put("username", "updateduser");
		updatedUser.put("phone", "1-555-555-9999");
		System.out.println("Request Body: " + updatedUser.toString(2));

		Response response = given()
				.header("Content-Type", "application/json")
				.body(updatedUser.toString())
				.put("/users/1");
		printResponseDetails(response, "PUT");
		org.testng.Assert.assertEquals(response.getStatusCode(), 200);
		//org.testng.assertTrue(response.getBody().asString().contains("Updated Test User"));
	}

	@Test(priority = 3)
	public void testPostRequestBDDComplete() {
		System.out.println("\n\n========== POST REQUEST - BDD ==========");

		JSONObject user = new JSONObject();
		user.put("title", "BDD Test Post");
		user.put("body", "Created using BDD approach");
		user.put("userId", 1);

		System.out.println("Request Body: " + user.toString(2));

		given().header("Content-Type", "application/json").body(user.toString()).when().post("/posts").then()
				.statusCode(201).statusLine(containsString("201")).contentType("application/json")
				.body("title", equalTo("BDD Test Post")).body("userId", equalTo(1)).body("id", notNullValue())
				.time(lessThan(5000L)).log().ifValidationFails();
	}

	@Test(priority = 4)
	public void testPutRequestBDDComplete() {
		System.out.println("\n\n========== PUT REQUEST - BDD ==========");
		JSONObject updatedPost = new JSONObject();
		updatedPost.put("id", 1);
		updatedPost.put("title", "BDD Updated Post");
		updatedPost.put("body", "Updated using BDD approach");
		updatedPost.put("userId", 1);

		System.out.println("Request Body: " + updatedPost.toString(2));

		given().header("Content-Type", "application/json")
			   .body(updatedPost.toString())
			   .when().put("/posts/1")
			   .then()
			   .statusCode(200)
			   .statusLine(containsString("200"))
			   .contentType("application/json")
			   .body("title", equalTo("BDD Updated Post"))
			   .body("body", equalTo("Updated using BDD approach"))
			   .body("userId", equalTo(1))
			   .time(lessThan(5000L))
			   .log().ifValidationFails();
	}

	@Test(priority = 5)
	public void testPutAndPostWithExtraction() {
		System.out.println("\n\n========== PUT & POST WITH EXTRACTION ==========");

		// POST Request with extraction
		JSONObject postData = new JSONObject();
		postData.put("title", "Extracted Post");
		postData.put("body", "This will be extracted");
		postData.put("userId", 2);

		Response postResponse = 
				given().header("Content-Type", "application/json")
				.body(postData.toString())
				.post("/posts")
				.then().statusCode(201).extract().response();

		System.out.println("POST Response Details:");
		System.out.println("1. Status Code: " + postResponse.getStatusCode());
		System.out.println("2. Response Body: " + postResponse.getBody().asString());
		System.out.println("3. Status Line: " + postResponse.getStatusLine());
		System.out.println("4. Content Type: " + postResponse.getContentType());
		System.out.println("5. Response Time: " + postResponse.getTime() + " ms");

		// PUT Request with extraction
		JSONObject putData = new JSONObject();
		putData.put("id", 1);
		putData.put("title", "Extracted Put");
		putData.put("body", "This is extracted PUT");
		putData.put("userId", 1);

		Response putResponse = 
				given()
				.header("Content-Type", "application/json")
				.body(putData.toString())
				.put("/posts/1")
				.then().statusCode(200).extract().response();

		System.out.println("\nPUT Response Details:");
		System.out.println("1. Status Code: " + putResponse.getStatusCode());
		System.out.println("2. Response Body: " + putResponse.getBody().asString());
		System.out.println("3. Status Line: " + putResponse.getStatusLine());
		System.out.println("4. Content Type: " + putResponse.getContentType());
		System.out.println("5. Response Time: " + putResponse.getTime() + " ms");

		org.testng.Assert.assertEquals(postResponse.getStatusCode(), 201);
		org.testng.Assert.assertEquals(putResponse.getStatusCode(), 200);
	}

	// Helper method
	private void printResponseDetails(Response response, String method) {
		System.out.println("\n--- " + method + " Response Details ---");
		System.out.println("1. Status Code: " + response.getStatusCode());
		System.out.println("2. Complete Response: " + response.asString());
		System.out.println("3. Response Body: " + response.getBody().asString());
		System.out.println("4. Response Status Line: " + response.getStatusLine());
		System.out.println("5. Response Content Type: " + response.getContentType());
		System.out.println("6. Response Time: " + response.getTime() + " ms");
	}
}