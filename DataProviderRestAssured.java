package com.RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DataProviderRestAssured {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @DataProvider(name = "postIds")
    public Object[][] getPostIds() {
        return new Object[][] {
            {1, 1},   // postId, expectedUserId
            {2, 1},
            {3, 1},
            {4, 1},
            {5, 1}
        };
    }

    @Test(dataProvider = "postIds")
    public void testMultiplePostsTraditional(int postId, int expectedUserId) {
        System.out.println("\n========== TESTING POST ID: " + postId + " ==========");

        Response response = given()
            .header("Accept", "application/json")
            .pathParam("id", postId)
            .get("/posts/{id}");

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Status Line: " + response.getStatusLine());
        System.out.println("Response Time: " + response.getTime() + " ms");
        System.out.println("Response Body: " + response.getBody().asString());

        org.testng.Assert.assertEquals(response.getStatusCode(), 200);
        org.testng.Assert.assertTrue(response.getBody().asString().contains("\"userId\": " + expectedUserId));
        org.testng.Assert.assertTrue(response.getBody().asString().contains("\"id\": " + postId));
    }

    @Test(dataProvider = "postIds")
    public void testMultiplePostsBDD(int postId, int expectedUserId) {
        System.out.println("\n========== BDD TESTING POST ID: " + postId + " ==========");

        given()
            .pathParam("id", postId)
            .header("Accept", "application/json")
        .when()
            .get("/posts/{id}")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("id", equalTo(postId))
            .body("userId", equalTo(expectedUserId))
            .body("title", notNullValue())
            .time(lessThan(3000L))
            .log().ifValidationFails();
    }
}