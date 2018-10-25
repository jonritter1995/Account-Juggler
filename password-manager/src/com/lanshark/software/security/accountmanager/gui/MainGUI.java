package com.lanshark.software.security.accountmanager.gui;

import com.lanshark.software.security.accountmanager.Account;
import com.lanshark.software.security.accountmanager.Main;
import com.lanshark.software.security.accountmanager.util.ComplexFieldPair;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class MainGUI
{
    /**
     * Created by IntelliJ
     */
    private JPanel mainPanel;
    private JPanel accountListPanel;
    private JList accountList;
    private JPanel accountEditorPanel;
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
    private JButton addEmailButton;
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
    private JButton deleteEmailButton;
    private JSplitPane listEditorSplit;

    /**
     * Created manually
     */
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem saveMenuItem;
    JMenuItem settingsMenuItem;
    JMenuItem exitMenuItem;

    DefaultListModel accountListModel;
    DefaultComboBoxModel emailListModel;

    public MainGUI()
    {
        accountListModel = new DefaultListModel<String>();
        accountList.setModel(accountListModel);
        emailListModel = new DefaultComboBoxModel<String>();
        emailDropdown.setModel(emailListModel);

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

        listEditorSplit.revalidate();

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
                    accountListModel.addElement(accName);

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
                accountListModel.removeElementAt(index);
                Main.accountManager.removeAccount(index);

                if (accountListModel.getSize() == 0)
                    accountEditorPanel.setVisible(false);
                else if (index - 1 < accountListModel.getSize() && index - 1 >= 0)
                    accountList.setSelectedIndex(index - 1);
                else if (index < accountListModel.getSize() && index >= 0)
                    accountList.setSelectedIndex(index);
                else if (index + 1 < accountListModel.getSize() && index + 1 < accountListModel.getSize())
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

                if (accountListModel.getSize() > 0)
                    accountEditorPanel.setVisible(true);

                accountList.grabFocus();
                System.out.println(accountList.hasFocus());
                System.out.println(accountNameField.hasFocus());

                accountNameField.setText(Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getAccountName());
                usernameField.setText(Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getUsername());
                passwordField.setText(Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getPassword());
                pinField.setText(Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getPin());
                noteField.setText(Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getNote());

                emailListModel.removeAllElements();

                for (ComplexFieldPair<String, String> email : Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getEmailAddresses())
                {
                    emailListModel.addElement(email);
                }

                if (emailListModel.getSize() == 0)
                {
                    emailIDField.setText("");
                    emailAddressField.setText("");
                    emailIDField.setEnabled(false);
                    emailAddressField.setEnabled(false);
                } else
                {
                    ComplexFieldPair<String, String> email;
                    email = Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getEmailPair(0);
                    emailIDField.setText(email.getKey().toString());
                    emailAddressField.setText(email.getValue().toString());
                    emailIDField.setEnabled(true);
                    emailAddressField.setEnabled(true);
                }
            }
        });

        accountNameField.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                updateAcc();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                updateAcc();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                updateAcc();
            }

            private void updateAcc()
            {
                if (!accountNameField.hasFocus())
                    return;

                if (!nameAlreadyInList(accountNameField.getText()))
                {
                    Main.accountManager.getAccountByIndex(accountList.getSelectedIndex())
                            .setAccountName(accountNameField.getText());
                    accountListModel.set(accountList.getSelectedIndex(), accountNameField.getText());
                } else
                {

                }
            }
        });

        accountNameField.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                super.focusLost(e);
                int count = 0;
                String baseName = accountNameField.getText();
                String accName = baseName;

                while (nameAlreadyInList(accName))
                {
                    accName = baseName + "(" + ++count + ")";
                }

                accountNameField.setText(accName);
                Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).setAccountName(accName);
                accountListModel.set(accountList.getSelectedIndex(), accName);

                if (count > 0)
                    JOptionPane.showMessageDialog(null, "That account name is already in use.");
            }
        });

        usernameField.getDocument().addDocumentListener(new StringFieldListener(Account.USERNAME, usernameField));
        passwordField.getDocument().addDocumentListener(new StringFieldListener(Account.PASSWORD, passwordField));
        pinField.getDocument().addDocumentListener(new StringFieldListener(Account.PIN, pinField));
        noteField.getDocument().addDocumentListener(new StringFieldListener(Account.NOTE, noteField));

        addEmailButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ComplexFieldPair<String, String> email = new ComplexFieldPair<>("New email", "");
                boolean added = Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).addEmail(email);
                int count = 0;

                while (!added)
                {
                    email.setKey("New email(" + ++count + ")");
                    added = Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).addEmail(email);
                }

                emailListModel.addElement(email);
                emailIDField.setText((String) email.getKey());
                emailAddressField.setText("");
                emailDropdown.setSelectedIndex(emailListModel.getSize() - 1);
                emailIDField.setEnabled(true);
                emailAddressField.setEnabled(true);
            }
        });

        deleteEmailButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).removeEmail(emailIDField.getText());
                emailListModel.removeElement(emailListModel.getSelectedItem());

                if (Main.accountManager.getAccountByIndex(accountList.getSelectedIndex()).getEmailAddresses().size() == 0)
                {
                    emailIDField.setText("");
                    emailAddressField.setText("");
                    emailIDField.setEnabled(false);
                    emailAddressField.setEnabled(false);
                } else
                {
                    ComplexFieldPair email = Main.accountManager.getAccountByIndex(accountList.getSelectedIndex())
                            .getEmailPair(emailDropdown.getSelectedIndex());
                    emailIDField.setText(email.getKey().toString());
                    emailAddressField.setText(email.getValue().toString());
                }
            }
        });

        emailDropdown.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!emailDropdown.hasFocus())
                    return;

                ComplexFieldPair<String, String> email = (ComplexFieldPair<String, String>) emailListModel.getSelectedItem();

                emailIDField.setText((String) email.getKey());
                emailAddressField.setText((String) email.getValue());
            }
        });

        emailIDField.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                updateEmail();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                updateEmail();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                updateEmail();
            }


        });

        emailAddressField.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                updateEmail();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                updateEmail();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                updateEmail();
            }

        });

        // Add the accounts that were loaded from the account file to the UI account list
        for (int i = 0; i < Main.accountManager.getAllAccounts().size(); i++)
        {
            accountListModel.addElement(Main.accountManager.getAccountByIndex(i).getAccountName());
        }
        accountNameField.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                super.focusGained(e);
                accountList.setEnabled(false);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                super.focusLost(e);
                accountList.setEnabled(true);
            }
        });
    }

    private boolean nameAlreadyInList(String name)
    {
        for (int i = 0; i < accountListModel.getSize(); i++)
        {
            if (accountListModel.elementAt(i).equals(name) && accountList.getSelectedIndex() != i)
                return true;
        }

        return false;
    }

    private boolean emailAlreadyInList(String emailID)
    {
        for (int i = 0; i < emailListModel.getSize(); i++)
        {
            ComplexFieldPair<String, String> cp = (ComplexFieldPair) emailListModel.getElementAt(i);
            if (cp.getKey().equals(emailID) && emailDropdown.getSelectedIndex() != i)
                return true;
        }

        return false;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setMinimumSize(new Dimension(550, 600));
        mainPanel.setPreferredSize(new Dimension(550, 600));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        mainPanel.add(panel1, BorderLayout.CENTER);
        listEditorSplit = new JSplitPane();
        listEditorSplit.setPreferredSize(new Dimension(510, 10));
        panel1.add(listEditorSplit, BorderLayout.CENTER);
        accountListPanel = new JPanel();
        accountListPanel.setLayout(new BorderLayout(0, 0));
        accountListPanel.setMinimumSize(new Dimension(0, 0));
        accountListPanel.setPreferredSize(new Dimension(150, 0));
        listEditorSplit.setLeftComponent(accountListPanel);
        accountListModifierPanel = new JPanel();
        accountListModifierPanel.setLayout(new BorderLayout(0, 0));
        accountListPanel.add(accountListModifierPanel, BorderLayout.SOUTH);
        addAccountButton = new JButton();
        addAccountButton.setText("Add Account");
        accountListModifierPanel.add(addAccountButton, BorderLayout.NORTH);
        deleteAccountButton = new JButton();
        deleteAccountButton.setText("Delete Selected");
        accountListModifierPanel.add(deleteAccountButton, BorderLayout.SOUTH);
        final JScrollPane scrollPane1 = new JScrollPane();
        accountListPanel.add(scrollPane1, BorderLayout.CENTER);
        accountList = new JList();
        accountList.setEnabled(true);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        accountList.setModel(defaultListModel1);
        accountList.putClientProperty("List.isFileList", Boolean.FALSE);
        scrollPane1.setViewportView(accountList);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        listEditorSplit.setRightComponent(panel2);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel2.add(scrollPane2, BorderLayout.CENTER);
        accountEditorPanel = new JPanel();
        accountEditorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        Font accountEditorPanelFont = this.$$$getFont$$$(null, Font.BOLD, -1, accountEditorPanel.getFont());
        if (accountEditorPanelFont != null) accountEditorPanel.setFont(accountEditorPanelFont);
        accountEditorPanel.setMinimumSize(new Dimension(0, 0));
        accountEditorPanel.setPreferredSize(new Dimension(350, 10));
        scrollPane2.setViewportView(accountEditorPanel);
        accountNamePanel = new JPanel();
        accountNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        accountNamePanel.setMinimumSize(new Dimension(400, 30));
        accountNamePanel.setPreferredSize(new Dimension(400, 35));
        accountEditorPanel.add(accountNamePanel);
        accountNameLable = new JLabel();
        accountNameLable.setPreferredSize(new Dimension(100, 16));
        accountNameLable.setText("Account Name:");
        accountNameLable.setToolTipText("The website or organization that this account is for.");
        accountNamePanel.add(accountNameLable);
        accountNameField = new JTextField();
        accountNameField.setPreferredSize(new Dimension(250, 28));
        accountNamePanel.add(accountNameField);
        usernamePanel = new JPanel();
        usernamePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        usernamePanel.setAlignmentX(0.5f);
        usernamePanel.setMinimumSize(new Dimension(400, 30));
        usernamePanel.setPreferredSize(new Dimension(400, 35));
        accountEditorPanel.add(usernamePanel);
        usernameLabel = new JLabel();
        usernameLabel.setPreferredSize(new Dimension(100, 16));
        usernameLabel.setText("Username:");
        usernameLabel.setToolTipText("The login username for the website/application.");
        usernamePanel.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 28));
        usernamePanel.add(usernameField);
        passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        passwordPanel.setMinimumSize(new Dimension(400, 30));
        passwordPanel.setPreferredSize(new Dimension(400, 35));
        accountEditorPanel.add(passwordPanel);
        passwordLabel = new JLabel();
        passwordLabel.setMinimumSize(new Dimension(100, 16));
        passwordLabel.setPreferredSize(new Dimension(100, 16));
        passwordLabel.setText("Password:");
        passwordLabel.setToolTipText("The password used to login to the website/application. It is recommended that you generate a random password and change it every few months.");
        passwordPanel.add(passwordLabel);
        passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(250, 28));
        passwordPanel.add(passwordField);
        generatePasswordButton = new JButton();
        generatePasswordButton.setPreferredSize(new Dimension(30, 22));
        generatePasswordButton.setText("...");
        generatePasswordButton.setToolTipText("Generate a password. ");
        passwordPanel.add(generatePasswordButton);
        pinPanel = new JPanel();
        pinPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pinPanel.setMinimumSize(new Dimension(400, 30));
        pinPanel.setPreferredSize(new Dimension(400, 35));
        accountEditorPanel.add(pinPanel);
        pinLabel = new JLabel();
        pinLabel.setMinimumSize(new Dimension(100, 16));
        pinLabel.setPreferredSize(new Dimension(100, 16));
        pinLabel.setText("Pin #:");
        pinLabel.setToolTipText("Used for any type of pin or number that needs to be entered to login to an account.");
        pinPanel.add(pinLabel);
        pinField = new JTextField();
        pinField.setMinimumSize(new Dimension(200, 24));
        pinField.setPreferredSize(new Dimension(250, 28));
        pinPanel.add(pinField);
        emailPanel = new JPanel();
        emailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        emailPanel.setMinimumSize(new Dimension(400, 110));
        emailPanel.setPreferredSize(new Dimension(400, 110));
        accountEditorPanel.add(emailPanel);
        emailPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        emailLabel = new JLabel();
        Font emailLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, emailLabel.getFont());
        if (emailLabelFont != null) emailLabel.setFont(emailLabelFont);
        emailLabel.setHorizontalAlignment(2);
        emailLabel.setPreferredSize(new Dimension(100, 16));
        emailLabel.setText("Email Addresses:");
        emailLabel.setToolTipText("The list of email addresses associated with this account.");
        emailPanel.add(emailLabel);
        emailDropdown = new JComboBox();
        emailDropdown.setEditable(false);
        emailDropdown.setPreferredSize(new Dimension(175, 28));
        emailDropdown.setToolTipText("");
        emailPanel.add(emailDropdown);
        addEmailButton = new JButton();
        Font addEmailButtonFont = this.$$$getFont$$$(null, -1, -1, addEmailButton.getFont());
        if (addEmailButtonFont != null) addEmailButton.setFont(addEmailButtonFont);
        addEmailButton.setMaximumSize(new Dimension(45, 22));
        addEmailButton.setMinimumSize(new Dimension(45, 25));
        addEmailButton.setPreferredSize(new Dimension(45, 25));
        addEmailButton.setText("+");
        addEmailButton.setToolTipText("Add an email address.");
        addEmailButton.setVerticalAlignment(0);
        addEmailButton.setVerticalTextPosition(0);
        emailPanel.add(addEmailButton);
        deleteEmailButton = new JButton();
        Font deleteEmailButtonFont = this.$$$getFont$$$(null, -1, -1, deleteEmailButton.getFont());
        if (deleteEmailButtonFont != null) deleteEmailButton.setFont(deleteEmailButtonFont);
        deleteEmailButton.setLabel("-");
        deleteEmailButton.setMinimumSize(new Dimension(45, 25));
        deleteEmailButton.setPreferredSize(new Dimension(45, 25));
        deleteEmailButton.setText("-");
        deleteEmailButton.setToolTipText("Remove the selected email address from the list.");
        emailPanel.add(deleteEmailButton);
        emailIDLabel = new JLabel();
        emailIDLabel.setPreferredSize(new Dimension(100, 16));
        emailIDLabel.setText("Email Identifier:");
        emailIDLabel.setToolTipText("A description of the primary use of the email (i.e. Primary, Family, Work)");
        emailPanel.add(emailIDLabel);
        emailIDField = new JTextField();
        emailIDField.setEnabled(false);
        emailIDField.setPreferredSize(new Dimension(250, 28));
        emailPanel.add(emailIDField);
        emailAddressLabel = new JLabel();
        emailAddressLabel.setPreferredSize(new Dimension(100, 16));
        emailAddressLabel.setText("Address:");
        emailAddressLabel.setToolTipText("The actual email address.");
        emailPanel.add(emailAddressLabel);
        emailAddressField = new JTextField();
        emailAddressField.setEnabled(false);
        emailAddressField.setPreferredSize(new Dimension(250, 28));
        emailPanel.add(emailAddressField);
        securityQuestionPanel = new JPanel();
        securityQuestionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        securityQuestionPanel.setMinimumSize(new Dimension(400, 110));
        securityQuestionPanel.setPreferredSize(new Dimension(400, 110));
        accountEditorPanel.add(securityQuestionPanel);
        securityQuestionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        secQuestionLabel = new JLabel();
        Font secQuestionLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, secQuestionLabel.getFont());
        if (secQuestionLabelFont != null) secQuestionLabel.setFont(secQuestionLabelFont);
        secQuestionLabel.setHorizontalAlignment(2);
        secQuestionLabel.setPreferredSize(new Dimension(100, 16));
        secQuestionLabel.setText("Sec. Questions:");
        secQuestionLabel.setToolTipText("A list of security questions that must be answered to login to or recover your account.");
        securityQuestionPanel.add(secQuestionLabel);
        secQuestionDropdown = new JComboBox();
        secQuestionDropdown.setEditable(false);
        secQuestionDropdown.setPreferredSize(new Dimension(175, 28));
        secQuestionDropdown.setToolTipText("");
        securityQuestionPanel.add(secQuestionDropdown);
        final JButton button1 = new JButton();
        Font button1Font = this.$$$getFont$$$(null, -1, -1, button1.getFont());
        if (button1Font != null) button1.setFont(button1Font);
        button1.setMaximumSize(new Dimension(45, 22));
        button1.setMinimumSize(new Dimension(45, 25));
        button1.setPreferredSize(new Dimension(45, 25));
        button1.setText("+");
        button1.setToolTipText("Add a security question to the list.");
        button1.setVerticalAlignment(0);
        button1.setVerticalTextPosition(0);
        securityQuestionPanel.add(button1);
        final JButton button2 = new JButton();
        Font button2Font = this.$$$getFont$$$(null, -1, -1, button2.getFont());
        if (button2Font != null) button2.setFont(button2Font);
        button2.setLabel("-");
        button2.setMinimumSize(new Dimension(45, 25));
        button2.setPreferredSize(new Dimension(45, 25));
        button2.setText("-");
        button2.setToolTipText("Remove the selected security question and answer from the list.");
        securityQuestionPanel.add(button2);
        questionLabel = new JLabel();
        questionLabel.setPreferredSize(new Dimension(100, 16));
        questionLabel.setText("Question:");
        questionLabel.setToolTipText("The security question.");
        securityQuestionPanel.add(questionLabel);
        questionField = new JTextField();
        questionField.setEnabled(false);
        questionField.setPreferredSize(new Dimension(250, 28));
        securityQuestionPanel.add(questionField);
        answerLabel = new JLabel();
        answerLabel.setPreferredSize(new Dimension(100, 16));
        answerLabel.setText("Answer:");
        answerLabel.setToolTipText("The answer to the security question.");
        securityQuestionPanel.add(answerLabel);
        answerField = new JTextField();
        answerField.setEnabled(false);
        answerField.setPreferredSize(new Dimension(250, 28));
        securityQuestionPanel.add(answerField);
        customFieldPanel = new JPanel();
        customFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        customFieldPanel.setMinimumSize(new Dimension(400, 110));
        customFieldPanel.setPreferredSize(new Dimension(400, 110));
        accountEditorPanel.add(customFieldPanel);
        customFieldPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        customFieldLabel = new JLabel();
        Font customFieldLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, customFieldLabel.getFont());
        if (customFieldLabelFont != null) customFieldLabel.setFont(customFieldLabelFont);
        customFieldLabel.setHorizontalAlignment(2);
        customFieldLabel.setPreferredSize(new Dimension(100, 16));
        customFieldLabel.setText("Custom Fields:");
        customFieldLabel.setToolTipText("A list of custom fields for accounts that require extra information.");
        customFieldPanel.add(customFieldLabel);
        customFieldDropdown = new JComboBox();
        customFieldDropdown.setEditable(false);
        customFieldDropdown.setPreferredSize(new Dimension(175, 28));
        customFieldDropdown.setToolTipText("");
        customFieldPanel.add(customFieldDropdown);
        final JButton button3 = new JButton();
        Font button3Font = this.$$$getFont$$$(null, -1, -1, button3.getFont());
        if (button3Font != null) button3.setFont(button3Font);
        button3.setMaximumSize(new Dimension(45, 22));
        button3.setMinimumSize(new Dimension(45, 25));
        button3.setPreferredSize(new Dimension(45, 25));
        button3.setText("+");
        button3.setToolTipText("Add a custom field to the list.");
        button3.setVerticalAlignment(0);
        button3.setVerticalTextPosition(0);
        customFieldPanel.add(button3);
        final JButton button4 = new JButton();
        Font button4Font = this.$$$getFont$$$(null, -1, -1, button4.getFont());
        if (button4Font != null) button4.setFont(button4Font);
        button4.setLabel("-");
        button4.setMinimumSize(new Dimension(45, 25));
        button4.setPreferredSize(new Dimension(45, 25));
        button4.setText("-");
        button4.setToolTipText("Remove the selected custom field from the list.");
        customFieldPanel.add(button4);
        fieldLabel = new JLabel();
        fieldLabel.setPreferredSize(new Dimension(100, 16));
        fieldLabel.setText("Field:");
        fieldLabel.setToolTipText("The name of the custom field (i.e. Address, phone number). It is not recommended to store financial information.");
        customFieldPanel.add(fieldLabel);
        fieldField = new JTextField();
        fieldField.setEnabled(false);
        fieldField.setPreferredSize(new Dimension(250, 28));
        customFieldPanel.add(fieldField);
        valueLabel = new JLabel();
        valueLabel.setPreferredSize(new Dimension(100, 16));
        valueLabel.setText("Value:");
        valueLabel.setToolTipText("The value of the custom field.");
        customFieldPanel.add(valueLabel);
        valueField = new JTextField();
        valueField.setEnabled(false);
        valueField.setPreferredSize(new Dimension(250, 28));
        customFieldPanel.add(valueField);
        notePanel = new JPanel();
        notePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        notePanel.setMinimumSize(new Dimension(400, 30));
        notePanel.setPreferredSize(new Dimension(400, 35));
        accountEditorPanel.add(notePanel);
        noteLabel = new JLabel();
        noteLabel.setMinimumSize(new Dimension(100, 16));
        noteLabel.setPreferredSize(new Dimension(100, 16));
        noteLabel.setText("Note:");
        noteLabel.setToolTipText("Any notes you wish to make regarding this account.");
        notePanel.add(noteLabel);
        noteField = new JTextField();
        noteField.setMinimumSize(new Dimension(250, 24));
        noteField.setPreferredSize(new Dimension(250, 28));
        notePanel.add(noteField);
        clearButton = new JButton();
        clearButton.setText("Clear");
        clearButton.setToolTipText("Clear all fields for this account. This will remove all email addresses, security questions, and custom fields.");
        accountEditorPanel.add(clearButton);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont)
    {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null)
        {
            resultName = currentFont.getName();
        } else
        {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1'))
            {
                resultName = fontName;
            } else
            {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return mainPanel;
    }

    private class StringFieldListener implements DocumentListener
    {

        private JTextField field;
        private int accField;

        public StringFieldListener(int accField, JTextField field)
        {
            super();
            this.field = field;
            this.accField = accField;
        }

        @Override
        public void insertUpdate(DocumentEvent e)
        {
            updateAcc();
        }

        @Override
        public void removeUpdate(DocumentEvent e)
        {
            updateAcc();
        }

        @Override
        public void changedUpdate(DocumentEvent e)
        {
            updateAcc();
        }

        private void updateAcc()
        {
            if (field.hasFocus())
            {
                Account account = Main.accountManager.getAccountByIndex(accountList.getSelectedIndex());
                account.setStringField(accField, field.getText());
            }
        }
    }

    private void updateEmail()
    {
        if (!(emailAddressField.hasFocus() || emailIDField.hasFocus()) || emailDropdown.getSelectedIndex() < 0)
            return;

        ComplexFieldPair<String, String> email = (ComplexFieldPair<String, String>) emailListModel.getSelectedItem();

        if (emailIDField.hasFocus() && !emailAlreadyInList(emailIDField.getText()))
            email.setKey(emailIDField.getText());

        email.setValue(emailAddressField.getText());
        emailDropdown.repaint();
        /*DefaultComboBoxModel temp = new DefaultComboBoxModel<String>();
        int index = emailDropdown.getSelectedIndex();
        ComplexFieldPair<String, String> email =
                new ComplexFieldPair<String, String>(emailIDField.getText(), emailAddressField.getText());
        Main.accountManager.getAccountByIndex(accountList.getSelectedIndex())
                .getEmailAddresses()
                .set(index, email);

        for (int i = 0; i < emailListModel.getSize(); i++)
        {
            if (i != index)
                temp.insertElementAt(emailListModel.getElementAt(i), i);
            else
                temp.insertElementAt(email.getKey(), i);
        }

        emailListModel = temp;
        emailDropdown.setModel(emailListModel);
        emailDropdown.setSelectedIndex(index);*/
    }

}
