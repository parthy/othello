import gki.game.*;

public class MyComputerPlayer<MoveT> implements IComputerPlayer<MoveT>
{
    private double bestReachableEvaluation=0;
    private MoveT lastMove;
    public String getLastMoveDescription()
    {
        return "Erreichbare Bewertung: "+bestReachableEvaluation+
                " letzter Zug: "+lastMove.toString();
    }

    public MoveT findBestMove(IStdGame<MoveT> game, int maxDepth,
            IGameEvaluator<MoveT> evaluator)
    {
        //++++++++++++++++++++++++++++++++++++
        // student begin
    	
    	//System.out.println("Find best move, looking "+maxDepth+" into the future.");
    	
    	double bestValue = -1;
    	MoveT bestMove = null;
    	boolean max = game.isPlayer1sTurn();
    	
    	for(MoveT move : game.getPossibleMoves()) {
    		//System.out.println("Maybe "+move.toString()+"?");
    		IStdGame<MoveT> tmpGame = game.copy();
    		try {
				tmpGame.doMove(move);
			} catch (Exception e) {}
    		
    		if(bestValue == -1) {
    			bestValue = evaluateState(tmpGame, maxDepth-1, evaluator, -1, max);
    			bestMove = move;
    		} else {
    			double newVal = evaluateState(tmpGame, maxDepth-1, evaluator, bestValue, max);
				if(max && newVal > bestValue) {
					bestValue = newVal;
					bestMove = move;
				}
				if(!max && newVal < bestValue) {
					bestValue = newVal;
					bestMove = move;
				}
    		}
    	}
    	
    	lastMove = bestMove;
    	
    	return bestMove;
        
        // student end
        //+++++++++++++++++++++++++++++++++
    }
    
    private double evaluateState(IStdGame<MoveT> game, int depth, IGameEvaluator<MoveT> evaluator, double currentBest, boolean wasMax) {
    	//System.out.println("evaluateState with depth "+depth);
    	if(depth == 0) {
    		double heurval = evaluator.evaluateGame(game);
    		//System.out.println("Directly using evaluator: "+heurval);
    		return heurval;
    	} else {
    		double bestVal = -1;
    		boolean max = game.isPlayer1sTurn();
    		for(MoveT move : game.getPossibleMoves()) {
    			
    			IStdGame<MoveT> tmpGame = game.copy();
    			try {
					tmpGame.doMove(move);
				} catch (Exception e) {}
				double newVal;
				if(!wasMax && max && bestVal > -1 && bestVal >= currentBest) newVal = evaluator.evaluateGame(tmpGame); // beta-pruning
				else if(wasMax && !max && bestVal > -1 && bestVal <= currentBest) newVal = evaluator.evaluateGame(tmpGame); // alpha-pruning
				else newVal = evaluateState(tmpGame, depth-1, evaluator, bestVal, max);
				if(bestVal == -1) {
					bestVal = newVal;
				} else {
					if(max && newVal > bestVal) bestVal = newVal; // maximize
					if(!max && newVal < bestVal) bestVal = newVal; // minimize
				}
    		}
    		return bestVal;
    	}
    }
}
