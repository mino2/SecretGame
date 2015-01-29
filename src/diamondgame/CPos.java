/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diamondgame;

/**
 *
 * @author Noubuk
 */
public class CPos {

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y){
        this.y = y;
        this.x = x;
    }
        
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int x;
    public int y;
    
    public CPos(int xx, int yy) {
        x = xx;
        y = yy;
    }
    
    
}
