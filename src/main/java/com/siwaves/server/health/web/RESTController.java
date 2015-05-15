package com.siwaves.server.health.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.siwaves.server.health.domain.Patient;
import com.siwaves.server.health.domain.SearchCriteria;
import com.siwaves.server.health.repository.PatientShelf;
import com.siwaves.server.health.web.strings.Urls.Messages;
import com.siwaves.server.health.web.strings.Urls.UrlsRESTApi;

/**
 * Handles requests for the rest api.
 */
@Controller
public class RESTController {

	private static final Logger logger = LoggerFactory
			.getLogger(RESTController.class);

	@Autowired
	PatientShelf patientShelf;
	
	@InitBinder
	public void initBinder(Locale locale, WebDataBinder dataBinder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		dataBinder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * home to show all inserted patients
	 */
	@RequestMapping(value = "/rest/patients", method = RequestMethod.GET)
	public @ResponseBody List<Patient> getAllListedPatients() {
		System.out.println("Sent all the patients");

		List<Patient> patients = new ArrayList<Patient>();
		patients = patientShelf.findAll();

		return patients;
	}

	@RequestMapping(value = UrlsRESTApi.CREATE_NEW_PATIENT, method = RequestMethod.POST)
	public @ResponseBody String createPatient(@RequestBody Patient patient,
			SessionStatus status, HttpServletResponse response) {
		System.out.println("Create a new patient");

		if (patient != null
				&& (patient.getP_id() == null || patient.getP_id().length() <= 0)) {
			return Messages.R_ERROR_PID_EMPTY;
		}

		Patient existing = patientShelf.find(patient.getP_id());
		if (existing != null) {
			return Messages.R_ERROR_PID_EXITS;
		}

		status.setComplete();
		patientShelf.add(patient);
		if (status.isComplete()) {
			response.setStatus(HttpStatus.CREATED.value());
		}
		return Messages.R_NEW_PATIENT_ADDED;
	}

	/**
	 * update a patient
	 */
	@RequestMapping(value = UrlsRESTApi.UPDATE_EXISTING_PATIENT, method = RequestMethod.POST)
	public @ResponseBody Patient modifyPatient(@PathVariable("p_id") String p_id,@RequestBody Patient patient, SessionStatus status,
			HttpServletResponse response) {


		Patient existing = null;
		if (!p_id.matches("") && !p_id.isEmpty() && p_id != null) {

			existing = patientShelf.find(p_id);
			if (existing!=null) {
				patientShelf.save(patient);
				status.setComplete();
				response.setStatus(HttpStatus.OK.value());
				return patient;
			} else {
				response.setStatus(HttpStatus.CONFLICT.value());
				return existing;
			}
		}
		return null;
	}
	
	/**
	 * Search for patients.
	 */
	@RequestMapping(value = UrlsRESTApi.SEARCH_PATIENTS_BY_SEARCHCRITERIAS, method = RequestMethod.POST)
	public @ResponseBody List<Patient> searchForPatients(@RequestBody SearchCriteria searchCriteria,  SessionStatus status, HttpServletResponse response) {
		
		List<Patient> patientsList = new ArrayList<Patient>();
		
		if (searchCriteria != null) {
			status.setComplete();
			patientsList.clear();
			patientsList.addAll(patientShelf.findByCategoriesOrYear(searchCriteria.getCategories(), searchCriteria.getStartYear()));
			response.setStatus(HttpStatus.OK.value());
			return patientsList;
		}

		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return null;
	}

}
