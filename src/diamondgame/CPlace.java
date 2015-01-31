package diamondgame;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class CPlace extends JButton {

    public CDiamond mDiamond;
    private static final Border mSelectedBorder = BorderFactory.createLineBorder(Color.green, 4); //border around selected gem
    //private static final Border mNormalBorder = BorderFactory.createEmptyBorder();
    private static final Border mNormalBorder = BorderFactory.createLineBorder(Color.red, 1); //border around all gems

    

    public CPlace(CDiamond diamond) {
        mDiamond=diamond;
        setColor(diamond.mColor);
    }
    
    private void setColor(Color c) {
        this.setBackground(c);
        setText(""+mDiamond.mID);
    }
            /**
     * change border color only to mSelectedBorder 
     */
    public void selectMe() {
        this.setBorder(mSelectedBorder);
    }
    
    
     /**
     * set gem as valid and gives it a color
     * 
     * @param diam
     */
   public void createMe(CDiamond diam) {
       mDiamond=diam;
       setColor(diam.mColor);
    }    

        /**
     * change border color only to mNormalBorder 
     */
    public void deselectMe() {
        this.setBorder(mNormalBorder);
    }
    
    
     /**
     * set validity to 0 and color to white 
     */
    
    public void deleteMe() {
        mDiamond.isValid = false;
        setColor(Color.WHITE);
    }
    
        /**
     * returns mMobility
     *
     * true if mobile, false if wall
     * @return whether is gem mobile or not 
     */
    public boolean isMobile() {
        return mDiamond.mMobility;
    }
    public Color getColor() {
        return mDiamond.mColor;
    }

    public boolean isValide() {
        return mDiamond.isValid();
    }
    
    public void draw()
    {
        setColor(mDiamond.mColor);
    }
}