

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class FrontViewDrawingPanel extends JPanel implements MouseListener, MouseMotionListener {

    int sleeperDisplayHeight = 20;
    int sleeperDisplayWidth = 80;
    int currentWindowHeight;
    int currentWindowWidth;
    int paddingX = 50;
    int paddingY = 50;
    int maxHeight, maxWidth;

    WallSegment activeWallSegment;

    ArrayList<ArrayList<Rectangle>> rectangles;

    int hoveredWallBay = -1;
    Point dragPoint;
    int dragBay;

    DrawingGUI gui;

    public FrontViewDrawingPanel(DrawingGUI gui) {
        this.gui = gui;
        setBackground(Color.GRAY);
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
    }

    public void setWallSegment(WallSegment wallSegment) {
        activeWallSegment = wallSegment;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (activeWallSegment == null) {
            setWallSegment(gui.wall.getSegment(0));
        }
        rectangles = new ArrayList<>();
        drawWall(g, activeWallSegment.bayHeights);
        revalidate();
    }

    //draw each sleeper in the bay
    private void drawBay(Graphics g, int pos, int height) {

        for (int i = 0; i < height; i++) {
            Color prevColor = g.getColor();
            //make the last white rectangle smaller so the black border goes to top
            if (i == height - 1) {
                g.fillRect(((sleeperDisplayWidth + 1) * pos) + paddingX,
                        (currentWindowHeight - (i * sleeperDisplayHeight)) - (paddingY - 1),
                        sleeperDisplayWidth, sleeperDisplayHeight - 1);
            } else {
                g.fillRect(((sleeperDisplayWidth + 1) * pos) + paddingX,
                        (currentWindowHeight - (i * sleeperDisplayHeight)) - paddingY,
                        sleeperDisplayWidth, sleeperDisplayHeight);
            }
            //draw a black outline.
            g.setColor(Color.BLACK);
            Rectangle p = new Rectangle(((sleeperDisplayWidth + 1) * pos) + paddingX,
                    (currentWindowHeight - (i * sleeperDisplayHeight)) - paddingY, sleeperDisplayWidth,
                    sleeperDisplayHeight);

            g.drawRect(((sleeperDisplayWidth + 1) * pos) + paddingX,
                    (currentWindowHeight - (i * sleeperDisplayHeight)) - paddingY,
                    sleeperDisplayWidth, sleeperDisplayHeight);

            rectangles.get(rectangles.size() - 1).add(p);

            //keep track of maximum height and width required to display image
            if ((i * sleeperDisplayHeight) > maxHeight) {
                maxHeight = (i * sleeperDisplayHeight);
            }
            if (((sleeperDisplayWidth + 1) * pos) + paddingX > maxWidth) {
                maxWidth = ((sleeperDisplayWidth + 1) * pos);
            }
            g.setColor(prevColor);
        }
    }

    private void drawWall(Graphics g, ArrayList<Integer> bayHeights) {
        //reset the maximum width and height required to draw wall
        maxHeight = 0;
        maxWidth = 0;
        currentWindowHeight = this.getSize().height;
        currentWindowWidth = this.getSize().width;

        //draw each bay in the wall
        for (int i = 0; i < bayHeights.size(); i++) {
            rectangles.add(new ArrayList<Rectangle>());
            if (i == hoveredWallBay) {
                g.setColor(Color.PINK);
            } else {
                g.setColor(Color.WHITE);
            }
            drawBay(g, i, bayHeights.get(i));
        }
        setPreferredSize(new Dimension(maxWidth + 150, maxHeight + 100));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = 0; j < rectangles.get(i).size(); j++) {
                if (rectangles.get(i).get(j).contains(e.getPoint())) {
                    if (dragBay != i) {
                        dragBay = i;
                        repaint();
                    }
                }
            }
        }
        dragPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragPoint = null;
        dragBay = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        hoveredWallBay = -1;
        repaint();
    }

    //change height of bays
    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragBay != -1) {
            if (e.getPoint().y > dragPoint.y + 20 && activeWallSegment.bayHeights.get(dragBay) > 1) {
                dragPoint.y += 20;
                activeWallSegment.bayHeights.set(dragBay, activeWallSegment.bayHeights.get(dragBay) - 1);
                repaint();
                gui.updateQuotationPanel();
            } else if (e.getPoint().y < dragPoint.y - 20) {
                dragPoint.y -= 20;
                activeWallSegment.bayHeights.set(dragBay, activeWallSegment.bayHeights.get(dragBay) + 1);
                repaint();
                gui.updateQuotationPanel();
            }
        }
    }

    //highlights wall on mouse hover and changes mouse cursor
    @Override
    public void mouseMoved(MouseEvent e) {
        boolean hoveringNow = false;
        for (int i = 0; i < rectangles.size(); i++) {
            for (int j = 0; j < rectangles.get(i).size(); j++) {
                if (rectangles.get(i).get(j).contains(e.getPoint())) {
                    hoveringNow = true;
                    setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    if (hoveredWallBay != i) {
                        hoveredWallBay = i;
                        repaint();
                    }
                    break;
                }
            }
        }
        if (hoveredWallBay != -1 && !hoveringNow) {
            setCursor(Cursor.getDefaultCursor());
            hoveredWallBay = -1;
            repaint();
        }
    }
}
