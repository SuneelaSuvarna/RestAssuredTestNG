package com.spotify.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.spotify.pojo2.ErrorPojo;
import com.spotify.pojo2.PlayListPojo;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PlaylistTests2 {

	RequestSpecification req;
	ResponseSpecification resp;
	String accessToken = "BQB3fIBJJOgJy4sQzR0dmGNSbT2vsacWTjp9LriV2d8OCO35vHDZL2CLHgujhgBn-_-OivtcDCh2B7dM_8Du9vLxE54QVO0iGJfoUvbMEjYMe4OJ4ZREmOie4tARe6IQakW0jzMUdGRvB8MGwgmuwRWBao3r4UhkoHQVN-4qF7NFREIhvmB5EukzF3huo_8ikHbiO_cSyXhYcZlw7rwJ28BNKVVOz9NjIcRu9qD8P-wP";
	String id;
	PlayListPojo playlist = null;

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

		playlist = new PlayListPojo();
		playlist.setName("Test PlayList1");
		playlist.setDescription("Test Description1");
		playlist.setPublic(false);

		PlayListPojo resp1 = given().spec(req).body(playlist)

				.when().post("/users/xxc5l4xwktd9mu3c3oisqc1m1/playlists").then().spec(resp).extract()
				.as(PlayListPojo.class);

		Assert.assertEquals(resp1.getName(), playlist.getName());
		Assert.assertEquals(resp1.getDescription(), playlist.getDescription());
		Assert.assertEquals(resp1.getPublic(), playlist.getPublic());

		id = resp1.getId();

	}

	//@Test
	public void shouldBeAbletoGetPlayList() {
		String url = "/playlists/" + id;

		PlayListPojo resp1 = given().spec(req).when().get(url).then().spec(resp).assertThat().statusCode(200).extract()
				.as(PlayListPojo.class);

		Assert.assertEquals(resp1.getName(), playlist.getName());
		Assert.assertEquals(resp1.getDescription(), playlist.getDescription());
		Assert.assertEquals(resp1.getPublic(), playlist.getPublic());

	}

	//@Test
	public void shouldBeAbletoUpdatePlayList() {
		String url = "playlists/" + id;
		playlist = new PlayListPojo();
		playlist.setName("Test PlayList Updated");
		playlist.setDescription("Test Description Updated");
		playlist.setPublic(false);

		given().spec(req).body(playlist).when().put(url).then().assertThat().statusCode(200);

	}

	//@Test
	public void shouldBeAbletoGetPlayListAfterUpdate() {

		String url = "/playlists/" + id;

		PlayListPojo resp1 = given().spec(req).when().get(url).then().spec(resp).assertThat().statusCode(200).extract()
				.as(PlayListPojo.class);

		Assert.assertEquals(resp1.getName(), playlist.getName());
		Assert.assertEquals(resp1.getDescription(), playlist.getDescription());
		Assert.assertEquals(resp1.getPublic(), playlist.getPublic());

	}

//	@Test
	public void shouldNotBeAbleToCreatePlayListWithoutName() {
		playlist = new PlayListPojo();
		playlist.setName("");
		playlist.setDescription("Test Description Updated");
		playlist.setPublic(false);

		ErrorPojo resp1 = given().spec(req).body(playlist)

				.when().post("/users/xxc5l4xwktd9mu3c3oisqc1m1/playlists").then().spec(resp).assertThat()
				.statusCode(400).extract().as(ErrorPojo.class);

		Assert.assertEquals(resp1.getError().getStatus(), 400);
		Assert.assertEquals(resp1.getError().getMessage(), "Missing required field: name");

	}

	//@Test
	public void wrongTokenTest() {
		playlist = new PlayListPojo();
		playlist.setName("Test PlayList Updated");
		playlist.setDescription("Test Description Updated");
		playlist.setPublic(false);

		ErrorPojo resp1 = given().baseUri("https://api.spotify.com").basePath("/v1")
				.header("Authorization", "Bearer asdsadsad").contentType("application/json").log().all().body(playlist)

				.when().post("/users/xxc5l4xwktd9mu3c3oisqc1m1/playlists").then().log().all().assertThat()
				.statusCode(401).extract().as(ErrorPojo.class);

		Assert.assertEquals(resp1.getError().getStatus(), 401);

		Assert.assertEquals(resp1.getError().getMessage(), "Invalid access token");
	}

	//@Test
	public void accessTokenExpiredTest() {
		playlist = new PlayListPojo();
		playlist.setName("Test PlayList Updated");
		playlist.setDescription("Test Description Updated");
		playlist.setPublic(false);
		String expiredToken = "BQCdpbqWn5JtnLV9Q97a_jN0jclQO6POm2aHiShiqgYiK_3YlFahVgr_sinefHN8TlvvAPsLqF4QkVsWJsj9sN93iTijlUnMC4LFe1In61kimIfyoxCYTRlEYep9goDOYqVq01OTA62Jb_4VwQejEuclAURoDBd_yQkdBHkXBvrxjPBfig5wFKQdlX21bSWwer67etsbGxhu1uG8vyowzZUkTnkahmro51vIsnzMU23_";

		ErrorPojo resp1 = given().baseUri("https://api.spotify.com").basePath("/v1")
				.header("Authorization", "Bearer " + expiredToken).contentType("application/json").log().all()
				.body(playlist)

				.when().post("/users/xxc5l4xwktd9mu3c3oisqc1m1/playlists").then().log().all().assertThat().extract()
				.as(ErrorPojo.class);
		Assert.assertEquals(resp1.getError().getStatus(), 401);
		Assert.assertEquals(resp1.getError().getMessage(), "The access token expired");
	}

}
