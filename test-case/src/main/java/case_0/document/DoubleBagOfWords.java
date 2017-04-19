package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import app.freelancer.syafiqq.text.classification.knn.core.Term;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.StringTerm;
import case_0.StringTermContainer;
import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
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
    @NotNull private Object2DoubleLinkedOpenHashMap<StringTerm> bow;

    public DoubleBagOfWords()
    {
        this.bow = new Object2DoubleLinkedOpenHashMap<>();
    }

    public double put(StringTerm stringTerm, double v)
    {
        return bow.put(stringTerm, v);
    }

    public double getDouble(Object k)
    {
        return bow.getDouble(k);
    }

    public boolean containsKey(Object k)
    {
        return bow.containsKey(k);
    }

    public Object2DoubleLinkedOpenHashMap<StringTerm> getBow()
    {
        return this.bow;
    }

    public void setBow(Object2DoubleLinkedOpenHashMap<StringTerm> bow)
    {
        this.bow = bow;
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

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("bow", bow)
                .toString();
    }
}
