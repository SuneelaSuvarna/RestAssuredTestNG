package com.spotify.api;

import com.spotify.pojo1.PlayListPojo;
import com.spotify.util.ConfigLoader;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PlayListApi {

	// public static String accessToken =
	// "BQBejjzk87qjIFzWQ6lpwFC6hMnuyJhRYYue88S3ermwm1X225Sj1QId92y1XBQueVRP27S5mSy6OJ94rX6H64PO5DBojzsf1CwJUq0HKXSa6X-r_CjKLQR3S2p_W_0djXCFtAdfoqIkpGUTsorVpWq3ktIi1Q9jAGjfk4KqkcAu6eZxAlX7ezhTKaXJ9c1S0cP5YYKvOZ9vuPBe1XX91pPKphG_ouRblGcje3DVz1VY";
	
	
	@Step
	public static Response postPlaylistApi(PlayListPojo playlist){
		
	
		
		return RestResource.postPlaylistApi(
				Constant.USERS + ConfigLoader.getInstance().getProperty("user_id") + Constant.PLAYLISTS,
				TokenManager.getToken(), playlist);
	}
	@Step
	public static Response getPlaylistApi(String url) {

		return RestResource.getPlaylistApi(url, TokenManager.getToken());

	}
	@Step
	public static Response putPlaylistApi(PlayListPojo playlist, String url) {
		return RestResource.putPlaylistApi(url, TokenManager.getToken(), playlist);
	}
	@Step
	public static Response postPlaylistApiWithNegToken(PlayListPojo playlist, String token) {
		return RestResource.postPlaylistApi(
				Constant.USERS + ConfigLoader.getInstance().getProperty("user_id") + Constant.PLAYLISTS, token,
				playlist);
	}
}
