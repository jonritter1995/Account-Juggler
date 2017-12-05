package com.lanshark.software.security.passwordmanager.com.lanshark.software.security.passwordmanager.gui;

import javax.swing.*;

public class MainGUI
{

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
    private JComboBox comboBox1;
    private JButton addButton;
    private JTextField textField1;
    private JTextField textField2;

    public MainGUI()
    {
        accountList.setModel(new DefaultListModel());

        JFrame frame = new JFrame();
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
