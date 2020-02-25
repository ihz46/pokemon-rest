
package com.ipartek.formacion.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.Servlet;
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
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Servlet implementation class PokemonController
 */
@WebServlet("/api/pokemon/*")
public class PokemonController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(PokemonController.class);
	private static PokemonDAO dao;   
	private final int SC_OK = 200, SC_NOT_FOUND = 404, SC_NO_CONTENT = 204, SC_CREATED = 201,  SC_CONFLICT = 409;
	
	
	
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
		int codigo = 200;
		Pokemon p = new Pokemon();
		// convertir json del request body a Objeto
		BufferedReader reader = request.getReader();               
		Gson gson = new Gson();
		p = gson.fromJson(reader, Pokemon.class);
				
		try {
			
			p = dao.create(p);
			codigo = ( p.getId()!= 0 )? ("".equals(p.getNombre()) || p.getNombre() == null )? SC_NO_CONTENT : SC_CREATED: SC_NOT_FOUND ; 
			response.setStatus(codigo);
		} catch (MySQLIntegrityConstraintViolationException e) {
			LOG.error("No se ha podido crear el Pokemon" + e.getMessage());
			codigo = SC_CONFLICT;
			response.setStatus(codigo);
		}catch(Exception e) {
			LOG.error("No se ha podido crear el Pokemon " + e.getMessage());
			
		}
		
		LOG.debug(codigo);
		
		try(PrintWriter out = response.getWriter();){
			/**
			 * Condicion para comprobar si el codigo de estado que devuelve es el correcto y en caso contrario
			 * mandar un mensaje al usuario 
			 */
			if(codigo == SC_CREATED) {
				Gson json = new Gson();
				out.println(json.toJson(p));
			}else {
				String mensaje = "No se ha encontrado ningun Pokemon";
				out.println(mensaje);
			}
			out.flush();
		}//End IF
		
	}//DoPost END
	
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pokemon p = new Pokemon();
		String id = request.getPathInfo();
		id = id.replace("/", "");
		
		// convertir json del request body a Objeto
		BufferedReader reader = request.getReader();               
		Gson gson = new Gson();
		p = gson.fromJson(reader, Pokemon.class);
		
		
		
		try {
			
			p = dao.update(Integer.parseInt(id), p);
		} catch (Exception e) {
			LOG.error("No se ha podido actualizar el pokemon " + e.getMessage());
			
		}
		
		int codigo = ( Integer.parseInt(id) != 0 )? ("".equals(p.getNombre()) || p.getNombre() == null )? SC_NO_CONTENT : SC_OK : SC_NOT_FOUND; 
		response.setStatus(codigo);
		
		try(PrintWriter out = response.getWriter();){
			/**
			 * Condicion para comprobar si el codigo de estado que devuelve es el correcto y en caso contrario
			 * mandar un mensaje al usuario 
			 */
			if(codigo == SC_OK) {
				Gson json = new Gson();
				out.println(json.toJson(p));
			}else {
				String mensaje = "No se ha encontrado ningun Pokemon";
				out.println(mensaje);
			}
			out.flush();
		}
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Object responseBody = null;
		String pathinfo = request.getPathInfo();
		int statusCode = HttpServletResponse.SC_OK;
		
		try {
			int id = Integer.parseInt(pathinfo.split("/")[1]);
			responseBody = dao.delete(id);
			
		} catch (Exception e) {
			LOG.error(e);
			responseBody = "No se ha podido eliminar el producto";
			statusCode = HttpServletResponse.SC_NOT_FOUND;
		}
		
		
		try( PrintWriter out = response.getWriter() ){
			
			if ( responseBody != null ) {
				Gson json = new Gson();
				out.print( json.toJson(responseBody) );
				out.flush();
			}	
			
		}catch (Exception e) {
			LOG.error(e);
			responseBody = "No se ha podido eliminar el pokemon ";
			statusCode = HttpServletResponse.SC_NOT_FOUND;
			
		}
		
	}
	
}