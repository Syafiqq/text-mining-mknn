package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn;

import app.freelancer.syafiqq.text.classification.knn.core.KNN;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 8:01 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("ConstantConditions") public class MKNN extends KNN
{
    @Override protected void calculateIDF()
    {
        @NotNull final BagOfWordsImpl dfi  = (BagOfWordsImpl) super.DFI;
        @NotNull final BagOfWordsImpl idf  = (BagOfWordsImpl) super.IDF;
        final double                  size = (double) super.classified.size();
        dfi.getBow().forEach((term, occurrence) -> idf.put(term, FastMath.log10(size / occurrence)));
    }
}
