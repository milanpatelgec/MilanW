package com.siwaves.server.health.repository;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.siwaves.server.health.domain.Ident;
import com.siwaves.server.health.domain.Patient;

@Repository
public class MongoPatientShelf implements PatientShelf {

	@Autowired
	MongoTemplate mongoTemplatePatient;
	@Autowired
	GridFsTemplate gridFsTemplate;

	@Override
	public void add(Patient patient) {
		lookUpdis(patient);
		mongoTemplatePatient.insert(patient);
	}

	@Override
	public void save(Patient patient) {
		lookUpdis(patient);
		mongoTemplatePatient.save(patient);
	}

	@Override
	public Patient find(String p_id) {
		Query query = new Query(Criteria.where("_id").is(p_id));
		return mongoTemplatePatient.findOne(query, Patient.class);
	}

	@Override
	public void remove(String p_id) {
		Query query = new Query(Criteria.where("_id").is(p_id));
		mongoTemplatePatient.remove(query, Patient.class);
	}

	@Override
	public List<Patient> findAll() {
		List<Patient> patients = mongoTemplatePatient.findAll(Patient.class);
		return Collections.unmodifiableList(patients);
	}

	@Override
	public List<Patient> findByCategoriesOrYear(Set<String> categories,
			String year) {
		String[] categoriesToMatch;
		if (categories == null) {
			categoriesToMatch = new String[] {};
		} else {
			categoriesToMatch = categories
					.toArray(new String[categories.size()]);
		}
		Date startDate = null;
		if (year != null && year.length() == 4) {
			DateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
			try {
				startDate = formatter.parse(year + "-01-01");
			} catch (ParseException e) {
			}
		}
		Criteria searchCriteria = null;
		if (startDate != null) {
			searchCriteria = Criteria.where("arrived").gte(startDate);
		} else {
			searchCriteria = new Criteria();
		}
		if (categoriesToMatch.length > 0) {
			searchCriteria.and("categories").in((Object[]) categoriesToMatch);
		}
		Query query = new Query(searchCriteria);
		return mongoTemplatePatient.find(query, Patient.class);
	}

	private void lookUpdis(Patient patient) {
		if (patient.getIdent() != null) {
			Query query = new Query(Criteria.where("dis").is(
					patient.getIdent().getDis()));
			Ident existing = mongoTemplatePatient.findOne(query, Ident.class);

			if (existing != null) {
				patient.setIdent(existing);
			} else {
				mongoTemplatePatient.insert(patient.getIdent());
			}

		}
	}

	@Override
	public DB getDatabase() {
		return mongoTemplatePatient.getDb();
	}

	public String storeImage(InputStream inputStream, String fileName,
			String contentType, DBObject metaData) {
		return this.gridFsTemplate
				.store(inputStream, fileName, contentType, metaData).getId()
				.toString();
	}

	public GridFSDBFile getImageById(String id) {
		return this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is( new ObjectId(
				id))));
	}

	public GridFSDBFile getImageByFilename(String fileName) {
		return gridFsTemplate.findOne(new Query(Criteria.where("filename").is(
				fileName)));
	}

	public GridFSDBFile retriveImage(String fileName) {
		return gridFsTemplate.findOne(new Query(Criteria.where("filename").is(
				fileName)));
	}

	public List findAllImages() {
		return gridFsTemplate.find(null);
	}

}
