import gki.game.*;
import othello.base.*;

public class Main 
{
    private static void testPlayer(IGameEvaluator<OthelloMove> evaluator, MyComputerPlayer player)
    {
        try
        {
            double erg=OthelloControler.percentageWinsPlayerAgainstRnd(evaluator, player, 3,100,true);
            System.out.println("Gewonnen: "+erg*100+" %");
        }
        catch(Exception ex)
        {
            System.err.println("Fehler: "+ex.toString());
            ex.printStackTrace(System.err);
        }
    }
    public static void main(String[] args) 
    {
        MyOthelloEvaluator evaluator=new MyOthelloEvaluator();
        MyComputerPlayer<OthelloMove> player=new MyComputerPlayer<OthelloMove>();
        
        //OthelloControler.run(evaluator, player);
        //OthelloControler.runConsole(evaluator, player,5);
        testPlayer(evaluator,player);
    }
}
