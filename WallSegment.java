

import java.util.ArrayList;

/**
 * A WallSegment is made up of bays. The number of bays is determined by the
 * length.
 */
public class WallSegment {

    public ArrayList<Integer> bayHeights;
    public int angle = 180;

    /**
     * Concrete sleepers are 2m in length and 0.2m in height
     *
     * @param length length of the wall in metres
     * @param startHeight height of the start of the wall in metres
     * @param endHeight height of the end of the wall in metres
     * @param angle default 180, angle of join to the previous wall
     */
    public WallSegment(int length, double startHeight, double endHeight, int angle) {
        //init arraylists
        bayHeights = new ArrayList<>();
        double diff;
        double theta;
        double x;
        this.angle = angle;

        //run loop to set the height of bays.
        //the height of the bays is defined by the height of the top left hand side of the topmost sleeper
        int indiBayHeight = 0;

        if (startHeight == endHeight) {
            for (int i = 0; i < length; i += 2) {
                indiBayHeight = (int) Math.round(startHeight / 0.2);
                bayHeights.add(indiBayHeight);
            }
        } else if (startHeight < endHeight) {
            //find difference
            diff = endHeight - startHeight;
            //find value for theta
            theta = Math.atan(diff / (length - 2));
            x = 0;
            for (int i = 0; i < length; i += 2) {
                x = i * Math.tan(theta);
                indiBayHeight = (int) Math.round((x + startHeight) / 0.2);
                bayHeights.add(indiBayHeight);
            }
        } else if (startHeight > endHeight) {
            //find difference
            diff = startHeight - endHeight;
            //find value for theta
            theta = (Math.atan(diff / (length - 2)));
            x = 0;
            for (int i = length - 2; i < length && i >= 0; i -= 2) {

                x = i * Math.tan(theta);
                indiBayHeight = (int) Math.round((x + endHeight) / 0.2);
                bayHeights.add(indiBayHeight);
            }
        }
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getLength() {
        return bayHeights.size() * 2;
    }

    public int getNumberOfBays() {
        return bayHeights.size();
    }

    public int getHeight(int bay) {
        return bayHeights.get(bay);
    }

    public int getSleepersInWallSegment() {
        int total = 0;
        for (Integer height : bayHeights) {
            total += height;
        }
        return total;
    }

}
