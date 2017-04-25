package case_1.model.dataset;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 8:35 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings({"WeakerAccess", "unused"}) public class MQuery
{
    public static final int UNASSIGNED_PK = -1;

    @Nullable private MClass clazz;
    private           int    id;
    private           String query;

    public MQuery(int id, String query, @Nullable MClass mClass)
    {
        this.id = id;
        this.query = query;
        this.clazz = mClass;
    }

    public MQuery(String query, @Nullable MClass mClass)
    {
        this(UNASSIGNED_PK, query, mClass);
    }

    public MQuery(String query)
    {
        this(UNASSIGNED_PK, query, null);
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getQuery()
    {
        return this.query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    @Nullable public MClass getClazz()
    {
        return this.clazz;
    }

    public void setClazz(@Nullable MClass clazz)
    {
        this.clazz = clazz;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof MQuery))
        {
            return false;
        }

        MQuery mQuery = (MQuery) o;

        return new EqualsBuilder()
                .append(getId(), mQuery.getId())
                .append(getQuery(), mQuery.getQuery())
                .append(getClazz(), mQuery.getClazz())
                .isEquals();
    }

    @Override public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getQuery())
                .append(getClazz())
                .toHashCode();
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("query", query)
                .append("clazz", clazz)
                .toString();
    }
}
