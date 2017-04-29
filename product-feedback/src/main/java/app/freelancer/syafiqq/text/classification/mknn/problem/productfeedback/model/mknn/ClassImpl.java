package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn;

import app.freelancer.syafiqq.text.classification.knn.core.Class;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.dataset.MClass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 6:27 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings({"WeakerAccess", "unused"}) public class ClassImpl extends Class
{
    @NotNull private MClass clazz;

    public ClassImpl(@NotNull MClass clazz)
    {
        this.clazz = clazz;
    }

    @NotNull public MClass getClazz()
    {
        return this.clazz;
    }

    public void setClazz(@NotNull MClass clazz)
    {
        this.clazz = clazz;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof ClassImpl))
        {
            return false;
        }

        ClassImpl that = (ClassImpl) o;

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
