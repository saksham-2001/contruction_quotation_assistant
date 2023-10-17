
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DrawingGUI extends JFrame {

    public Wall wall;
    final static String[] difficultyOptions = {"Normal", "Sandy", "Limestone", "Bluestone"};
    private QuotationPanel quotationPanel;
    private JTextField identifier;

    public static void main(String[] args) {
        DrawingGUI gui = new DrawingGUI();

        NewWallSegmentPanel inputPanel = new NewWallSegmentPanel(false);
        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add a wall segment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            gui.wall.addWallSegment(new WallSegment(inputPanel.getLength(), inputPanel.getStartHeight(), inputPanel.getEndHeight(), inputPanel.getAngle()));
            gui.pack();
            gui.setVisible(true);
            gui.quotationPanel.drawQuote();
        } else {
            gui.dispose();
        }
    }

    public DrawingGUI() throws HeadlessException {
        super("Construction Quotation Assistant");
        ImageIcon img = new ImageIcon("logo.png");
        setIconImage(img.getImage());
        wall = new Wall(false, false, Difficulty.normal);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container c = getContentPane();
        setLayout(new BorderLayout());
        final DrawingPanel drawingPanel = new DrawingPanel(this);
        c.add(drawingPanel);

        JPanel widgetsPanel = new JPanel();
        widgetsPanel.setLayout(new BoxLayout(widgetsPanel, BoxLayout.Y_AXIS));

        //Panel for the add segment button
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
        controlsPanel.setBorder(new EmptyBorder(5, 10, 0, 10));
        JButton addSegmentButton = new JButton("Add segment");
        JLabel identifierLabel = new JLabel("Job identifier");
        controlsPanel.add(identifierLabel);
        controlsPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        //the job identifier
        identifier = new JTextField(20);
        identifier.setMaximumSize(identifier.getPreferredSize());
        identifier.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = identifier.getText();
                String title = "Construction Quotation Assistant";
                if (text.length() > 0) {
                    title = "Construction Quotation Assistant - " + text;
                }
                wall.setIdentifier(text);
                DrawingGUI.this.setTitle(title);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        controlsPanel.add(identifier);
        controlsPanel.add(Box.createHorizontalGlue());
        controlsPanel.add(addSegmentButton);
        widgetsPanel.add(controlsPanel);

        //Outer panel for the option selections
        JPanel outerOptionsPanel = new JPanel();
        outerOptionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        JPanel optionsPanel = new JPanel();

        //Inner panel for the option selections
        optionsPanel.setMaximumSize(new Dimension(400, 150));
        optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        optionsPanel.setLayout(new GridLayout(4, 1));
        JCheckBox local = new JCheckBox("Is the wall to be built locally?");
        optionsPanel.add(local, 0);
        JCheckBox access = new JCheckBox("Is there adequate access to the site?");
        optionsPanel.add(access, 1);
        JLabel difficultyLabel = new JLabel("What is the difficulty to the site?");
        optionsPanel.add(difficultyLabel, 2);
        JComboBox difficulty = new JComboBox(difficultyOptions);
        optionsPanel.add(difficulty, 3);
        outerOptionsPanel.add(optionsPanel);

        //option selections listener
        ActionListener settingsListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrawingGUI.this.wall.setLocal(local.isSelected());
                DrawingGUI.this.wall.setAccess(access.isSelected());
                DrawingGUI.this.wall.setDifficulty((String) difficulty.getSelectedItem());
                quotationPanel.revalidate();
                quotationPanel.drawQuote();
            }
        };
        local.addActionListener(settingsListener);
        access.addActionListener(settingsListener);
        difficulty.addActionListener(settingsListener);

        //Outer panel for the quotation
        JPanel outerMostQuotationPanel = new JPanel();
        outerMostQuotationPanel.setBorder(BorderFactory.createTitledBorder("Quote"));
        quotationPanel = new QuotationPanel(this);
        outerMostQuotationPanel.add(quotationPanel);
        widgetsPanel.add(outerOptionsPanel);
        widgetsPanel.add(outerMostQuotationPanel);
        c.add(widgetsPanel, BorderLayout.WEST);

        addSegmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewWallSegmentPanel inputPanel = new NewWallSegmentPanel(true);
                int result = JOptionPane.showConfirmDialog(DrawingGUI.this, inputPanel, "Add a wall segment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    wall.addWallSegment(new WallSegment(inputPanel.getLength(), inputPanel.getStartHeight(), inputPanel.getEndHeight(), inputPanel.getAngle()));
                    drawingPanel.topPanel.repaint();
                    quotationPanel.revalidate();
                    quotationPanel.drawQuote();
                }
            }
        });
    }

    public void updateQuotationPanel() {
        quotationPanel.revalidate();
        quotationPanel.drawQuote();
    }
}
