package com.siwaves.server.health.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ident {
	
	@Id @SuppressWarnings("unused")
	private String id;
	
	private String dis;

	public String getDis() {
		return dis;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

}
