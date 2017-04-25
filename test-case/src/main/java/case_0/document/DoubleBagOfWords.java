package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.StringTerm;
import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 19 April 2017, 7:40 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DoubleBagOfWords extends BagOfWords
{
    @NotNull private final Object2DoubleMap<StringTerm> bow;

    public DoubleBagOfWords()
    {
        this.bow = new Object2DoubleLinkedOpenHashMap<>();
    }

    @Override public void setTerms(@NotNull TermContainer terms)
    {
        @NotNull final Object2DoubleMap<StringTerm> _bow = this.getBow();
        _bow.clear();
        terms.getTerms().forEach(term -> _bow.put((StringTerm) term, 0));
    }

    @Override public void checkExistence(@NotNull Documents document)
    {
        ((DoubleBagOfWords) document.getBagOfWords()).getBow().forEach((term, count) ->
        {
            if(count > 0)
            {
                this.increment(term);
            }
        });
    }

    public void increment(@NotNull StringTerm term)
    {
        this.put(term, this.getDouble(term) + 1);
    }

    public double put(StringTerm key, double value)
    {
        return bow.put(key, value);
    }

    public double getDouble(StringTerm key)
    {
        return bow.getDouble(key);
    }

    public boolean containsKey(StringTerm key)
    {
        return bow.containsKey(key);
    }

    @NotNull public Object2DoubleMap<StringTerm> getBow()
    {
        return this.bow;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("bow", bow)
                .toString();
    }
}
