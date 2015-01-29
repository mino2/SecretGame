package diamondgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
public class CGameboard extends javax.swing.JFrame {

    private final int mWidth;
    private final int mHeight;
    private CPos mFirstActive;
    private CPos mSecondActive;
    private JPanel desktop;
    private static ArrayList<ArrayList<CDiamond>> items;
    private int totalDiamonds;
    private ArrayList<Color> mAllColors;
    private static final String version = "0.3";

    public CGameboard() {
        super.setTitle("Diamanty verze " + version);  //title setup
        mWidth = 15;
        mHeight = 10;
        initComponents();
        initGameboard();
    }

    public int fillBoard() {
        return 0;
    }

    public void destroyGem(CPos pos) {
        GetItem(pos).deleteMe();
    }

    public CDiamond GetItem(int x, int y){
    return items.get(x).get(y);
    }
    
    public CDiamond GetItem(CPos pos){
    return items.get(pos.getX()).get(pos.getY());
    }
    
    public void SetItem(int x, int y, CDiamond diamond){
        if(x>0 && y>0)
    items.get(x).set(y, diamond);
    }
    
    public void SetItem(CPos pos, CDiamond diamond){
        if(pos.getX() > 0 && pos.getY() > 0){
    items.get(pos.getX()).set(pos.getY(), diamond);
        }
    }
    
    public void swap(CDiamond first, CDiamond second) {
        if(CDiamondGame.DEBUG)System.out.println("SWAPPING :-)");
        
        CDiamond tmpGem = first;
        SetItem(first.getPos(), second);
        SetItem(second.getPos(), tmpGem);
        return;
    }

    public CDiamond generateRandDiamond() {
        Color Dcolor = mAllColors.get((int) (Math.random() * mAllColors.size()));
        CPos tmppos = new CPos(-2, -2);
        return new CDiamond(Dcolor, true, tmppos);
    }

    public void generateDiamond(int IDofDiamondType) {
        //to implement
    }

    public void fall(CPos pos) {
        int upper = pos.getY() - 1;
        for (; upper >= 0 && !GetItem(pos.getX(),upper).isValid; upper--) {
            //this finds first upper valid diamond
        }
        if (upper < 0) {//no valid gems above
            //generate new gem
            CDiamond tmp = generateRandDiamond();
            tmp.mPos.setXY(pos.getX(), pos.getY());
            //swap(items.get(pos.getX()).get(pos.getY()), tmp);
            SetItem(pos, tmp);
            System.out.println("falling new " + upper + " on " + pos.getY() + " ("+pos.getX()+")");
            return;
        } else {
            swap(items.get(pos.getX()).get(pos.getY()), items.get(pos.getX()).get(upper));//now the upper gem will be invalid
            System.out.println("falling " + upper + " on " + pos.getY() + " ("+pos.getX()+")");
            return;
        }
    }

    public void checkAll() { //projed vsechny odspoda, kdyz je nekde prazdno tak shod zezhora a projed nakonci znovu
        boolean changed = false;
        do {
            changed = false;
            for (int i = 0; i < items.size(); i++) {//from left to right
                for (int j = items.get(i).size()-1; j >= 0; j--) { //from bottom to top
                    if (!items.get(i).get(j).isValid) {
                        CPos pos = new CPos(i, j);
                        fall(pos);
                        changed = true;
                    }
                }
            }
        } while (changed);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
    }

    private void initColors() {
        mAllColors = new ArrayList<>();
        mAllColors.add(Color.red);
        mAllColors.add(Color.blue);
        mAllColors.add(Color.yellow);
        mAllColors.add(Color.green);
        mAllColors.add(Color.pink);
    }

    private void initGameboard() {

        mFirstActive  = new CPos(-1, -1);
        mSecondActive = new CPos(-1, -1);
        totalDiamonds = mWidth * mHeight;

        initColors();
        items = new ArrayList<ArrayList<CDiamond>>();
        CDiamond diamond;
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < mWidth; i++){ //initialize columns in the first row
        items.add(new ArrayList<CDiamond>());
        }
        
