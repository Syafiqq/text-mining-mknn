package app.freelancer.syafiqq.text.classification.knn.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 4:59 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class Documents
{
    @Nullable protected Class      clazz;
    @NotNull protected  BagOfWords bagOfWords;

    public Documents(@Nullable Class clazz, @NotNull BagOfWords bagOfWords)
    {
        this.clazz = clazz;
        this.bagOfWords = bagOfWords;
    }

    @Nullable public Class getClazz()
    {
        return this.clazz;
    }

    public void setClazz(@Nullable Class clazz)
    {
        this.clazz = clazz;
    }

    @NotNull public BagOfWords getBagOfWords()
    {
        return this.bagOfWords;
    }

    public void setBagOfWords(@NotNull BagOfWords bagOfWords)
    {
        this.bagOfWords = bagOfWords;
    }

    public abstract void preProcess();

    public abstract void tokenize();

    public abstract void collectTerms(@Nullable TermContainer terms);

    public abstract void findTermHighOccurrence(@NotNull TermCounter container);

    public abstract void normalizeBOW(@NotNull TermCounter container);

    public abstract void findTermExistence(@NotNull BagOfWords dfi);

    public abstract void calculateTFIDF(@NotNull BagOfWords idf);

    public abstract void calculateSimilarity(@NotNull Documents unclassified);

    @Override public String toString()
    {
        return "Documents{" +
                "clazz=" + clazz +
                ", bagOfWords=" + bagOfWords +
                '}';
    }
}
