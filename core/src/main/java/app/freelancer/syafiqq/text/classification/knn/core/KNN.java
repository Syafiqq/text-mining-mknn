package app.freelancer.syafiqq.text.classification.knn.core;

import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 7:26 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings({"SameParameterValue", "WeakerAccess", "unused", "ConstantConditions", "UnusedReturnValue"}) public abstract class KNN
{
    @NotNull protected  List<Documents> classified;
    @NotNull protected  List<Documents> unclassified;
    @NotNull protected  List<Class>     classes;
    @Nullable protected TermContainer   terms;
    @Nullable protected BagOfWords      DFI;
    @Nullable protected BagOfWords      IDF;
    protected           int             k;
    private             double          accuracy;

    public KNN()
    {
        this.classified = new LinkedList<>();
        this.unclassified = new LinkedList<>();
        this.classes = new LinkedList<>();
    }

    public void test(@NotNull Documents unclassified)
    {
        unclassified.preProcess();
        unclassified.countTerms(this.terms);
        unclassified.calculateTFIDF(this.IDF);
        this.classified.forEach(document -> unclassified.calculateSimilarity(document, this.terms));
        unclassified.orderSimilarity();
        unclassified.calculateWeightVoting(this.k);
        unclassified.summarize(this.classes);
        unclassified.orderAndVote();
        this.addUnclassifiedDocument(unclassified);
    }

    public void train()
    {
        //===Compile===
        if(this.terms == null)
        {
            System.err.println("Specify Terms First");
            System.exit(-1);
        }

        if(this.IDF == null)
        {
            System.err.println("Specify IDF First");
            System.exit(-1);
        }

        if(this.DFI == null)
        {
            System.err.println("Specify DFI First");
            System.exit(-1);
        }

        if((this.k <= 0) || this.k >= (this.classified.size()))
        {
            System.err.println("1 >= k <= train_size");
            System.exit(-1);
        }

        //===Cleaning===
        this.classified.forEach(documents ->
        {
            documents.preProcess();
            this.terms.collectTerms(documents);
        });

        //===Collect Terms===
        this.classified.forEach(documents -> documents.countTerms(this.terms));
        this.DFI.setTerms(this.terms);
        this.IDF.setTerms(this.terms);

        //===Calculate TF-IDF===
        this.classified.forEach(document -> this.DFI.checkExistence(document));
        this.calculateIDF();
        this.classified.forEach(document -> document.calculateTFIDF(this.IDF));

        //===Calculate Validity===
        this.classified.forEach(train ->
        {
            this.classified.stream().filter(document -> !document.equals(train)).forEach(document -> train.calculateSimilarity(document, this.terms));
            train.orderSimilarity();
            train.calculateValidity(this.k);
        });

        this.calculateAccuracy();
    }

    private void calculateAccuracy()
    {
        int tp = 0;
        for(Documents document : this.classified)
        {
            this.test(document);
            if(document.getClazz().equals(document.getClassified()))
            {
                ++tp;
            }
        }
        this.getUnclassified().clear();
        this.setAccuracy((double) tp / (double) this.classified.size());
    }

    protected abstract void calculateIDF();

    public boolean addClassifiedDocument(@NotNull Documents documents)
    {
        return this.classified.add(documents);
    }

    public boolean addUnclassifiedDocument(@NotNull Documents documents)
    {
        return this.unclassified.add(documents);
    }

    public boolean addClass(@NotNull Class aClass)
    {
        return this.classes.add(aClass);
    }

    @NotNull public List<Documents> getClassifiedDocument()
    {
        return this.classified;
    }

    public void setClassified(@NotNull List<Documents> classified)
    {
        this.classified = classified;
    }

    @NotNull public List<Documents> getUnclassified()
    {
        return this.unclassified;
    }

    public void setUnclassified(@NotNull List<Documents> unclassified)
    {
        this.unclassified = unclassified;
    }

    @NotNull public List<Class> getClasses()
    {
        return this.classes;
    }

    public void setClasses(@NotNull List<Class> classes)
    {
        this.classes = classes;
    }

    @Nullable public TermContainer getTerms()
    {
        return this.terms;
    }

    public void setTerms(@Nullable TermContainer terms)
    {
        this.terms = terms;
    }

    @Nullable public BagOfWords getDFI()
    {
        return this.DFI;
    }

    public void setDFI(@Nullable BagOfWords DFI)
    {
        this.DFI = DFI;
    }

    @Nullable public BagOfWords getIDF()
    {
        return this.IDF;
    }

    public void setIDF(@Nullable BagOfWords IDF)
    {
        this.IDF = IDF;
    }

    public int getK()
    {
        return this.k;
    }

    public void setK(int k)
    {
        this.k = k;
    }

    public double getAccuracy()
    {
        return this.accuracy;
    }

    public void setAccuracy(double accuracy)
    {
        this.accuracy = accuracy;
    }

    @SuppressWarnings("StringBufferReplaceableByString") @Override public String toString()
    {
        final StringBuilder sb = new StringBuilder("KNN{");
        sb.append("classified=").append(classified);
        sb.append(", unclassified=").append(unclassified);
        sb.append(", classes=").append(classes);
        sb.append(", terms=").append(terms);
        sb.append(", DFI=").append(DFI);
        sb.append(", IDF=").append(IDF);
        sb.append(", k=").append(k);
        sb.append('}');
        return sb.toString();
    }
}
