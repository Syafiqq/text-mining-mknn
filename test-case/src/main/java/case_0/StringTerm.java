package case_0;

import app.freelancer.syafiqq.text.classification.knn.core.Term;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 7:40 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class StringTerm extends Term
{
    @NotNull private String term;

    public StringTerm(@NotNull String term)
    {
        this.term = term;
    }

    @NotNull public String getTerm()
    {
        return this.term;
    }

    public void setTerm(@NotNull String term)
    {
        this.term = term;
    }

    @Override public boolean equals(Object obj)
    {
        return obj instanceof StringTerm && this.term.contentEquals(((StringTerm) obj).term);
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("term", term)
                .toString();
    }
}
