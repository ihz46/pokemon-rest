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

		String sql = " SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', h.nombre 'nombre_habilidad', h.id 'id_habilidad' "
				+ " FROM pokemon p, pokemon_has_habilidades ph, habilidad h "
				+ " WHERE p.id = ph.id_pokemon AND ph.id_habilidad = h.id " + " ORDER BY p.id DESC LIMIT 500;";

		LOG.debug(sql);

		HashMap<Integer, Pokemon> hm = new HashMap<Integer, Pokemon>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
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
		String sql = " SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', h.nombre 'nombre_habilidad', h.id 'id_habilidad' "
				+ " FROM pokemon p, pokemon_has_habilidades ph, habilidad h "
				+ " WHERE p.id = ph.id_pokemon AND ph.id_habilidad = h.id AND p.id = ? "
				+ " ORDER BY p.id DESC LIMIT 500;";

		LOG.debug(sql);

		HashMap<Integer, Pokemon> hm = new HashMap<Integer, Pokemon>();
		Pokemon p = null;

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

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
		
		String sql = " SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', h.nombre 'nombre_habilidad', h.id 'id_habilidad' "
				+ " FROM pokemon p, pokemon_has_habilidades ph, habilidad h "
				+ " WHERE p.id = ph.id_pokemon AND ph.id_habilidad = h.id AND p.nombre LIKE ? "
				+ " ORDER BY p.id DESC LIMIT 500;";	

		LOG.debug(sql);

		HashMap<Integer, Pokemon> hm = new HashMap<Integer, Pokemon>();
		

		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pst = con.prepareStatement(sql);) {

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
		Pokemon p = getById(id);
		
		String sql = "DELETE FROM pokemon WHERE id=?";
		try(Connection con = ConnectionManager.getConnection();PreparedStatement pst = con.prepareStatement(sql);){
			pst.setInt(1, id);
			
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pokemon create(Pokemon pojo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	private Pokemon mapper(HashMap<Integer, Pokemon> hm, ResultSet rs) throws Exception {
		
		int idPokemon = rs.getInt("id_pokemon");
		Pokemon p = hm.get(idPokemon);
		if (p == null) {
			p = new Pokemon();
			p.setId(idPokemon);
			p.setNombre(rs.getString("nombre_pokemon"));
		}

		Habilidad h = new Habilidad();
		h.setId(rs.getInt("id_habilidad"));
		h.setNombre(rs.getString("nombre_habilidad"));

		p.getHabilidades().add(h);

		hm.put(idPokemon, p);	
		
		return p;
	}
	

	

}
