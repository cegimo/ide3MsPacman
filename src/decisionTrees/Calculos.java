package decisionTrees;

import java.util.ArrayList;
import java.util.HashMap;
import dataRecording.DataTuple;

public class Calculos {
	
	public float entropia(ArrayList<DataTuple> datos, String atributo) {
		float resultadoEntropia = 0;
		ArrayList<String> valores = null;
		if(atributo == "strategy") {
			valores = DecisionTree.estrategiaElegida;
		} else {
			System.out.println(atributo);
			valores = DecisionTree.atributos.get(atributo);
		}
		
		
		for (String valor : valores) {
			float pi = calculaPi(datos, atributo, valor);
			if (pi == 0) pi = 1;
			resultadoEntropia += pi*log2(pi);
		}
		return (-resultadoEntropia);
	}

	
    private float infoA(ArrayList<DataTuple> datos, String atributo) 
    {
    	float info = 0;
    	ArrayList<String> valores = DecisionTree.atributos.get(atributo);
    	for(String valor : valores)
    	{
    		ArrayList<DataTuple> dataT = DecisionTree.tuplasPorValorDeAtributo(datos, atributo, valor);
    		info += (dataT.size() / datos.size()) * entropia(dataT, "estrategiaElegida");
    	}
    	return info;
    }
	
    //calculo de p
	public float calculaPi(ArrayList<DataTuple> dato, String atributos, String valor) {
		float pi = 0;
		if(dato.size() > 0) {
			for (DataTuple dataTuple : dato) {
				if (dataTuple.discretizar(atributos).equals(valor)) {
					pi++;
				}
			}
			pi /= dato.size();
		}
		return pi;
	}

	//Calculo log base 2
	float log2(float x) {
		if(x == 0) return 0;
		float resultado = (float)(Math.log(x) / Math.log(2));
		return resultado;
	}
	
	
	
	
	
	
}
