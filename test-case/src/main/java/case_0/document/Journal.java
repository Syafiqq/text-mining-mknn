package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.Term;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import app.freelancer.syafiqq.text.classification.knn.core.TermCounter;
import case_0.IntTermCounter;
import case_0.StringTerm;
import case_0.clazz.IntegerClass;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
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
public class Journal extends Documents
{
    @NotNull private  String           documents;
    @NotNull private  List<String>     tokenizeDocuments;
    @Nullable private DoubleBagOfWords normalizeBOW;

    public Journal(@Nullable IntegerClass clazz, @NotNull IntBagOfWords bow, @NotNull String documents)
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

    @Override public void collectTerms(@Nullable TermContainer terms, @NotNull BagOfWords bagOfWords)
    {
        @NotNull final IntBagOfWords _bagOfWords = (IntBagOfWords) bagOfWords;
        if(terms != null)
        {
            for(final Term term : terms.getTerms())
            {
                _bagOfWords.put((StringTerm) term, 0);
            }

            for(final Term term : terms.getTerms())
            {
                @NotNull final StringTerm _term = (StringTerm) term;
                for(@NotNull final String token : this.tokenizeDocuments)
                {
                    if(_term.getTerm().contentEquals(token))
                    {
                        _bagOfWords.increment(_term);
                    }
                }
            }
        }
    }

    @Override public void normalizeBOW(@NotNull TermCounter counter)
    {
        final @NotNull IntTermCounter _counter = (IntTermCounter) counter;
        this.normalizeBOW = new DoubleBagOfWords();
        @NotNull Object2DoubleMap<StringTerm> normalizeBOW = this.normalizeBOW.getBow();
        for(@NotNull final Object2IntMap.Entry<StringTerm> word : ((IntBagOfWords) this.bagOfWords).getBow().object2IntEntrySet())
        {
            normalizeBOW.put(word.getKey(), word.getIntValue() / _counter.getCount());
        }
    }

    @Override public void checkExistence(@NotNull BagOfWords dfi)
    {
        ((IntBagOfWords) this.bagOfWords).checkExistence((IntBagOfWords) dfi);
    }

    @Override public void getMaximumWord(@NotNull TermCounter container)
    {
        ((IntBagOfWords) this.bagOfWords).getMaximumWord((IntTermCounter) container);
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("clazz", clazz)
                .append("bagOfWords", bagOfWords)
                .append("classified", documents)
                .toString();
    }
}
