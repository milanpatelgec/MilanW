package com.siwaves.server.health.domain;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Document
public class Patient {
	
	@Id
	private String p_id;
	
	private String fname;
	
	private String lname;

	@Indexed
	private Date arrived;
    
	@DBRef
	private Ident ident;
	
	private Set<String> categories;
	
	private String imageId;
	
	@Indexed
	private Date discharge;
	
	@Indexed
	private Date dob;
	
	private String note;
	
	
//	private CommonsMultipartFile file; 
//	
//	public CommonsMultipartFile getFile() {
//		return file;
//	}
//
//	public void setFile(CommonsMultipartFile file) {
//		this.file = file;
//	}

	public Date getDischarge() {
		return discharge;
	}

	public void setDischarge(Date discharge) {
		this.discharge = discharge;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	
	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Ident getIdent() {
		return ident;
	}

	public void setIdent(Ident ident) {
		this.ident = ident;
	}


	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public Date getArrived() {
		return arrived;
	}

	public void setArrived(Date arrived) {
		this.arrived = arrived;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}



}
