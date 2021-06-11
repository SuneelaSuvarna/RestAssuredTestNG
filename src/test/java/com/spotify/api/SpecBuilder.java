package com.spotify.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

	

	public static RequestSpecification getRequestSpec() {
		return new RequestSpecBuilder().setBaseUri("https://api.spotify.com").setBasePath(Constant.BASE_PATH)
				.setContentType(ContentType.JSON).log(LogDetail.ALL).addFilter(new AllureRestAssured())
				.build();

	}
	/*
	public static RequestSpecification getRequestSpecWithNegToken(String negToken) {
		return new RequestSpecBuilder().setBaseUri("https://api.spotify.com").setBasePath("/v1")
				.addHeader("Authorization", "Bearer " + negToken).setContentType(ContentType.JSON).log(LogDetail.ALL)
				.build();

	}
*/

	public static ResponseSpecification getResponseSpec() {
		return new ResponseSpecBuilder().expectContentType(ContentType.JSON).log(LogDetail.ALL).build();
	}
	
	public static RequestSpecification getRenewTokenSpec()
	{
		return new RequestSpecBuilder().setBaseUri("https://accounts.spotify.com").addFilter(new AllureRestAssured()).setContentType(ContentType.URLENC)
				.log(LogDetail.ALL).build();
	}

}
