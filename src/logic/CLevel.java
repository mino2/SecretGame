package logic;

import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import shared.CDialogs;

public class CLevel {

    public int mLevelNumber; //which level is this?
    private int mGoal;
    private int mWidth; //width of gameboard (number of columns)
    private int mHeight; //height of gameboard (number of rows)
    public int totalDiamonds; //just mWidth*mHeight for shorter notation
    private ArrayList<Dimension> mWalls; // list of walls on gameboard
    private ArrayList<Dimension> mValuable; //list of valuable items to collect on gameboard
    private ArrayList<ArrayList<CPlace>> items; //stores diamonds for access
    private ArrayList<Color> mAllColors; //all possible collors for diamonds
    private Dimension mFirstActive; //position of active place (after first choose by clicking on it)
    private Dimension mSecondActive; //position of second place (only needed for swapping with mFirstActive so far)
    private CGameboard mGame;

    public CLevel(int level, CGameboard game) {
        mGame = game;
        mLevelNumber = level;
        mGoal = 100 * level;
        initColors();
        generateNewLevel();
        initLevel();
    }

    private void generateNewLevel() {

        mWalls = new ArrayList<>();
        mValuable = new ArrayList<>();

        switch (mLevelNumber) {
            case 1:
                mWidth =8;
                mHeight = 8;
                break;
            case 2:
                mWidth = 6;
                mHeight = 6;
                mWalls.add(new Dimension(3, 3)); //jebne zed doprostred hraci plochy
                break;
            default:
                mWidth = 5;
                mHeight = 5;
                break;
        }
        totalDiamonds = mWidth * mHeight;
    }
    
    public void updateLevel()
    {
        items.clear();
        mLevelNumber++;
        mGoal= 1000 * mLevelNumber;
        generateNewLevel();
        initLevel();
    }

    private void initLevel() {

        mFirstActive = new Dimension(-1, -1);
        mSecondActive = new Dimension(-1, -1);
        items = new ArrayList<>();
        CPlace place;
        // ButtonGroup group =new ButtonGroup();
        for (int i = 0; i < mWidth; i++) { //initialize columns in the first row
            items.add(new ArrayList<>());
        }

        for (int i = 0; i < totalDiamonds; i++) {
            place = new CPlace(generateRandDiamond());
            place.addActionListener(new ActionListenerDiamond());
            place.deselectMe();

            items.get(i % mWidth).add(place); //adding diamonds from left to right
            //    group.add(place);
        }
        checkAll();
    }

    public ArrayList<ArrayList<CPlace>> getItems() {
        return items;
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
    public CPlace GetItem(Dimension pos) {
        return items.get(pos.width).get(pos.height);
    }

    /**
     * Sets specific gem to position [pos].
     *
     * Use {@link #swap(CDiamond first, CDiamond second)}. Use
     * {@link #fall(Cpos pos)}.
     *
     * @param pos - Coordinates of item
     * @param diamond - place this item to [pos] into "items"
     */
    /**
     * Swaps first and second diamonds.
     *
     * Use {@link #fall(CPos pos)}.
     *
     * @param first - first place
     * @param second - second place
     */
    public void swap(Dimension first, Dimension second) {
        if (CDiamondGame.DEBUG) {
            CDialogs.printDebug("SWAPPING :-)");
        }
        CDiamond tmpGem = items.get(first.width).get(first.height).mDiamond;
        items.get(first.width).get(first.height).mDiamond = items.get(second.width).get(second.height).mDiamond;
        items.get(second.width).get(second.height).mDiamond = tmpGem;
    }

    /**
     * Generates random place.
     *
     * Use {@link #fall(CPos pos)}.
     *
     * @return generated place
     */
    public CDiamond generateRandDiamond() {
        Color Dcolor = mAllColors.get((int) (Math.random() * mAllColors.size()));
        return new CDiamond(Dcolor, true);
    }

    /**
     * fills empty place after destroyed gem.
     *
     * finds nearest upper valid gems and puts them down to fill empty space
     *
     * if no upper valid gem is available, generate new random gem and put it
     * here
     *
     * Use {@link #checkAll()}.
     *
     * @param pos position of destroyed gem to be filled
     */
    public void fall(Dimension pos) {
        int upper = pos.height - 1;
        for (; upper >= 0 && !GetItem(pos.width, upper).isValide(); upper--) {
            //this finds first upper valid place
        }
        if (upper < 0) {//no valid gems above
            //generate new gem
            items.get(pos.width).get(pos.height).mDiamond = generateRandDiamond();
            //swap(items.get(pos.getX()).get(pos.getY()), tmp);
            //SetItem(pos, tmp);
            CDialogs.printDebug("falling new " + upper + " on " + pos.height + " (" + pos.width + ")" + items.get(pos.width).get(pos.height).mDiamond.getColor());
        } else {
            swap(pos, new Dimension(pos.width, upper));//now the upper gem will be invalid
            CDialogs.printDebug("falling " + upper + " on " + pos.height + " (" + pos.width + ")");
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
     * Check the whole gameboard for invalid/destroyed gems.as result of this
     * method, the whole gameboard should be valid
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
                        Dimension pos = new Dimension(i, j);
                        fall(pos);
                        CDialogs.printDebug("After fall " + items.get(i).get(j).getColor());
                        changed = true;
                    }
                }
            }

