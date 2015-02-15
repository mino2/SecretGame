package logic;

import java.awt.Dimension;
import java.util.ArrayList;

public class CLevel {

    public int mLevelNumber; //which level is this?
    private int mWidth; //width of gameboard (number of columns)
    private int mHeight; //height of gameboard (number of rows)
    private ArrayList<Dimension> mWalls; // list of walls on gameboard
    private ArrayList<Dimension> mValuable; //list of valuable items to collect on gameboard

    public CLevel(int level, int w, int h, ArrayList<Dimension> wall, ArrayList<Dimension> values) {
        mLevelNumber = level;
        mWidth = w;
        mHeight = h;
        mWalls = wall;
        mValuable = values;
    }

    public static CLevel generateNewLevel(int lvl) {
        int levelNumber = lvl;
        int width;
        int height;
        ArrayList<Dimension> walls;
        ArrayList<Dimension> valuable;
        
        switch (lvl) {
            case 1:
                width = 5;
                height = 5;
                walls = new ArrayList<>();
                valuable = new ArrayList<>();
                break;
            case 2:
                width = 6;
                height = 6;
                walls = new ArrayList<>();
                walls.add(new Dimension(3, 3)); //jebne zed doprostred hraci plochy
                valuable = new ArrayList<>();
                break;
            default:
                width = 5;
                height = 5;
                walls = new ArrayList<>();
                valuable = new ArrayList<>();
                break;
        }

    return new CLevel(levelNumber, width, height, walls, valuable);
}

}
