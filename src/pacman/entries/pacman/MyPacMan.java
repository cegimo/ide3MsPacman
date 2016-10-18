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
		
		System.out.println(strategy);
		return MOVE.DOWN;
	}
}