package case_0.document;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import app.freelancer.syafiqq.text.classification.knn.core.Class;
import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.Term;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.StringTerm;
import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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
    @NotNull private final static Locale ID_LOCALE = new Locale("id", "ID", "ID");

    @NotNull private String                      documents;
    @NotNull private List<StringTerm>            tokenize;
    @NotNull private Object2DoubleMap<Documents> weightVoting;
    @NotNull private DoubleBagOfWords            tfIdf;
    @NotNull private DoubleBagOfWords            tfIdf2;
    @NotNull private Object2DoubleMap<Documents> similarity;
    @NotNull private Object2DoubleMap<Class>     classVoting;

    private String cleanedDocument;
    private double validity;

    public Journal(@NotNull String query, @Nullable Class clazz, @NotNull BagOfWords bagOfWords)
    {
        super(clazz, bagOfWords);
        this.documents = query;
        this.tokenize = new LinkedList<>();
        this.weightVoting = new Object2DoubleLinkedOpenHashMap<>();
        this.tfIdf = new DoubleBagOfWords();
        this.tfIdf2 = new DoubleBagOfWords();
        this.similarity = new Object2DoubleLinkedOpenHashMap<>();
        this.classVoting = new Object2DoubleLinkedOpenHashMap<>();
    }

    @Override public void preProcess()
    {
        this.cleaning();
        this.tokenize();
    }

    private void cleaning()
    {
        this.cleanedDocument = this.documents;
        //To Lower Case
        this.cleanedDocument = this.cleanedDocument.toLowerCase(Locale.US);
        this.cleanedDocument = this.cleanedDocument.toLowerCase(ID_LOCALE);
    }

    private void tokenize()
    {
        @NotNull final Analyzer          analyzer    = new WhitespaceAnalyzer();
        @NotNull TokenStream             tokenStream = analyzer.tokenStream("contents", new StringReader(this.cleanedDocument));
        @NotNull final CharTermAttribute term        = tokenStream.addAttribute(CharTermAttribute.class);

        try
        {
            tokenStream.reset();
            while(tokenStream.incrementToken())
            {
                final String _term = term.toString();
                this.tokenize.add(new StringTerm(_term));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void countTerms(@NotNull TermContainer terms)
    {
        @NotNull final DoubleBagOfWords             _bow      = (DoubleBagOfWords) super.bagOfWords;
        final @NotNull Object2DoubleMap<StringTerm> __bow     = _bow.getBow();
        final @NotNull Set<Term>                    container = terms.getTerms();
        this.tokenize.stream().distinct().collect(Collectors.toList()).forEach(term -> __bow.put(term, 0));
        this.tokenize.forEach(term ->
        {
            if(container.contains(term))
            {
                _bow.increment(term);
            }
        });
    }

    @Override public void calculateTFIDF(@NotNull BagOfWords idf)
    {
        final @NotNull DoubleBagOfWords             _idf   = (DoubleBagOfWords) idf;
        final @NotNull Object2DoubleMap<StringTerm> tfIdf  = this.tfIdf.getBow();
        final @NotNull Object2DoubleMap<StringTerm> tfIdf2 = this.tfIdf2.getBow();

        tfIdf.clear();
        tfIdf2.clear();

        ((DoubleBagOfWords) super.bagOfWords).getBow().forEach((term, occurrence) ->
        {
            final double val = occurrence * _idf.getDouble(term);
            tfIdf.put(term, val);
            tfIdf2.put(term, FastMath.pow(val, 2.0));
        });
    }

    @Override public void calculateSimilarity(@NotNull Documents document, @NotNull TermContainer terms)
    {
        final @NotNull Object2DoubleMap<StringTerm> tfIdf    = this.tfIdf.getBow();
        final @NotNull Object2DoubleMap<StringTerm> tfIdf_1  = ((Journal) document).tfIdf.getBow();
        final @NotNull Object2DoubleMap<StringTerm> tfIdf2   = this.tfIdf2.getBow();
        final @NotNull Object2DoubleMap<StringTerm> tfIdf2_1 = ((Journal) document).tfIdf2.getBow();

        if((tfIdf.size() > 0) && (tfIdf_1.size() > 0))
        {
            tfIdf.defaultReturnValue(0.0);
            tfIdf_1.defaultReturnValue(0.0);
            tfIdf2.defaultReturnValue(0.0);
            tfIdf2_1.defaultReturnValue(0.0);

            double qD = 0.0;
            double q_ = 0.0;
            double d_ = 0.0;
            for(final Term term : terms.getTerms())
            {
                qD += (tfIdf_1.getDouble(term) * tfIdf.getDouble(term));
                q_ += tfIdf2_1.getDouble(term);
                d_ += tfIdf2.getDouble(term);
            }
            this.similarity.put(document, qD / (FastMath.sqrt(q_) * FastMath.sqrt(d_)));
        }
        else
        {
            this.similarity.put(document, 0);
        }
    }

    @Override public void orderSimilarity()
    {
        @NotNull final Object2DoubleMap<Documents> _similarity = this.similarity;
        @NotNull final Object2DoubleMap<Documents> tmp         = new Object2DoubleLinkedOpenHashMap<>(this.similarity);
        _similarity.clear();
        tmp.object2DoubleEntrySet()
           .stream()
           .sorted((o1, o2) -> -Double.compare(o1.getDoubleValue(), o2.getDoubleValue()))
           .forEachOrdered(document -> _similarity.put(document.getKey(), document.getDoubleValue()));
        tmp.clear();
    }

    @Override public void calculateValidity(int k)
    {
        int s = 0;
        for(@NotNull final Documents documents : this.similarity.keySet().stream().limit(k).collect(Collectors.toList()))
        {
            if(this.getClazz().equals(documents.getClazz()))
            {
                ++s;
            }
        }
        this.validity = (1.0 / (double) k) * s;
    }

    @Override public void calculateWeightVoting(int k)
    {
        this.similarity.object2DoubleEntrySet().stream().limit(k).forEach(entry ->
        {
            @NotNull final Journal document = (Journal) entry.getKey();
            this.weightVoting.put(document, document.getValidity() * (1.0 / (entry.getDoubleValue() + 0.5)));
        });
    }

    @Override public void summarize(@NotNull List<Class> classes)
    {
        classes.forEach(clazz -> this.classVoting.put(clazz, 0.0));
        this.weightVoting.object2DoubleEntrySet().forEach(entry ->
        {
            @Nullable final Class clazz = entry.getKey().getClazz();
            if(clazz == null)
            {
                System.err.println("Training Document must have class");
                System.exit(-1);
            }
            this.classVoting.put(clazz, this.classVoting.get(clazz) + entry.getDoubleValue());
        });
    }

    @Override public void orderAndVote()
    {
        @NotNull final Object2DoubleMap<Class> classVoting = this.classVoting;
        @NotNull final Object2DoubleMap<Class> tmp         = new Object2DoubleLinkedOpenHashMap<>(this.classVoting);
        classVoting.clear();
        tmp.object2DoubleEntrySet()
           .stream()
           .sorted((o1, o2) -> -Double.compare(o1.getDoubleValue(), o2.getDoubleValue()))
           .forEachOrdered(document -> classVoting.put(document.getKey(), document.getDoubleValue()));
        tmp.clear();
        super.setClassified(this.classVoting.keySet().iterator().next());
    }

    @Override public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }

        if(!(o instanceof Journal))
        {
            return false;
        }

        Journal document = (Journal) o;

        return new EqualsBuilder()
                .append(documents, document.documents)
                .isEquals();
    }

    @Override public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(documents)
                .toHashCode();
    }

    public String getDocuments()
    {
        return this.documents;
    }

    public void setDocuments(String documents)
    {
        this.documents = documents;
    }

    public List<StringTerm> getTokenize()
    {
        return this.tokenize;
    }

    public void setTokenize(List<StringTerm> tokenize)
    {
        this.tokenize = tokenize;
    }

    public Object2DoubleMap<Documents> getWeightVoting()
    {
        return this.weightVoting;
    }

    public void setWeightVoting(Object2DoubleMap<Documents> weightVoting)
    {
        this.weightVoting = weightVoting;
    }

    public DoubleBagOfWords getTfIdf()
    {
        return this.tfIdf;
    }

    public void setTfIdf(DoubleBagOfWords tfIdf)
    {
        this.tfIdf = tfIdf;
    }

    public DoubleBagOfWords getTfIdf2()
    {
        return this.tfIdf2;
    }

    public void setTfIdf2(DoubleBagOfWords tfIdf2)
    {
        this.tfIdf2 = tfIdf2;
    }

    public Object2DoubleMap<Documents> getSimilarity()
    {
        return this.similarity;
    }

    public void setSimilarity(Object2DoubleMap<Documents> similarity)
    {
        this.similarity = similarity;
    }

    public Object2DoubleMap<Class> getClassVoting()
    {
        return this.classVoting;
    }

    public void setClassVoting(Object2DoubleMap<Class> classVoting)
    {
        this.classVoting = classVoting;
    }

    public String getCleanedDocument()
    {
        return this.cleanedDocument;
    }

    public void setCleanedDocument(String cleanedDocument)
    {
        this.cleanedDocument = cleanedDocument;
    }

    public double getValidity()
    {
        return this.validity;
    }

    public void setValidity(double validity)
    {
        this.validity = validity;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("documents", documents)
                .append("tokenize", tokenize)
                .append("weightVoting", weightVoting)
                .append("tfIdf", tfIdf)
                .append("tfIdf2", tfIdf2)
                .append("similarity", similarity)
                .append("cleanedDocument", cleanedDocument)
                .append("validity", validity)
                .toString();
    }
}
