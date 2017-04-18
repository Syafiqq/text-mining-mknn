package app.freelancer.syafiqq.text.classification.knn.core;

import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 7:21 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("WeakerAccess") public class TermContainer<T extends Term>
{
    @NotNull protected List<T> terms;

    public TermContainer()
    {
        this.terms = new LinkedList<>();
    }

    public boolean add(T t)
    {
        return !this.terms.contains(t) && terms.add(t);
    }

    @NotNull public List<T> getTerms()
    {
        return this.terms;
    }

    @Override public String toString()
    {
        return "TermContainer{" +
                "terms=" + terms +
                '}';
    }
}
