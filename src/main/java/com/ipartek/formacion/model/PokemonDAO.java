package com.ipartek.formacion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.model.pojo.Habilidad;
import com.ipartek.formacion.model.pojo.Pokemon;

public class PokemonDAO implements IDAO<Pokemon> {

	private static PokemonDAO INSTANCE;
	private static final Logger LOG = Logger.getLogger(PokemonDAO.class);
	
	//Consultas
	
	//GETALL
	private static final String SQL_GET_ALL = "SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', p.imagen, h.nombre 'nombre_habilidad', h.id 'id_habilidad' FROM pokemon p LEFT JOIN pokemon_has_habilidades ph ON p.id = ph.id_pokemon LEFT JOIN habilidad h ON ph.id_habilidad = h.id  ORDER BY p.id DESC LIMIT 500;";
	
	//GETBYID
	private static final String SQL_GET_BY_ID = " SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', p.imagen, h.nombre 'nombre_habilidad', h.id 'id_habilidad' FROM pokemon p LEFT JOIN pokemon_has_habilidades ph ON p.id = ph.id_pokemon LEFT JOIN habilidad h  ON ph.id_habilidad = h.id WHERE p.id = ?  ORDER BY p.id DESC LIMIT 500;";
	
	//GETBYNOMBRE
	private static final String SQL_GET_BY_NOMBRE = "SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', h.nombre 'nombre_habilidad', h.id 'id_habilidad' FROM pokemon p, pokemon_has_habilidades ph, habilidad h  WHERE p.id = ph.id_pokemon AND ph.id_habilidad = h.id AND p.nombre LIKE ? ORDER BY p.id DESC LIMIT 500;";
	
	//CREATE
	private static final String SQL_CREATE = "INSERT INTO pokemon ('nombre') VALUES (?);";
	
	//UPDATE
	private static final String SQL_UPDATE = "UPDATE `pokemon` SET `nombre`=? WHERE  `id`=?";
	
	//DELETE
	private static final String SQL_DELETE = "DELETE FROM pokemon WHERE id=?";
	
	

	private PokemonDAO() {
		super();
	}

	public static synchronized PokemonDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PokemonDAO();
		}
		return INSTANCE;
	}

	@Override
	public List<Pokemon> getAll() {

		HashMap<Integer, Pokemon> hm = new HashMap<Integer, Pokemon>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {

				mapper(hm, rs);
			}

		} catch (Exception e) {
			// TODO: LOG
			e.printStackTrace();
		}

		return new ArrayList<Pokemon>(hm.values());
	}

	@Override
	public Pokemon getById(int id) {
	
		HashMap<Integer, Pokemon> hm = new HashMap<Integer, Pokemon>();
		Pokemon p = null;

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID);) {

			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {
					p = mapper(hm, rs);
				}
			}

		} catch (Exception e) {
			// TODO: LOG
			e.printStackTrace();
		}

		return p;
	}
	
	
	
	public List<Pokemon> getByNombre( String nombre) {
				
		HashMap<Integer, Pokemon> hm = new HashMap<Integer, Pokemon>();
		

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_GET_BY_NOMBRE);) {

			pst.setString(1, "%" + nombre + "%");

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					mapper(hm, rs);					
				}
			}

		} catch (Exception e) {
			LOG.error("Error en el getByNombre: " + e.getMessage());
			e.printStackTrace();
		}

		return new ArrayList<Pokemon>(hm.values());
	}
	
	@Override
	public Pokemon delete(int id) throws Exception {
		Pokemon p =  getById(id);
				
		try(Connection con = ConnectionManager.getConnection();PreparedStatement pst = con.prepareStatement(SQL_DELETE);){
			pst.setInt(1, id);
			LOG.debug(pst);
			int affectedRows = pst.executeUpdate();
			if (affectedRows == 0) {
				throw new Exception("No existe ningún pokemon con el id" + id);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return p;
	}

	@Override
	public Pokemon update(int id, Pokemon pojo) throws Exception {
		try(Connection con = ConnectionManager.getConnection();){
			PreparedStatement pst = con.prepareStatement(SQL_UPDATE);
			//1.nombre
			pst.setString(1, pojo.getNombre());
			//2.id
			pst.setInt(2, id);
			
			LOG.debug(pst);
			int filasAfectadas = pst.executeUpdate();
			if ( filasAfectadas >= 1 ) {
				pojo.setId(id);
			}else {
				throw new Exception("No se ha encontrado ningún pokemon con el id " +id);
			}
		}
		return pojo;
	}

	@Override
	public Pokemon create(Pokemon pojo) throws Exception {
		try(Connection con = ConnectionManager.getConnection();){
			PreparedStatement pst = con.prepareStatement(SQL_CREATE);
			pst.setString(1, pojo.getNombre());
			LOG.debug(pst);
			
			int filasAfectadas = pst.executeUpdate();
			if (filasAfectadas == 1) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					pojo.setId(rs.getInt(1));
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return pojo;
	}
	

	private Pokemon mapper(HashMap<Integer, Pokemon> hm, ResultSet rs) throws Exception {
		
		int idPokemon = rs.getInt("id_pokemon");
		Pokemon p = hm.get(idPokemon);
		if (p == null) {
			p = new Pokemon();
			p.setId(idPokemon);
			p.setNombre(rs.getString("nombre_pokemon"));
			p.setImagen(rs.getString("imagen"));
		}

		Habilidad h = new Habilidad();
		h.setId(rs.getInt("id_habilidad"));
		h.setNombre(rs.getString("nombre_habilidad"));

		p.getHabilidades().add(h);

		hm.put(idPokemon, p);	
		
		return p;
	}
	

	

}
