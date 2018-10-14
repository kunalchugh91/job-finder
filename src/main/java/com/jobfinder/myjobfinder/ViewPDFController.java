package com.jobfinder.myjobfinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobfinder.myjobfinder.dao.DAO;
import com.jobfinder.myjobfinder.pojo.UserProfile;

@Controller
public class ViewPDFController {

	@RequestMapping(value="resume.pdf", method=RequestMethod.GET)
	public void getPDF(HttpServletRequest request,
					   HttpServletResponse response,
			           @RequestParam("userid") int userid,
			           @RequestParam("jobID") int jobID) throws IOException{
		
		UserProfile u = DAO.getSession().get(UserProfile.class, userid);
		
		// get absolute path of the application
		   ServletContext context = request.getSession().getServletContext();
		   String appPath = context.getRealPath("");

		   // construct the complete absolute path of the file
		   String fullPath = null;
		   fullPath = u.getFile();
		   if(fullPath != null){
		   File downloadFile = new File(fullPath);
		   FileInputStream inputStream = new FileInputStream(downloadFile);

		   // get MIME type of the file
		   String mimeType = context.getMimeType(fullPath);
		   if (mimeType == null) {
		       // set to binary type if MIME mapping not found
		       mimeType = "application/pdf";
		   }
		   System.out.println("MIME type: " + mimeType);


		   String headerKey = "Content-Disposition";

		   //response.addHeader("Content-Disposition", "attachment;filename=report.pdf");
		   response.setContentType(mimeType);

		   // get output stream of the response
		   OutputStream outStream = response.getOutputStream();

		   byte[] buffer = new byte[100];
		   int bytesRead = -1;

		   try{
		   // write bytes read from the input stream into the output stream
		   while ((bytesRead = inputStream.read(buffer)) != -1) {
		       outStream.write(buffer, 0, bytesRead);
		   }
		   }

		   finally{
		   inputStream.close();
		   outStream.close();
		   }
		   }else{
			   response.sendRedirect("viewcandidates.htm?jobID="+jobID);
		   }
		
	}
	
}
