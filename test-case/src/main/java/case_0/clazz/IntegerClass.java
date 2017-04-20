package case_0.clazz;

import app.freelancer.syafiqq.text.classification.knn.core.Class;
import case_0.StringTerm;
import case_0.StringTermContainer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:11 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */


@SuppressWarnings("unused") public class IntegerClass extends Class
{
    private          int                 clazz;
    @NotNull private StringTermContainer terms;


    public IntegerClass(int clazz)
    {
        this.clazz = clazz;
        this.terms = new StringTermContainer();
    }

    public int getClazz()
    {
        return this.clazz;
    }

    public void setClazz(int clazz)
    {
        this.clazz = clazz;
    }

    public void addTerm(StringTerm stringTerm)
    {
        this.terms.add(stringTerm);
    }

    @NotNull public StringTermContainer getTermContainer()
    {
        return this.terms;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("clazz", clazz)
                .append("terms", terms)
                .toString();
    }
}
