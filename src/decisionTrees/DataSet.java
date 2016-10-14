package decisionTrees;
import java.util.ArrayList;
import java.util.HashMap;

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;


public class DataSet {

	
	// array con los datos leidos de trainingData
	public DataTuple[] dataTuples;
	
	//UNa vez discretizados se categorizan los valores en un hashmap 
	//String --> isPinkyEdible (tipo de parametro)
	//String2 --> true or false (tipo de valor)  
	//Integer --> numero de veces que ocurre ese valor
	private HashMap<String, HashMap<String, Integer>> dataSetClasificado;
	
	public DataSet()
	{
		dataTuples = DataSaverLoader.LoadPacManData();
		//System.out.println(dataTuples[0].blinkyDist);
	}
	
	public DataTuple[] getDataTuple(){
		return dataTuples;
	}
	
	
	// near < 15
	/*public int getCounterGhostNear(){
		int i = 0;
		int total = 0;
		for(i = 0; i < dataTuples.length; i++){
			if(dataTuples[i].blinkyDist <= 15){
				total = total + 1;
			}
		return total;
			
		}*/
	} 
	



