package case_0;

import app.freelancer.syafiqq.text.classification.knn.core.KNN;
import case_0.document.DoubleBagOfWords;
import case_0.document.IntBagOfWords;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 19 April 2017, 8:35 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class KNNImpl extends KNN
{
    @Override protected void calculateIDF()
    {
        @NotNull IntBagOfWords    dfi = (IntBagOfWords) super.DFI;
        @NotNull DoubleBagOfWords idf = (DoubleBagOfWords) super.IDF;
        for(final Object2IntMap.Entry<StringTerm> word : dfi.getBow().object2IntEntrySet())
        {
            idf.put(word.getKey(), FastMath.log10((double) super.terms.getTerms().size() / word.getIntValue()));
        }
    }
}
