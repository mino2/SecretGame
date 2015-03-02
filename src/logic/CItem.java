package logic;

import java.awt.Color;
import java.io.Serializable;
        
public class CItem implements Serializable{
    
    private static final long serialVersionUID = 55L;
    
    private static int ID=1;
    public int mID;
    //pak dat private
    public Color mColor; //color of gem
    public boolean mMobility; //true if can be moved, false if wall
    //   public int mID;
    public boolean isValid; //true if valid, false if destroyed
    
    
         /**
     * constructor
     * 
     * @param color color of new gem
     * @param mobility if gem is mobile
     */
        public CItem(Color color, boolean mobility) {
        mID=ID++;
        mColor=color;
        mMobility = mobility;
        //      mID = ID;
        isValid = true;
    }
        
     /**
     * returns mMobility
     *
     * true if mobile, false if wall
     * @return whether is gem mobile or not 
     */
    public boolean isMobile() {
        return mMobility;
    }



    public Color getColor() {
        return mColor;
    }

    public boolean isValid() {
        return isValid;
    }
    
}
