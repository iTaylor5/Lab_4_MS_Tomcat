/*
 * GradeServlet.java
 *
 * Copyright:  Kevin A. Gary All Rights Reserved
 *
 */
package edu.asupoly.ser422;



import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import org.json.simple.JSONObject;

import java.io.*;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Kevin Gary
 *
 */
@SuppressWarnings("serial")
public class MapServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(MapServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		StringBuffer pageBuf = new StringBuffer();
		double grade = Double.parseDouble(req.getParameter("grade"));
		// String gradeString = req.getParameter("grade");
		// String subject = req.getParameter("subject");

		// if (year != null && !year.trim().isEmpty()) {
		// 	pageBuf.append("<br/>Year: " + year);
		// }
		// if (subject != null && !subject.trim().isEmpty()) {
		// 	pageBuf.append("<br/>Subject: " + subject);
		// }

		 GradeService service = null;
		 try {
		 	service = GradeService.getService();
		 } catch (Exception e) {
		 	e.printStackTrace();
		 }
		// if (service == null) {
		// 	pageBuf.append("\tSERVICE NOT AVAILABLE");
		// } else {
		// 	grade = service.calculateGrade(year, subject);
		// 	pageBuf.append("\n\t<br/>Grade: " + grade);
		// 	pageBuf.append("\n\t<br/>Letter: " + service.mapToLetterGrade(grade));
		// }
        String letterToGrade = service.mapToLetterGrade(grade);

		// some generic setup - our content type and output stream
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("This is the Map App. Grade = " + grade + ". Letter For Grade: " + letterToGrade);
		//out.println(pageBuf.toString());
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		log.info("** MAP ** In the post MapServlet ** MAP **");

		StringBuffer jb = new StringBuffer();

		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { /*report an error*/ }

		log.info(jb.toString());


		String value = req.getParameter("key1");
		log.info("value = " + value);
//		try {
//			JSONObject jsonObject =  HTTP.toJSONObject(jb.toString());
//		} catch (JSONException e) {
//			// crash and burn
//			throw new IOException("Error parsing JSON request string");
//		}

//		JSONObject replyJSON = new JSONObject();
//		JSONParser parser = new JSONParser();
//
//		JSONObject json = new JSONObject();
//		String payloadRequest = "";
//
//		try{
//			payloadRequest = getBody(req);
//			log.info("payLoad = " + payloadRequest);
//			json = (JSONObject) parser.parse(payloadRequest);
//		}catch (IOException | ParseException e){
//			replyJSON.put("ErrorParser", e.toString());
//			log.info("Error getting request" + e);
//		}
//
//		log.info("Got the body no issues.");
//
//		try {
//			json = (JSONObject) parser.parse(payloadRequest);
//		}catch (ParseException e) {
//			replyJSON.put("ErrorParser", e.toString());
//			log.info("ErrorParser Parsing" + e);
//			e.printStackTrace();
//		}
//
//		log.info("Parsed the body.");
//
//		StringBuffer pageBuf = new StringBuffer();
//
//		int grade = json.getInt("grade");
//
//		replyJSON.put("grade", grade);
//
//		GradeService service = null;
//
//		try {
//			service = GradeService.getService();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (service == null) {
//			pageBuf.append("\tSERVICE NOT AVAILABLE");
//			replyJSON.put("Error", "SERVICE NOT AVAILABLE");
//		} else {
//			// grade = service.calculateGrade(year, subject);
//			pageBuf.append("\n\t<br/>Grade: " + grade);
//			String letterGrade = service.mapToLetterGrade(grade);
//			pageBuf.append("\n\t<br/>Letter: " + letterGrade);
//			replyJSON.put("letterGrade", letterGrade);
//		}
//
//		res.setContentType("text/html");
//		PrintWriter out = res.getWriter();
//		// pageBuf.toString()
//		out.println(replyJSON.toString());

	}

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			log.info("Error in the getBody():" + ex);
			//throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					log.info("Error in the getBody():" + ex);
					//throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}
}
