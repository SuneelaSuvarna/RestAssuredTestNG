package com.spotify.tests;

import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PlaylistTests {

	RequestSpecification req;
	ResponseSpecification resp;
	String accessToken = "BQAc3eZ59YZLFUUuF75-iuhtYqvrPqXEmJY_QbgigwPxeAduqxxQfFqPpoIrnmNPIZt5wx9ijTtQay_uTZQXmXzz63NPl-t-kzTUH31IYLvkAk60q7FkALjWBqVChlXCKwXx5hqZFTbG6yStB1rSeDeZ9GiXnUheIx5xGvKFJDumMtYmBiQ3Ql6x_6xKDXbqr_2k69FObcqMgZCFkKwnYkrnVYNc-UysZJrPQVdZ-A70";
	String id;

	//@BeforeClass
	public void beforeClass() {

		RequestSpecBuilder reqBuilder = new RequestSpecBuilder().setBaseUri("https://api.spotify.com")
				.setBasePath("/v1").addHeader("Authorization", "Bearer " + accessToken).setContentType(ContentType.JSON)
				.log(LogDetail.ALL);

		req = reqBuilder.build();

		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.log(LogDetail.ALL);

		resp = respBuilder.build();

	}

	//@Test
	public void shouldBeAbletoCreatePlayList() {
		String payload = "{\n" + "  \"name\": \"New Playlist1\",\n"
				+ "  \"description\": \"New playlist description1\",\n" + "  \"public\": false\n" + "}";
		String resp1 = given().spec(req).body(payload)

				.when().post("/users/xxc5l4xwktd9mu3c3oisqc1m1/playlists").then().spec(resp).assertThat()
				.statusCode(201).body("name", equalTo("New Playlist1"), "description",
						equalTo("New playlist description1"), "public", equalTo(false))
				.extract().asString();

		JsonPath js = new JsonPath(resp1);
		id = js.getString("id");
	}

	//@Test
	public void shouldBeAbletoGetPlayList() {
		String url = "/playlists/" + id;
		given().spec(req).when().get(url).then().spec(resp).assertThat().statusCode(200).body("name",
				equalTo("New Playlist1"), "description", equalTo("New playlist description1"), "public",
				equalTo(false));

	}

//	@Test
	public void shouldBeAbletoUpdatePlayList() {
		String url = "playlists/" + id;
		String payload = "{\n" + "  \"name\": \"Updated Playlist Name2\",\n"
				+ "  \"description\": \"Updated playlist description2\",\n" + "  \"public\": false\n" + "}";
		given().spec(req).body(payload).when().put(url).then().assertThat().statusCode(200);

	}

//	@Test
	public void shouldNotBeAbleToCreatePlayListWithoutName() {
		String payload = "{\n" + "  \"name\": \"\",\n" + "  \"description\": \"New playlist description\",\n"
				+ "  \"public\": false\n" + "}";
		given().spec(req).body(payload)

				.when().post("/users/xxc5l4xwktd9mu3c3oisqc1m1/playlists").then().spec(resp).assertThat()
				.statusCode(400)
				.body("error.message", equalTo("Missing required field: name"), "error.status", equalTo(400));

	}

//	@Test
	public void wrongTokenTest() {
		String payload = "{\n" + "  \"name\": \"New Playlist1\",\n"
				+ "  \"description\": \"New playlist description1\",\n" + "  \"public\": false\n" + "}";

		given().baseUri("https://api.spotify.com").basePath("/v1").header("Authorization", "Bearer asdsadsad")
				.contentType("application/json").log().all().body(payload)

				.when().post("/users/xxc5l4xwktd9mu3c3oisqc1m1/playlists").then().log().all().assertThat()
				.statusCode(401).body("error.message", equalTo("Invalid access token"));

	}

//	@Test
	public void accessTokenExpiredTest() {
		String expiredToken = "BQCdpbqWn5JtnLV9Q97a_jN0jclQO6POm2aHiShiqgYiK_3YlFahVgr_sinefHN8TlvvAPsLqF4QkVsWJsj9sN93iTijlUnMC4LFe1In61kimIfyoxCYTRlEYep9goDOYqVq01OTA62Jb_4VwQejEuclAURoDBd_yQkdBHkXBvrxjPBfig5wFKQdlX21bSWwer67etsbGxhu1uG8vyowzZUkTnkahmro51vIsnzMU23_";

		String payload = "{\n" + "  \"name\": \"New Playlist1\",\n"
				+ "  \"description\": \"New playlist description1\",\n" + "  \"public\": false\n" + "}";
		given().baseUri("https://api.spotify.com").basePath("/v1").header("Authorization", "Bearer " + expiredToken)
				.contentType("application/json").log().all().body(payload)

				.when().post("/users/xxc5l4xwktd9mu3c3oisqc1m1/playlists").then().log().all().assertThat()
				.statusCode(401).body("error.message", equalTo("The access token expired"));

	}

}
