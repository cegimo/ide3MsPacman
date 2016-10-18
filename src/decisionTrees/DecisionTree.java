package decisionTrees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.STRATEGY;
import pacman.game.Game;


public class DecisionTree {


	public static HashMap<String, ArrayList<String>> atributos;
	public static ArrayList<String>	estrategiaElegida;
	Nodo raiz;

	public DecisionTree() {
		atributos =  new HashMap<String, ArrayList<String>>();

		ArrayList<String> valoresDoubles = new ArrayList<String>();
		valoresDoubles.add("VERY_LOW");
		valoresDoubles.add("LOW");
		valoresDoubles.add("MEDIUM");
		valoresDoubles.add("HIGH");
		valoresDoubles.add("VERY_HIGH");
		valoresDoubles.add("NONE");
		
		atributos.put("numOfPillsLeft", valoresDoubles);
		atributos.put("numOfPowerPillsLeft", valoresDoubles);

		
		atributos.put("blinkyDist",valoresDoubles);
		atributos.put("inkyDist", valoresDoubles);
		atributos.put("pinkyDist", valoresDoubles);
		atributos.put("sueDist", valoresDoubles);

		ArrayList<String> valoresBool = new ArrayList<String>();
		valoresBool.add("true");
		valoresBool.add("false");
		
		atributos.put("isBlinkyEdible", valoresBool);
		atributos.put("isInkyEdible", valoresBool);
		atributos.put("isPinkyEdible", valoresBool);
		atributos.put("isSueEdible", valoresBool);

		ArrayList<String> valoresStrategy = new ArrayList<String>();
		valoresStrategy.add("huir");
		valoresStrategy.add("cazar");
		valoresStrategy.add("comer");

		estrategiaElegida = valoresStrategy;
	}
	
	//construimos el arbol con las tuplas y una lista de atributos
		public void construirArbol() 
		{
			DataTuple[] tuples = DataSaverLoader.LoadPacManData();
			ArrayList<DataTuple> data = new ArrayList<DataTuple>(Arrays.asList(tuples));
			//la lista de atributos la cogemos del Keyset del hashmap atributos
			ArrayList<String> listaAtributos = new ArrayList<String>(atributos.keySet());
			raiz = generarArbol(data, listaAtributos);
		}
		
		//generamos el �rbol con el algoritmo de construcción automática
		public Nodo generarArbol(ArrayList<DataTuple> D, ArrayList<String> listaAtributos) 
		{
			// Creamos Nodo
			
			Nodo N = null;
			
			// 2. Si las tuplas en D tienen todas la misma clase C, return N como un nodo hoja etiquetado con la clase C.
			if (tuplasMismaClase(D)) 
			{
				String clase = D.get(0).strategy;
				N = new Nodo(clase, false);
				return N;
			}
			
			// 3. En otro caso, si la lista de atributos está vacía, return N como un nodo final etiquetado con la clase
			// mayoritaria en D.
			else if (listaAtributos.isEmpty())
			{
				String clase = getClaseMayoritaria(D);
				N = new Nodo(clase, true);
				return N;
			}
			
			// 4. En otro caso:
			else 
			{
				// 1. Aplicar el método de selección de atributos sobre los datos y la lista de atributos, para encontrar el
				// mejor atributo actual A: S(D, lista de atributos) -> A.
				id3 metodo = new id3();
				
				String atributo = metodo.selectAttr(D, listaAtributos);

				// 2. Etiquetar a N como A y eliminar A de la lista de atributos.
				N = new Nodo(atributo, false);
				listaAtributos.remove(atributo);
				// 3. Para cada valor aj del atributo A:
				ArrayList<String> valorA = atributos.get(atributo);

				for (String aj : valorA) 
				{
					ArrayList<String> copiaAtributos = (ArrayList<String>) listaAtributos.clone();
					// a) Separar todas las tuplas en D para las cuales el atributo A toma el valor aj, creando el subconjunto de datos Dj
					ArrayList<DataTuple> Dj = tuplasPorValorDeAtributo(D, atributo, aj);
					// b) Si Dj está vacío, añadir a N un nodo hoja etiquetado con la clase mayoritaria en D.
					if (Dj.isEmpty()) 
					{
						String clase = getClaseMayoritaria(D);
						N.setHijo(aj, new Nodo(clase, true));
					}
					// c) En otro caso, añadir a N el nodo hijo resultante de llamar al método generarArbol
					else 
					{
						N.setHijo(aj, generarArbol(Dj, copiaAtributos));
					}
				}
				// 4. Return N.
				return N;
			}
	}
	
	//Devuelve true o false si encuentra una tupla con la misma clase
	public boolean tuplasMismaClase(ArrayList<DataTuple> datos) 
	{
		String estrategiaAux = datos.get(0).strategy;
		for (DataTuple dataT : datos) 
		{
			if (dataT.strategy != estrategiaAux) 
			{
				return false;
			}
		}
		return true;
	}
	
	//es un contandor que permite saber cual es la clase mayoritaria y la devuelve
	public String getClaseMayoritaria(ArrayList<DataTuple> datos) {
		int huir = 0, cazar = 0, comer = 0;
		String claseMayoritaria = null;

		for (DataTuple tuple : datos) {
			String clase = (tuple.strategy).toString();
			switch (clase) {
				case ("huir"):
					huir++;
					break;
				case ("cazar"):
					cazar++;
					break;
				case ("comer"):
					comer++;
					break;
			
			}
		}

		int max = Math.max(Math.max(huir, cazar), comer);
		if (max == huir) claseMayoritaria = "huir";
		else if (max == cazar) claseMayoritaria = "cazar";
		else if (max == comer) claseMayoritaria = "comer";
		else claseMayoritaria = "NONE";
		return claseMayoritaria;
	}
	
	// Devuelve un conjunto de tuplas de todas las que tienen el valor pasado como parametro
	static public ArrayList<DataTuple> tuplasPorValorDeAtributo(ArrayList<DataTuple> datos, String atributo, String valor) {		
		ArrayList<DataTuple> resultado = new ArrayList<DataTuple>();
		for (DataTuple dataTuple : datos) {
			if(dataTuple.discretizar(atributo).equals(valor)){
				resultado.add(dataTuple);
			}
		}
		return resultado;
	}

	public void pintar(){
		pintarRecursivo(raiz, 0);
	}
	
	private void pintarRecursivo(Nodo nodo, int tabs){
		for(int i = 0; i < tabs; i++){
			System.out.print("\t");
		} 
		System.out.println(nodo.atributeName);
		
		if (!nodo.esNodoFinal) {
			for (Nodo hijo : nodo.hijos.values()) {
				pintarRecursivo(hijo, tabs + 1);
			}
		}
	}
	
	public String buscar(Game game) {
		DataTuple dataTuple = new DataTuple(game, null);
		return buscarRecursivo(raiz, dataTuple); 
	}

	private String buscarRecursivo(Nodo nodo, DataTuple dataTuple){
		String res = null;
		String atribute = nodo.atributeName;
		if(nodo.esNodoFinal) res = atribute;
		else {
			String valorActual = dataTuple.discretizar(atribute);
			Nodo hijo = nodo.hijos.get(valorActual);
			return buscarRecursivo(hijo, dataTuple);
		}
		return res;
	}
	
	public String getStrategy(Game game) {
		return buscar(game);
	}
	
 }








