package app.freelancer.syafiqq.text.classification.knn.core.document;

import app.freelancer.syafiqq.text.classification.knn.core.Class;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 4:59 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class Documents<C extends Class>
{
    @Nullable protected C clazz;

    public Documents(@Nullable C clazz)
    {
        this.clazz = clazz;
    }

    @Nullable public C getClazz()
    {
        return this.clazz;
    }

    public void setClazz(@Nullable C clazz)
    {
        this.clazz = clazz;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("clazz", clazz)
                .toString();
    }
}
