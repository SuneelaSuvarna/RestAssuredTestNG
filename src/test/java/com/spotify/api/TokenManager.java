package com.spotify.api;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;

import com.spotify.util.ConfigLoader;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class TokenManager {

	private static String accessToken;
	private static Instant expiryTime;

	public static String getToken() {

		try {
			if (accessToken == null || Instant.now().isAfter(expiryTime)) {
				System.out.println("Renewing access token");
				Response resp = renewToken();
				accessToken = resp.path("access_token");
				int expiryTimeInMiliSeconds = resp.path("expires_in");
				expiryTime = Instant.now().plusSeconds(expiryTimeInMiliSeconds - 300);

			} else {
				System.out.println("Access token is active");
			}
		} catch (Exception e) {
			throw new RuntimeException("Abort !!! Renew acces token failed");
		}
		return accessToken;

	}

	private static Response renewToken() throws IOException {

		HashMap<String, String> formParams = new HashMap<String, String>();

		formParams.put("grant_type", ConfigLoader.getInstance().getProperty("grant_type"));
		formParams.put("refresh_token",ConfigLoader.getInstance().getProperty("refresh_token"));
		formParams.put("client_id", ConfigLoader.getInstance().getProperty("client_id"));
		formParams.put("client_secret", ConfigLoader.getInstance().getProperty("client_secret"));

		Response resp = RestResource.postRenew(formParams);
		return resp;
	}

}
