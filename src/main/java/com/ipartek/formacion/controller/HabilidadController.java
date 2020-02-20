package com.ipartek.formacion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ipartek.formacion.model.HabilidadDAO;
import com.ipartek.formacion.model.pojo.Habilidad;

/**
 * Servlet implementation class HabilidadController
 */
@WebServlet("/api/habilidad/*")
public class HabilidadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(HabilidadController.class);
	private static HabilidadDAO dao;   
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = HabilidadDAO.getInstance();
	}
	
	@Override
	public void destroy() {
		super.destroy();
		dao = null;
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		super.service(request, response);
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Object responseBody = null;
		String pathinfo = request.getPathInfo();
		int statusCode = HttpServletResponse.SC_OK;
		
		LOG.debug("pathinfo= " + pathinfo );
		
		ArrayList<Habilidad> habilidades = (ArrayList<Habilidad>) dao.getAll();
		
		LOG.debug(habilidades);
		if(habilidades.isEmpty() || habilidades == null) {
			statusCode = HttpServletResponse.SC_NO_CONTENT;
			
		}else {
			responseBody = habilidades;
			
		}
		response.setStatus(statusCode); 
		
		try( PrintWriter out = response.getWriter() ){
			
			if ( responseBody != null ) {
				Gson json = new Gson();
				out.print( json.toJson(responseBody) );
				out.flush();
			}	
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
