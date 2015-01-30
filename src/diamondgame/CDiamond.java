package diamondgame;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class CDiamond extends JButton {
 
    private Color mColor;
    public boolean mMobility;
 //   public int mID;
    public boolean isValid;
    private static final Border mSelectedBorder = BorderFactory.createLineBorder(Color.green, 4);
    //private static final Border mNormalBorder = BorderFactory.createEmptyBorder();
    private static final Border mNormalBorder = BorderFactory.createLineBorder(Color.red, 1);
    
    CPos mPos;

    public CDiamond(Color color, boolean mobility, CPos pos) {
        setColor(color);
        mMobility = mobility;
  //      mID = ID;
        mPos = pos; 
        isValid = true;
    }

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

    public void selectMe() {
        this.setBorder(mSelectedBorder);
    }

    public void deselectMe() {
        this.setBorder(mNormalBorder);
    }

    public void draw() {
        setText("a");
    }
    
    public void drawPos(CPos pos) {
        setText(pos.x+"/"+pos.y);
    }

    public void deleteMe() {
        isValid = false;
        //setColor(null);
        setColor(Color.WHITE);
    }

    public void createMe(Color color) {
        setColor(color);
        isValid = true;
    }
    
    public void transformMe(CDiamond diamond){
    setColor(diamond.mColor);
    mMobility = diamond.mMobility;
    isValid = diamond.isValid;
    mPos.x = diamond.mPos.x;
    mPos.y = diamond.mPos.y;
    //other attributes
    }
    
    public CPos getPos(){
    return mPos;
    }

}
