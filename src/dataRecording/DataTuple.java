package dataRecording;

import java.util.HashMap;

import decisionTrees.DataSet;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class DataTuple {

	public enum DiscreteTag {
		VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH, NONE;

		public static DiscreteTag DiscretizeDouble(double aux) {
			if (aux < 0.1)
				return DiscreteTag.VERY_LOW;
			else if (aux <= 0.3)
				return DiscreteTag.LOW;
			else if (aux <= 0.5)
				return DiscreteTag.MEDIUM;
			else if (aux <= 0.7)
				return DiscreteTag.HIGH;
			else
				return DiscreteTag.VERY_HIGH;
		}
	}

	public MOVE DirectionChosen;

	// General game state this - not normalized!
	public int mazeIndex;
	public int currentLevel;
	public int pacmanPosition;
	public int pacmanLivesLeft;
	public int currentScore;
	public int totalGameTime;
	public int currentLevelTime;
	public int numOfPillsLeft;
	public int numOfPowerPillsLeft;
	public String strategy; //CREA LA ESTRATEGIA
	public String distanciaFantasmaMasCercano;

	// Ghost this, dir, dist, edible - BLINKY, INKY, PINKY, SUE
	public boolean isBlinkyEdible = false;
	public boolean isInkyEdible = false;
	public boolean isPinkyEdible = false;
	public boolean isSueEdible = false;

	public int blinkyDist = -1;
	public int inkyDist = -1;
	public int pinkyDist = -1;
	public int sueDist = -1;

	public MOVE blinkyDir;
	public MOVE inkyDir;
	public MOVE pinkyDir;
	public MOVE sueDir;

	// Util data - useful for normalization
	public int numberOfNodesInLevel;
	public int numberOfTotalPillsInLevel;
	public int numberOfTotalPowerPillsInLevel;
	private int maximumDistance = 150;

	public DataTuple(Game game, MOVE move) {
		if (move == MOVE.NEUTRAL) {
			move = game.getPacmanLastMoveMade();
		}

		this.DirectionChosen = move;

		this.mazeIndex = game.getMazeIndex();
		this.currentLevel = game.getCurrentLevel();
		this.pacmanPosition = game.getPacmanCurrentNodeIndex();
		this.pacmanLivesLeft = game.getPacmanNumberOfLivesRemaining();
		this.currentScore = game.getScore();
		this.totalGameTime = game.getTotalTime();
		this.currentLevelTime = game.getCurrentLevelTime();
		this.numOfPillsLeft = game.getNumberOfActivePills();
		this.numOfPowerPillsLeft = game.getNumberOfActivePowerPills();
		this.strategy = game.getStrategy();
		

		if (game.getGhostLairTime(GHOST.BLINKY) == 0) {
			this.isBlinkyEdible = game.isGhostEdible(GHOST.BLINKY);
			this.blinkyDist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY));
		}

		if (game.getGhostLairTime(GHOST.INKY) == 0) {
			this.isInkyEdible = game.isGhostEdible(GHOST.INKY);
			this.inkyDist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY));
		}

		if (game.getGhostLairTime(GHOST.PINKY) == 0) {
			this.isPinkyEdible = game.isGhostEdible(GHOST.PINKY);
			this.pinkyDist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY));
		}

		if (game.getGhostLairTime(GHOST.SUE) == 0) {
			this.isSueEdible = game.isGhostEdible(GHOST.SUE);
			this.sueDist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE));
		}

		this.blinkyDir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.BLINKY), DM.PATH);
		this.inkyDir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.INKY), DM.PATH);
		this.pinkyDir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.PINKY), DM.PATH);
		this.sueDir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(GHOST.SUE), DM.PATH);

		this.numberOfNodesInLevel = game.getNumberOfNodes();
		this.numberOfTotalPillsInLevel = game.getNumberOfPills();
		this.numberOfTotalPowerPillsInLevel = game.getNumberOfPowerPills();
		
		this.strategy = game.getStrategy(); //Obtiene la estrategia
	}

	public DataTuple(String data) {
		String[] dataSplit = data.split(";");

		//this.DirectionChosen = MOVE.valueOf(dataSplit[0]);

		//this.mazeIndex = Integer.parseInt(dataSplit[1]);
		//this.currentLevel = Integer.parseInt(dataSplit[2]);
		//this.pacmanPosition = Integer.parseInt(dataSplit[3]);
		//this.pacmanLivesLeft = Integer.parseInt(dataSplit[4]);
		//this.currentScore = Integer.parseInt(dataSplit[5]);
		//this.totalGameTime = Integer.parseInt(dataSplit[6]);
		//this.currentLevelTime = Integer.parseInt(dataSplit[7]);
		this.numOfPillsLeft = Integer.parseInt(dataSplit[0]);
		this.numOfPowerPillsLeft = Integer.parseInt(dataSplit[1]);
		this.isBlinkyEdible = Boolean.parseBoolean(dataSplit[2]);
		this.isInkyEdible = Boolean.parseBoolean(dataSplit[3]);
		this.isPinkyEdible = Boolean.parseBoolean(dataSplit[4]);
		this.isSueEdible = Boolean.parseBoolean(dataSplit[5]);
		this.blinkyDist = Integer.parseInt(dataSplit[6]);
		this.inkyDist = Integer.parseInt(dataSplit[7]);
		this.pinkyDist = Integer.parseInt(dataSplit[8]);
		this.sueDist = Integer.parseInt(dataSplit[9]);
		//this.blinkyDir = MOVE.valueOf(dataSplit[10]);
		//this.inkyDir = MOVE.valueOf(dataSplit[11]);
		//this.pinkyDir = MOVE.valueOf(dataSplit[12]);
		//this.sueDir = MOVE.valueOf(dataSplit[13]);
		//this.numberOfNodesInLevel = Integer.parseInt(dataSplit[22]);
		//this.numberOfTotalPillsInLevel = Integer.parseInt(dataSplit[23]);
		//this.numberOfTotalPowerPillsInLevel = Integer.parseInt(dataSplit[24]);
		this.strategy = dataSplit[10];
	}

	public String getSaveString() {
		StringBuilder stringbuilder = new StringBuilder();

		//stringbuilder.append(this.DirectionChosen + ";"); estrategia
	
		//stringbuilder.append(this.mazeIndex + ";");
		//stringbuilder.append(this.currentLevel + ";");
		//stringbuilder.append(this.pacmanPosition + ";");
		//stringbuilder.append(this.pacmanLivesLeft + ";");
		//stringbuilder.append(this.currentScore + ";");
		//stringbuilder.append(this.totalGameTime + ";");
		//stringbuilder.append(this.currentLevelTime + ";");
		stringbuilder.append(this.numOfPillsLeft + ";");
		stringbuilder.append(this.numOfPowerPillsLeft + ";");
		stringbuilder.append(this.isBlinkyEdible + ";");
		stringbuilder.append(this.isInkyEdible + ";");
		stringbuilder.append(this.isPinkyEdible + ";");
		stringbuilder.append(this.isSueEdible + ";");
		stringbuilder.append(this.blinkyDist + ";");
		stringbuilder.append(this.inkyDist + ";");
		stringbuilder.append(this.pinkyDist + ";");
		stringbuilder.append(this.sueDist + ";");
		//stringbuilder.append(this.blinkyDir + ";");
		//stringbuilder.append(this.inkyDir + ";");
		//stringbuilder.append(this.pinkyDir + ";");
		//stringbuilder.append(this.sueDir + ";");
		//stringbuilder.append(this.numberOfNodesInLevel + ";");
		//stringbuilder.append(this.numberOfTotalPillsInLevel + ";");
		//stringbuilder.append(this.numberOfTotalPowerPillsInLevel + ";");
		stringbuilder.append(this.strategy + ";");
		
		return stringbuilder.toString();
	}

	/**
	 * Used to normalize distances. Done via min-max normalization. Supposes
	 * that minimum possible distance is 0. Supposes that the maximum possible
	 * distance is 150.
	 * 
	 * @param dist
	 *            Distance to be normalized
	 * @return Normalized distance
	 */
	public double normalizeDistance(int dist) {
		return ((dist - 0) / (double) (this.maximumDistance - 0)) * (1 - 0) + 0;
	}

	public DiscreteTag discretizeDistance(int dist) {
		if (dist == -1)
			return DiscreteTag.NONE;
		double aux = this.normalizeDistance(dist);
		return DiscreteTag.DiscretizeDouble(aux);
	}
	


	public double normalizeLevel(int level) {
		return ((level - 0) / (double) (Constants.NUM_MAZES - 0)) * (1 - 0) + 0;
	}

	public double normalizePosition(int position) {
		return ((position - 0) / (double) (this.numberOfNodesInLevel - 0)) * (1 - 0) + 0;
	}

	public DiscreteTag discretizePosition(int pos) {
		double aux = this.normalizePosition(pos);
		return DiscreteTag.DiscretizeDouble(aux);
	}

	public double normalizeBoolean(boolean bool) {
		if (bool) {
			return 1.0;
		} else {
			return 0.0;
		}
	}

	public double normalizeNumberOfPills(int numOfPills) {
		return ((numOfPills - 0) / (double) (this.numberOfTotalPillsInLevel - 0)) * (1 - 0) + 0;
	}

	public DiscreteTag discretizeNumberOfPills(int numOfPills) {
		double aux = this.normalizeNumberOfPills(numOfPills);
		return DiscreteTag.DiscretizeDouble(aux);
	}

	public double normalizeNumberOfPowerPills(int numOfPowerPills) {
		return ((numOfPowerPills - 0) / (double) (this.numberOfTotalPowerPillsInLevel - 0)) * (1 - 0) + 0;
	}

	public DiscreteTag discretizeNumberOfPowerPills(int numOfPowerPills) {
		double aux = this.normalizeNumberOfPowerPills(numOfPowerPills);
		return DiscreteTag.DiscretizeDouble(aux);
	}

	public double normalizeTotalGameTime(int time) {
		return ((time - 0) / (double) (Constants.MAX_TIME - 0)) * (1 - 0) + 0;
	}

	public DiscreteTag discretizeTotalGameTime(int time) {
		double aux = this.normalizeTotalGameTime(time);
		return DiscreteTag.DiscretizeDouble(aux);
	}

	public double normalizeCurrentLevelTime(int time) {
		return ((time - 0) / (double) (Constants.LEVEL_LIMIT - 0)) * (1 - 0) + 0;
	}

	public DiscreteTag discretizeCurrentLevelTime(int time) {
		double aux = this.normalizeCurrentLevelTime(time);
		return DiscreteTag.DiscretizeDouble(aux);
	}
	

	/**
	 * 
	 * Max score value lifted from highest ranking PacMan controller on PacMan
	 * vs Ghosts website: http://pacman-vs-ghosts.net/controllers/1104
	 * 
	 * @param score
	 * @return
	 */
	public double normalizeCurrentScore(int score) {
		return ((score - 0) / (double) (82180 - 0)) * (1 - 0) + 0;
	}

	public DiscreteTag discretizeCurrentScore(int score) {
		double aux = this.normalizeCurrentScore(score);
		return DiscreteTag.DiscretizeDouble(aux);
	}
	
	//Funciones de discretización hechas por nosotros
	
	
	//Para discretizar los booleanos
	//Devuelve ya un String con el valor
	public String discretizeBoolean(boolean value){
		if(value)
			return "true";
		else
			return "false";
	}
		
	
	
	public String discretizarDistanciaFantasmas(int distancia){
	
		if(distancia <= 25)
			return "cerca";
		else
			return "lejos";
			
	}
	
	//Devuelve un hashMap con la lista de atributos ya discretizados
	public HashMap<String, String> getHash(){
		
		HashMap hash = new HashMap<>();
		String[] atributos = {"DirectionChosen", "numOfPillsLeft", "numOfPowerPillsLeft", "isBlinkyEdible", "isInkyEdible", "isPinkyEdible", "isSueEdible", "blinkyDist", "inkyDist", "pinkyDist", "sueDist", "strategy"};
		
		for(String atributo : atributos){
			hash.put(atributo, discretizar(atributo));
		}
		
		
		return hash;			
				
	}
	
	
	//STRATEGY 
	/*public enum discretizeStrategy(){
		salirCorriendo,
		comerPildoras
		//Falta por completar	
		
	}*/
	
	//Par discretizar los datos de las tuplas
	public  String discretizar(String nombreAtributo){
		switch (nombreAtributo) {
			case "numOfPillsLeft":
				return discretizeNumberOfPills(numOfPillsLeft).toString();
			
			case "numOfPowerPillsLeft":
				return discretizeNumberOfPowerPills(numOfPowerPillsLeft).toString();
			
			case "isBlinkyEdible":
				return discretizeBoolean(isBlinkyEdible); 
			
			case "isInkyEdible":
				return discretizeBoolean(isInkyEdible);
		
			case "isPinkyEdible":
				return discretizeBoolean(isPinkyEdible); 
		
			case "isSueEdible":
				return discretizeBoolean(isSueEdible); 
		
			case "blinkyDist":
				return discretizarDistanciaFantasmas(blinkyDist);	
		
			case "inkyDist":
				return discretizarDistanciaFantasmas(inkyDist);	
			
			case "pinkyDist":
				return discretizarDistanciaFantasmas(pinkyDist);	
			
			case "sueDist":
				return discretizarDistanciaFantasmas(sueDist);	
		
			//case "strategy":
				//return discretizeStrategy().toString();
		default:
			return "None";
		}
		
	
	}

}
