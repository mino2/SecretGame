package logic;

import GUI.CGameLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CGameboard {

    public final int mWidth = 10; //width of gameboard
    public final int mHeight = 10; //height of gameboard
    private CPos mFirstActive; //position of active place (after first choose by clicking on it)
    private CPos mSecondActive; //position of second place (only needed for swapping with mFirstActive so far)

    private ArrayList<ArrayList<CPlace>> items; //stores diamonds for access
    public int totalDiamonds; //just mWidth*mHeight for shorter notation
    private ArrayList<Color> mAllColors; //all possible collors for diamonds
    private static final String version = "0.5"; //actual version
    private CPlayer player;
    private boolean wasStartCount;
    private final CGameLayout mGameLayout;

    public  ArrayList<ArrayList<CPlace>> getItems() {
        return items;
    }

    
    /**
     *
     */
    public CGameboard() {
        wasStartCount=false;
        initGameboard();
        mGameLayout = new CGameLayout(version, this);
        
    }


    /**
     * Calls "deleteMe" of given place.
     *
     * set isValid to false and color to white
     *
     * Use {@link #CleanDiamonds(ArrayList<CPos> positions)} . to get diamonds.
     *
     * @param pos -position of item to destroy
     */
    public void destroyGem(CPos pos) {
        GetItem(pos).deleteMe();
    }

    /**
     * Gets specific item from "items".
     *
     * Use {@link #fall(CPos pos)} to get diamonds. Use {@link #initGameboard()}
     * to get diamonds.
     *
     * @param x - X coordinate in "items"
     * @param y - Y coordinate in "items"
     * @return selected item from [X,Y] position
     */
    public CPlace GetItem(int x, int y) {
        return items.get(x).get(y);
    }

    /**
     * Gets specific item from "items".
     *
     * Use {@link #destroyGem(CPos pos)} to get diamonds.
     *
     * @param pos - Coordinates of item
     * @return selected item from [X,Y] position
     */
    public CPlace GetItem(CPos pos) {
        return items.get(pos.x).get(pos.y);
    }

    /**
     * Sets specific gem to position [X,Y].
     *
     *
     * @param x - X coordinate in "items"
     * @param y - Y coordinate in "items"
     * @param diamond - place this item to [x,y] into "items" 
     */
    /*public void SetItem(int x, int y, CPlace diamond) {
        if (x >= 0 && y >= 0) {
            items.get(x).set(y, diamond);
        }
    }*/

    /**
     * Sets specific gem to position [pos].
     *
     * Use {@link #swap(CDiamond first, CDiamond second)}. Use
     * {@link #fall(Cpos pos)}.
     *
     * @param pos - Coordinates of item
     * @param diamond - place this item to [pos] into "items" 
     */
    /*public void SetItem(CPos pos, CPlace diamond) {
        if (pos.x >= 0 && pos.y >= 0) {
            items.get(pos.x).set(pos.y, diamond);
        }
    }*/
    
      /**
     * Swaps first and second diamonds.
     *
     * Use {@link #fall(CPos pos)}.
     *
     * @param first  - first place
     * @param second - second place 
     */

    public void swap(CPos first, CPos second) {
        if (CDiamondGame.DEBUG) {
            printDebug("SWAPPING :-)");
        }
        CDiamond tmpGem=items.get(first.x).get(first.y).mDiamond;
        items.get(first.x).get(first.y).mDiamond=items.get(second.x).get(second.y).mDiamond;
        items.get(second.x).get(second.y).mDiamond=tmpGem;
    }
    

          /**
     * Generates random place.
     * 
     * Use {@link #fall(CPos pos)}.
     * @return generated place
     */
     public CDiamond generateRandDiamond() {
        Color Dcolor = mAllColors.get((int) (Math.random() * mAllColors.size()));
        return new CDiamond(Dcolor, true);
    }

     /**
     * NOT IMPLEMENTED YET!
     * 
     * Generates random place based on ID of place types.
 for example ID 1-10 =classic diamonds, 11 = wall...
     * 
     * @param IDofDiamondType
     * @return generated place
     */
     
    public CDiamond generateDiamond(int IDofDiamondType) {
        //to implement
        return new CDiamond(Color.yellow, 666<667); //just random place before implementation of this method
    }

     /**
     * fills empty place after destroyed gem.
     *
     * finds nearest upper valid gems and puts them down to fill empty space
     * 
     * if no upper valid gem is available, generate new random gem and put it here
     * 
     * Use {@link #checkAll()}.
     *
     * @param pos position of destroyed gem to be filled 
     */
    
    public void fall(CPos pos) {
        int upper = pos.y - 1;
        for (; upper >= 0 && !GetItem(pos.x, upper).isValide(); upper--) {
            //this finds first upper valid place
        }
        if (upper < 0) {//no valid gems above
            //generate new gem
            items.get(pos.x).get(pos.y).mDiamond=generateRandDiamond();
            //swap(items.get(pos.getX()).get(pos.getY()), tmp);
            //SetItem(pos, tmp);
       printDebug("falling new " + upper + " on " + pos.y + " (" + pos.x + ")"+items.get(pos.x).get(pos.y).mDiamond.getColor());
        } else {
            swap(pos, new CPos(pos.x,upper));//now the upper gem will be invalid
       printDebug("falling " + upper + " on " + pos.y + " (" + pos.x + ")");
        }
    }
    
     /**
     * Check the whole gameboard for invalid/destroyed gems.
     *
     * as result of this method, the whole gameboard should be valid
     * 
     * Use {@link #initGameboard()}.
     *
     * @param
     * @return 
     */
    
    /**
     * Check the whole gameboard for invalid/destroyed gems.as result of this method, the whole gameboard should be valid
     * 
     * Use {@link #initGameboard()}. 
     */
    public void checkAll() { //projed vsechny odspoda, kdyz je nekde prazdno tak shod zezhora a projed nakonci znovu
        boolean changed;
        do {
            changed = false;
            for (int i = 0; i < items.size(); i++) {//from left to right
                for (int j = items.get(i).size() - 1; j >= 0; j--) { //from bottom to top
                    if (!items.get(i).get(j).isValide()) {
                        CPos pos = new CPos(i, j);
                        fall(pos); 
                        printDebug("After fall "+items.get(i).get(j).getColor());
                        changed = true;
                    }
                }
            }
            
           for (int i = 0; i < items.size(); i++) {//from left to right
                for (int j = items.get(i).size() - 1; j >= 0; j--) { //from bottom to top
                    if (tryDiamond(new CPos(i,j))) {
                        CPos pos = new CPos(i, j);
                        CleanDiamonds(getNeighboursToDelete(pos));
                        printDebug("After fall "+items.get(i).get(j).getColor());
                        changed = true;
                    }
                }
            }
           /* try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(CGameboard.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
        } while (changed);
        drawDiamonds();
    }
         /**
     * Here you can add colors of gems
     *
     * as result of this method, the whole gameboard should be valid
     * 
     * Use {@link #initGameboard()}.
     *
     * @param
     * @return 
     */
    
    private void initColors() {
        mAllColors = new ArrayList<>();
        mAllColors.add(Color.red);
        mAllColors.add(Color.blue);
        mAllColors.add(Color.yellow);
        mAllColors.add(Color.green);
    //    mAllColors.add(Color.pink);
    //    mAllColors.add(Color.magenta);
     //   mAllColors.add(Color.DARK_GRAY);
    //    mAllColors.add(Color.black);
    }
    
        /**
     *
     * @return hodnota
     */
    public int fillBoard() {
        return 0;
    }

    
     /**
     * Initialize of whole gameboard
     *
     * set size of gameboard, initialize colors, generate all gems...
     * 
     * Use {@link #CGameboard()}.
     *
     * @param
     * @return 
     */
    
    private void initGameboard() {

        mFirstActive = new CPos(-1, -1);
        mSecondActive = new CPos(-1, -1);
        totalDiamonds = mWidth * mHeight;
        initColors();
        items = new ArrayList<>();
        CPlace place;
       // ButtonGroup group =new ButtonGroup();
        for (int i = 0; i < mWidth; i++) { //initialize columns in the first row
            items.add(new ArrayList<CPlace>());
        }

        for (int i = 0; i < totalDiamonds; i++) {
            place=new CPlace( generateRandDiamond());
            place.addActionListener(new ActionListenerDiamond());
            place.deselectMe();

            items.get(i % mWidth).add(place); //adding diamonds from left to right
        //    group.add(place);
        }

        player=new CPlayer("Plajer", 0);
        
        
        checkAll();
        if(!wasStartCount)
            wasStartCount=true;
    }

    private class ActionListenerDiamond implements ActionListener {

        
        @Override
        public void actionPerformed(ActionEvent e) {
            CPlace place = (CPlace) e.getSource();
            CPos clickDiamond=findPlace(place); 
            if (!place.isMobile()) { // cant move this piece bro :-( You just cant move walls and laws
                return;
            }
            /*bro testoval jsem to to porvonani CPOS porovanva ID to znamena ze e to stejnej objekt
            a pokud se ti muze stat ye 2 objekty budou mit stejny souradnice a jiny ID selze to*/
            if (mFirstActive.x < 0 || mFirstActive.y < 0 || clickDiamond.compare(mFirstActive)) {
                deselectDiamond(mFirstActive);
                deselectDiamond(mSecondActive);
                selectDiamond(clickDiamond);
                return;
            }

            if (isNeighbour(clickDiamond, mFirstActive)) {
                mSecondActive = clickDiamond;
                boolean success1 = false;
                boolean success2 = false;
                ArrayList<CPos> toRemove = new ArrayList<>();
                swap(mFirstActive,mSecondActive);

                if (tryDiamond(mFirstActive)) {
                    success1 = true;
                }
                if (tryDiamond(mSecondActive)) {
                    success2 = true;
                }

                if (!success1 && !success2) {//bad move, return back
                    swap(mFirstActive,mSecondActive);
                    deselectDiamond(mFirstActive);
                    return;
                }

                if (success1) {
                    toRemove.addAll(getNeighboursToDelete(mFirstActive));
                }
                if (success2) {
                    toRemove.addAll(getNeighboursToDelete(mSecondActive));
                }
                deselectDiamond(mFirstActive);
                CleanDiamonds(toRemove);
                checkAll();
                
            } else {
                deselectDiamond(mFirstActive);
                selectDiamond(clickDiamond);
            }
        }
    }

    private ArrayList<CPos> getNeighboursToDelete(CPos Active) {
        ArrayList<CPos> neighbours = new ArrayList<>();

        Color refColor = items.get(Active.x).get(Active.y).getColor();
     printDebug("refColor je: " + refColor);
        boolean horMatch = false;
        boolean vertMatch = false;

        //find the most left place of the same color
        int left = Active.x;
        while (left-1 >= 0 && items.get(left - 1).get(Active.y).getColor() == refColor) {
      printDebug("porovnavam vlevo s: " + items.get(left - 1).get(Active.y).getColor());
            left--;
        }
        int mostLeft = left;
        int widthColor = 1;
        while ((left + 1) < mWidth /*end of row*/ && items.get(left + 1).get(Active.y).getColor() == refColor) {
            left++;
            widthColor++;
        }
        int mostRight = left;

        //find the lowest place of the same color
        int up = Active.y;
        while (up - 1 >= 0 && items.get(Active.x).get(up - 1).getColor() == refColor) {
       printDebug("porovnavam nahore s: " + items.get(Active.x).get(up - 1).getColor());
            up--;
        }
        int mostUp = up;
        int heightColor = 1;
        while ((up + 1) < mHeight && items.get(Active.x).get(up + 1).getColor() == refColor) {
            up++;
            heightColor++;
        }
        int mostDown = up;

        /*add mostLeft to mostRight if widthColor >= 3*/
        if (widthColor >= 3) {
            horMatch = true;
            for (int i = mostLeft; i <= mostRight; i++) {
                CPos pos = new CPos(i, Active.y);
                neighbours.add(pos);
            }
        }

        if (heightColor >= 3) {
            vertMatch = true;
            for (int j = mostUp; j <= mostDown; j++) {
                CPos pos = new CPos(Active.x, j);
                neighbours.add(pos);
            }
        }
        if (vertMatch && horMatch) {//vertical and horizontal match
            for (int i = 0; i < neighbours.size(); i++) {
                if (neighbours.get(i).compare(Active)) {
                    neighbours.remove(i); //there would be the active element twice
                    return neighbours;
                }
            }
        }
        return neighbours;
    }

    private void CleanDiamonds(ArrayList<CPos> positions) {
        if(wasStartCount)
        {
        player.incrementScore(positions.size());
        mGameLayout.updateScore();

        }
        for (CPos position : positions) {
            //adding score and so on here//
            destroyGem(position);
        }
    }

    private boolean isNeighbour(CPos first, CPos second) {
        if (Math.abs(first.x - second.x) == 1 && first.y == second.y) {
            return true;
        }
        if (Math.abs(first.y - second.y) == 1 && first.x == second.x) {
            return true;
        }
        return false;
    }

    private void selectDiamond(CPos pos) {
        if (pos.x < 0 || pos.y < 0) {
            if (CDiamondGame.DEBUG) {
           printDebug("SELECTING bad diamond bro");
            }
            return;
        }
        mFirstActive = pos;
        //items.get(diamondID).setBorder(BorderFactory.createLineBorder(Color.green, 4)); //change active place to green
        items.get(pos.x).get(pos.y).selectMe();
    }

    private void deselectDiamond(CPos pos) {
        if (mFirstActive.x >= 0) {
            items.get(mFirstActive.x).get(mFirstActive.y).deselectMe();
        }
        if (mSecondActive.x >= 0) {
            items.get(mSecondActive.x).get(mSecondActive.y).deselectMe();
        }

        if (pos.x >= 0 && pos.y >= 0) {
            CPos p = new CPos(-1, -1);
            mFirstActive = p;
            mSecondActive = p;
            //  items.get(diamondID).setBorder(BorderFactory.createEmptyBorder()); //change active place to green
            items.get(pos.x).get(pos.y).deselectMe();
        }
    }

    private boolean tryDiamond(CPos pos) {
        ArrayList<CPos> list = getNeighboursToDelete(pos);
        return list.size() > 0;
    }
    private void drawDiamonds()
    {
        for (ArrayList<CPlace> d : items)
        {
            for(CPlace d2:d)
            {
                d2.draw();
            }
        }
    }
    private CPos findPlace(CPlace place)
    {
        for (int i = 0; i < mWidth; i++) {
            for (int j = 0; j < mHeight; j++) {
                if(items.get(i).get(j) == place)
                    return new CPos(i, j);
            }
        }
        return null;
    }
    
    public void printDebug(String s){
    if(CDiamondGame.DEBUG){
        System.out.println(s);
    }
    }
    
/*public void createLayout() {
        // final CGameLayout layout = new CGameLayout("Diamanty", desktop);

        // Use an appropriate Look and Feel 
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        // Turn off metal's use of bold fonts //
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event dispatchi thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CGameLayout.createAndShowGUI("Diamanty", desktop,CGameboard.this);
            }
        });
    }*/

    public static String getVersion() {
        return version;
    }

    public CPlayer getPlayer() {
        return player;
    }
 
    
}
