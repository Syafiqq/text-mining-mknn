package case_0;

import app.freelancer.syafiqq.text.classification.knn.core.KNN;
import case_0.document.DoubleBagOfWords;
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
        @NotNull final DoubleBagOfWords dfi  = (DoubleBagOfWords) super.DFI;
        @NotNull final DoubleBagOfWords idf  = (DoubleBagOfWords) super.IDF;
        final double                    size = (double) super.classified.size();
        dfi.getBow().forEach((term, occurrence) -> idf.put(term, FastMath.log10(size / occurrence)));
    }
}
