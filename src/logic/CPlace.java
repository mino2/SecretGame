package logic;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

public class CPlace extends JButton{

   private static final long serialVersionUID = 45L;
    
    public CItem mDiamond;
    private static final Border mSelectedBorder = BorderFactory.createLineBorder(Color.green, 4); //border around selected gem
    //private static final Border mNormalBorder = BorderFactory.createEmptyBorder();
    private static final Border mNormalBorder = BorderFactory.createLineBorder(Color.black, 1); //border around all gems

    public CPlace(CItem item) {
        super();
        mDiamond = item;
        setColor(item.mColor);
    }

    private void setColor(Color c) {

        if (c.equals(Color.white)) {
            this.setBackground(c);
            this.setIcon(null);
            this.setOpaque(true);
            this.setContentAreaFilled(true);
            return;
        }

        this.setOpaque(false);
        this.setContentAreaFilled(false);
        //  this.setBorderPainted(false);
        if (c.equals(Color.yellow)) {
            this.setIcon(new ImageIcon(this.getClass().getResource("/images/diamond_yellow.png")));
        }
        if (c.equals(Color.blue)) {
            this.setIcon(new ImageIcon(this.getClass().getResource("/images/diamond_blue.png")));
        }
        if (c.equals(Color.green)) {
            this.setIcon(new ImageIcon(this.getClass().getResource("/images/diamond_green.png")));
        }
        if (c.equals(Color.red)) {
            this.setIcon(new ImageIcon(this.getClass().getResource("/images/diamond_red.png")));
        }
        if (c.equals(Color.black)) {
            this.setIcon(new ImageIcon(this.getClass().getResource("/images/wall.png")));
        }
        //setText(""+mDiamond.mID);
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
    public void createMe(CItem diam) {
        mDiamond = diam;
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
     *
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

    public void draw() {
        setColor(mDiamond.mColor);
    }
}
