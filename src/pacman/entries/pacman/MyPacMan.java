package pacman.entries.pacman;

import java.util.ArrayList;

import decisionTrees.DecisionTree;
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
private static final int MIN_DISTANCE=25;	//if a ghost is this close, run away
private DecisionTree decisionTree;

	public MyPacMan() {
		decisionTree = new DecisionTree();
		decisionTree.construirArbol();
		decisionTree.pintar();
	}
	
	
	
	public MOVE getMove(Game game,long timeDue)
	{			
		String strategy = decisionTree.getStrategy(game);
		int current=game.getPacmanCurrentNodeIndex();
		
		System.out.println(strategy);
		
		if(strategy == "huir"){
			//Strategy 1: if any non-edible ghost is too close (less than MIN_DISTANCE), run away
			for(GHOST ghost : GHOST.values())
				if(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
					if(game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE){			
						return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghost),DM.PATH);
					}
		}
		
		if(strategy == "cazar"){
			//Strategy 2: find the nearest edible ghost and go after them 
			int minDistance=Integer.MAX_VALUE;
			GHOST minGhost=null;		
			
			for(GHOST ghost : GHOST.values())
				if(game.getGhostEdibleTime(ghost)>0)
				{
					int distance=game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost));
					
					if(distance<minDistance)
					{
						minDistance=distance;
						minGhost=ghost;
					}
				}
			
			if(minGhost!=null){	//we found an edible ghost
				
				return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(minGhost),DM.PATH);
			}
		}
		if(strategy == "comer"){
			//Strategy 3: go after the pills and power pills
			int[] pills=game.getPillIndices();
			int[] powerPills=game.getPowerPillIndices();		
			
			ArrayList<Integer> targets=new ArrayList<Integer>();
			
			for(int i=0;i<pills.length;i++)					//check which pills are available			
				if(game.isPillStillAvailable(i))
					targets.add(pills[i]);
			
			for(int i=0;i<powerPills.length;i++)			//check with power pills are available
				if(game.isPowerPillStillAvailable(i))
					targets.add(powerPills[i]);				
			
			int[] targetsArray=new int[targets.size()];		//convert from ArrayList to array
			
			for(int i=0;i<targetsArray.length;i++)
				targetsArray[i]=targets.get(i);
			//Set de la estrategia
			game.setStrategy("comer");
			//return the next direction once the closest target has been identified
			return game.getNextMoveTowardsTarget(current,game.getClosestNodeIndexFromNodeIndex(current,targetsArray,DM.PATH),DM.PATH);
		}
		return MOVE.NEUTRAL;
	}
}