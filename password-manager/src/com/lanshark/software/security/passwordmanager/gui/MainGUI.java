package com.lanshark.software.security.passwordmanager.gui;

import com.lanshark.software.security.passwordmanager.Account;
import com.lanshark.software.security.passwordmanager.Main;
import com.lanshark.software.security.passwordmanager.util.ComplexFieldPair;
import com.lanshark.software.util.KeyValuePair;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
                }
                else
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
                }
                else
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
                emailIDField.setText((String)email.getKey());
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
                }
                else
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

                ComplexFieldPair<String, String> email = (ComplexFieldPair<String, String>)emailListModel.getSelectedItem();

                emailIDField.setText((String)email.getKey());
                emailAddressField.setText((String)email.getValue());
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
            ComplexFieldPair<String, String> cp = (ComplexFieldPair)emailListModel.getElementAt(i);
            if (cp.getKey().equals(emailID) && emailDropdown.getSelectedIndex() != i)
                return true;
        }

        return false;
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

        ComplexFieldPair<String, String> email = (ComplexFieldPair<String, String>)emailListModel.getSelectedItem();

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
