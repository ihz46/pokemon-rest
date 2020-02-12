package com.ipartek.formacion.utils;

public class Utilidad {
	
	/**
	 * obtenemos el id del pathInfo o uri
	 * @param pathInfo partte de la URI donde debemos buscar un numero
	 * @return numero id
	 * @throws Exception si el pathinfo esta mal formado
	 * 
	 * ejemplos:
	 * 	<ol>
	 * 		<li>/ pathinfo valido</li>
	 *     <li> /2 pathinfo valido</li>
	 *     <li> /2/ pathinfo valido</li>
	 *     <li> /2/2 url pathinfo esta mal formado</li>
	 *     <li> /2/otracosa/34/ pathinfo esta mal formado</li>
	 * </ol>
	 * 
	 */
	
public static int obtenerId(String pathInfo ) throws Exception {
		
		int resul = -1;
		if ( pathInfo != null ) {
			
			try {
				String[] cachos = pathInfo.split("/");
				if ( cachos.length == 2 ) {
					resul = Integer.parseInt(cachos[1]);
				}else if( cachos.length > 2 ) {
					throw new Exception("URL mal formada " + pathInfo);
				}	
			}catch (NumberFormatException e) {

				throw new Exception("URL mal formada porque no es numerico " + pathInfo);	
				
			}	
			
		}	
		return resul;
	}

}
