package logic;

import java.io.Serializable;

public class CPlayer implements Serializable{
    
    private final String mName;
    private int mScore;

    public CPlayer()      
    {
        this("Pan R.");
    }
    
    public CPlayer(String mName) {
        this.mName = mName;
        this.mScore = 0;
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
