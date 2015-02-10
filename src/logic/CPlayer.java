package logic;

public class CPlayer {
    
    private final String mName;
    private int mScore;

    public CPlayer()      
    {
        this("Pan R.",0);
    }
    
    public CPlayer(String mName, int score) {
        this.mName = mName;
        this.mScore = score;
    }
    public void incrementScore(int score){
        mScore+=score;
    }

    public int getScore() {
        return mScore;
    }

    public String getName() {
        return mName;
    }

    
}
