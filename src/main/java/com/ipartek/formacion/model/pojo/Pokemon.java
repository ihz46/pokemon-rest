package com.ipartek.formacion.model.pojo;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

	private int id;
	private String nombre;	
	private List<Habilidad> habilidades;
	private String imagen = "";
	
	public Pokemon() {
		super();
		this.id = 0;
		this.nombre = "";
		this.habilidades = new ArrayList<Habilidad>();
		this.imagen= "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		

	public List<Habilidad> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<Habilidad> habilidades) {
		this.habilidades = habilidades;
	}

	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", nombre=" + nombre + ", habilidades=" + habilidades + "]";
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
}
