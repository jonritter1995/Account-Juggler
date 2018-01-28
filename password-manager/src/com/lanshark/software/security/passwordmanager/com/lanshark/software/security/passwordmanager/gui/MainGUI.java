package com.lanshark.software.security.passwordmanager.com.lanshark.software.security.passwordmanager.gui;

import com.lanshark.software.security.passwordmanager.Account;
import com.lanshark.software.security.passwordmanager.Main;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
    private JLabel questionLabel;
    private JTextField questionField;
    private JLabel answerLabel;
    private JTextField answerField;
    private JPanel customFieldPanel;
    private JLabel customFieldLabel;
    private JComboBox customFieldDropdown;
    private JLabel fieldLabel;
    private JTextField fieldField;
    private JLabel valueLabel;
    private JTextField valueField;
    private JPanel notePanel;
    private JLabel noteLabel;
    private JTextField noteField;
    private JButton clearButton;
    private JButton addAccountButton;
    private JButton deleteAccountButton;
    private JPanel accountListModifierPanel;
    private JButton updateAccountButton;

    /**
     * Created manually
     */
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem saveMenuItem;
    JMenuItem settingsMenuItem;
    JMenuItem exitMenuItem;

    DefaultListModel listModel;


    public MainGUI()
    {
        listModel = new DefaultListModel<String>();
        accountList.setModel(listModel);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveMenuItem = new JMenuItem("Save");
        settingsMenuItem = new JMenuItem("Settings");
        exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(saveMenuItem);
        fileMenu.add(settingsMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(fileMenu);

        accountEditorPanel.setVisible(false);

        JFrame frame = new JFrame(Main.TITLE + " - " + Main.VERSION);
        frame.setContentPane(this.mainPanel);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        generatePasswordButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new PasswordGeneratorDialog().setVisible(true);
            }
        });

        accountEditorPanel.addFocusListener(new FocusAdapter()
        {
        });

        exitMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // todo check if user has unsaved changes and prompt to save.
                System.exit(0);
            }
        });

        addAccountButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String accName = "New Account";
                int count = 0;

                while (Main.accountManager.containsAccount(accName))
                {
                    accName = "New Account" + "(" + count++ + ")";
                }

                int index = Main.accountManager.addAccount(accName);
                if (index >= 0)
                    listModel.addElement(accName);

                accountList.setSelectedIndex(index);
            }
        });

        deleteAccountButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (accountList.getSelectedIndex() < 0)
                    return;

                int index = accountList.getSelectedIndex();
                listModel.removeElementAt(index);
                Main.accountManager.removeAccount(index);

                if (listModel.getSize() == 0)
                    accountEditorPanel.setVisible(false);
                else if (index - 1 < listModel.getSize() && index - 1 >= 0)
                    accountList.setSelectedIndex(index - 1);
                else if (index < listModel.getSize() && index >= 0)
                    accountList.setSelectedIndex(index);
                else if (index + 1 < listModel.getSize() && index + 1 < listModel.getSize())
                    accountList.setSelectedIndex(index + 1);
            }
        });

        accountList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting() || accountList.getSelectedIndex() < 0)
                    return;

                if (listModel.getSize() > 0)
                    accountEditorPanel.setVisible(true);

                accountNameField.setText(Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getAccountName());
            }
        });
    }

}
