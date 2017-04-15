package case_0.clazz;

import app.freelancer.syafiqq.text.classification.knn.core.Class;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.StringTerm;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:11 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */


public class IntegerClass extends Class
{
    private          int                       clazz;
    @NotNull private TermContainer<StringTerm> terms;


    public IntegerClass(int clazz)
    {
        this.clazz = clazz;
        this.terms = new TermContainer<>();
    }

    public int getClazz()
    {
        return this.clazz;
    }

    public void setClazz(int clazz)
    {
        this.clazz = clazz;
    }

    public boolean addTerm(StringTerm stringTerm)
    {
        return this.terms.add(stringTerm);
    }

    @NotNull public TermContainer<StringTerm> getTermContainer()
    {
        return this.terms;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("clazz", clazz)
                .toString();
    }

    @Override public void collectTerms(@NotNull TermContainer terms)
    {
        @NotNull final List<StringTerm> container = terms.getTerms();
        for(@NotNull final StringTerm term : this.terms.getTerms())
        {
            if(!container.contains(term))
            {
                container.add(term);
            }
        }
    }
}
