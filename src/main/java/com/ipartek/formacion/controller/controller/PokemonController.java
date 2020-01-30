package com.ipartek.formacion.controller.controller;

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
import com.ipartek.formacion.model.PokemonDAO;
import com.ipartek.formacion.model.pojo.Pokemon;

/**
 * Servlet implementation class PokemonController
 */
@WebServlet("/api/pokemon/*")
public class PokemonController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(PokemonController.class);
    private static PokemonDAO dao;   
  

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = PokemonDAO.getInstance();
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		dao = null;
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
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
		String nombre = request.getParameter("nombre");
		int statusCode = HttpServletResponse.SC_OK;
		
		LOG.debug("pathinfo= " + pathinfo );
		LOG.debug("Parametro nombre= " + nombre );
		
		
		
		if ( nombre != null ) {
			ArrayList<Pokemon> pokemons = (ArrayList<Pokemon>) dao.getByNombre(nombre);
			
			if ( pokemons.isEmpty() ) {
				statusCode = HttpServletResponse.SC_NO_CONTENT;
			}
			
			responseBody = pokemons;
			
		}else if ( pathinfo == null || "/".equals(pathinfo) ){
			responseBody = (ArrayList<Pokemon>) dao.getAll();
			
		}else {
			int id = Integer.parseInt(pathinfo.split("/")[1]);
			responseBody = dao.getById(id);
			if ( responseBody == null ) {
				statusCode = HttpServletResponse.SC_NOT_FOUND;
			}
			
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

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
