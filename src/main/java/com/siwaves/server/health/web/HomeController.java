package com.siwaves.server.health.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.siwaves.server.health.domain.Ident;
import com.siwaves.server.health.domain.Patient;
import com.siwaves.server.health.domain.SearchCriteria;
import com.siwaves.server.health.repository.PatientShelf;
import com.siwaves.server.health.web.strings.Urls.Messages;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(HomeController.class);

	private String imageId = null;

	private final List<String> categories = Arrays.asList("ECG",
			"Pulse Oximeter", "Blood Pressure");

	private final List<String> years = Arrays.asList("", "2000", "2001",
			"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
			"2010", "2011");

	@Autowired
	PatientShelf patientShelf;

	@InitBinder
	public void initBinder(Locale locale, WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		dataBinder.registerCustomEditor(Date.class, null, new CustomDateEditor(
				dateFormat, true));
		// dataBinder.registerCustomEditor(String.class, null,new
		// StringMultipartFileEditor());
	}

	/**
	 * home to show all inserted patients
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {

		model.addAttribute("version",
				"The demo version for storing and accessing patients data from sensors");

		model.addAttribute("patientList", patientShelf.findAll());

		return "home";
	}

	/**
	 * Add new patient's form.
	 */
	@RequestMapping(value = "/newpatient", method = RequestMethod.GET)
	public String newPatient(Model model) {

		Patient patient = new Patient();
		patient.setIdent(new Ident());
		model.addAttribute("patient", patient);

		model.addAttribute("categories", categories);

		return "addPatient";
	}

	/**
	 * Save the new patient by POST method.
	 */
	@RequestMapping(value = "/newpatient", method = RequestMethod.POST)
	public String addPatient(@ModelAttribute("patient") Patient newPatient,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) {

		String profileName = "no name";

		if (!request.getParameter("filename").matches("")
				&& request.getParameter("filename") != null) {
			profileName = request.getParameter("filename");
		} else {
			profileName = "no name";
		}

		if (request.getParameter("_cancel") != null) {
			return "redirect:/";
		}
		if (newPatient != null
				&& (newPatient.getP_id() == null || newPatient.getP_id()
						.length() <= 0)) {
			ObjectError error = new ObjectError("patient.p_id",
					Messages.B_ERROR_PID_EMPTY);
			result.addError(error);
		}

		Patient existing = patientShelf.find(newPatient.getP_id());
		if (existing != null) {
			ObjectError error = new ObjectError("patient.p_id",
					Messages.B_ERROR_PID_EXITS);
			result.addError(error);
		}

		if (result.hasErrors()) {
			return "addPatient";
		}
		if (newPatient != null) {
			status.setComplete();
			System.out.println("Little ways down the road!");
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile("file");

			System.out.println(file.getOriginalFilename() + " "
					+ file.getName() + " " + newPatient.getFname() + " "
					+ profileName);

			if (!file.isEmpty()) {
				try {
					imageId = storeImages(file.getInputStream(), profileName,
							file.getContentType());
				} catch (IOException e) {
					e.printStackTrace();
					return "redirect:/";

				}
			} else {
				imageId = "no";
			}
			newPatient.setImageId(imageId);
			patientShelf.add(newPatient);
			imageId = "no";
		}

		return "redirect:/";
	}

	/**
	 * Edit a patient, access by p_id id.
	 */
	@RequestMapping(value = "/editpatient/{p_id}", method = RequestMethod.GET)
	public String editPatient(@PathVariable("p_id") String p_id, Model model, HttpServletResponse response) {

		Patient patient = patientShelf.find(p_id);

		System.out.println("Image of the patient is: " + patient.getImageId());

		GridFSDBFile byFileId = patientShelf.getImageById(patient.getImageId());
//		System.out.print(byFileId.getLength());
//		try {
//			byFileId.writeTo("C:/Users/sangani/Desktop/Pics/wwd.jpg");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

		model.addAttribute("patient", patient);

		model.addAttribute("categories", categories);
		
		
//		 byte[] thumb = byFileId.getInputStream().;
//
//	        String name = "userAvatar";
//	        response.setContentType("image/jpeg");
//	        response.setContentLength(thumb.length);
//
//	        response.setHeader("Content-Disposition", "inline; filename=\"" + name
//	                + "\"");
//
//	        BufferedInputStream input = null;
//	        BufferedOutputStream output = null;
//
//	        try {
//	            input = new BufferedInputStream(new ByteArrayInputStream(thumb));
//	            output = new BufferedOutputStream(response.getOutputStream());
//	            byte[] buffer = new byte[8192];
//	            int length;
//	            while ((length = input.read(buffer)) > 0) {
//	                output.write(buffer, 0, length);
//	            }
//	        } catch (IOException e) {
//	            System.out.println("Thereerrors in reading/writing image stream "
//	                    + e.getMessage());
//	        } finally {
//	            if (output != null)
//	                try {
//	                    output.close();
//	                } catch (IOException ignore) {
//	                }
//	            if (input != null)
//	                try {
//	                    input.close();
//	                } catch (IOException ignore) {
//	                }
//	        }
		
		
		

		return "editPatient";
	}

	/**
	 * Save an edited patient.
	 */
	@RequestMapping(value = "/editpatient/{p_id}", method = RequestMethod.POST)
	public String modifyPatient(@PathVariable("p_id") String p_id,
			@ModelAttribute("patient") Patient patient, BindingResult result,
			SessionStatus status, HttpServletRequest request) {

		String profileName = "no name";

		if (!request.getParameter("filename").matches("")
				&& request.getParameter("filename") != null) {
			profileName = request.getParameter("filename");
		} else {
			profileName = "no name";
		}

		if (request.getParameter("_cancel") != null) {
			return "redirect:/";
		}
		if (request.getParameter("_delete") != null) {
			patientShelf.remove(p_id);
			return "redirect:/";
		}
		if (result.hasErrors()) {
			return "editPatient";
		}
		if (patient != null) {

			System.out.println("Little ways down the road!");
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multiRequest.getFile("file");

			System.out.println(file.getOriginalFilename() + " "
					+ file.getName() + " " + patient.getFname() + " "
					+ profileName);

			if (!file.isEmpty()) {
				try {
					imageId = storeImages(file.getInputStream(), profileName,file.getContentType());
				} catch (IOException e) {
					e.printStackTrace();
					return "redirect:/";

				}
			} else {
				imageId = "no";
			}
			patient.setImageId(imageId);

			status.setComplete();
			patientShelf.save(patient);
		}

		return "redirect:/";
	}

	/**
	 * Define search criteria.
	 */
	@RequestMapping(value = "/searchpatient", method = RequestMethod.GET)
	public String searchPatient(Model model) {

		SearchCriteria searchCriteria = new SearchCriteria();
		model.addAttribute("searchpatient", searchCriteria);

		model.addAttribute("years", years);
		model.addAttribute("categories", categories);

		List<Patient> patientList = new ArrayList<Patient>();
		model.addAttribute("patientList", patientList);

		return "searchPatient";
	}

	/**
	 * Search for patients.
	 */
	@RequestMapping(value = "/searchpatient", method = RequestMethod.POST)
	public String searchForPatients(
			@ModelAttribute("searchpatient") SearchCriteria searchCriteria,
			@ModelAttribute("patientList") ArrayList<Patient> patientList,
			Model model, BindingResult result, SessionStatus status,
			HttpServletRequest request) {

		if (request.getParameter("_cancel") != null) {
			return "redirect:/";
		}
		if (result.hasErrors()) {
			return "searchPatient";
		}

		if (searchCriteria != null) {
			status.setComplete();
			patientList.clear();
			patientList.addAll(patientShelf.findByCategoriesOrYear(
					searchCriteria.getCategories(),
					searchCriteria.getStartYear()));
		}

		model.addAttribute("years", years);
		model.addAttribute("categories", categories);

		model.addAttribute("patientList", patientList);

		return "searchPatient";
	}

	private String storeImages(InputStream is, String name, String contentType) {
		DBObject metaData = new BasicDBObject();
		metaData.put("profilename", name);

		String id = patientShelf.storeImage(is, name, contentType, metaData);

		System.out.println("Find By Filename----------------------");
		GridFSDBFile byFileName = patientShelf.getImageById(id);
		System.out.println("File Name:- " + byFileName.getFilename());
		return id;
	}

}
