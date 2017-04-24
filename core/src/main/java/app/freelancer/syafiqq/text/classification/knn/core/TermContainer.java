package app.freelancer.syafiqq.text.classification.knn.core;

import java.util.LinkedHashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 7:21 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "StringBufferReplaceableByString"}) public abstract class TermContainer
{
    @NotNull protected Set<Term> terms;

    public TermContainer()
    {
        this.terms = new LinkedHashSet<>();
    }

    public boolean add(Term t)
    {
        return !this.terms.contains(t) && terms.add(t);
    }

    @NotNull public Set<Term> getTerms()
    {
        return this.terms;
    }

    public abstract void collectTerms(@NotNull Documents document);

    @Override public String toString()
    {
        final StringBuilder sb = new StringBuilder("TermContainer{");
        sb.append("terms=").append(terms);
        sb.append('}');
        return sb.toString();
    }
}
