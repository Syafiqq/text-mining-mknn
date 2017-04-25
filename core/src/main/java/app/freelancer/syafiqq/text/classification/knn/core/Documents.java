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
@SuppressWarnings({"WeakerAccess", "unused", "StringBufferReplaceableByString"}) public abstract class Documents
{
    @Nullable protected Class      clazz;
    @Nullable protected Class      classified;
    @NotNull protected  BagOfWords bagOfWords;

    public Documents(@Nullable Class clazz, @NotNull BagOfWords bagOfWords)
    {
        this.clazz = clazz;
        this.classified = null;
        this.bagOfWords = bagOfWords;
    }

    @Nullable public Class getClassified()
    {
        return this.classified;
    }

    public void setClassified(@Nullable Class classified)
    {
        this.classified = classified;
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

    public abstract void countTerms(@NotNull TermContainer terms);

    public abstract void calculateTFIDF(@NotNull BagOfWords idf);

    public abstract void calculateSimilarity(@NotNull Documents document, @NotNull TermContainer terms);

    public abstract void orderSimilarity();

    public abstract void calculateValidity(int k);

    public abstract void calculateWeightVoting(int k);

    public abstract void summarize(@NotNull List<Class> classes);

    public abstract void orderAndVote();

    @Override public String toString()
    {
        @NotNull final StringBuilder sb = new StringBuilder("Documents{");
        sb.append("clazz=").append(clazz);
        sb.append(", classified=").append(classified);
        sb.append(", bagOfWords=").append(bagOfWords);
        sb.append('}');
        return sb.toString();
    }
}
