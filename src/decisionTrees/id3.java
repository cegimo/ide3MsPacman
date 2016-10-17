package decisionTrees;

import java.util.*;
import dataRecording.DataTuple;
import decisionTrees.Calculos;;

public class id3 extends Calculos{
	
	
	
	public String selectAttr(ArrayList<DataTuple> datos, ArrayList<String> atributos) {
		String resultado = null;
		float infoD = entropia(datos, "DirectionChosen");
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
		
		resultado = atributos.get(posMax);
		return resultado;
	}

	private float infoA(ArrayList<DataTuple> datos, ArrayList<String> atributos) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
