package decisionTrees;

import java.util.ArrayList;
import java.util.HashMap;

import dataRecording.DataTuple;

public class Calculos {
	
	public float entropia(ArrayList<DataTuple> data, String attr) {
		float info = 0;
		ArrayList<String> values = null;
		if(attr == "DirectionChosen") {
			values = DecisionTree.DirectionChosen;
		} else {
			values = DecisionTree.attrs.get(attr);
		}
		
		for (String value : values) {
			float pi = calculaPi(data, attr, value);
			info += pi*log2(pi);
		}
		return (-info);
	}

	
	private float infoA(ArrayList<DataTuple> dato, ArrayList<String> atributos) 
	{
		float info = 0;
		ArrayList<String> values = DecisionTree.atributos.get(atributos);
		for(String value : values){
			HashMap<String, String> datatuple = DataTuple.getHash();
			info += (datatuple.size() / dato.size()) * entropia(datatuple, "DirectionChosen");
		}
		return info;
	}
	
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

	float log2(float x) {
		if(x == 0) return 0;
		float resultado = (float)(Math.log(x) / Math.log(2));
		return resultado;
	}
	
	
	
	
	
	
}
