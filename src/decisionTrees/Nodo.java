package decisionTrees;

import java.util.ArrayList;
import java.util.HashMap;

import pacman.controllers.Controller;
import pacman.game.Game;

import static pacman.game.Constants.*;

public class Nodo {
	
	
	String atributeName;
	//Si es nodo final, nuestro nodo tendrá una clase si no sera none
	boolean esNodoFinal;

	//lista de hijos
	HashMap<String, Nodo> hijos;
	
	//COnstructor
	public Nodo(String atributeName, boolean esNodoFinal){
		this.atributeName = atributeName;
		this.esNodoFinal = esNodoFinal;
		
		if(!esNodoFinal){
			this.hijos	= new HashMap<String, Nodo>();
		}
	}
	
	public void setHijo(String atributeValue, Nodo hijo){
		
			this.hijos.put(atributeValue, hijo);
		
	}
	
	public String classify(Game game) {
		if(esNodoFinal) {
			return atributeName;
		}
		//en Game tengo que programar la funcion getValueOF
		// Tiene que sacar el valor del siguiente hijo para añadirlo  a la lista de hijos
		return this.hijos.get(game.getValueOf(atributeName)).classify(game);
	}
	
}
