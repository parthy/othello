import gki.game.*;
import othello.base.*;

public class MyOthelloEvaluator implements IGameEvaluator<OthelloMove>
{
    public double evaluateGame(IStdGame<OthelloMove> gameSetting)
    {
        OthelloGame game=(OthelloGame) gameSetting;

        //+++++++++++++++++++++++++++++++++++++++++++
        //student begin
        
        // if we reached a final state, i.e. someone has won, return the real value
        Winner winner = game.getWinner();
        if(winner == Winner.Player1) return 1;
        if(winner == Winner.Player2) return 0;
        if(winner == Winner.Drawn) return 0.5;
        
        // find piececount
        int pieces1=0, pieces2=0, corners1=0, corners2=0;
        for(int i=0; i<8; i++)
        	for(int j=0; j<8; j++) {
        		OthelloGame.PlayerColor color = game.getFieldColor(i, j);
        		if(color == OthelloGame.PlayerColor.Player1) {
        			pieces1++;
        			if(i == 0 && j == 0) corners1++;
        			if(i == 0 && j == 7) corners1++;
        			if(i == 7 && j == 7) corners1++;
        			if(i == 7 && j == 0) corners1++;
        		}
        		if(color == OthelloGame.PlayerColor.Player2) {
        			pieces2++;
        			if(i == 0 && j == 0) corners2++;
        			if(i == 0 && j == 7) corners2++;
        			if(i == 7 && j == 7) corners2++;
        			if(i == 7 && j == 0) corners2++;
        		}
        	}
        
        // determine mobility
        int moves=0;
        double mobility=0, mobility2=0;
        
        for(OthelloMove move : game.getPossibleMoves()) moves++;
        
    	mobility = (game.isPlayer1sTurn()) ? moves : 0;
    	mobility2 = (game.isPlayer1sTurn()) ? 0 : moves;
    	
    	double myVal = (0.01*pieces1 + corners1*10 + mobility);
    	double oppVal = (0.01*pieces2 + corners2*10 + mobility2);
    
        return myVal/(myVal+oppVal);
        
        //student end
        //+++++++++++++++++++++++++++++++++++++++++++
    }
}
