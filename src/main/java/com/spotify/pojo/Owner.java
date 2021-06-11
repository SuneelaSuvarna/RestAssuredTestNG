package com.spotify.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Owner {

	private String display_name;
	
	
	@JsonProperty("external_urls")
	private ExternalUrls_ external_urls_1;
	private String href;
	private String id;
	private String type;
	private String uri;

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public ExternalUrls_ getExternal_urls_1() {
		return external_urls_1;
	}

	public void setExternal_urls_1(ExternalUrls_ external_urls_1) {
		this.external_urls_1 = external_urls_1;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
