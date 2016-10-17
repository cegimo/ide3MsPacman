package pacman.entries.pacman;

import java.util.ArrayList;

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




public class MyPacMan extends Controller<MOVE>
{	
	//private MOVE myMove=MOVE.NEUTRAL;
	private static final int MIN_DISTANCE=20;	//if a ghost is this close, run away
	
	public MOVE getMove(Game game,long timeDue)
	{			
		int current=game.getPacmanCurrentNodeIndex();
		
		//Strategy 1: if any non-edible ghost is too close (less than MIN_DISTANCE), run away
		for(GHOST ghost : GHOST.values())
			if(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
				if(game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE){
					//game.getStrategy = runaway;
					game.setStrategy("huir");
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
