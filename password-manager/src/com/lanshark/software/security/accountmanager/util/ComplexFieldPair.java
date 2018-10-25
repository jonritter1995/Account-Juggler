package com.lanshark.software.security.accountmanager.util;

import com.lanshark.software.util.MutablePair;

/**
 * Represents a complex field pair where the Key identifies the field
 * and the Value represents the value of the field.
 * Both the Key and the Value are mutable.
 *
 * @param <K>   The field identifier (or Key).
 * @param <V>   The field Value.
 */
public class ComplexFieldPair<K, V> extends MutablePair
{

    /**
     * Creates a new ComplexValuePair.
     *
     * @param key       The identifier of the complex field (key).
     * @param value     The value of the complex field (value).
     */
    public ComplexFieldPair(K key, V value)
    {
        super(key, value);
    }

    /**
     * Returns only the String representation of the Key portion of the pair.
     *
     * @return  The String representation of the ComplexFieldPair.
     */
    @Override
    public String toString()
    {
        return this.key.toString();
    }

    /**
     * Determines if two email addresses are equal.
     *
     * @param obj   The other ComplexFieldPair to check for equality.
     * @return      True if the ComplexFieldPairs have the same key.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof ComplexFieldPair))
            return false;
        else
        {
            return this.key.equals(((ComplexFieldPair)obj).key);
        }
    }
}
