package logic;


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

    return this.x == pos.x && this.y == pos.y;
    }
    
    
}
