package com.ipartek.formacion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.model.pojo.Habilidad;
import com.ipartek.formacion.model.pojo.Pokemon;

public class HabilidadDAO implements IDAO<Habilidad>{
	
	private static HabilidadDAO INSTANCE;
	private static final Logger LOG = Logger.getLogger(HabilidadDAO.class);
	
	private static final String SQL_GET_ALL = "SELECT id, nombre FROM habilidad ORDER BY id ASC LIMIT 500;";
	
	private HabilidadDAO() {
		super();
	}
	
	public static synchronized HabilidadDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HabilidadDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<Habilidad> getAll() {
		
		ArrayList<Habilidad> habilidades = new ArrayList<Habilidad>();
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery()) {
				LOG.debug(pst);

			while (rs.next()) {

				habilidades.add(mapper(habilidades, rs));
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return habilidades;
	}

	@Override
	public Habilidad getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Habilidad delete(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Habilidad update(int id, Habilidad pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Habilidad create(Habilidad pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Habilidad mapper(ArrayList<Habilidad> habilidades, ResultSet rs) throws Exception {
		
		Habilidad h = new Habilidad();
		h.setId(rs.getInt("id"));
		h.setNombre(rs.getString("nombre"));
		
				
		return h;
	}

}
