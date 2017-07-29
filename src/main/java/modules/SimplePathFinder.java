package modules;

import data.Layout;
import parsers.LayoutParser;
import ui.LayoutComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SimplePathFinder {
    private static JFrame frame;

    private static File selectedFile = null;
    private static JTextField filenameTextField;
    private static JButton browseButton;
    private static JButton importButton;

    private static LayoutComponent layoutComponent;

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initUI();
            }
        });
    }

    public static void initUI(){
        frame = new JFrame("Simple A* Pathfinder");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Options"),
                        BorderFactory.createEmptyBorder(4, 4, 4, 4)
                )
        ));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        frame.add(buttonPanel, BorderLayout.NORTH);
        //Import
        JPanel importPanel = new JPanel();
        importPanel.setLayout(new BoxLayout(importPanel, BoxLayout.LINE_AXIS));
        JLabel importLabel = new JLabel("Import:");
        filenameTextField = new JTextField(50);
        filenameTextField.setEditable(false);
        browseButton = new JButton("Browse...");
        importButton = new JButton("Import");
        importButton.setEnabled(false);

        importPanel.add(importLabel);
        importPanel.add(Box.createHorizontalStrut(4));
        importPanel.add(filenameTextField);
        importPanel.add(Box.createHorizontalStrut(4));
        importPanel.add(browseButton);
        importPanel.add(Box.createHorizontalStrut(4));
        importPanel.add(importButton);
        buttonPanel.add(importPanel);

        //Layout Panel
        layoutComponent = new LayoutComponent();
        layoutComponent.setEnabled(false);
        frame.add(layoutComponent, BorderLayout.CENTER);

        //Listeners
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setMultiSelectionEnabled(false);

                File workingDirectory = new File(System.getProperty("user.dir"));
                jfc.setCurrentDirectory(workingDirectory);

                int returnVal = jfc.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    selectedFile = jfc.getSelectedFile();
                    filenameTextField.setText(selectedFile.getName());
                    importButton.setEnabled(true);
                }
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedFile != null){
                    Layout layout = LayoutParser.parseLayout(selectedFile);
                    layoutComponent.setLayoutComponent(layout);
                    layoutComponent.setEnabled(true);
                }
            }
        });

        frame.pack();
        frame.setSize(new Dimension(750, 750));
        frame.setVisible(true);
    }
}
