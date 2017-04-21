package app.freelancer.syafiqq.text.classification.knn.core;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 5:26 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class Class
{
    public abstract void summarizeVoting(@NotNull List<Documents> collect);

    public abstract int orderByWeight(@NotNull Class clazz);
}
