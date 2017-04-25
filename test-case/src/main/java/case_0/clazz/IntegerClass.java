package case_0.clazz;

import app.freelancer.syafiqq.text.classification.knn.core.Class;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:11 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */


@SuppressWarnings("unused") public class IntegerClass extends Class
{
    private int clazz;

    public IntegerClass(int clazz)
    {
        this.clazz = clazz;
    }

    public int getClazz()
    {
        return this.clazz;
    }

    public void setClazz(int clazz)
    {
        this.clazz = clazz;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof IntegerClass))
        {
            return false;
        }

        IntegerClass that = (IntegerClass) o;

        return new EqualsBuilder()
                .append(getClazz(), that.getClazz())
                .isEquals();
    }

    @Override public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getClazz())
                .toHashCode();
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("clazz", clazz)
                .toString();
    }
}