        for (int i = 0; i < totalDiamonds; i++) {
            diamond = generateRandDiamond();
            //diamond = new CDiamond(Color.BLUE, true, i); //various
            diamond.addActionListener(new ActionListenerDiamond());
            //   diamond.addFocusListener(new FocusListenerDiamond());
            //   diamond.addKeyListener(new KeyListenerDiamond());
            diamond.deselectMe();
            CPos pos;

            diamond.mPos.setXY(i % mWidth, i / mWidth);
            
            items.get(i % mWidth).add(diamond); //adding diamonds from left to right
            group.add(diamond);
        }

        //nastaveni layoutu
        setLayout(new BorderLayout());
        setSize(600, 600);
        setVisible(true);

        /*plocha = new JPanel();
         plocha.setLayout(new GridLayout(3, 3));
         add(plocha, BorderLayout.CENTER);

         plocha.setLayout(new GridLayout(3, 3));*/
        desktop = new JPanel();
        desktop.setLayout(new GridLayout(mHeight, mWidth));
        add(desktop, BorderLayout.CENTER);
        for (int i = 0; i < totalDiamonds; i++) {
            int x = i % mWidth;
            int y = i / mWidth;
            desktop.add(items.get(x).get(y));
            if(CDiamondGame.DEBUG){
            //items.get(x).get(y).draw();
                GetItem(x,y).drawPos(new CPos(x, y));
            }
        }

        desktop.setBorder(BorderFactory.createLineBorder(Color.black, 2));
   
