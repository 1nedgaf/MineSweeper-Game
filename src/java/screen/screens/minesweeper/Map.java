package screen.screens.minesweeper;

import screen.screens.MineSweeper;
import utils.MathHelper;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    public int[] points = new int[] {
            -1, -1,
            -1, 0,
            -1, 1,
            0, -1,
            0, 1,
            1, -1,
            1, 0,
            1, 1
    };
    private ArrayList<Box> boxList;
    public Map() {
        this.boxList = new ArrayList<>();
    }
    public void addBox(Box box) {
        this.boxList.add(box);
    }

    public ArrayList<Box> getBoxList() { return this.boxList; }


    public Box getBoxByPosition(int x, int y) {
        for(Box box : boxList) {
            if(box.getPosX() == x && box.getPosY() == y) {
                return box;
            }
        }
        return null;
    }

    public void boom(int x, int y) {
        Box start = this.getBoxByPosition(x, y);
        float boomPercent = 1F;
        if(start != null) {
            ArrayList<BoxPos> toBoom = new ArrayList();
            toBoom.add(start.pos);
            while(!toBoom.isEmpty()) {
                BoxPos pos = toBoom.get(0);
                Box box = this.getBoxByPosition(pos.x, pos.y);
                box.hidden = false;
                for(int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0:
                            if(new Random().nextFloat() < boomPercent) {
                                Box targetBox = this.getBoxByPosition(pos.x - MineSweeper.boxSize, pos.y);
                                if(targetBox != null && targetBox.hidden) {
                                    toBoom.add(new BoxPos(targetBox.getPosX(), targetBox.getPosY()));
                                }
                            }
                            break;
                        case 1:
                            if(new Random().nextFloat() < boomPercent) {
                                Box targetBox = this.getBoxByPosition(pos.x + MineSweeper.boxSize, pos.y);
                                if(targetBox != null && targetBox.hidden) {
                                    toBoom.add(new BoxPos(targetBox.getPosX(), targetBox.getPosY()));
                                }
                            }
                            break;
                        case 2:
                            if(new Random().nextFloat() < boomPercent) {
                                Box targetBox = this.getBoxByPosition(pos.x, pos.y - MineSweeper.boxSize);
                                if(targetBox != null && targetBox.hidden) {
                                    toBoom.add(new BoxPos(targetBox.getPosX(), targetBox.getPosY()));
                                }
                            }
                            break;
                        case 3:
                            if(new Random().nextFloat() < boomPercent) {
                                Box targetBox = this.getBoxByPosition(pos.x, pos.y + MineSweeper.boxSize);
                                if(targetBox != null && targetBox.hidden) {
                                    toBoom.add(new BoxPos(targetBox.getPosX(), targetBox.getPosY()));
                                }
                            }
                            break;
                    }
                }
                boomPercent -= 0.05F;
                toBoom.remove(0);
            }
        }

        for(Box box : this.boxList) {
            if(!box.hidden) {
                continue;
            }
            for (int i = 0; i < points.length; i++) {
                int dx = points[i]  * MineSweeper.boxSize;
                int dy = points[++i] * MineSweeper.boxSize;
                Box pointBox = this.getBoxByPosition(box.getPosX() + dx, box.getPosY() + dy);
                if(pointBox != null && !pointBox.hidden && pointBox.INT == -1) {
                    this.openBox(box);
                }
            }
        }


    }

    public void openBox(Box box) {
        generateBomb(box);
        int count = countBombs(box);
        if(count == 0) {
            generateBomb(box);
            count = countBombs(box);
        }
        box.INT = count;
        box.hidden = false;
    }

    public void generateBomb(Box box) {
        for (int i = 0; i < points.length; i++) {
            int dx = points[i]  * MineSweeper.boxSize;
            int dy = points[++i] * MineSweeper.boxSize;
            Box pointBox = this.getBoxByPosition(box.getPosX() + dx, box.getPosY() + dy);
            if(pointBox == null) continue;
            boolean c = true;
            for (int i1 = 0; i1 < points.length; i1++) {
                int x = points[i1]  * MineSweeper.boxSize;
                int y = points[++i1] * MineSweeper.boxSize;
                Box check = this.getBoxByPosition(pointBox.getPosX() + x, pointBox.getPosY() + y);
                if(check != null && !check.hidden) {
                    c = false;
                    break;
                }
            }
            if(c && new Random().nextFloat() < 0.2F) {
                pointBox.bomb = true;
            }
        }
    }

    public int countBombs(Box box) {
        int count = 0;
        for (int i = 0; i < points.length; i++) {
            int dx = points[i] * MineSweeper.boxSize;
            int dy = points[++i] * MineSweeper.boxSize;
            Box pointBox = this.getBoxByPosition(box.getPosX() + dx, box.getPosY() + dy);
            if (pointBox != null && pointBox.bomb) {
                count++;
            }
        }
        return count;
    }



}
