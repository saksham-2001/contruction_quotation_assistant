

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DrawingPanel extends JPanel {

    //seperate panels for the top view and front view.
    TopViewDrawingPanel topPanel;
    FrontViewDrawingPanel frontPanel;
    private final int SCROLLSPEED = 20;

    DrawingGUI gui;

    public DrawingPanel(DrawingGUI gui) {
        this.gui = gui;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(720, 600));
        setBackground(Color.GRAY);

        //outermost panel
        JPanel outerContainerPanel = new JPanel();
        outerContainerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //upper and lower panels
        JPanel upperContainerPanel = new JPanel();
        upperContainerPanel.setLayout(new BorderLayout());
        JPanel lowerContainerPanel = new JPanel();
        lowerContainerPanel.setLayout(new BorderLayout());

        topPanel = new TopViewDrawingPanel(gui);
        frontPanel = new FrontViewDrawingPanel(gui);

        JPanel tvPanel = new JPanel();
        tvPanel.setBackground(Color.GRAY);
        tvPanel.add(new JLabel("Top View"));

        JPanel fvPanel = new JPanel();
        fvPanel.setBackground(Color.GRAY);
        fvPanel.add(new JLabel("Front View  (Red wall segment only)"));

        upperContainerPanel.add(tvPanel, BorderLayout.NORTH);
        JScrollPane topViewScrollPane = new JScrollPane(topPanel);
        topViewScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);
        topViewScrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLLSPEED);
        topViewScrollPane.setBorder(null);
        upperContainerPanel.add(topViewScrollPane, BorderLayout.CENTER);
        lowerContainerPanel.add(fvPanel, BorderLayout.NORTH);
        JScrollPane frontViewScrollPane = new JScrollPane(frontPanel);
        frontViewScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);
        frontViewScrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLLSPEED);
        frontViewScrollPane.setBorder(null);
        lowerContainerPanel.add(frontViewScrollPane, BorderLayout.CENTER);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = java.awt.GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        outerContainerPanel.add(upperContainerPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = java.awt.GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        outerContainerPanel.add(lowerContainerPanel, c);

        this.add(outerContainerPanel);
        topPanel.setCallback(this);
    }

    public void ShowFrontPanel() {
        this.add(frontPanel);
        this.revalidate();
    }

    public void HideFrontPanel() {
        this.remove(frontPanel);
        this.revalidate();
    }

    public void setWallSegment(int i) {
        frontPanel.setWallSegment(gui.wall.getSegment(i));
    }
}
