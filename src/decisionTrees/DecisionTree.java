package decisionTrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;

public class DecisionTree {


	ArrayList <String> atributos;
	//ArrayList<String>	DirectionChosen;
	Nodo raiz;

	public DecisionTree() {
		atributos = new ArrayList <String> ();

		atributos.add("numOfPillsLeft");
		atributos.add("numOfPowerPillsLeft");

		atributos.add("blinkyDist");
		atributos.add("inkyDist");
		atributos.add("pinkyDist");
		atributos.add("sueDist");

		atributos.add("isBlinkyEdible");
		atributos.add("isInkyEdible");
		atributos.add("isPinkyEdible");
		atributos.add("isSueEdible");
		atributos.add("strategy");

		//DirectionChosen = valuesMove;
	}
	
	public void construirArbol() 
	{
		DataTuple[] tuples = DataSaverLoader.LoadPacManData();
		ArrayList<DataTuple> data = new ArrayList<DataTuple>(Arrays.asList(tuples));
		raiz = generarArbol(data, atributos);
	}
	//generamos el árbol con el algoritmo de construcción automática
	public Nodo generarArbol(ArrayList<DataTuple> D, ArrayList<String> listaAtributos) 
	{
		// Creamos Nodo
		Nodo N = null;
		
		// 2. Si las tuplas en D tienen todas la misma clase C, return N como un nodo hoja etiquetado con la clase C.
		if (tuplasMismaClase(D)) {
			String Nodo = D.get(10).strategy;
			N = new Nodo(nombreClase);
			return N;
		}
		
		// 3. En otro caso, si la lista de atributos está vacía, return N como un nodo hoja etiquetado con la clase
		// mayoritaria en D.
		else if (listaAtributos.isEmpty()) {
			String nombreClase = getClaseMayoritaria(D);
			N = new Nodo(nombreClase);
			return N;
		}
		
		// 4. En otro caso:
		else {
			// 1. Aplicar el método de selección de atributos sobre los datos y
			// la lista de atributos, para encontrar el
			// mejor atributo actual A:
			// S(D, lista de atributos) -> A.
			
			id3 metodo = new id3();
			

			
			String attr = metodo.selectAttr(D, listaAtributos);

			// 2. Etiquetar a N como A y eliminar A de la lista de atributos.
			N = new Nodo(attr);
			listaAtributos.remove(attr);
			// 3. Para cada valor aj del atributo A:
			ArrayList<String> valoresA = attrs.get(attr);

			for (String aj : valoresA) {
				@SuppressWarnings("unchecked")
				ArrayList<String> copiaAttrList = (ArrayList<String>) attrList.clone();
				// a) Separar todas las tuplas en D para las cuales el atributo
				// A toma el valor aj
				// , creando el subconjunto de datos Dj
				ArrayList<DataTuple> Dj = separarSubconjunto(D, attr, aj);
				// b) Si Dj está vacío, añadir a N un nodo hoja etiquetado con
				// la clase mayoritaria en D.
				if (Dj.isEmpty()) {
					String nombreClase = getClaseMayoritaria(D);
					N.addChild(aj, new SimpleNode(nombreClase));
				}
				// c) En otro caso, añadir a N el nodo hijo resultante de llamar
				// a Generar_Arbol (Dj
				// , lista de atributos)
				else {
					N.addChild(aj, generarArbol(Dj, copiaAttrList));
				}
			}
			// 4. Return N.
			return N;
		}
	}
	public boolean tuplasMismaClase(ArrayList<DataTuple> data) {
		MOVE aux = data.get(0).DirectionChosen;
		for (DataTuple dataTuple : data) {
			if (dataTuple.DirectionChosen != aux) {
				return false;
			}
		}
		return true;
	}
	public String getClaseMayoritaria(ArrayList<DataTuple> data) {
		int left = 0, right = 0, up = 0, down = 0, none = 0;
		String claseMayoritaria = null;

		for (DataTuple tuple : data) {
			String clase = (tuple.DirectionChosen).toString();
			switch (clase) {
				case ("LEFT"):
					left++;
					break;
				case ("RIGHT"):
					right++;
					break;
				case ("UP"):
					up++;
					break;
				case ("DOWN"):
					down++;
					break;
				case ("NONE"):
					none++;
					break;
			}
		}

		int max = Math.max(Math.max(left, right), Math.max(Math.max(up, down), none));
		if (max == left) claseMayoritaria = "LEFT";
		else if (max == right) claseMayoritaria = "RIGHT";
		else if (max == up) claseMayoritaria = "UP";
		else if (max == down) claseMayoritaria = "DOWN";
		else claseMayoritaria = "NONE";
		return claseMayoritaria;
	}

	static public ArrayList<DataTuple> separarSubconjunto(ArrayList<DataTuple> data, String attr, String aj) {		
		ArrayList<DataTuple> res = new ArrayList<DataTuple>();
		for (DataTuple dataTuple : data) {
			if(dataTuple.getAttribute(attr).equals(aj)){
				res.add(dataTuple);
			}
		}
		return res;
	}
	
	public void pintar(){
		TreeConverter tc = new TreeConverter();
		tc.tree2graph(root).display();
	}

	public MOVE buscarRecursivo(SimpleNode simpleNode, DataTuple game){
		MOVE move = null;
		if(simpleNode.isLeaf()) move = MOVE.valueOf(simpleNode.getName());
		else {			
			String valueNode = game.getAttribute(simpleNode.getName());
			TreeMap tree = simpleNode.getChildren();
			SimpleNode nextNode = (SimpleNode) tree.get(valueNode);
			move = buscarRecursivo(nextNode, game);
		}
		return move;	
	}
	
	public MOVE buscar(Game game){
		DataTuple actualGame = new DataTuple(game, null);
		return buscarRecursivo(root, actualGame);
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		return buscar(game);
	}

}