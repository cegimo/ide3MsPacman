package pacman.entries.pacman;

import java.util.ArrayList;

import decisionTrees.Nodo;
import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;



/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */


public class MyPacmanTest extends Controller<MOVE>
{	
	//private MOVE myMove=MOVE.NEUTRAL;
	private static final int MIN_DISTANCE=15;	//if a ghost is this close, run away
	Nodo root;
	Nodo ghostEdible ;
	
	//Nodo final
	Nodo eat;
	Nodo run;
	
	//TESTEO DE NODOS    creacion del arbol
	public MyPacmanTest(){
		//Creacion del arbol a pelo
			//Nodo padre
			root = new Nodo("nearGhostDistance", false);
			ghostEdible = new Nodo("ghostEdible", false);
			
			//Nodo final
			 eat = new Nodo("eat", true);
			run = new Nodo("run", true);
			
			//Nodos hijos
			root.setHijo("near", ghostEdible);
			root.setHijo("far", run);
			ghostEdible.setHijo("yes", eat);
			ghostEdible.setHijo("no", run);
			
	}	
	
	public MOVE getMove(Game game,long timeDue)
	{			
		int current=game.getPacmanCurrentNodeIndex();
		
		
		
		// cerca <=15    lejos > 15   ===== MIN_DISTANCE
		
		for(GHOST ghost : GHOST.values())
		{
			root.classify(game);
			
		}
			
		
		
		
		
		//Strategy 1: if any non-edible ghost is too close (less than MIN_DISTANCE), run away
		for(GHOST ghost : GHOST.values())
			if(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
				if(game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE){
					//game.getStrategy = runaway;
					game.setStrategy(1);
					return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghost),DM.PATH);
				}
		
		return MOVE.NEUTRAL;
	}
}

/*public class MyPacMan extends Controller<MOVE>
{
	
	//contructor arbol de decision
	
	
	public MOVE getMove(Game game, long timeDue) 
	{
		//estrategia =  this.arboldecision.clasificar();
		
		//return estrategia();
		//return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getActivePowerPillsIndices()[0], DM.MANHATTAN);
		return myMove;
	}
}*/
