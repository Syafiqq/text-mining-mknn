package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.dataset;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 23 April 2017, 6:40 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("WeakerAccess") public class MVocabulary
{
    public static final int UNASSIGNED_PK = -1;

    @NotNull private MLocale locale;
    private          int     id;
    private          String  word;

    public MVocabulary(int id, String word, @NotNull MLocale locale)
    {
        this.id = id;
        this.word = word;
        this.locale = locale;
    }

    public MVocabulary(String word, @NotNull MLocale locale)
    {
        this(UNASSIGNED_PK, word, locale);
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getWord()
    {
        return this.word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    @NotNull public MLocale getLocale()
    {
        return this.locale;
    }

    public void setLocale(@NotNull MLocale locale)
    {
        this.locale = locale;
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof MVocabulary))
        {
            return false;
        }

        MVocabulary mQuery = (MVocabulary) o;

        return new EqualsBuilder()
                .append(getId(), mQuery.getId())
                .append(getWord(), mQuery.getWord())
                .append(getLocale(), mQuery.getLocale())
                .isEquals();
    }

    @Override public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getWord())
                .append(getLocale())
                .toHashCode();
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("word", word)
                .append("locale", locale)
                .toString();
    }
}
