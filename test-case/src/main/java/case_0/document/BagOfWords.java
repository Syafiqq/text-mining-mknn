package case_0.document;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:56 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class BagOfWords extends app.freelancer.syafiqq.text.classification.knn.core.BagOfWords
{
    private Object2IntLinkedOpenHashMap<String> bag;

    public BagOfWords()
    {
        this.bag = new Object2IntLinkedOpenHashMap<>();
    }

    public Integer increment(String term)
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
}
