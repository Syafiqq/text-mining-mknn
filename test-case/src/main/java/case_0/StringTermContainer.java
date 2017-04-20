package case_0;

import app.freelancer.syafiqq.text.classification.knn.core.Class;
import app.freelancer.syafiqq.text.classification.knn.core.Term;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.clazz.IntegerClass;
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
    @Override public void collectTerms(@NotNull Class clazz)
    {
        for(@NotNull final Term term : ((IntegerClass) clazz).getTermContainer().getTerms())
        {
            if(!super.terms.contains(term))
            {
                super.terms.add(term);
            }
        }
    }
}
