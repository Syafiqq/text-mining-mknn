package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.Term;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.StringTerm;
import case_0.clazz.IntegerClass;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;
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
    @Nullable private DoubleBagOfWords tfIdf;
    @Nullable private DoubleBagOfWords tfIdf2;
    private           double           similarity;

    public Journal(@Nullable IntegerClass clazz, @NotNull IntBagOfWords bow, @NotNull String documents)
    {
        super(clazz, bow);
        this.documents = documents;
        this.tokenizeDocuments = new LinkedList<>();
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

    @Override public void collectTerms(@Nullable TermContainer terms)
    {
        @NotNull final IntBagOfWords _bagOfWords = (IntBagOfWords) super.bagOfWords;
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

    @Override public void findTermExistence(@NotNull BagOfWords dfi)
    {
        ((IntBagOfWords) this.bagOfWords).checkExistence((IntBagOfWords) dfi);
    }

    @Override public void calculateTFIDF(@NotNull BagOfWords idf)
    {
        final @NotNull DoubleBagOfWords _idf = (DoubleBagOfWords) idf;
        if(this.tfIdf == null)
        {
            this.tfIdf = new DoubleBagOfWords();
        }
        else
        {
            this.tfIdf.getBow().clear();
        }

        if(this.tfIdf2 == null)
        {
            this.tfIdf2 = new DoubleBagOfWords();
        }
        else
        {
            this.tfIdf2.getBow().clear();
        }
        //@NotNull Object2DoubleMap<StringTerm> tfidf = this.tfIdf.getBow();
        //@NotNull Object2DoubleMap<StringTerm> tfidf2 = this.tfIdf2.getBow();
        for(final Object2IntMap.Entry<StringTerm> term : ((IntBagOfWords) this.bagOfWords).getBow().object2IntEntrySet())
        {
            @NotNull final StringTerm _term = term.getKey();
            this.tfIdf.put(_term, term.getIntValue() * _idf.getDouble(_term));
            this.tfIdf2.put(_term, FastMath.pow(this.tfIdf.getDouble(_term), 2.0));
        }
    }

    @Override public void calculateSimilarity(@NotNull Documents unclassified)
    {
        @NotNull final Journal _unclassified = (Journal) unclassified;

        double qD = 0.0;
        double q_ = 0.0;
        double d_ = 0.0;
        for(@NotNull final Object2DoubleMap.Entry<StringTerm> tfidf : _unclassified.tfIdf.getBow().object2DoubleEntrySet())
        {
            @NotNull final StringTerm _term = tfidf.getKey();
            qD += (tfidf.getDoubleValue() * this.tfIdf.getDouble(_term));
            q_ += _unclassified.tfIdf2.getDouble(_term);
            d_ += this.tfIdf2.getDouble(_term);
        }

        this.similarity = qD / (FastMath.sqrt(q_) * FastMath.sqrt(d_));
    }

    @NotNull public String getDocuments()
    {
        return this.documents;
    }

    public void setDocuments(@NotNull String documents)
    {
        this.documents = documents;
    }

    @NotNull public List<String> getTokenizeDocuments()
    {
        return this.tokenizeDocuments;
    }

    public void setTokenizeDocuments(@NotNull List<String> tokenizeDocuments)
    {
        this.tokenizeDocuments = tokenizeDocuments;
    }

    @Nullable public DoubleBagOfWords getTfIdf()
    {
        return this.tfIdf;
    }

    @Nullable public DoubleBagOfWords getTfIdf2()
    {
        return this.tfIdf2;
    }

    public double getSimilarity()
    {
        return this.similarity;
    }

    public void setSimilarity(double similarity)
    {
        this.similarity = similarity;
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
