package com.ipartek.formacion.model.pojo;

import static org.junit.Assert.*;



import org.junit.Test;

public class PokemonTest {

	@Test
	public void testPokemon() {
		Pokemon p = new Pokemon();
		assertNotNull(p);
		
		assertEquals(0, p.getId());
		
		assertEquals("", p.getNombre());
		
		assertEquals("", p.getImagen());
		
		assertNotNull(p.getHabilidades());
		
		assertTrue(p.getHabilidades().isEmpty());
	}
	
	/*

	@Test
	public void testGetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNombre() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetNombre() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHabilidades() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetHabilidades() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetImagen() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetImagen() {
		fail("Not yet implemented");
	}
	*/
}
