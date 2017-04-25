package case_1.model.dataset;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 23 April 2017, 10:08 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MLocale
{
    public static final int UNASSIGNED_PK = -1;

    private int    id;
    private String name;

    public MLocale()
    {
    }

    public MLocale(String name)
    {
        this(UNASSIGNED_PK, name);
    }

    public MLocale(@NotNull MLocale locale)
    {
        this(locale.id, locale.name);
    }

    public MLocale(int id, String name)
    {
        this.setId(id);
        this.setName(name);
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof MLocale))
        {
            return false;
        }

        MLocale locale = (MLocale) o;

        return new EqualsBuilder()
                .append(getId(), locale.getId())
                .append(getName(), locale.getName())
                .isEquals();
    }

    @Override public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getName())
                .toHashCode();
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
