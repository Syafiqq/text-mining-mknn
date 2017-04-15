package app.freelancer.syafiqq.text.classification.knn.core.document;

import app.freelancer.syafiqq.text.classification.knn.core.Class;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 4:59 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class ClassifiedDocuments extends Documents
{
    @NotNull private Class clazz;

    public ClassifiedDocuments(@NotNull Class clazz)
    {
        this.clazz = clazz;
    }

    @NotNull public Class getClazz()
    {
        return this.clazz;
    }

    public void setClazz(@NotNull Class clazz)
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
