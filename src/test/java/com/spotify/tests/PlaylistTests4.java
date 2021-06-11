package com.spotify.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;


import com.spotify.api.PlayListApi;
import com.spotify.pojo1.ErrorPojo;
import com.spotify.pojo1.PlayListPojo;

import io.restassured.response.Response;

public class PlaylistTests4 {

	String id;
	PlayListPojo playlist = null;

	@Test
	public void shouldBeAbletoCreatePlayList()  {

		playlist = new PlayListPojo();
		playlist.setName("Test PlayList1");
		playlist.setDescription("Test Description1");
		playlist.set_public(false);

		Response resp = PlayListApi.postPlaylistApi(playlist);

		PlayListPojo resp1 = resp.as(PlayListPojo.class);

		Assert.assertEquals(resp.getStatusCode(), 201);
		Assert.assertEquals(resp1.getName(), playlist.getName());
		Assert.assertEquals(resp1.getDescription(), playlist.getDescription());
		Assert.assertEquals(resp1.get_public(), playlist.get_public());

		id = resp1.getId();

	}

	@Test
	public void shouldBeAbletoGetPlayList() {
		String url = "/playlists/" + id;

		Response resp = PlayListApi.getPlaylistApi(url);

		PlayListPojo resp1 = resp.as(PlayListPojo.class);

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(resp1.getName(), playlist.getName());
		Assert.assertEquals(resp1.getDescription(), playlist.getDescription());
		Assert.assertEquals(resp1.get_public(), playlist.get_public());

	}

	@Test
	public void shouldBeAbletoUpdatePlayList() {
		String url = "playlists/" + id;
		playlist = new PlayListPojo();
		playlist.setName("Test PlayList Updated");
		playlist.setDescription("Test Description Updated");
		playlist.set_public(false);

		Response resp = PlayListApi.putPlaylistApi(playlist, url);
		Assert.assertEquals(resp.getStatusCode(), 200);

	}

	@Test
	public void shouldBeAbletoGetPlayListAfterUpdate() {

		String url = "/playlists/" + id;

		Response resp = PlayListApi.getPlaylistApi(url);

		PlayListPojo resp1 = resp.as(PlayListPojo.class);

		Assert.assertEquals(resp.getStatusCode(), 200);
		Assert.assertEquals(resp1.getName(), playlist.getName());
		Assert.assertEquals(resp1.getDescription(), playlist.getDescription());
		Assert.assertEquals(resp1.get_public(), playlist.get_public());

	}

	@Test
	public void shouldNotBeAbleToCreatePlayListWithoutName() {
		playlist = new PlayListPojo();
		playlist.setName("");
		playlist.setDescription("Test Description Updated");
		playlist.set_public(false);

		Response resp = PlayListApi.postPlaylistApi(playlist);

		ErrorPojo resp1 = resp.as(ErrorPojo.class);
		Assert.assertEquals(resp.getStatusCode(), 400);
		Assert.assertEquals(resp1.getError().getStatus(), 400);
		Assert.assertEquals(resp1.getError().getMessage(), "Missing required field: name");

	}

	@Test
	public void wrongTokenTest() {
		playlist = new PlayListPojo();
		playlist.setName("Test PlayList Updated");
		playlist.setDescription("Test Description Updated");
		playlist.set_public(false);
		String wrongToken = "dsdas";
		Response resp = PlayListApi.postPlaylistApiWithNegToken(playlist, wrongToken);
		ErrorPojo resp1 = resp.as(ErrorPojo.class);

		Assert.assertEquals(resp.getStatusCode(), 401);

		Assert.assertEquals(resp1.getError().getStatus(), 401);

		Assert.assertEquals(resp1.getError().getMessage(), "Invalid access token");
	}

	@Test
	public void accessTokenExpiredTest() {
		playlist = new PlayListPojo();
		playlist.setName("Test PlayList Updated");
		playlist.setDescription("Test Description Updated");
		playlist.set_public(false);
		String expiredToken = "BQCdpbqWn5JtnLV9Q97a_jN0jclQO6POm2aHiShiqgYiK_3YlFahVgr_sinefHN8TlvvAPsLqF4QkVsWJsj9sN93iTijlUnMC4LFe1In61kimIfyoxCYTRlEYep9goDOYqVq01OTA62Jb_4VwQejEuclAURoDBd_yQkdBHkXBvrxjPBfig5wFKQdlX21bSWwer67etsbGxhu1uG8vyowzZUkTnkahmro51vIsnzMU23_";

		Response resp = PlayListApi.postPlaylistApiWithNegToken(playlist, expiredToken);
		ErrorPojo resp1 = resp.as(ErrorPojo.class);

		Assert.assertEquals(resp.getStatusCode(), 401);
		Assert.assertEquals(resp1.getError().getStatus(), 401);
		Assert.assertEquals(resp1.getError().getMessage(), "The access token expired");
	}

}
