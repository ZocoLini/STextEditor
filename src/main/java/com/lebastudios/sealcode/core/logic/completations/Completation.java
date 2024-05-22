package com.lebastudios.sealcode.core.logic.completations;

import com.lebastudios.sealcode.core.frontend.fxextends.IconView;

import java.util.Comparator;
import java.util.Objects;

public abstract class Completation implements Comparable<Completation>
{
    protected String value;
    private String iconName;

    public Completation(String value, String iconName)
    {
        this.value = value;
        this.iconName = iconName;
    }
    
    public Completation(String iconName)
    {
        this.iconName = iconName;
    }

    public String getValue()
    {
        return value;
    }

    public IconView getIcon()
    {
        return new IconView(iconName);
    }

    public abstract String getDescription();
    
    public abstract String getCompletation();

    @Override
    public int compareTo(Completation o)
    {
        Comparator<Completation> comparator = Comparator.comparing(Completation::getValue)
                .thenComparingInt(a -> a.getCompletation().length());
        
        return comparator.compare(this, o);
    }

    @Override
    public String toString()
    {
        return getValue();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        Completation that = (Completation) o;
        
        return Objects.equals(value, that.value)
                && Objects.equals(getCompletation(), that.getCompletation());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(value, iconName);
    }
}
