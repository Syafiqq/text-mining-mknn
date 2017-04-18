package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.StringTerm;
import case_0.clazz.IntegerClass;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
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
public class Journal extends Documents<IntegerClass, BagOfWordsImpl, TermContainer<StringTerm>>
{
    @NotNull private String       documents;
    @NotNull private List<String> tokenizeDocuments;

    public Journal(@Nullable IntegerClass clazz, @NotNull BagOfWordsImpl bow, @NotNull String documents)
    {
        super(clazz, bow);
        this.documents = documents;
        this.tokenizeDocuments = new LinkedList<>();
    }

    @NotNull public String getDocuments()
    {
        return this.documents;
    }

    public void setDocuments(@NotNull String documents)
    {
        this.documents = documents;
    }

    @Override public void preProcess()
    {
        this.documents = this.documents.toLowerCase(new Locale("ind", "ID", "ID"));
    }

    @Override public void tokenize()
    {
        @NotNull final StringTokenizer tokenizer = new StringTokenizer(this.documents, " ");
        while(tokenizer.hasMoreTokens())
        {
            this.tokenizeDocuments.add(tokenizer.nextToken());
        }
    }

    @Override public void collectTerms(@Nullable TermContainer<StringTerm> terms, @NotNull BagOfWordsImpl bagOfWords)
    {
        if(terms != null)
        {
            for(@NotNull final StringTerm term : terms.getTerms())
            {
                bagOfWords.put(term.getTerm(), 0);
            }

            for(@NotNull final StringTerm term : terms.getTerms())
            {
                @NotNull final String t = term.getTerm();
                for(@NotNull final String token : this.tokenizeDocuments)
                {
                    if(t.contentEquals(token))
                    {
                        bagOfWords.increment(t);
                    }
                }
            }
        }
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("clazz", clazz)
                .append("bagOfWords", bagOfWords)
                .append("documents", documents)
                .toString();
    }
}
