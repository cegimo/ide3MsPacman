package decisionTrees;

import java.util.*;
import dataRecording.DataTuple;

public class id3 {
	
	
	
	public String selectAttr(ArrayList<DataTuple> datos, ArrayList<String> atributos) {
		String res = null;
		//float infoD = entropy(datos, "DirectionChosen");
//		System.out.println(infoD);
		
		ArrayList<Float> infoA = new ArrayList<Float>();
		ArrayList<Float> gainA = new ArrayList<Float>();

		//calcular infoA
		for (String attr : atributos) 
		{
			float auxInfoA = infoA(datos, atributos);
			infoA.add(auxInfoA);
		}
		
		//calcular gainA
		for (int i = 0; i < infoA.size(); i++) {
			gainA.add(infoD - infoA.get(i));
		}
		
		//conseguir mas alto
		float max = 0;
		int posMax = 0;
		
		for (int i = 0; i < gainA.size(); i++) {
			if(gainA.get(i) > max) {
				max = gainA.get(i);
				posMax = i;
			}
		}
		
		res = attrs.get(posMax);
		return res;
	}

	private float infoA(ArrayList<DataTuple> datos, ArrayList<String> atributos) 
	{
		float info = 0;
		ArrayList<String> values = DecisionTree.attrs.get(attr);
		for(String value : values){
			ArrayList<DataTuple> dt = DecisionTree.separarSubconjunto(data, attr, value);
			info += (dt.size() / data.size()) * entropy(dt, "DirectionChosen");
		}
		return info;
	}
	public float calculatePi(ArrayList<DataTuple> data, String attr, String value) {
		float pi = 0;
		if(data.size() > 0) {
			for (DataTuple dataTuple : data) {
				if (dataTuple.getAttribute(attr).equals(value)) {
					pi++;
				}
			}
			pi /= data.size();
		}
		return pi;
	}

	float log2(float x) {
		if(x == 0) return 0;
		float res = (float)(Math.log(x) / Math.log(2));
		return res;
	}
	
}
