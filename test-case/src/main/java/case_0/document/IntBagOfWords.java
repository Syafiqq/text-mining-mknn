package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import app.freelancer.syafiqq.text.classification.knn.core.Term;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.IntTermCounter;
import case_0.StringTerm;
import case_0.StringTermContainer;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:56 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class IntBagOfWords extends BagOfWords
{
    @NotNull private Object2IntLinkedOpenHashMap<StringTerm> bow;

    public IntBagOfWords()
    {
        this.bow = new Object2IntLinkedOpenHashMap<>();
    }

    public int increment(StringTerm term)
    {
        return this.bow.addTo(term, 1);
    }

    public int put(StringTerm s, int v)
    {
        return bow.put(s, v);
    }

    public int getInt(StringTerm k)
    {
        return bow.getInt(k);
    }

    public boolean containsKey(StringTerm k)
    {
        return bow.containsKey(k);
    }

    public void getMaximumWord(@NotNull IntTermCounter maxTermFrequency)
    {
        for(final Object2IntMap.Entry<StringTerm> term : this.bow.object2IntEntrySet())
        {
            if(term.getIntValue() > maxTermFrequency.getCount())
            {
                maxTermFrequency.setProperties(term.getKey(), term.getIntValue());
            }
        }
    }

    @NotNull public Object2IntLinkedOpenHashMap<StringTerm> getBow()
    {
        return this.bow;
    }

    public void setBow(@NotNull Object2IntLinkedOpenHashMap<StringTerm> bow)
    {
        this.bow = bow;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("bow", bow)
                .toString();
    }

    public void checkExistence(@NotNull IntBagOfWords dfi)
    {
        for(final StringTerm term : dfi.getBow().keySet())
        {
            if(this.getInt(term) > 0)
            {
                dfi.increment(term);
            }
        }
    }

    @Override public void setTerms(@NotNull TermContainer terms)
    {
        @NotNull final StringTermContainer _terms = (StringTermContainer) terms;
        this.getBow().clear();
        for(final Term term : _terms.getTerms())
        {
            this.put((StringTerm) term, 0);
        }
    }
}
