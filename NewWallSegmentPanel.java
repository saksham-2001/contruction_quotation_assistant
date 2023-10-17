

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class NewWallSegmentPanel extends JPanel {

    boolean ignoreNextChange = false;

    public NewWallSegmentPanel(boolean hasAngle) {
        if (!hasAngle) {
            setLayout(new GridBagLayout());
        } else {
            setLayout(new GridBagLayout());
        }
        GridBagConstraints c = new GridBagConstraints();

        //////// Length //////////
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel("Length (m)"), c);

        c.gridx = 1;
        c.gridy = 0;
        lengthSlider = new JSlider(2, 50, 10);
        lengthSlider.setName("Length");
        add(lengthSlider, c);

        c.gridx = 2;
        c.gridy = 0;
        lengthSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 50, 2));
        lengthSpinner.setPreferredSize(new Dimension(60, 22));
        add(lengthSpinner, c);

        /////// Start //////////
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel("Start height (m)"), c);

        c.gridx = 1;
        c.gridy = 1;
        startHeightSlider = new JSlider(0, 10 * 10, 1 * 10);
        startHeightSlider.setName("Start height");
        add(startHeightSlider, c);

        c.gridx = 2;
        c.gridy = 1;
        startHeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 0.2));
        startHeightSpinner.setPreferredSize(new Dimension(60, 22));
        add(startHeightSpinner, c);

        ///////// End ////////
        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel("End height (m)"), c);

        c.gridx = 1;
        c.gridy = 2;
        endHeightSlider = new JSlider(0, 10 * 10, 2 * 10);
        endHeightSlider.setName("End height");
        add(endHeightSlider, c);

        c.gridx = 2;
        c.gridy = 2;
        endHeightSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 10, 0.2));
        endHeightSpinner.setPreferredSize(new Dimension(60, 22));
        add(endHeightSpinner, c);

        angleSlider = new JSlider(0, 360 * 10, 180 * 10);
        angleSlider.setName("Angle");
        angleSpinner = new JSpinner(new SpinnerNumberModel(180, 0, 360, 1));
        angleSpinner.setPreferredSize(new Dimension(60, 22));

        if (hasAngle) {
            c.gridx = 0;
            c.gridy = 3;
            add(new JLabel("Angle"), c);

            c.gridx = 1;
            c.gridy = 3;
            add(angleSlider, c);

            c.gridx = 2;
            c.gridy = 3;
            add(angleSpinner, c);
        }

        ChangeListener spinnerChangeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (ignoreNextChange) {
                    ignoreNextChange = false;
                    return;
                }
                ignoreNextChange = true;
                lengthSlider.setValue((Integer) lengthSpinner.getValue());
                ignoreNextChange = true;
                try {
                    startHeightSlider.setValue((int) (Math.round((Integer) startHeightSpinner.getValue()) * 10));
                } catch (ClassCastException ex) {
                    startHeightSlider.setValue((int) (Math.round((Double) startHeightSpinner.getValue()) * 10));
                }
                ignoreNextChange = true;
                try {
                    endHeightSlider.setValue((int) (Math.round((Integer) endHeightSpinner.getValue()) * 10));
                } catch (ClassCastException ex) {
                    endHeightSlider.setValue((int) (Math.round((Double) endHeightSpinner.getValue()) * 10));
                }
                ignoreNextChange = true;
                angleSlider.setValue((Integer) angleSpinner.getValue() * 10);
                ignoreNextChange = false;
            }
        };
        lengthSpinner.addChangeListener(spinnerChangeListener);
        startHeightSpinner.addChangeListener(spinnerChangeListener);
        endHeightSpinner.addChangeListener(spinnerChangeListener);
        angleSpinner.addChangeListener(spinnerChangeListener);

        ChangeListener sliderChangeListener = new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (ignoreNextChange) {
                    ignoreNextChange = false;
                    return;
                }
                ignoreNextChange = true;
                String name = source.getName();
                if (name == null) {
                    return;
                }
                switch (name) {
                    case "Length":
                        lengthSpinner.setValue((Integer) lengthSlider.getValue());
                        break;
                    case "Start height":
                        startHeightSpinner.setValue((double) startHeightSlider.getValue() / 10);
                        break;
                    case "End height":
                        endHeightSpinner.setValue((double) endHeightSlider.getValue() / 10);
                        break;
                    case "Angle":
                        angleSpinner.setValue(angleSlider.getValue() / 10);
                        break;
                }
            }
        };

        lengthSlider.addChangeListener(sliderChangeListener);
        startHeightSlider.addChangeListener(sliderChangeListener);
        endHeightSlider.addChangeListener(sliderChangeListener);
        angleSlider.addChangeListener(sliderChangeListener);
    }
    private JSlider angleSlider;
    private JSlider endHeightSlider;
    private JSlider startHeightSlider;
    private JSlider lengthSlider;
    private JSpinner angleSpinner;
    private JSpinner endHeightSpinner;
    private JSpinner startHeightSpinner;
    private JSpinner lengthSpinner;

    public int getLength() {
        return (int) lengthSpinner.getValue();
    }

    public double getEndHeight() {
        return (double) endHeightSpinner.getValue();
    }

    public double getStartHeight() {
        return (double) startHeightSpinner.getValue();
    }

    public int getAngle() {
        return (int) angleSpinner.getValue();
    }
}
