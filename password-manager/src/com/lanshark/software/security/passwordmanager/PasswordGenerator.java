package com.lanshark.software.security.passwordmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Generates passwords based on the allowed characters and defined length.
 *
 * @author Jonathan Ritter
 * @version 1.0
 */
public class PasswordGenerator
{
    /**
     * The list of characters included in the lowercase option.
     */
    public static final Character[] LOWERCASE = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    /**
     * The list of characters included in the uppercase option.
     */
    public static final Character[] UPPERCASE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    /**
     * The list of characters included in the numeric option.
     */
    public static final Character[] NUMERIC = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * A list of special characters.
     * These can be allowed or disallowed individually.
     */
    public static final Character[] SPECIAL = { '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+',
            '[', '{', ']', '}', '\\', '/', '|', '<', '>' };

    /**
     * A list of punctuation characters.
     * These can be allowed or disallowed individually.
     */
    public static final Character[] PUNCTUATION = { ';', ':', '\'', '\"', ',', '.', '?', '!' };

    /**
     * The index of the minRequired array that represents the minimum number of lowercase characters
     * that must be present in the password.
     */
    public static final int MIN_LOWERCASE_INDEX = 0;

    /**
     * The index of the minRequired array that represents the minimum number of uppercase characters
     * that must be present in the password.
     */
    public static final int MIN_UPPERCASE_INDEX = 1;

    /**
     * The index of the minRequired array that represents the minimum number of numeric characters
     * that must be present in the password.
     */
    public static final int MIN_NUMERIC_INDEX = 2;

    /**
     * The index of the minRequired array that represents the minimum number of special characters
     * that must be present in the password.
     */
    public static final int MIN_SPECIAL_INDEX = 3;

    /**
     * The index of the minRequired array that represents the minimum number of punctuation characters
     * that must be present in the password.
     */
    public static final int MIN_PUNCTUATION_INDEX = 4;


    /**
     * The number of characters that the password should be.
     */
    private int length;

    /**
     * Keeps track of whether or not lowercase characters are allowed in the password.
     */
    private boolean lowercaseAllowed;

    /**
     * Keeps track of whether or not uppercase characters are allowed in the password.
     */
    private boolean uppercaseAllowed;

    /**
     * Keeps track of whether or not numeric characters are allowed in the password.
     */
    private boolean numericAllowed;

    /**
     * Keeps track of whether or not a given special character is allowed in the password.
     * Indices correspond to the characters defined in the SPECIAL array.
     */
    private boolean[] specialAllowed = new boolean[NUMERIC.length];

    /**
     * Keeps track of whether or not a given punctuation character is allowed in the password.
     * Indices correspond to the characters defined in the PUNCTUATION array.
     */
    private boolean[] punctuationAllowed = new boolean[PUNCTUATION.length];

    /**
     * The minimum number of times a character type must show up in the password.
     */
    private final int[] minOccurances = new int[5];


    /**
     * Creates a PasswordGenerator object that will generate passwords with 16 characters.
     */
    public PasswordGenerator()
    {
        this(16);
    }

    /**
     * Creates a PasswordGenerator that will generate a password with the specified length.
     *
     * @param length	the number of characters the password should be.
     */
    public PasswordGenerator(int length)
    {
        this.length = length;
        this.minOccurances[MIN_LOWERCASE_INDEX] = 1;
        this.minOccurances[MIN_UPPERCASE_INDEX] = 0;
        this.minOccurances[MIN_NUMERIC_INDEX] = 0;
        this.minOccurances[MIN_SPECIAL_INDEX] = 0;
        this.minOccurances[MIN_PUNCTUATION_INDEX] = 0;
        this.numericAllowed = true;
        this.uppercaseAllowed = true;
        this.lowercaseAllowed = true;

        for (int i = 0; i < this.specialAllowed.length; i++)
            this.specialAllowed[i] = true;

        for (int i = 0; i < this.punctuationAllowed.length; i++)
            this.punctuationAllowed[i] = true;
    }

