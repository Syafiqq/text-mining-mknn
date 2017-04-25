package case_1.model.mknn;

import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 6:31 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class TermContainerImpl extends TermContainer
{
    @Override public void collectTerms(@NotNull Documents document)
    {
        super.getTerms().addAll(((DocumentImpl) document).getTokenize());
    }
}
