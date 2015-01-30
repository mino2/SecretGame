package diamondgame;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class CDiamond extends JButton {

    private Color mColor; //color of gem
    public boolean mMobility; //true if can be moved, false if wall
    //   public int mID;
    public boolean isValid; //true if valid, false if destroyed
    private static final Border mSelectedBorder = BorderFactory.createLineBorder(Color.green, 4); //border around selected gem
    //private static final Border mNormalBorder = BorderFactory.createEmptyBorder();
    private static final Border mNormalBorder = BorderFactory.createLineBorder(Color.red, 1); //border around all gems
    CPos mPos; //position of this gem

    
     /**
     * constructor
     * 
     * @param color color of new gem
     * @param mobility if gem is mobile
     * @param pos position of gem
     * @return new gem
     */
    public CDiamond(Color color, boolean mobility, CPos pos) {
        setColor(color);
        mMobility = mobility;
        //      mID = ID;
        mPos = pos;
        isValid = true;
    }

     /**
     * returns mMobility
     *
     * true if mobile, false if wall
     * 
     * @param 
     * @return whether is gem mobile or not 
     */
    public boolean isMobile() {
        return mMobility;
    }

    public void setColor(Color c) {
        this.setBackground(c);
        mColor = c;
    }

    public Color getColor() {
        return mColor;
    }

    /**
     * change border color only to mSelectedBorder
     *
     * @param 
     * @return 
     */
    
    public void selectMe() {
        this.setBorder(mSelectedBorder);
    }

        /**
     * change border color only to mNormalBorder
     *
     * @param 
     * @return 
     */
    public void deselectMe() {
        this.setBorder(mNormalBorder);
    }

     /**
     * set some text into gem
     *
     * @param 
     * @return 
     */
    public void draw() {
        setText("a");
    }

     /**
     * write position into gem
     *
     * @param pos given position
     * @return 
     */
    public void drawPos(CPos pos) {
        setText(pos.x + "/" + pos.y);
    }

     /**
     * set validity to 0 and color to white
     *
     * @param
     * @return 
     */
    
    public void deleteMe() {
        isValid = false;
        //setColor(null);
        setColor(Color.WHITE);
    }

     /**
     * set gem as valid and gives it a color
     *
     * @param color new color of gem
     * @return 
     */
    
    public void createMe(Color color) {
        setColor(color);
        isValid = true;
    }

         /**
     * transform diamond into the given one
     *
     * @param diamond my example design to get on me
     * @return 
     */
    
    public void transformMe(CDiamond diamond) {
        setColor(diamond.mColor);
        mMobility = diamond.mMobility;
        isValid = diamond.isValid;
        mPos.x = diamond.mPos.x;
        mPos.y = diamond.mPos.y;
        //other attributes
    }

    public CPos getPos() {
        return mPos;
    }
}
