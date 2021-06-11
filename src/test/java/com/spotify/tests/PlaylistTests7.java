package com.spotify.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.spotify.api.Constant;
import com.spotify.api.PlayListApi;
import com.spotify.api.StatusCode;
import com.spotify.pojo1.ErrorPojo;
import com.spotify.pojo1.PlayListPojo;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;

@Epic("Spotify oAuth2.0")
@Feature("PlayList APIs")
public class PlaylistTests7 {

	String id;
	PlayListPojo playlist = null;

	@Story("Create a playlist api")
	@Issue("AMA-2342")
	@Link("AMA-3123")
	@Epic("AMA-13")
	@TmsLink("TMS-1321")

	@Description("Verify create playlist api and validate the response")
	@Test(priority = 1, description = "Verify Create Playlist Api")
	public void shouldBeAbletoCreatePlayList() {

		playlist = playListBuilder("Test PlayList1", "Test Description1", false);

		Response resp = PlayListApi.postPlaylistApi(playlist);
		PlayListPojo resp1 = resp.as(PlayListPojo.class);
		assertPlayList(resp1, playlist);
		assertSuccess(resp, StatusCode.CODE_201.code);

		id = resp1.getId();

	}

	@Story("View playlist api")
	@Description("Verify get playlist api and validate the response")
	@Test(priority = 2, description = "Verify View Playlist Api")
	public void shouldBeAbletoGetPlayList() {
		String url = Constant.PLAYLISTS + "/" + id;

		Response resp = PlayListApi.getPlaylistApi(url);

		PlayListPojo resp1 = resp.as(PlayListPojo.class);
		assertSuccess(resp, StatusCode.CODE_200.code);
		assertPlayList(resp1, playlist);

	}

	@Description("Verify update playlist api and validate the response")
	@Test(priority = 3, description = "Verify Update Playlist Test api")
	public void shouldBeAbletoUpdatePlayList() {
		String url = Constant.PLAYLISTS + "/" + id;

		playlist = playListBuilder("Test PlayList Updated", "Test Description Updated", false);

		Response resp = PlayListApi.putPlaylistApi(playlist, url);
		assertSuccess(resp, StatusCode.CODE_200.code);

	}

	@Story("View playlist api")
	@Description("Verify modified playlist api and validate the response")
	@Test(priority = 4, description = "Verify Modified Playlist ")
	public void shouldBeAbletoGetPlayListAfterUpdate() {

		String url = Constant.PLAYLISTS + "/" + id;

		Response resp = PlayListApi.getPlaylistApi(url);

		PlayListPojo resp1 = resp.as(PlayListPojo.class);

		assertSuccess(resp, StatusCode.CODE_200.code);
		assertPlayList(resp1, playlist);

	}

	@Description("Verify create playlist api without name and validate the error message")
	@Test(priority = 5, description = "Verify Create Playlist Test without Name")
	public void shouldNotBeAbleToCreatePlayListWithoutName() {

		playlist = playListBuilder("", "Test Description Updated", false);

		Response resp = PlayListApi.postPlaylistApi(playlist);

		ErrorPojo resp1 = resp.as(ErrorPojo.class);
		assertSuccess(resp, StatusCode.CODE_400_MISSING_NAME.code);

		assertErrorPlayList(resp1, StatusCode.CODE_400_MISSING_NAME.code, StatusCode.CODE_400_MISSING_NAME.message);

	}

	@Story("View playlist api")
	@Description("Verify  playlist api with wrong token and validate the error message")
	@Test(priority = 6, description = "Verify playlist api with wrong token")
	public void wrongTokenTest() {
		playlist = playListBuilder("Test PlayList Updated", "Test Description Updated", false);

		String wrongToken = "dsdas";
		Response resp = PlayListApi.postPlaylistApiWithNegToken(playlist, wrongToken);
		ErrorPojo resp1 = resp.as(ErrorPojo.class);

		assertSuccess(resp, StatusCode.CODE_401_WRONG_TOKEN.code);

		assertErrorPlayList(resp1, StatusCode.CODE_401_WRONG_TOKEN.code, StatusCode.CODE_401_WRONG_TOKEN.message);

	}

	@Story("View playlist api")
	@Description("Verify  playlist api with expired token and validate the error message")
	@Test(priority = 7, description = "Verify playlist api with expired token")
	public void accessTokenExpiredTest() {
		playlist = playListBuilder("Test PlayList Updated", "Test Description Updated", false);

		String expiredToken = "BQCdpbqWn5JtnLV9Q97a_jN0jclQO6POm2aHiShiqgYiK_3YlFahVgr_sinefHN8TlvvAPsLqF4QkVsWJsj9sN93iTijlUnMC4LFe1In61kimIfyoxCYTRlEYep9goDOYqVq01OTA62Jb_4VwQejEuclAURoDBd_yQkdBHkXBvrxjPBfig5wFKQdlX21bSWwer67etsbGxhu1uG8vyowzZUkTnkahmro51vIsnzMU23_";

		Response resp = PlayListApi.postPlaylistApiWithNegToken(playlist, expiredToken);
		ErrorPojo resp1 = resp.as(ErrorPojo.class);

		assertSuccess(resp, StatusCode.CODE_401_EXPIRE_TOKEN.code);

		assertErrorPlayList(resp1, StatusCode.CODE_401_EXPIRE_TOKEN.code, StatusCode.CODE_401_EXPIRE_TOKEN.message);

	}

	@Step
	public PlayListPojo playListBuilder(String name, String description, Boolean _public) {
		playlist = new PlayListPojo();
		playlist.setName(name);
		playlist.setDescription(description);
		playlist.set_public(_public);
		return playlist;
	}

	@Step
	public void assertPlayList(PlayListPojo resp1, PlayListPojo playlist) {

		Assert.assertEquals(resp1.getName(), playlist.getName());
		Assert.assertEquals(resp1.getDescription(), playlist.getDescription());
		Assert.assertEquals(resp1.get_public(), playlist.get_public());
	}

	@Step
	public void assertSuccess(Response resp, int statusCode) {
		Assert.assertEquals(resp.getStatusCode(), statusCode);
	}

	@Step
	public void assertErrorPlayList(ErrorPojo resp1, int statusCode, String message) {
		Assert.assertEquals(resp1.getError().getStatus(), statusCode);
		Assert.assertEquals(resp1.getError().getMessage(), message);
	}

}
