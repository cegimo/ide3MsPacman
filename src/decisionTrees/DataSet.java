package decisionTrees;
import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;


public class DataSet {

	//gestion de tuplas
	//devuelve los atributos
	// array con los datos leidos de trainingData
	DataTuple[] dataTuples;
	
	public DataSet()
	{
		dataTuples = DataSaverLoader.LoadPacManData();
		//System.out.println(dataTuples[0].blinkyDist);
	}
	
	public DataTuple[] getDataTuple(){
		return dataTuples;
	}
	
	
	// near < 15
	public int getCounterGhostNear(){
		int i = 0;
		int total = 0;
		for(i = 0; i < dataTuples.length; i++){
			if(dataTuples[i].blinkyDist <= 15){
				total = total + 1;
			}
		return total;
			
		}
	} 
	
}


