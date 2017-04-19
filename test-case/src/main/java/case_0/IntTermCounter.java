package case_0;

import app.freelancer.syafiqq.text.classification.knn.core.TermCounter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 19 April 2017, 6:38 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class IntTermCounter extends TermCounter
{
    @Nullable private StringTerm term;
    private           int        count;

    public IntTermCounter(@Nullable StringTerm term, int count)
    {
        this.term = term;
        this.count = count;
    }

    public void setProperties(@NotNull StringTerm term, int count)
    {
        this.setTerm(term);
        this.setCount(count);
    }

    @Nullable public StringTerm getTerm()
    {
        return this.term;
    }

    public void setTerm(@NotNull StringTerm term)
    {
        this.term = term;
    }

    public int getCount()
    {
        return this.count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("term", term)
                .append("count", count)
                .toString();
    }
}
