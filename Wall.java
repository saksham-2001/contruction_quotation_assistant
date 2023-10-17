
import java.util.ArrayList;

/**
 * A Wall is composed of WallSegments
 */
public class Wall {

    private boolean isLocal;
    private boolean hasAccess;
    private Difficulty difficulty;
    private String identifier;

    /**
     *
     * @param local true if local, false otherwise
     * @param access true if adequate access, false otherwise
     * @param difficulty not difficult, sandy conditions, limestone, or
     * bluestone
     */
    public Wall(boolean local, boolean access, Difficulty difficulty) {
        this.isLocal = local;
        this.hasAccess = access;
        this.difficulty = difficulty;
    }

    ArrayList<WallSegment> wallSegments = new ArrayList<>();

    public void addWallSegment(WallSegment wallSegment) {
        wallSegments.add(wallSegment);
    }

    public int getSleepersInWall() {
        int total = 0;
        for (WallSegment segment : wallSegments) {
            total += segment.getSleepersInWallSegment();
        }
        return total;
    }

    public int getNumberOfSegments() {
        return wallSegments.size();
    }

    public WallSegment getSegment(int segmentNumber) {
        return wallSegments.get(segmentNumber);
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    /**
     * @return String yes if local, no if not local
     */
    public String getLocal() {
        if (isLocal) {
            return "yes";
        }
        return "no";
    }

    /**
     * @return Boolean true if is local, false otherwise
     */
    public Boolean getLocalBool() {
        return isLocal;
    }

    public void setAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }

    /**
     * @return String - "yes" if hasAccess is true, "no" otherwise
     */
    public String getAccess() {
        if (hasAccess) {
            return "yes";
        }
        return "no";
    }

    /**
     * @return Boolean - true if Access is normal, false otherwise
     */
    public Boolean getAccessBool() {
        return hasAccess;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     *
     * @return String "normal, sandy, limestone, or bluestone"
     */
    public String getDifficulty() {

        return difficulty.toString();
    }

    public void setDifficulty(String difficulty) {
        Difficulty d = Difficulty.normal;
        switch (difficulty) {
            case "Sandy":
                d = Difficulty.sandy;
                break;
            case "Limestone":
                d = Difficulty.limestone;
                break;
            case "Bluestone":
                d = Difficulty.bluestone;
                break;
        }
        this.difficulty = d;
    }


    public void setIdentifier(String text) {
        identifier = text;
    }

}


