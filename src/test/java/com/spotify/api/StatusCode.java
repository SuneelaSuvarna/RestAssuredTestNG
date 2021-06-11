package com.spotify.api;

public enum StatusCode {

	CODE_200(200, ""), CODE_201(201, ""), CODE_400_MISSING_NAME(400, "Missing required field: name"),
	CODE_401_WRONG_TOKEN(401, "Invalid access token"), CODE_401_EXPIRE_TOKEN(401, "The access token expired");

	public final int code;
	public final String message;

	StatusCode(int code, String message) {

		this.code = code;
		this.message = message;

	}

}
