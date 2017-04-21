package app.freelancer.syafiqq.text.classification.knn.core;

import java.util.List;
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

    public abstract void findTermExistence(@NotNull BagOfWords dfi);

    public abstract void calculateTFIDF(@NotNull BagOfWords idf);

    public abstract void calculateSimilarity(@NotNull Documents unclassified);

    public abstract int orderBySimilarity(@NotNull Documents document1);

    public abstract void calculateValidity(@NotNull List<Documents> collection);

    public abstract void calculateWeightVoting();

    @Override public String toString()
    {
        final StringBuilder sb = new StringBuilder("Documents{");
        sb.append("clazz=").append(clazz);
        sb.append(", bagOfWords=").append(bagOfWords);
        sb.append('}');
        return sb.toString();
    }
}
