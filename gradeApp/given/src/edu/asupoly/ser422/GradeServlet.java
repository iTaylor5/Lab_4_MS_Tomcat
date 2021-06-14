/*
 * GradeServlet.java
 *
 * Copyright:  Kevin A. Gary All Rights Reserved
 *
 */
package edu.asupoly.ser422;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Kevin Gary
 *
 */
@SuppressWarnings("serial")
public class GradeServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(GradeServlet.class.getName());

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 *
	 * localhost:8080/gradeapp?year=3&subject=English
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		StringBuffer pageBuf = new StringBuffer();
		double grade;
		String year = req.getParameter("year");
		String subject = req.getParameter("subject");

		if (year != null && !year.trim().isEmpty()) {
			pageBuf.append("<br/>Year: " + year);
		}
		if (subject != null && !subject.trim().isEmpty()) {
			pageBuf.append("<br/>Subject: " + subject);
		}

		GradeService service = null;
		try {
			service = GradeService.getService();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (service == null) {
			pageBuf.append("\tSERVICE NOT AVAILABLE");
		} else {
			grade = service.calculateGrade(year, subject);

			String url = "http://localhost:8081/calcApp?" + "year=" + year + "&subject=" + subject;

			log.info("Sending redirect to calcApp ++++");
			res.sendRedirect(url);
			// pageBuf.append("\n\t<br/>Grade: " + grade);
			// pageBuf.append("\n\t<br/>Letter: " + service.mapToLetterGrade(grade));
		}

		// some generic setup - our content type and output stream
		// res.setContentType("text/html");
		// PrintWriter out = res.getWriter();

		// out.println("Hello you are in grade servlet. Year: " + year + "subject:" + subject);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}
