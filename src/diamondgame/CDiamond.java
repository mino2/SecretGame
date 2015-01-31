/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diamondgame;

import java.awt.Color;

/**
 *
 * @author Jirka
 */
public class CDiamond {
    
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
        public CDiamond(Color color, boolean mobility) {
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
