package app.freelancer.syafiqq.text.classification.knn.core;

import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:50 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class BagOfWords
{
    public abstract void setTerms(@NotNull TermContainer terms);

    public abstract void checkExistence(@NotNull Documents document);
}
