/*
 * GradeServlet.java
 *
 * Copyright:  Kevin A. Gary All Rights Reserved
 *
 */
package edu.asupoly.ser422;

import jdk.jfr.ContentType;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Kevin Gary
 *
 */
@SuppressWarnings("serial")
public class CalcAppServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(CalcAppServlet.class.getName());

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        StringBuffer pageBuf = new StringBuffer();
        double grade = -100;
        String year = req.getParameter("year");
        String subject = req.getParameter("subject");


        log.info("In the CalcAppServlet - calcApp - Year: " + year + " subject: " + subject);

        String resString = "";
        if (year != null && !year.trim().isEmpty()) {
            pageBuf.append("<br/>Year: " + year);
        }
        if (subject != null && !subject.trim().isEmpty()) {
            pageBuf.append("<br/>Subject: " + subject);
        }

        CalcService service = null;
        try {
            service = CalcService.getService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (service == null) {
            pageBuf.append("\tSERVICE NOT AVAILABLE");
        } else {
            JSONObject json = service.calculateGrade(year, subject);
//            pageBuf.append("\n\t<br/>Result: " + json.getString("result"));
//            pageBuf.append("\n\t<br/>error: " + json.getString("error"));
//            pageBuf.append("\n\t<br/>query: " + json.getString("query"));
//            pageBuf.append("\n\t<br/>grade: " + json.getInt("grade"));
            //resString = sendPost(json);
            //grade = service.calculateGrade(year, subject);

            if(json.getString("error").equals("none")){
                log.info("No errors after completing cal.");
                res.sendRedirect("http://localhost:8081/mapApp?grade=" + json.getInt("grade"));
            } else {
                log.info("ERROR: " + json.getString("error"));
                // TODO: Uncomment this out once DB is up and running....
//                pageBuf.append("\n\t<br/>ERROR: " + json.getString("error"));
//                res.setContentType("text/html");
//                PrintWriter out = res.getWriter();
//                out.println(pageBuf.toString());

                res.sendRedirect("http://localhost:8081/mapApp?grade=" + json.getInt("grade"));
            }
//			String url = "http://localhost:8081/mapApp?grade=" + grade;
//			res.sendRedirect(url);
            //pageBuf.append("\n\t<br/>Letter: " + service.mapToLetterGrade(grade));
        }

        // some generic setup - our content type and output stream
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        //pageBuf.toString()
        out.println(resString);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }

//    private String sendPost(JSONObject json) {
//
////        HttpPost method = new HttpPost(new URI("http://localhost:8081/mapApp"));
////        method.setHeader("Content-Type", "application/json");
////        method.setEntity(new StringEntity(json.toString(), ContentType.APPLICATION_JSON));
////        HttpParams params=message.getParams();
////        HttpConnectionParams.setConnectionTimeout(params, timeout);
////        HttpConnectionParams.setSoTimeout(params, timeout);
////
////        HttpClient client = new DefaultHttpClient();
////        HttpResponse response = client.execute(method);
////        InputStream in = response.getEntity().getContent();
//
//
//        try {
//            // Construct data
//            StringBuilder dataBuilder = new StringBuilder();
//            dataBuilder.append(URLEncoder.encode("key1", "UTF-8")).append('=').append(URLEncoder.encode(json.toString(), "UTF-8")).
//                    append(URLEncoder.encode("key2", "UTF-8")).append('=').append(URLEncoder.encode("value2", "UTF-8"));
//
//            // Send data
//            URL url = new URL("http://localhost:8081/mapApp");
//            URLConnection conn = url.openConnection();
//            conn.setDoOutput(true);
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//            wr.write(dataBuilder.toString());
//            wr.flush();
//
//            StringBuilder resString = new StringBuilder();
//            // Get the response
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = rd.readLine()) != null) {
//                log.info(line);
//                resString.append(line);
//            }
//            wr.close();
//            rd.close();
//           return resString.toString();
//        } catch (Exception e) {
//            return e.toString();
//        }
//    }
}
