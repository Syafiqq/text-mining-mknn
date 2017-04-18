package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import org.apache.commons.lang3.builder.ToStringBuilder;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:56 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class BagOfWordsImpl extends BagOfWords
{
    private Object2IntLinkedOpenHashMap<String> bag;

    public BagOfWordsImpl()
    {
        this.bag = new Object2IntLinkedOpenHashMap<>();
    }

    public int increment(String term)
    {
        return this.bag.addTo(term, 1);
    }

    public int put(String s, int v)
    {
        return bag.put(s, v);
    }

    public int getInt(String k)
    {
        return bag.getInt(k);
    }

    public boolean containsKey(String k)
    {
        return bag.containsKey(k);
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("bag", bag)
                .toString();
    }
}
