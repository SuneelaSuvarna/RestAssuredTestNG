package com.spotify.api;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class RestResource {

	public static Response postPlaylistApi(String path, String accessToken, Object playlist) {
		return given().spec(SpecBuilder.getRequestSpec()).body(playlist).auth().oauth2(accessToken).when().post(path)
				.then().spec(SpecBuilder.getResponseSpec()).extract().response();
	}

	public static Response getPlaylistApi(String path, String accessToken) {

		return given().spec(SpecBuilder.getRequestSpec()).auth().oauth2(accessToken).when().get(path).then()
				.spec(SpecBuilder.getResponseSpec()).extract().response();

	}

	public static Response putPlaylistApi(String path, String accessToken, Object playlist) {
		return given().spec(SpecBuilder.getRequestSpec()).auth().oauth2(accessToken).body(playlist).when().put(path)
				.then().extract().response();
	}

	public static Response postRenew(HashMap<String, String> formParams) {
		return given().spec(SpecBuilder.getRenewTokenSpec()).formParams(formParams).when()
				.post(Constant.API + Constant.TOKEN).then().spec(SpecBuilder.getResponseSpec()).extract().response();

	}
}
