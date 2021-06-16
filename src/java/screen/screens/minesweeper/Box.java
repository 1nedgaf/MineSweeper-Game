package screen.screens.minesweeper;

public class Box {

    public BoxPos pos;
    public boolean hidden;
    public boolean flag;
    public int INT;
    public boolean bomb;

    public Box(int x, int y) {
        INT = -1;
        this.hidden = true;
        pos = new BoxPos(x, y);
    }

    public int getPosX() {
        return this.pos.x;
    }
    public int getPosY() {
        return this.pos.y;
    }


}
