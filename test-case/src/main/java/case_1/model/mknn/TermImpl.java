package case_1.model.mknn;

import app.freelancer.syafiqq.text.classification.knn.core.Term;
import java.util.Locale;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 6:32 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings({"WeakerAccess", "unused"}) public class TermImpl extends Term
{
    @NotNull private String term;

    public TermImpl(@NotNull String term)
    {
        this.term = term.toLowerCase(new Locale("id", "ID", "ID"));
        this.term = this.term.toLowerCase(Locale.US);
    }

    @NotNull public String getTerm()
    {
        return this.term;
    }

    public void setTerm(@NotNull String term)
    {
        this.term = term;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof TermImpl))
        {
            return false;
        }

        TermImpl term1 = (TermImpl) o;

        return new EqualsBuilder()
                .append(getTerm(), term1.getTerm())
                .isEquals();
    }

    @Override public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getTerm())
                .toHashCode();
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("term", term)
                .toString();
    }
}
