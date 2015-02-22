package logic;

import GUI.CGameLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import shared.CDialogs;
import shared.CFunctions;

public class CGameboard {

    //public int mWidth = 10; //width of gameboard
    //public int mHeight = 10; //height of gameboard
    private CLevel mLevel;

    private int mMaxLevel;

    // private ArrayList<ArrayList<CPlace>> items; //stores diamonds for access
    // public int totalDiamonds; //just mWidth*mHeight for shorter notation
    // private ArrayList<Color> mAllColors; //all possible collors for diamonds
    private CPlayer mPlayer;
    private boolean wasStartCount;
    private CGameLayout mGameLayout;

    /**
     *
     * @param player
     */
    public CGameboard(CPlayer player) {
        mPlayer = player;
        wasStartCount = false;
        mLevel = new CLevel(1, this);
        mMaxLevel = 3;
        mGameLayout = new CGameLayout(CFunctions.getVersion(), this);
        initGameboard();
    }

    public CGameboard(File path) {

        loadGame(path);
        mGameLayout = new CGameLayout(CFunctions.getVersion(), this);
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

        if (!wasStartCount) {
            wasStartCount = true;
        }
    }

    public void updateScore(int score) {
        if (wasStartCount) {
            mPlayer.incrementScore(score);
            //winLevel();
            mGameLayout.updateScore();
        }
    }

    public CPlayer getPlayer() {
        return mPlayer;
    }

    public void save(File path) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path + ".sav"));
            mLevel.Save(os);
            os.writeObject(mPlayer);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void loadGame(File path) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            mLevel = new CLevel(0, this);
            mLevel.Load(in);
            mPlayer = ((CPlayer) in.readObject());
            wasStartCount = true;
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void winLevel() {
        if (mLevel.getGoal() <= mPlayer.getScore()) {
            shared.CFunctions.printDebug("\n\n\n\n\n dosazen goal "+mLevel.getGoal()+" pomoci score "+mPlayer.getScore() +" v levelu "+mLevel.getLevelNumber());
            if (mLevel.getLevelNumber() >= mMaxLevel) {
                shared.CFunctions.printDebug("\n\n\nUplne vitezstvi!!!");
                shared.CDialogs.win(mGameLayout);
                return;
            }
            wasStartCount = false;
            shared.CFunctions.printDebug("\n\n\nUpdatuju level");
            mPlayer.setScore(mLevel.getGoal());
            mLevel.updateLevel();
            shared.CFunctions.printDebug("\n\n\njsem v leveu " +mLevel.getLevelNumber() +" se score "+ mPlayer.getScore());
            wasStartCount = true;
            mGameLayout.dispose();
            mGameLayout = new CGameLayout(CFunctions.getVersion(), this);
            //mGameLayout.refreshLayout();
        }

    }

    public int getActualLevel() {
        return mLevel.getLevelNumber();
    }

    public int getHeight() {
        return mLevel.getHeight();
    }

    public int getWidth() {
        return mLevel.getWidth();
    }

    public int getTotalDiamonds() {
        return mLevel.getTotalDiamonds();
    }

    public CPlace GetItem(int x, int y) {
        return mLevel.GetItem(x, y);
    }

    public ArrayList<ArrayList<CPlace>> getItems() {
        return mLevel.getItems();
    }
}
