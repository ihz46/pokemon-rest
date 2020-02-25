package com.ipartek.formacion.model.pojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

public class PokemonTest {

	
	
	@Test
	public  void testConstructor() {
		Pokemon p = new Pokemon();
		
		//Comprobamos que el pokemon no sea null
		assertNotNull(p);
		
		//Comprobamos que el nombre por defecto sea vacío
		assertEquals("", p.getNombre());
		
		//Comprobamos que el id por defecto sea 0
		assertEquals(0, p.getId());
		
		//Comprobamos que la imagen por defecto sea vacía
		assertEquals("", p.getImagen());
		
		//Comprobamos que el pokemon tenga un array vacío de habilidades		
		assertEquals( new ArrayList<Habilidad>(), p.getHabilidades());
		
	}

}
