package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 6:30 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings({"unused", "WeakerAccess"}) public class BagOfWordsImpl extends BagOfWords
{
    @NotNull private final Object2DoubleMap<TermImpl> bow;

    public BagOfWordsImpl()
    {
        this.bow = new Object2DoubleLinkedOpenHashMap<>();
    }

    @Override public void setTerms(@NotNull TermContainer terms)
    {
        @NotNull final Object2DoubleMap<TermImpl> _bow = this.getBow();
        _bow.clear();
        terms.getTerms().forEach(term -> _bow.put((TermImpl) term, 0));
    }

    @Override public void checkExistence(@NotNull Documents document)
    {
        ((BagOfWordsImpl) document.getBagOfWords()).getBow().forEach((term, count) ->
        {
            if(count > 0)
            {
                this.increment(term);
            }
        });
    }

    public void increment(@NotNull TermImpl term)
    {
        this.put(term, this.getDouble(term) + 1);
    }

    public double put(TermImpl key, double value)
    {
        return bow.put(key, value);
    }

    public double getDouble(TermImpl key)
    {
        return bow.getDouble(key);
    }

    public boolean containsKey(TermImpl key)
    {
        return bow.containsKey(key);
    }

    @NotNull public Object2DoubleMap<TermImpl> getBow()
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
