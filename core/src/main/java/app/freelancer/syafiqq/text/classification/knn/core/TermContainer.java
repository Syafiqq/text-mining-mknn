package app.freelancer.syafiqq.text.classification.knn.core;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 7:21 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class TermContainer<T extends Term>
{
    @NotNull protected List<T> terms;

    public TermContainer()
    {
        this.terms = new LinkedList<>();
    }

    public boolean add(T t)
    {
        if(!this.terms.contains(t))
        {
            return terms.add(t);
        }
        return false;
    }

    @NotNull public List<T> getTerms()
    {
        return this.terms;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("terms", terms)
                .toString();
    }
}
