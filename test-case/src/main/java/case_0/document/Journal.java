package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import case_0.clazz.IntegerClass;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 5:36 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Journal extends Documents<IntegerClass, BagOfWords>
{
    @NotNull private String documents;

    public Journal(@Nullable IntegerClass clazz, @NotNull BagOfWords bow, @NotNull String documents)
    {
        super(clazz, bow);
        this.documents = documents;
    }

    @NotNull public String getDocuments()
    {
        return this.documents;
    }

    public void setDocuments(@NotNull String documents)
    {
        this.documents = documents;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("clazz", clazz)
                .append("documents", documents)
                .toString();
    }
}
