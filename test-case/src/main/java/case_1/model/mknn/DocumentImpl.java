package case_1.model.mknn;

import app.freelancer.syafiqq.text.classification.knn.core.BagOfWords;
import app.freelancer.syafiqq.text.classification.knn.core.Class;
import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.Term;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_1.model.dataset.MLocale;
import case_1.model.dataset.MQuery;
import case_1.model.dataset.MStopWords;
import case_1.model.dataset.MVocabulary;
import case_1.model.orm.AbstractORM;
import case_1.model.orm.ORMLocale;
import case_1.model.orm.ORMStopWords;
import case_1.model.orm.ORMVocabulary;
import case_1.model.setting.DBSetting;
import case_1.model.util.DBType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.math3.util.FastMath;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.id.IndonesianStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 6:29 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions", "Duplicates"}) public class DocumentImpl extends Documents
{
    @NotNull private final static String SINGLE_CHAR          = "\\b\\w\\b\\s?";
    @NotNull private final static String MULTIPLE_LINE        = "\\n\\s*\\n";
    @NotNull private final static String URL_REGEX            = "((www\\.[\\s]+)|(https?://[^\\s]+))";
    @NotNull private final static String CONSECUTIVE_CHARS    = "([a-z])\\1{2,}";
    @NotNull private final static String STARTS_WITH_NUMBER   = "[1-9]\\s*(\\w+)";
    @NotNull private final static String AT_USER              = "@([^\\s]+)";
    @NotNull private final static String HASHTAG              = "(^|\\s)#(\\w*[a-zA-Z_]+\\w*)";
    @NotNull private final static String LAUGHING             = "\\b(?>a*+(?>ha)++h?|(?>l++o++)++l++|k*+(?>wk)++w?|(?>he)++h?)|\\b";
    @NotNull private final static String PUNCTUATION          = "['!\"#$%&\\\\'()\\*+,\\-\\.\\/:;<=>?@\\[\\\\\\]\\^_`{|}~']";
    @NotNull private final static String EXTRA_SPACES         = "\\s{2,}";
    @NotNull private final static String TRAILING_WHITE_SPACE = "^\\s+|\\s+$";
    @NotNull private final static String URL                  = "\\b((?:[a-z][\\w-]+:(?:\\/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[" +
            ".][a-z]{2,4}\\/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()" +
            "<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))";
    @NotNull private final static String UNCOMMON_CHAR        = "[^a-zA-Z0-9`!@#$%^&*()_+|\\-=\\\\{}\\[\\]:\"\";'<>?,./]";
    @NotNull private final static Locale ID_LOCALE            = new Locale("id", "ID", "ID");

    @Nullable private final static CharArraySet      ENGLISH_STOP_WORDS;
    @Nullable private final static CharArraySet      INDONESIA_STOP_WORDS;
    @NotNull private final static  ObjectSet<String> INDONESIA_VOCAB;
    @NotNull private final static  ObjectSet<String> ENGLISH_VOCAB;

    static
    {
        AbstractORM            orm       = null;
        Int2ObjectMap<MLocale> swLocales = null;
        try
        {
            orm = new AbstractORM(DBSetting.getDBUrl(DBSetting.path, DBType.DEFAULT));
            swLocales = ORMLocale.getAll(orm);

        }
        catch(SQLException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        if((orm != null) && (swLocales != null))
        {
            ENGLISH_STOP_WORDS = StopFilter.makeStopSet(ORMStopWords.getByLocaleID(orm, 2, swLocales).values().stream().map(MStopWords::getWord).collect(Collectors.toList()), true);
            INDONESIA_STOP_WORDS = StopFilter.makeStopSet(ORMStopWords.getByLocaleID(orm, 1, swLocales).values().stream().map(MStopWords::getWord).collect(Collectors.toList()), true);
        }
        else
        {
            INDONESIA_STOP_WORDS = ENGLISH_STOP_WORDS = null;
        }


        //===Initializing Vocab===

        INDONESIA_VOCAB = new ObjectLinkedOpenHashSet<>();
        ENGLISH_VOCAB = new ObjectLinkedOpenHashSet<>();
        if((orm != null) && (swLocales != null))
        {
            INDONESIA_VOCAB.addAll(ORMVocabulary.getByLocaleID(orm, 1, swLocales).values().stream().map(MVocabulary::getWord).collect(Collectors.toList()));
            ENGLISH_VOCAB.addAll(ORMVocabulary.getByLocaleID(orm, 2, swLocales).values().stream().map(MVocabulary::getWord).collect(Collectors.toList()));
        }

    }

    @NotNull private MQuery                      documents;
    @NotNull private List<TermImpl>              tokenize;
    @NotNull private Object2DoubleMap<Documents> weightVoting;
    @NotNull private BagOfWordsImpl              tfIdf;
    @NotNull private BagOfWordsImpl              tfIdf2;
    @NotNull private Object2DoubleMap<Documents> similarity;
    @NotNull private Object2DoubleMap<Class>     classVoting;

    private String cleanedDocument;
    private double validity;

    public DocumentImpl(@NotNull MQuery query, @Nullable Class clazz, @NotNull BagOfWords bagOfWords)
    {
        super(clazz, bagOfWords);
        this.documents = query;
        this.tokenize = new LinkedList<>();
        this.weightVoting = new Object2DoubleLinkedOpenHashMap<>();
        this.tfIdf = new BagOfWordsImpl();
        this.tfIdf2 = new BagOfWordsImpl();
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
        this.cleanedDocument = this.documents.getQuery();

        //To Lower Case
        this.cleanedDocument = this.cleanedDocument.toLowerCase(Locale.US);
        this.cleanedDocument = this.cleanedDocument.toLowerCase(ID_LOCALE);

        // Remove multiple Line
        this.cleanedDocument = this.cleanedDocument.replaceAll(MULTIPLE_LINE, " ");

        // Remove urls
        this.cleanedDocument = this.cleanedDocument.replaceAll(URL_REGEX, " ");

        // Remove advanced urls
        this.cleanedDocument = this.cleanedDocument.replaceAll(URL, " ");

        // Remove @username
        this.cleanedDocument = this.cleanedDocument.replaceAll(AT_USER, " ");

        // Remove #Hastag
        this.cleanedDocument = this.cleanedDocument.replaceAll(HASHTAG, " ");

        // Remove laughing
        this.cleanedDocument = this.cleanedDocument.replaceAll(LAUGHING, " ");

        // Remove character repetition
        this.cleanedDocument = this.cleanedDocument.replaceAll(CONSECUTIVE_CHARS, "$1");

        // Remove punctuation
        this.cleanedDocument = this.cleanedDocument.replaceAll(PUNCTUATION, " ");

        // Remove uncommon char
        this.cleanedDocument = this.cleanedDocument.replaceAll(UNCOMMON_CHAR, " ");

        // Remove words starting with a number
        this.cleanedDocument = this.cleanedDocument.replaceAll(STARTS_WITH_NUMBER, " ");

        // Remove singe character
        this.cleanedDocument = this.cleanedDocument.replaceAll(SINGLE_CHAR, " ");

        // Remove extra spaces
        this.cleanedDocument = this.cleanedDocument.replaceAll(EXTRA_SPACES, " ");
        this.cleanedDocument = this.cleanedDocument.trim();

        // Remove extra end the end of text
        this.cleanedDocument = this.cleanedDocument.replaceAll(TRAILING_WHITE_SPACE, "");

        // Escape HTML
        this.cleanedDocument = this.cleanedDocument.replaceAll("&amp;", "&");
        this.cleanedDocument = StringEscapeUtils.unescapeHtml4(this.cleanedDocument);
    }

    private void tokenize()
    {
        @NotNull final Analyzer          analyzer    = new WhitespaceAnalyzer();
        @NotNull TokenStream             tokenStream = analyzer.tokenStream("contents", new StringReader(this.cleanedDocument));
        @NotNull final CharTermAttribute term        = tokenStream.addAttribute(CharTermAttribute.class);

        if(INDONESIA_STOP_WORDS != null)
        {
            tokenStream = new StopFilter(tokenStream, INDONESIA_STOP_WORDS);
            tokenStream = new StopFilter(tokenStream, ENGLISH_STOP_WORDS);
        }

        tokenStream = new IndonesianStemFilter(tokenStream);
        tokenStream = new PorterStemFilter(tokenStream);

        try
        {
            tokenStream.reset();
            while(tokenStream.incrementToken())
            {
                @NotNull final String _term = term.toString();
                if(INDONESIA_VOCAB.contains(_term) || ENGLISH_VOCAB.contains(_term))
                {
                    this.tokenize.add(new TermImpl(_term));
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override public void countTerms(@NotNull TermContainer terms)
    {
        @NotNull final BagOfWordsImpl             bow       = (BagOfWordsImpl) super.bagOfWords;
        @NotNull final Object2DoubleMap<TermImpl> _bow      = bow.getBow();
        @NotNull final Set<Term>                  container = terms.getTerms();
        this.tokenize.stream().distinct().collect(Collectors.toList()).forEach(term -> _bow.put(term, 0));
        this.tokenize.forEach(term ->
        {
            if(container.contains(term))
            {
                bow.increment(term);
            }
        });
    }

    @Override public void calculateTFIDF(@NotNull BagOfWords idf)
    {
        final @NotNull BagOfWordsImpl             _idf   = (BagOfWordsImpl) idf;
        final @NotNull Object2DoubleMap<TermImpl> tfIdf  = this.tfIdf.getBow();
        final @NotNull Object2DoubleMap<TermImpl> tfIdf2 = this.tfIdf2.getBow();

        tfIdf.clear();
        tfIdf2.clear();

        ((BagOfWordsImpl) super.bagOfWords).getBow().forEach((term, occurrence) ->
        {
            final double val = occurrence * _idf.getDouble(term);
            tfIdf.put(term, val);
            tfIdf2.put(term, FastMath.pow(val, 2.0));
        });
    }

    @Override public void calculateSimilarity(@NotNull Documents document, @NotNull TermContainer terms)
    {
        final @NotNull Object2DoubleMap<TermImpl> tfIdf    = this.tfIdf.getBow();
        final @NotNull Object2DoubleMap<TermImpl> tfIdf_1  = ((DocumentImpl) document).tfIdf.getBow();
        final @NotNull Object2DoubleMap<TermImpl> tfIdf2   = this.tfIdf2.getBow();
        final @NotNull Object2DoubleMap<TermImpl> tfIdf2_1 = ((DocumentImpl) document).tfIdf2.getBow();

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
            @NotNull final DocumentImpl document = (DocumentImpl) entry.getKey();
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

        if(!(o instanceof DocumentImpl))
        {
            return false;
        }

        DocumentImpl document = (DocumentImpl) o;

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

    @NotNull public MQuery getDocuments()
    {
        return this.documents;
    }

    public void setDocuments(@NotNull MQuery documents)
    {
        this.documents = documents;
    }

    @NotNull public List<TermImpl> getTokenize()
    {
        return this.tokenize;
    }

    public void setTokenize(@NotNull List<TermImpl> tokenize)
    {
        this.tokenize = tokenize;
    }

    @NotNull public Object2DoubleMap<Documents> getWeightVoting()
    {
        return this.weightVoting;
    }

    public void setWeightVoting(@NotNull Object2DoubleMap<Documents> weightVoting)
    {
        this.weightVoting = weightVoting;
    }

    @NotNull public BagOfWordsImpl getTfIdf()
    {
        return this.tfIdf;
    }

    public void setTfIdf(@NotNull BagOfWordsImpl tfIdf)
    {
        this.tfIdf = tfIdf;
    }

    @NotNull public BagOfWordsImpl getTfIdf2()
    {
        return this.tfIdf2;
    }

    public void setTfIdf2(@NotNull BagOfWordsImpl tfIdf2)
    {
        this.tfIdf2 = tfIdf2;
    }

    @NotNull public Object2DoubleMap<Documents> getSimilarity()
    {
        return this.similarity;
    }

    public void setSimilarity(@NotNull Object2DoubleMap<Documents> similarity)
    {
        this.similarity = similarity;
    }

    @NotNull public Object2DoubleMap<Class> getClassVoting()
    {
        return this.classVoting;
    }

    public void setClassVoting(@NotNull Object2DoubleMap<Class> classVoting)
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
}
