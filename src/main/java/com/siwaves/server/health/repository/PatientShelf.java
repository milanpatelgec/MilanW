package com.siwaves.server.health.repository;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.siwaves.server.health.domain.Patient;

public interface PatientShelf {
	
	void add(Patient patient);
	
	void save(Patient patient);
	
	Patient find(String p_id);
	
	void remove(String p_id);
	
	List<Patient> findAll();
	
	List<Patient> findByCategoriesOrYear(Set<String> categories, String year);
	
	 
	/**
	 * @return database name
	 */
	 DB getDatabase(); 
	 
	
	 /**
	 * @param inputStream : images 
	 * @param fileName
	 * @param contentType
	 * @param metaData
	 * @return
	 */
	String storeImage(InputStream inputStream, String fileName, String contentType, DBObject metaData);
			 
	 GridFSDBFile retriveImage(String fileName);

	 GridFSDBFile getImageById(String id);

	 GridFSDBFile getImageByFilename(String filename);

	 List findAllImages();

}