        checkAll();
    }

    private class ActionListenerDiamond implements ActionListener {

        private ActionListenerDiamond() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CDiamond diamond = (CDiamond) e.getSource();

            System.out.println("pos("+diamond.getPos().x+","+diamond.getPos().y+") clicked");
            if (!diamond.mMobility) { // cant move this piece bro :-( You just cant move walls and laws
                return;
            }

            if (mFirstActive.getX() < 0 || mFirstActive.getY() < 0 || diamond.getPos() == mFirstActive) {
                deselectDiamond(mFirstActive);
                deselectDiamond(mSecondActive);
                selectDiamond(diamond.getPos());
                return;
            }

            if (isNeighbour(diamond.getPos(), mFirstActive)) {
                mSecondActive = diamond.getPos();
                boolean success1 = false;
                boolean success2 = false;
                ArrayList<CPos> toRemove = new ArrayList<>();
                swap(items.get(mFirstActive.getX()).get(mFirstActive.getY()), items.get(mSecondActive.getX()).get(mSecondActive.getY()));

                if (tryDiamond(mFirstActive)) {
                    success1 = true;
                }
                if (tryDiamond(mSecondActive)) {
                    success2 = true;
                }

                if (!success1 && !success2) {//bad move, return back
                    swap(items.get(mFirstActive.getX()).get(mFirstActive.getY()), items.get(mSecondActive.getX()).get(mSecondActive.getY()));
                    return;
                }

                if (success1) {
                    ArrayList<CPos> tmpToDelete = getNeighboursToDelete(mFirstActive);
                    toRemove.addAll(tmpToDelete);
                }
                if (success2) {
                    ArrayList<CPos> tmpToDelete = getNeighboursToDelete(mSecondActive);
                    toRemove.addAll(tmpToDelete);
                }
                deselectDiamond(mFirstActive);
                CleanDiamonds(toRemove);
                checkAll();
            } else {
                deselectDiamond(mFirstActive);
                selectDiamond(diamond.getPos());
            }
        }
    }

    private ArrayList<CPos> getNeighboursToDelete(CPos Active) {
        ArrayList<CPos> neighbours = new ArrayList<>();

        Color refColor = items.get(Active.getX()).get(Active.getY()).getColor();
        System.out.println("refColor je: " + refColor);
        boolean horMatch = false;
        boolean vertMatch = false;

        //find the most left diamond of the same color
        int left = Active.getX();
        while (left > 0 && items.get(left - 1).get(Active.getY()).getColor() == refColor) {
            System.out.println("porovnavam vlevo s: " + items.get(left - 1).get(Active.getY()).getColor());
            left--;
        }
        int mostLeft = left;
        int widthColor = 1;
        while ((left + 1) < mWidth  /*end of row*/ && items.get(left + 1).get(Active.getY()).getColor() == refColor) {
            left++;
            widthColor++;
        }
        int mostRight = left;

        //find the lowest diamond of the same color
        int up = Active.getY();
        while (up - 1 >= 0 && items.get(Active.getX()).get(up-1).getColor() == refColor) {
            System.out.println("porovnavam nahore s: " + items.get(Active.getX()).get(up-1).getColor());
            up --;
        }
        int mostUp = up;
        int heightColor = 1;
        while ((up +1) < mHeight && items.get(Active.getX()).get(up +1).getColor() == refColor) {
            up++;
            heightColor++;
        }
        int mostDown = up;

        /*add mostLeft to mostRight if widthColor >= 3*/
        if (widthColor >= 3) {
            horMatch = true;
            for (int i = mostLeft; i <= mostRight; i++) {
                CPos pos = new CPos(i, Active.getY());
                neighbours.add(pos);
            }
        }

        if (heightColor >= 3) {
            vertMatch = true;
            for (int j = mostUp; j <= mostDown; j ++) {
                CPos pos = new CPos(Active.getX(), j);
                neighbours.add(pos);
            }
        }
        if (vertMatch && horMatch) {//vertical and horizontal match
            for (int i = 0; i < neighbours.size(); i++) {
                if (neighbours.get(i) == Active) {
                    neighbours.remove(i); //there would be the active element twice
                    return neighbours;
                }
            }
        }
        return neighbours;
    }

    private void CleanDiamonds(ArrayList<CPos> positions) {
        for (int i = 0; i < positions.size(); i++) {
            //adding score and so on here//
            destroyGem(positions.get(i));
        }
    }

    private boolean isNeighbour(CPos first, CPos second) {
        if (Math.abs(first.getX() - second.getX()) == 1 && first.getY() == second.getY()) {
            return true;
        }
        if (Math.abs(first.getY() - second.getY()) == 1 && first.getX() == second.getX()) {
            return true;
        }
        return false;
    }

    private void selectDiamond(CPos pos) {
        if(pos.getX() < 0 || pos.getY() < 0){
        if(CDiamondGame.DEBUG) {System.out.println("SELECTING bad diamond bro");}
        return;
        }
        mFirstActive = pos;
        //items.get(diamondID).setBorder(BorderFactory.createLineBorder(Color.green, 4)); //change active diamond to green
        items.get(pos.getX()).get(pos.getY()).selectMe();
    }

    private void deselectDiamond(CPos pos) {
        if (mFirstActive.getX() >= 0) {
            items.get(mFirstActive.getX()).get(mFirstActive.getY()).deselectMe();
        }
        if (mSecondActive.getX() >= 0) {
            items.get(mSecondActive.getX()).get(mSecondActive.getY()).deselectMe();
        }
        
        if(pos.getX() >=0 && pos.getY() >=0){
        CPos p = new CPos(-1, -1);
        mFirstActive  = p;
        mSecondActive = p;
        //  items.get(diamondID).setBorder(BorderFactory.createEmptyBorder()); //change active diamond to green
        items.get(pos.getX()).get(pos.getY()).deselectMe();
        }
        return;
    }

    private boolean tryDiamond(CPos pos) {
        ArrayList<CPos> list = getNeighboursToDelete(pos);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    /*private class FocusListenerDiamond extends FocusAdapter {

     @Override
     public void focusGained(FocusEvent e) {
     super.focusGained(e);
     JBSudoku button = (JBSudoku) e.getSource();
     if (button.getNumber() == 0) {
     button.setBackground(Color.CYAN);
     } else {
     if (checkSudoku(button.id)) {
     button.setBackground(colorRight);
     } else {
     button.setBackground(colorWrong);
     }
     }       // if (!button.hasFocus()) button.requestFocus();
     Sudoku.activeButton = button.id;
     }

     @Override
     public void focusLost(FocusEvent e) {
     super.focusLost(e);
     JBSudoku button = (JBSudoku) e.getSource();
     button.setBackground(null);
     }
     }*/
}
