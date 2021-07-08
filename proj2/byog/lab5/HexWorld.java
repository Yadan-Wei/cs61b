package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 30;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /** compute the width of row i for a size s hexagon.
     * @param s the size of the hex.
     * @param i the row number where i = 0 is the bottom row.
     * @return
     *                 i    width     i     width              xOffset
     *      aaaa       7      4      s+s-1 s+2*(2s-1-(2s-1))     0
     *     aaaaaa      6      6      s+2   s+2*(2s-1-(2s-2))    -1
     *    aaaaaaaa     5      8      s+1   s+2*(2s-1-(2s-3))    -2
     *   aaaaaaaaaa    4     10      s     s+2*(s-1)            -3
     *   aaaaaaaaaa    3     10      s-1   s+2*(s-1)            -3
     *    aaaaaaaa     2      8      2     s+2*2                -2
     *     aaaaaa      1      6      1     s+2*1                -1
     *      aaaa       0      4      0     s+2*0                 0
     */
    public static int hexRowWidth(int s, int i) {
        int para = i;
        if (i >= s) {
            para = 2*s - 1 - para;
        }
        return s + 2*para;
    }
    /**
     * Computes relative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position,
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return row offset.
     */
    public static int hexRowOffset(int s, int i) {
        int para = i;
        if (i >= s) {
            para = 2*s - 1 - para;
        }
        return -para;
    }
    /** a helper class to record coordinate.*/
    private static class Position {
        int x;
        int y;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32,RANDOM);
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int yi = 0; yi < 2*s; yi += 1) {
            int thisRowY = p.y + yi;
            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);
            int rowWidth = hexRowWidth(s, yi);
            addRow(world, rowStartP, rowWidth, t);
        }
    }

    /** calculate start position based on width of world
     * and size of hexagon.
     * the column number of start position is 0.
     *   bbbbbbbaaaccccccc
     *    bbbbbaaaaaccccc
     *     bbbaaaaaaaccc
     *        aaaaaaa
     *         aaaaa
     *         (a)aa
     *  a in parenthesis is the start position,
     *  a hexagon's column number is 0,
     *  b hexagon's column number is -1,
     *  c hexagon's column number is 1.
     * @param width the width of the world
     * @param s the size of the hexagon.
     * @return the start position.
     */

    public static Position startP(int width, int s) {
        int startX = width/2 - s/2;
        return new Position(startX, 0);
    }

    /** Get the maximum hexagons in column 0.
     * based on the height of the world. e.g.
     * we can draw 50/8 = 6 hexagons with size 4 in column 0 in a 50 height world.
     * @param height the height of the world.
     * @param s the size of a single hexagon.
     * @return the maximum number.
     */
    public static int maxColumnHex(int height, int s) {
        return height/(2*s);
    }

    /** get the max hexagon number a column can draw
     * the max number must be in column 0,
     * then column -1 hex number is max number - 1
     * it won't be negative cause we calculate the max columns
     * a world can have.
     * @param column the column number
     * @param maxColumnHex the max hex number in column 0.
     * @return the hex number we can draw in a column.
     */
    public static int hexNum(int column, int maxColumnHex) {
        if (column == 0) {
            return maxColumnHex;
        }
        return maxColumnHex - java.lang.Math.abs(column);
    }

    @Test
    public void testHexNum() {
        assertEquals(4, hexNum(-1,5));
        assertEquals(3, hexNum(2,5));
    }


    /** get the max hex number in a row
     * assume we can have n hex in a row
     * the max width of a hex is 3*s - 2, the min width of a hex is s
     * in a row we always have b max width and b-1 min width
     * so we can get b*(3*s -2)+(b-1)*s <= width
     * so the hex number = 2b - 1 <= (width + 1 - s )/(2 * s - 1)
     * at the same time the hex number shouldn't exceed 2*(height/2*s)-1
     * to make sure we have at least one hex in a column
     * also, it must be an odd number to be symmetric.
     * @param width the width of the world
     * @param height the height of the world
     * @param s the size of the hexagon.
     * @return the max hex in a row.
     */
    public static int maxRowHex(int width,int height, int s) {
        int maxRowHex = (width + 1 - s )/(2 * s - 1);
        if (maxRowHex < 0) {
            return 0;
        }
        if (maxRowHex%2 == 0) {
            maxRowHex = maxRowHex - 1;
        }
        return java.lang.Math.min(maxRowHex, height/s-1);
    }


    /** get each column start position
     * based on column 0 start position.
     * @param p column 0 start position.
     * @param s hex size
     * @param column column index, the middle column index is 0
     *               left is negative, right is positive.
     * @return column start position.
     */
    public static Position columnStartP(Position p, int s, int column) {
        int columnX = p.x + column*(2 * s - 1);
        int columnY = p.y + java.lang.Math.abs(column)*s;
        return new Position(columnX, columnY);
    }

    /** add hexagon column by column
     * @param world the world need to fill
     * @param p column start position
     * @param hexNum hex number we can draw in this column
     * @param s hex size
     */
    public static void addColumnHexagon(TETile[][] world,Position p,
                                        int hexNum, int s) {
        for (int i = 0; i < hexNum; i += 1) {
            addHexagon(world, p, s, randomTile());
            p = new Position(p.x, p.y + 2*s);
        }
    }

    @Test
    public void testColumnStartP() {
        Position startP = new Position(20, 0);
        Position column1StartP = new Position(13, 4);
        assertEquals(column1StartP.x, columnStartP(startP, 4, -1).x);
        assertEquals(column1StartP.y, columnStartP(startP, 4, -1).y);
        Position column2StartP = new Position(6,8);
        assertEquals(column2StartP.x, columnStartP(startP, 4, -2).x);
        assertEquals(column2StartP.y, columnStartP(startP, 4, -2).y);
    }

    @Test
    public void testStartP() {
        assertEquals(23, startP(50, 4).x);
        assertEquals(28, startP(60, 5).x);
        assertEquals(29, startP(63, 5).x);
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(8);
        return switch (tileNum) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.FLOWER;
            case 2 -> Tileset.MOUNTAIN;
            case 3 -> Tileset.FLOOR;
            case 4 -> Tileset.GRASS;
            case 5 -> Tileset.SAND;
            case 6 -> Tileset.WATER;
            case 7 -> Tileset.TREE;
            default -> Tileset.NOTHING;
        };
    }
    
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        int s = 2;
    /* to avoid null tile error, initialize all space first.*/
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int maxRowHex = maxRowHex(WIDTH, HEIGHT, s);
        int maxColumnHex = maxColumnHex(HEIGHT, s);
        Position startP = startP(WIDTH, s);

        int column = -maxRowHex/2;
        int hexNum = hexNum(column, maxColumnHex);
        Position columnStartP = columnStartP(startP, s, column);

        while (column <= maxRowHex/2) {
            addColumnHexagon(world, columnStartP, hexNum, s);
            column = column + 1;
            hexNum = hexNum(column, maxColumnHex);
            columnStartP = columnStartP(startP, s, column);
        }
        ter.renderFrame(world);
    }
}