            for (int i = 0; i < items.size(); i++) {//from left to right
                for (int j = items.get(i).size() - 1; j >= 0; j--) { //from bottom to top
                    if (tryDiamond(new Dimension(i, j))) {
                        Dimension pos = new Dimension(i, j);
                        CleanDiamonds(getNeighboursToDelete(pos));
                        CDialogs.printDebug("After fall " + items.get(i).get(j).getColor());
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

    private ArrayList<Dimension> getNeighboursToDelete(Dimension Active) {
        ArrayList<Dimension> neighbours = new ArrayList<>();

        Color refColor = items.get(Active.width).get(Active.height).getColor();
        CDialogs.printDebug("refColor je: " + refColor);
        boolean horMatch = false;
        boolean vertMatch = false;

        //find the most left place of the same color
        int left = Active.width;
        while (left - 1 >= 0 && items.get(left - 1).get(Active.height).getColor().equals(refColor)) {
            CDialogs.printDebug("porovnavam vlevo s: " + items.get(left - 1).get(Active.height).getColor());
            left--;
        }
        int mostLeft = left;
        int widthColor = 1;
        while ((left + 1) < mWidth /*end of row*/ && items.get(left + 1).get(Active.height).getColor().equals(refColor)) {
            left++;
            widthColor++;
        }
        int mostRight = left;

        //find the lowest place of the same color
        int up = Active.height;
        while (up - 1 >= 0 && items.get(Active.width).get(up - 1).getColor().equals(refColor)) {
            CDialogs.printDebug("porovnavam nahore s: " + items.get(Active.width).get(up - 1).getColor());
            up--;
        }
        int mostUp = up;
        int heightColor = 1;
        while ((up + 1) < mHeight && items.get(Active.width).get(up + 1).getColor().equals(refColor)) {
            up++;
            heightColor++;
        }
        int mostDown = up;

        /*add mostLeft to mostRight if widthColor >= 3*/
        if (widthColor >= 3) {
            horMatch = true;
            for (int i = mostLeft; i <= mostRight; i++) {
                Dimension pos = new Dimension(i, Active.height);
                neighbours.add(pos);
            }
        }

        if (heightColor >= 3) {
            vertMatch = true;
            for (int j = mostUp; j <= mostDown; j++) {
                Dimension pos = new Dimension(Active.width, j);
                neighbours.add(pos);
            }
        }
        if (vertMatch && horMatch) {//vertical and horizontal match
            for (int i = 0; i < neighbours.size(); i++) {
                if (neighbours.get(i).equals(Active)) {
                    neighbours.remove(i); //there would be the active element twice
                    return neighbours;
                }
            }
        }
        return neighbours;
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
    public void destroyGem(Dimension pos) {
        GetItem(pos).deleteMe();
    }

    /**
     * NOT IMPLEMENTED YET!
     *
     * Generates random place based on ID of place types. for example ID 1-10
     * =classic diamonds, 11 = wall...
     *
     * @param IDofDiamondType
     * @return generated place
     */
    public CDiamond generateDiamond(int IDofDiamondType) {
        //to implement
        return new CDiamond(Color.yellow, 666 < 667); //just random place before implementation of this method
    }

    private boolean tryDiamond(Dimension pos) {
        ArrayList<Dimension> list = getNeighboursToDelete(pos);
        return list.size() > 0;
    }

    public void drawDiamonds() {
        for (ArrayList<CPlace> d : items) {
            for (CPlace d2 : d) {
                d2.draw();
            }
        }
    }

    private boolean isNeighbour(Dimension first, Dimension second) {
        if (Math.abs(first.width - second.width) == 1 && first.height == second.height) {
            return true;
        }
        if (Math.abs(first.height - second.height) == 1 && first.width == second.width) {
            return true;
        }
        return false;
    }

    private class ActionListenerDiamond implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CPlace place = (CPlace) e.getSource();
            Dimension clickDiamond = findPlace(place);
            if (!place.isMobile()) { // cant move this piece bro :-( You just cant move walls and laws
                return;
            }
            /*bro testoval jsem to to porvonani CPOS porovanva ID to znamena ze e to stejnej objekt
             a pokud se ti muze stat ye 2 objekty budou mit stejny souradnice a jiny ID selze to*/
            if (mFirstActive.width < 0 || mFirstActive.height < 0 || clickDiamond.equals(mFirstActive)) {
                deselectDiamond(mFirstActive);
                deselectDiamond(mSecondActive);
                selectDiamond(clickDiamond);
                return;
            }

            if (isNeighbour(clickDiamond, mFirstActive)) {
                mSecondActive = clickDiamond;
                boolean success1 = false;
                boolean success2 = false;
                ArrayList<Dimension> toRemove = new ArrayList<>();
                swap(mFirstActive, mSecondActive);

                if (tryDiamond(mFirstActive)) {
                    success1 = true;
                }
                if (tryDiamond(mSecondActive)) {
                    success2 = true;
                }

                if (!success1 && !success2) {//bad move, return back
                    swap(mFirstActive, mSecondActive);
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

    private void selectDiamond(Dimension pos) {
        if (pos.width < 0 || pos.height < 0) {
            if (CDiamondGame.DEBUG) {
                CDialogs.printDebug("SELECTING bad diamond bro");
            }
            return;
        }
        mFirstActive = pos;
        //items.get(diamondID).setBorder(BorderFactory.createLineBorder(Color.green, 4)); //change active place to green
        items.get(pos.width).get(pos.height).selectMe();
    }

    private void deselectDiamond(Dimension pos) {
        if (mFirstActive.width >= 0) {
            items.get(mFirstActive.width).get(mFirstActive.height).deselectMe();
        }
        if (mSecondActive.width >= 0) {
            items.get(mSecondActive.width).get(mSecondActive.height).deselectMe();
        }

        if (pos.width >= 0 && pos.height >= 0) {
            Dimension p = new Dimension(-1, -1);
            mFirstActive = p;
            mSecondActive = p;
            //  items.get(diamondID).setBorder(BorderFactory.createEmptyBorder()); //change active place to green
            items.get(pos.width).get(pos.height).deselectMe();
        }
    }

    private Dimension findPlace(CPlace place) {
        for (int i = 0; i < mWidth; i++) {
            for (int j = 0; j < mHeight; j++) {
                if (items.get(i).get(j) == place) {
                    return new Dimension(i, j);
                }
            }
        }
        return null;
    }

    private void CleanDiamonds(ArrayList<Dimension> positions) {

        mGame.updateScore(positions.size());
        for (Dimension position : positions) {
            destroyGem(position);
        }
    }

    /**
     *
     * @param os
     * @throws Exception
     */
    public void Save(ObjectOutputStream os) throws Exception {
        os.writeInt(mWidth);
        os.writeInt(mHeight);
        os.writeInt(mLevelNumber);
        os.writeInt(mGoal);
        os.writeObject(items);
    }

    public void Load(ObjectInputStream is) throws Exception {
        mWidth = is.readInt();
        mHeight = is.readInt();
        mLevelNumber = is.readInt();
        mGoal = is.readInt();
        items = ((ArrayList<ArrayList<CPlace>>) is.readObject());
        for (int i = 0; i < mWidth; i++) {
            for (int j = 0; j < mHeight; j++) {
                items.get(i).get(j).addActionListener((new ActionListenerDiamond()));
            }
        }
        totalDiamonds = mHeight * mWidth;
    }

    public int getLevelNumber() {
        return mLevelNumber;
    }

    public int getGoal() {
        return mGoal;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getTotalDiamonds() {
        return totalDiamonds;
    }

}