    /**
     * Generates a random password based on the allowed characters.
     *
     * @return		A randomly generated password.
     */
    public String generatePassword()
    {
        char[] passwordAsChar = new char[this.length];
        String passwordAsString = "";
        ArrayList<Character> usableCharacters = new ArrayList<Character>();
        Random random = new Random();

        if (this.lowercaseAllowed)
            usableCharacters.addAll(Arrays.asList(LOWERCASE));

        if (this.uppercaseAllowed)
            usableCharacters.addAll(Arrays.asList(UPPERCASE));

        if (this.numericAllowed)
            usableCharacters.addAll(Arrays.asList(NUMERIC));

        usableCharacters.addAll(getListOfUsableSpecialCharacters());
        usableCharacters.addAll(getListOfUsablePunctuationCharacters());

        while(! (containsEnoughLowercase(passwordAsString)
                && containsEnoughUppercase(passwordAsString)
                && containsEnoughNumeric(passwordAsString)
                && containsEnoughSpecial(passwordAsString)
                && containsEnoughPunctuation(passwordAsString) ))
        {
            for (int i = 0; i < this.length; i++)
            {
                passwordAsChar[i] = usableCharacters.get(random.nextInt(usableCharacters.size()));
            }

            passwordAsString = new String(passwordAsChar);
        }

        return passwordAsString;
    }

    /**
     * Enable or disable lowercase characters in the generated password.
     *
     * @param enabled	If true, lowercase characters are allowed. Otherwise, lowercase
     * 					characters will not be included in the password.
     */
    public void enableLowercase(boolean enabled)
    {
        this.lowercaseAllowed = enabled;
    }

    /**
     * Enable or disable uppercase characters in the generated password.
     *
     * @param enabled	If true, uppercase characters are allowed. Otherwise, uppercase
     * 					characters will not be included in the password.
     */
    public void enableUppercase(boolean enabled)
    {
        this.uppercaseAllowed = enabled;
    }

    /**
     * Enable or disable numeric characters in the generated password.
     *
     * @param enabled	If true, numeric characters are allowed. Otherwise, numeric
     * 					characters will not be included in the password.
     */
    public void enableNumeric(boolean enabled)
    {
        this.numericAllowed = enabled;
    }

    /**
     * Enable or disable a specific special character.
     *
     * @param c				The character to enable or disable.
     * @param enabled		If true, the specified character will be allowed in the password.
     * 						Otherwise, the specified character will not be allowed in the password.
     */
    public void enableSpecial(Character c, boolean enabled)
    {
        ArrayList<Character> list = new ArrayList<Character>(Arrays.asList(SPECIAL));
        int index = list.indexOf(c);
        this.specialAllowed[index] = enabled;
    }

    /**
     * Enables or disables all special characters.
     *
     * @param enabled	If true, all special characters will be enabled. If false,
     * 					all special characters will be disabled.
     */
    public void enableAllSpecial(boolean enabled)
    {
        for (int i = 0; i < this.specialAllowed.length; i++)
        {
            this.specialAllowed[i] = enabled;
        }
    }

    /**
     * Enable or disable a specific punctuation character.
     *
     * @param c				The character to enable or disable.
     * @param enabled		If true, the specified character will be allowed in the password.
     * 						Otherwise, the specified character will not be allowed in the password.
     */
    public void enablePunctuation(Character c, boolean enabled)
    {
        ArrayList<Character> list = new ArrayList<Character>(Arrays.asList(PUNCTUATION));
        int index = list.indexOf(c);
        this.punctuationAllowed[index] = enabled;
    }

    /**
     * Enables or disables all punctuation characters.
     *
     * @param enabled	If true, all punctuation characters will be enabled. If false,
     * 					all punctuation characters will be disabled.
     */
    public void enableAllPunctuation(boolean enabled)
    {
        for (int i = 0; i < this.punctuationAllowed.length; i++)
        {
            this.punctuationAllowed[i] = enabled;
        }
    }

    /**
     * Sets the minimum number of lowercase characters that must appear in the password.
     *
     * @param min	The minimum number of lowercase characters that must appear.
     */
    public void setMinimumLowercase(int min)
    {
        this.minOccurances[MIN_LOWERCASE_INDEX] = min;
    }

    /**
     * Sets the minimum number of uppercase characters that must appear in the password.
     *
     * @param min	The minimum number of uppercase characters that must appear.
     */
    public void setMinimumUppercase(int min)
    {
        this.minOccurances[MIN_UPPERCASE_INDEX] = min;
    }

    /**
     * Sets the minimum number of numeric characters that must appear in the password.
     *
     * @param min	The minimum number of numeric characters that must appear.
     */
    public void setMinimumNumeric(int min)
    {
        this.minOccurances[MIN_NUMERIC_INDEX] = min;
    }

    /**
     * Sets the minimum number of special characters that must appear in the password.
     *
     * @param min	The minimum number of special characters that must appear.
     */
    public void setMinimumSpecial(int min)
    {
        this.minOccurances[MIN_SPECIAL_INDEX] = min;
    }

