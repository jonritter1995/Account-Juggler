package com.lanshark.software.security.passwordmanager.com.lanshark.software.security.passwordmanager.gui;

import javax.swing.*;

public class MainGUI
{
    /**
     * Created by IntelliJ
     */
    private JPanel mainPanel;
    private JPanel accountListPanel;
    private JList accountList;
    private JPanel accountEditorPanel;
    private JPanel headerPanel;
    private JPanel eastHeaderPanel;
    private JLabel accountHeader;
    private JPanel centerHeaderPanel;
    private JLabel accountEditorHeader;
    private JPanel accountNamePanel;
    private JLabel accountNameLable;
    private JTextField accountNameField;
    private JPanel usernamePanel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JPanel passwordPanel;
    private JLabel passwordLabel;
    private JTextField passwordField;
    private JButton generatePasswordButton;
    private JPanel pinPanel;
    private JTextField pinField;
    private JLabel emailLabel;
    private JLabel pinLabel;
    private JPanel emailPanel;
    private JComboBox emailDropdown;
    private JButton emailButton;
    private JTextField emailIDField;
    private JTextField emailAddressField;
    private JPanel securityQuestionPanel;
    private JLabel emailIDLabel;
    private JLabel emailAddressLabel;
    private JLabel secQuestionLabel;
    private JComboBox secQuestionDropdown;
    private JButton secQuestionButton;
    private JLabel questionLabel;
    private JTextField questionField;
    private JLabel answerLabel;
    private JTextField answerField;
    private JPanel customFieldPanel;
    private JLabel customFieldLabel;
    private JComboBox customFieldDropdown;
    private JButton customFieldButton;
    private JLabel fieldLabel;
    private JTextField fieldField;
    private JLabel valueLabel;
    private JTextField valueField;
    private JPanel notePanel;
    private JLabel noteLabel;
    private JTextField noteField;
    private JButton newAccountButton;
    private JButton deleteAccountButton;
    private JButton saveAccountButton;

    /**
     * Created manually
     */
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem settingsMenuItem;
    JMenuItem exitMenuItem;


    public MainGUI()
    {
        accountList.setModel(new DefaultListModel());

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        settingsMenuItem = new JMenuItem("Settings");
        exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(settingsMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(fileMenu);

        JFrame frame = new JFrame();
        frame.setContentPane(this.mainPanel);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
