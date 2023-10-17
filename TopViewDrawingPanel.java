

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class TopViewDrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

    DrawingGUI gui;

    int sleeperDisplayHeight = 80;
    int sleeperDisplayWidth = 8;
    int paddingX = 50;
    int paddingY = 50;
    int currentWindowHeight;
    int currentWindowWidth;

    ArrayList<ArrayList<Polygon>> rectangles;
    int hoveredWallSegment = -1;
    private DrawingPanel drawingPanel;

    public TopViewDrawingPanel(DrawingGUI gui) {
        this.gui = gui;
        setBackground(Color.GRAY);
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentWindowHeight = this.getSize().height;
        currentWindowWidth = this.getSize().width;
        rectangles = new ArrayList<>();
        drawWall(g, paddingX, paddingY, gui.wall);
        revalidate(); // This makes the scrollpane resize
    }

    /**
     * Draws an individual bay
     *
     * @param g
     * @param positionX
     * @param positionY
     * @param angle
     * @return point of last x and y position of last bay
     */
    protected Point drawBay(Graphics g, int positionX, int positionY, int angle) {
        angle = 360 - angle;
        Polygon p = new Polygon();
        int lastX = positionX;
        int lastY = positionY;
        p.addPoint(lastX, lastY);
        angle -= 90;
        lastX += sleeperDisplayWidth * Math.cos(Math.toRadians(angle));
        lastY += sleeperDisplayWidth * Math.sin(Math.toRadians(angle));
        p.addPoint(lastX, lastY);
        angle -= 90;
        lastX += sleeperDisplayHeight * Math.cos(Math.toRadians(angle));
        lastY += sleeperDisplayHeight * Math.sin(Math.toRadians(angle));
        p.addPoint(lastX, lastY);
        angle -= 90;
        lastX += sleeperDisplayWidth * Math.cos(Math.toRadians(angle));
        lastY += sleeperDisplayWidth * Math.sin(Math.toRadians(angle));
        p.addPoint(lastX, lastY);

        Color prevColor = g.getColor();
        g.fillPolygon(p);
        g.setColor(Color.WHITE);
        g.drawPolygon(p);
        g.setColor(prevColor);

        rectangles.get(rectangles.size() - 1).add(p);

        return new Point(lastX, lastY);
    }

    /**
     * Draws an individual wall segment consisting of bays
     *
     * @param g
     * @param positionX
     * @param positionY
     * @param angle
     * @param numberOfBays
     * @return point of last x and y position of last bay
     */
    protected Point drawWallSegment(Graphics g, int positionX, int positionY, int angle, int numberOfBays) {
        //draw all bays in the wall segment
        rectangles.add(new ArrayList<Polygon>());
        for (int i = 0; i < numberOfBays; i++) {
            Point x = drawBay(g, positionX, positionY, angle);
            positionX = (int) x.getX();
            positionY = (int) x.getY();
        }
        return new Point(positionX, positionY);
    }

    /**
     * Draws the wall as a whole which consists of wall segments
     *
     * @param g
     * @param positionX
     * @param positionY
     * @param wall
     */
    protected void drawWall(Graphics g, int positionX, int positionY, Wall wall) {
        //draw all wall segments in the wall
        int maxX = -1;
        int maxY = -1;
        for (int i = 0; i < wall.getNumberOfSegments(); i++) {
            final WallSegment segment = gui.wall.getSegment(i);
            if (drawingPanel.frontPanel.activeWallSegment == segment) {
                g.setColor(Color.RED);
            } else if (i == hoveredWallSegment) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(new Color(0, 0, 200));
            }
            Point x = drawWallSegment(g, positionX, positionY, segment.angle, segment.getNumberOfBays());
            positionX = (int) x.getX();
            positionY = (int) x.getY();

            //resises the panel to allow the jscrollpane to work properly
            //will also increse the size if the drawing encroaches above or to 
            //the left of the original starting point
            if (positionX > maxX) {
                maxX = positionX;
            }
            if (positionY > maxY) {
                maxY = positionY;
            }
            if (positionX < 0) {
                paddingX = (Math.abs(positionX) + paddingX + 50);
            }
            if (positionY < 0) {
                paddingY = (Math.abs(positionY) + paddingY + 50);
            }
        }
        setPreferredSize(new Dimension(maxX + 50, maxY + 50));
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = 0; j < rectangles.get(i).size(); j++) {
                if (rectangles.get(i).get(j).contains(e.getPoint())) {
                    if (drawingPanel != null) {
                        drawingPanel.setWallSegment(i);
                        repaint();
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hoveredWallSegment = -1;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean hoveringNow = false;
        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = 0; j < rectangles.get(i).size(); j++) {
                if (rectangles.get(i).get(j).contains(e.getPoint())) {
                    hoveringNow = true;
                    if (hoveredWallSegment != i) {
                        hoveredWallSegment = i;
                        repaint();
                    }
                }
            }
        }
        if (hoveredWallSegment != -1 && !hoveringNow) {
            hoveredWallSegment = -1;
            repaint();
        }
    }

    void setCallback(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }
}