    /**
     * Sets the minimum number of punctuation characters that must appear in the password.
     *
     * @param min	The minimum number of punctuation characters that must appear.
     */
    public void setMinimumPunctuation(int min)
    {
        this.minOccurances[MIN_PUNCTUATION_INDEX] = min;
    }

    /**
     * Determines if the generated password meets the requirement for the minimum number
     * of lowercase characters that must be present.
     *
     * @param password		The password to test.
     * @return				True if the password contains enough lowercase characters.
     */
    private boolean containsEnoughLowercase(String password)
    {
        ArrayList<Character> list = new ArrayList<Character>(Arrays.asList(LOWERCASE));
        int count = 0;
        boolean enough = false;

        for (int i = 0; i < password.length(); i++)
        {
            if (list.contains(password.charAt(i)))
                count++;
        }

        if (count >= this.minOccurances[MIN_LOWERCASE_INDEX])
            enough = true;

        return enough;
    }

    /**
     * Determines if the generated password meets the requirement for the minimum number
     * of uppercase characters that must be present.
     *
     * @param password		The password to test.
     * @return				True if the password contains enough uppercase characters.
     */
    private boolean containsEnoughUppercase(String password)
    {
        ArrayList<Character> list = new ArrayList<Character>(Arrays.asList(UPPERCASE));
        int count = 0;
        boolean enough = false;

        for (int i = 0; i < password.length(); i++)
        {
            if (list.contains(password.charAt(i)))
                count++;
        }

        if (count >= this.minOccurances[MIN_UPPERCASE_INDEX])
            enough = true;

        return enough;
    }

    /**
     * Determines if the generated password meets the requirement for the minimum number
     * of numeric characters that must be present.
     *
     * @param password		The password to test.
     * @return				True if the password contains enough numeric characters.
     */
    private boolean containsEnoughNumeric(String password)
    {
        ArrayList<Character> list = new ArrayList<Character>(Arrays.asList(NUMERIC));
        int count = 0;
        boolean enough = false;

        for (int i = 0; i < password.length(); i++)
        {
            if (list.contains(password.charAt(i)))
                count++;
        }

        if (count >= this.minOccurances[MIN_NUMERIC_INDEX])
            enough = true;

        return enough;
    }

    /**
     * Determines if the generated password meets the requirement for the minimum number
     * of special characters that must be present.
     *
     * @param password		The password to test.
     * @return				True if the password contains enough special characters.
     */
    private boolean containsEnoughSpecial(String password)
    {
        ArrayList<Character> list = new ArrayList<Character>(Arrays.asList(SPECIAL));
        int count = 0;
        boolean enough = false;

        for (int i = 0; i < password.length(); i++)
        {
            if (list.contains(password.charAt(i)))
                count++;
        }

        if (count >= this.minOccurances[MIN_SPECIAL_INDEX])
            enough = true;

        return enough;
    }

    /**
     * Determines if the generated password meets the requirement for the minimum number
     * of punctuation characters that must be present.
     *
     * @param password		The password to test.
     * @return				True if the password contains enough punctuation characters.
     */
    private boolean containsEnoughPunctuation(String password)
    {
        ArrayList<Character> list = new ArrayList<Character>(Arrays.asList(PUNCTUATION));
        int count = 0;
        boolean enough = false;

        for (int i = 0; i < password.length(); i++)
        {
            if (list.contains(password.charAt(i)))
                count++;
        }

        if (count >= this.minOccurances[MIN_PUNCTUATION_INDEX])
            enough = true;

        return enough;
    }

    /**
     * Returns a list of special characters that are allowed to be used in the password.
     *
     * @return		A list of usable special characters.
     */
    private ArrayList<Character> getListOfUsableSpecialCharacters()
    {
        ArrayList<Character> usableSpecialCharacters = new ArrayList<Character>();

        for (int i = 0; i < this.specialAllowed.length; i++)
        {
            if (this.specialAllowed[i])
            {
                usableSpecialCharacters.add(SPECIAL[i]);
            }
        }

        return usableSpecialCharacters;
    }

    /**
     * Returns a list of punctuation characters that are allowed to be used in the password.
     *
     * @return		A list of usable punctuation characters.
     */
    private ArrayList<Character> getListOfUsablePunctuationCharacters()
    {
        ArrayList<Character> usablePunctuationCharacters = new ArrayList<Character>();

        for (int i = 0; i < this.punctuationAllowed.length; i++)
        {
            if (this.punctuationAllowed[i])
            {
                usablePunctuationCharacters.add(PUNCTUATION[i]);
            }
        }

        return usablePunctuationCharacters;
    }
}
