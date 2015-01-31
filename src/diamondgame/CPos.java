package diamondgame;

import com.sun.org.apache.xpath.internal.operations.Equals;

final class CPos {

    public void setXY(int x, int y){
        this.y = y;
        this.x = x;
    }
        
    public int x;
    public int y;
    
    public CPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean compare(CPos pos) {
    if ( this == pos ) return true; //same address
    if(this.x == pos.x && this.y == pos.y) return true; //fix me

    return false;
    }
    
    
}
