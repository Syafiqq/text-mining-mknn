package case_0;

import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.document.Journal;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 19 April 2017, 5:14 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class StringTermContainer extends TermContainer
{
    @Override public void collectTerms(@NotNull Documents document)
    {
        super.getTerms().addAll(((Journal) document).getTokenize());
    }
}
