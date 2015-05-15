package com.siwaves.server.health.web;
//package org.springframework.data.demo.web;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.math.BigInteger;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.data.demo.domain.PatientInfo;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//
///**
// * 
// * @author hiren for accessing data from the Android devices through POST
// *         request
// */
//@SuppressWarnings("serial")
//@WebServlet(name = "remotestation", urlPatterns = { "/remotestation" })
//public class remotestation extends HttpServlet {
//
//private String patientID = "";
//
//	/**
//	 * Handles the HTTP <code>GET</code> method.
//	 * 
//	 * @param request
//	 *            servlet request
//	 * @param response
//	 *            servlet response
//	 * @throws ServletException
//	 *             if a servlet-specific error occurs
//	 * @throws IOException
//	 *             if an I/O error occurs
//	 */
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
//	 * , javax.servlet.http.HttpServletResponse) We will use for static
//	 * contents, When we will use it, Our request parameters go through http
//	 * packet header. Or size of http packet header is fixed. So only limited
//	 * data can be send. or in case of doGet() request parameters are shown in
//	 * address bar, Or in network data send like plane text.
//	 */
//	@Override
//	protected void doGet(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
//	}
//
//	public static String getData() {
//		return "Hi I am the server!!!";
//	}
//
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
//	 * , javax.servlet.http.HttpServletResponse)We will use for dynamic
//	 * contents, When we will use it, Our request parameters go through http
//	 * packet body. Or size of http packet body is not fixed. So Unlimited data
//	 * can be send. or in case of doPost() request parameters are not shown in
//	 * address bar, Or in network data send like encrypted text.
//	 */
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		
//		String  serverMsg = "", setMessage = "", minThreshold = "", 
//				maxThreshold = "", minYellow = "", maxYellow = "";
//        int availableBytes = 0;
//		
//		boolean boolPulse = Boolean.parseBoolean(req.getParameter("true_pulse"));
//		boolean boolECG = Boolean.parseBoolean(req.getParameter("true_ecg"));
//		String wave0, wave1, wave2, wave3, wave4;
//		int pulserate = 0;
//		String getGraphData = "";
//		
//		minThreshold = req.getParameter("min_heart");
//		maxThreshold = req.getParameter("max_heart");
//		patientID = req.getParameter("patient_id");
//		setMessage = req.getParameter("set_message");
//		serverMsg = req.getParameter("type_message");
//		minYellow = req.getParameter("min_yellow");
//		maxYellow = req.getParameter("max_yellow");
//		
//		
//		availableBytes = Integer.parseInt(req.getParameter("avaiable_bytes"));
//		
//	 PatientInfo p = new PatientInfo();
//		
//		
//		if(boolPulse){
//			
//			boolECG = false;
//			
//			String getStream = req.getParameter("data_stream");
//        	String dataBytes[] = getStream.split("x");
//        	
//		
//        if(availableBytes ==12 ){
//        
//        	
//        	pulserate = Integer.parseInt(
//					dataBytes[7].concat(dataBytes[6]), 16);
//    		p.setPulse_rate(String.valueOf(pulserate));(pulserate);
//    		
//    	//	FakeDatabase.putHeart(pulserate);
//        }
//        
//		if(availableBytes==11){
//			
//			wave0 = hexToBin(dataBytes[5]);
//			wave1 = hexToBin(dataBytes[6]);
//			wave2 = hexToBin(dataBytes[7]);
//			wave3 = hexToBin(dataBytes[8]);
//			wave4 = hexToBin(dataBytes[9]);
//
//			char w0 = wave0.charAt(0);
//			char w1 = wave1.charAt(0);
//			char w2 = wave2.charAt(0);
//			char w3 = wave3.charAt(0);
//			char w4 = wave4.charAt(0);
//
//			String beatCheck = String.valueOf(w0)
//					+ String.valueOf(w1) + String.valueOf(w2)
//					+ String.valueOf(w3) + String.valueOf(w4);
//		StringBuilder	 wave_string = new StringBuilder();
//
//			if (beatCheck.contains("11111")) {
//
//				for (int i = 5; i < 10; i++) {
//					int parseInt = Integer.parseInt(dataBytes[i],16);
//					wave_string.append(parseInt/8 + ",");
//
//				}
//				
//				getGraphData = wave_string.deleteCharAt(wave_string.length()-1).toString();
//			
//			}
//			
//			
//		}
//		
//	}
//		
//		if(boolECG){
//			
//			boolPulse = false;
//			
//	        getGraphData = req.getParameter("data_stream");
//			pulserate = availableBytes;
//			
//			minThreshold = req.getParameter("min_heart");
//    		maxThreshold = req.getParameter("max_heart");
//    		patientID = req.getParameter("patient_id");
//    		setMessage = req.getParameter("set_message");
//    		serverMsg = req.getParameter("type_message");
//    		minYellow = req.getParameter("min_yellow");
//    		maxYellow = req.getParameter("max_yellow");
//    		
//    		p.setHeartRate(pulserate);
//    		//FakeDatabase.putHeart(pulserate);
//			
//		}
//	///	rocky.varun6
//		
//	
//		
//		p.setMinThre(minThreshold);p.setMaxThre(maxThreshold);p.setPatientID(patientID);p.setSetMsg(setMessage);
//        p.setServerMsg(serverMsg);p.setGraphData(getGraphData);		
//
////		System.out.println(pulserate+ " || " + getGraphData + " || "
////			+ minThreshold + " || " + maxThreshold + " || " + patientID
////				+ " || " + setMessage + " || " + serverMsg + " || " + minYellow
////				+ " || " + maxYellow);
//		
//	//	MongoClient mongo = (MongoClient) req.getServletContext()
//	//			.getAttribute("MONGO_CLIENT");
//	//	MgDBPatientDAO personDAO = new MgDBPatientDAO(mongo);
//	//	personDAO.createPatient(p);
//        
//        if(!patientID.isEmpty() && !patientID.matches("") && patientID!=null ){
//		storeInDB(p);
//        }
//		
//		
//	//	System.out.println("Person Added Successfully with id="+p.getId());
//		
//	//	FakeDatabase.save(String.valueOf(pulserate), getGraphData, minThreshold, maxThreshold,
//	//			patientID, setMessage, serverMsg, minYellow, maxYellow);
//		
//		super.doPost(req, resp);
//	
//	}
//		
//		private void storeInDB(PatientInfo p) throws UnknownHostException {
//			
//			List<String> list = new ArrayList<String>();
//			
//			if(!p.getGraphData().isEmpty() && !p.getGraphData().matches("") && p.getGraphData()!=null){
//			list.add(p.getGraphData());
//			
//	         BasicDBObject query = new BasicDBObject();
//	         query.put("patient ID",  p.getPatientID());
//	         DBCursor cursor1 = coll.find(query);
//	         if(cursor1.count()>0){
//	        	 BasicDBObject newDocument = new BasicDBObject();
//	        		newDocument.put("graphData", list);
//	        	 
//	        		BasicDBObject updateObj = new BasicDBObject();
//	        		updateObj.put("$push", newDocument);
//	        	 
//	        		coll.update(query, updateObj);
//	        	 
//	         }else{
//	                 BasicDBObject doc = new BasicDBObject();
//	                 doc.put("patient ID", p.getPatientID());
//	                 doc.put("heartRate", p.getHeartRate());
//	                 doc.put("minH", p.getMinThre());
//	                 doc.put("maxH", p.getMaxThre());
//	                 doc.put("setMsg", p.getSetMsg());
//	                 doc.put("serverMsg", p.getServerMsg());
//	                 doc.put("graphData",  list);
//	              coll.insert(doc);
//	              DBCursor cursor = coll.find();
//	              int i=1;
//	              while (cursor.hasNext()) { 
//	                 System.out.println("Inserted Document: "+i); 
//	                 System.out.println(cursor.next()); 
//	                 i++;
//	              }
//	              
//	         }
//	         
//		}
//		
//	}
//		
////		private void checkForPatientID(String pid, String graph){
////			
////			DBCollection table = db.getCollection("user");
////			BasicDBObject query = new BasicDBObject();
////			query.put("patient ID", pid);
////			
////			BasicDBObject newDocument = new BasicDBObject();
////			newDocument.put("graphData", graph);
////			
////			BasicDBObject updateObj = new BasicDBObject();
////			updateObj.put("$set", newDocument);
////		 
////			table.update(query, updateObj);
////		}
//
//		static String hexToBin(String s) {
//			return new BigInteger(s, 16).toString(2);
//		}
//		
//}
