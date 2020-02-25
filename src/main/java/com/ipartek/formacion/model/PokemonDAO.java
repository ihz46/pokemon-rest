package com.ipartek.formacion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.model.pojo.Habilidad;
import com.ipartek.formacion.model.pojo.Pokemon;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class PokemonDAO implements IDAO<Pokemon> {

	private static PokemonDAO INSTANCE;
	private static final Logger LOG = Logger.getLogger(PokemonDAO.class);
	
	//Consultas
	
	//GETALL
	private static final String SQL_GET_ALL = "SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', p.imagen, h.nombre 'nombre_habilidad', h.id 'id_habilidad' FROM pokemon p LEFT JOIN pokemon_has_habilidades ph ON p.id = ph.id_pokemon LEFT JOIN habilidad h ON ph.id_habilidad = h.id  ORDER BY p.id DESC LIMIT 500;";
	
	//GETBYID
	private static final String SQL_GET_BY_ID = " SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', p.imagen, h.nombre 'nombre_habilidad', h.id 'id_habilidad' FROM pokemon p LEFT JOIN pokemon_has_habilidades ph ON p.id = ph.id_pokemon LEFT JOIN habilidad h  ON ph.id_habilidad = h.id WHERE p.id = ?  ORDER BY p.id DESC LIMIT 500;";
	
	//GETBYNOMBRE
	private static final String SQL_GET_BY_NOMBRE = "SELECT p.id 'id_pokemon', p.nombre 'nombre_pokemon', p.imagen, h.nombre 'nombre_habilidad', h.id 'id_habilidad' FROM pokemon p, pokemon_has_habilidades ph, habilidad h  WHERE p.id = ph.id_pokemon AND ph.id_habilidad = h.id AND p.nombre LIKE ? ORDER BY p.id DESC LIMIT 500;";
	
	//CREATE
	private static final String SQL_CREATE = "INSERT INTO `pokemon` (`nombre`,`imagen`) VALUES (?,?)";
	
	//UPDATE
	private static final String SQL_UPDATE = "UPDATE `pokemon` SET `nombre`=?,`imagen`=? WHERE  `id`=?";
	
	//DELETE
	private static final String SQL_DELETE = "DELETE FROM pokemon WHERE id=?";
	
	//INSERTAR HABILIDAD EN TABLA INTERMEDIA
	private static final String SQL_INSERT_POKEMON_HAS_HABILIDADES = "INSERT INTO `pokemon_has_habilidades` (`id_pokemon`, `id_habilidad`) VALUES (?, ?);";
	
	//ELIMINAR HABILIDAD DEN TABLA INTERMEDIA
	private static final String SQL_DELETE_POKEMON_HAS_HABILIDADES = "DELETE FROM `pokemon_has_habilidades` WHERE  `id_pokemon`=? ";

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
		Connection con = null;
		
		PreparedStatement pst = null;
		
		PreparedStatement  pstEliminarHabilidades = null;
		
		PreparedStatement pstAddHabilidades = null;
		
		//Obtenemos las habilidades del pokemon 
		ArrayList<Habilidad> habilidades = (ArrayList<Habilidad>) pojo.getHabilidades();
		
		try{
			con = ConnectionManager.getConnection();
			
			//No realizamos la transaccion hasta que se haga el commit
			con.setAutoCommit(false);
			
			//Actualizamos los datos del pokemon 
		    pst = con.prepareStatement(SQL_UPDATE);
			
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getImagen());
			pst.setInt(3, pojo.getId());
			
			LOG.debug(pst);
			
			int filasAfectadas = pst.executeUpdate();
			
			//Cerramos el statement
			pst.close();
			
			//En caso de que se realice correctamente la update, continuamos con las demás transacciones.
			if (filasAfectadas == 1) {
				 pstEliminarHabilidades = con.prepareStatement(SQL_DELETE_POKEMON_HAS_HABILIDADES);
				
				//Recorremos las habilidades para eliminarlas de la tabla pokemon_has_habilidades
				pstEliminarHabilidades.setInt(1, pojo.getId());
				
				int affectedRows = pstEliminarHabilidades.executeUpdate();
								
				if(affectedRows>0) {
					//Ahora tenemos que insertar en la tabla por cada habilidad
					 pstAddHabilidades = con.prepareStatement(SQL_INSERT_POKEMON_HAS_HABILIDADES);
					for (Habilidad habilidad : habilidades) {
						pstAddHabilidades.setInt(1, pojo.getId());
						pstAddHabilidades.setInt(2, habilidad.getId());
						LOG.debug(pstAddHabilidades);
						pstAddHabilidades.executeUpdate();
					}
				
				}
				
				con.commit();
			}
		}catch (MySQLIntegrityConstraintViolationException e) {
			con.rollback();
			if(e.getMessage().contains("Duplicate")) {
				throw new MySQLIntegrityConstraintViolationException("El nombre ya existe");
			}else {
				throw new MySQLIntegrityConstraintViolationException("Has introducido alguna habilidad que no existe.");
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			
			//Cerramos los prepared statement
			
			pstAddHabilidades.close();
			
			pst.close();
			
			pstAddHabilidades.close();
			
			//Cerramos la conexión si no es nula
			
			if(con!=null) {
				con.close();
			}
		}
		return pojo;
	}
			
	

	@Override
	public Pokemon create(Pokemon pojo) throws Exception {
		Connection con = null;
							
		//Obtenemos las habilidades del pokemon
		ArrayList <Habilidad> habilidades = (ArrayList<Habilidad>) pojo.getHabilidades();
		
		try{
			con = ConnectionManager.getConnection();
			//No comita en la base de datos las consultas hasta que se haga un commit (No guarda automaticamente los cambios)
			con.setAutoCommit(false);
			
			PreparedStatement pst = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getImagen());
			LOG.debug(pst);
			
			int filasAfectadas = pst.executeUpdate();
			if (filasAfectadas == 1) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					pojo.setId(rs.getInt(1));
					
					PreparedStatement pstHab = null;
					//Cogemos las habilidades
					for (Habilidad h : habilidades) {
						pstHab = con.prepareStatement(SQL_INSERT_POKEMON_HAS_HABILIDADES);
						pstHab.setInt(1, pojo.getId());
						pstHab.setInt(2, h.getId());
						
						LOG.debug(pstHab);
						
						pstHab.executeUpdate();
						
						
					}
					pstHab.close();
					con.commit();
										
				}
				
			}
			
		}catch (MySQLIntegrityConstraintViolationException e) {
			LOG.error(e.getMessage());
			//Restablece los cambios
			con.rollback();
			if(e.getMessage().contains("Duplicate")) {
				throw new MySQLIntegrityConstraintViolationException("El nombre ya existe");
			}else {
				throw new MySQLIntegrityConstraintViolationException("Has introducido alguna habilidad que no existe.");
			}
			
						
		}catch(Exception e) {
			LOG.error(e.getMessage());
			//Restablece los cambios
			con.rollback();
			throw new Exception(e);
			
		}finally {
			//Cerramos la conexión si no es nula
			
			if(con!=null) {
				con.close();
			}
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
