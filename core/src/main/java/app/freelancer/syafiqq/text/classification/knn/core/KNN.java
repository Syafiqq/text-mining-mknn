package app.freelancer.syafiqq.text.classification.knn.core;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 7:26 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class KNN
{
    @NotNull protected  List<Documents> classified;
    @NotNull protected  List<Class>     classes;
    @NotNull protected  List<Documents> documents;
    @Nullable protected Documents       unclassified;
    @Nullable protected TermContainer   terms;
    @Nullable protected BagOfWords      DFI;
    @Nullable protected BagOfWords      IDF;
    protected           int             k;

    public KNN()
    {
        this.classified = new LinkedList<>();
        this.classes = new LinkedList<>();
        this.documents = new LinkedList<>();
    }

    public void compile()
    {
        if(this.unclassified == null)
        {
            System.err.println("Add an Unclassified Document first");
            System.exit(-1);
        }

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

        if((this.k <= 0) && this.k >= (this.classified.size() - 1))
        {
            System.err.println("1 >= k <= train_size");
            System.exit(-1);
        }

        this.documents.clear();
        this.documents.addAll(this.classified);
        this.documents.add(this.unclassified);
    }

    public void collectTerms()
    {
        this.classes.forEach(clazz -> this.terms.collectTerms(clazz));
        this.DFI.setTerms(this.terms);
        this.IDF.setTerms(this.terms);
    }

    public void cleaningDocument()
    {
        this.documents.forEach(documents ->
        {
            documents.preProcess();
            documents.tokenize();
            documents.collectTerms(this.terms);
        });
    }

    public void calculateTFIDF()
    {
        this.classified.forEach(document -> document.findTermExistence(this.DFI));
        this.calculateIDF();
        this.documents.forEach(document -> document.calculateTFIDF(this.IDF));
    }

    public void calculateValidity()
    {
        this.classified.forEach(train ->
        {
            this.classified.forEach(validator -> validator.calculateSimilarity(train));
            this.classified.sort((o1, o2) -> o1.orderBySimilarity(o2));
            train.calculateValidity(this.classified.stream().skip(1).limit(this.k).collect(Collectors.toList()));
        });
    }

    public void calculateTest()
    {
        this.classified.forEach(document -> document.calculateSimilarity(this.unclassified));
        this.classified.sort((o1, o2) -> o1.orderBySimilarity(o2));
        this.classified.stream().limit(this.k).collect(Collectors.toList()).forEach(Documents::calculateWeightVoting);
        this.classes.forEach(clazz -> clazz.summarizeVoting(this.classified.stream().limit(this.k).filter(documents1 -> documents1.clazz.equals(clazz)).collect(Collectors.toList())));
        this.classes.sort((o1, o2) -> o1.orderByWeight(o2));
        this.unclassified.setClazz(this.classes.get(0));
    }

    protected abstract void calculateIDF();

    public boolean addClassifiedDocument(@NotNull Documents documents)
    {
        return this.classified.add(documents);
    }

    public boolean addClass(@NotNull Class aClass)
    {
        return this.classes.add(aClass);
    }

    @NotNull public List<Documents> getClassifiedDocument()
    {
        return this.classified;
    }

    public void setClassifiedDocument(@NotNull List<Documents> classified)
    {
        this.classified = classified;
    }

    @Nullable public TermContainer getTermContainer()
    {
        return this.terms;
    }

    public void setTerms(@NotNull TermContainer terms)
    {
        this.terms = terms;
    }

    @NotNull public List<Class> getClasses()
    {
        return this.classes;
    }

    public void setClasses(@NotNull List<Class> classes)
    {
        this.classes = classes;
    }

    public void addUnclassifiedDocument(@NotNull Documents documents)
    {
        this.unclassified = documents;
    }

    @Nullable public Documents getUnclassifiedDocument()
    {
        return this.unclassified;
    }

    @Nullable public BagOfWords getDFI()
    {
        return this.DFI;
    }

    public void setDFI(@NotNull BagOfWords DFI)
    {
        this.DFI = DFI;
    }

    @Nullable public BagOfWords getIDF()
    {
        return this.IDF;
    }

    public void setIDF(@NotNull BagOfWords IDF)
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

    @Override public String toString()
    {
        final StringBuilder sb = new StringBuilder("KNN{");
        sb.append("classified=").append(classified);
        sb.append(", classes=").append(classes);
        sb.append(", documents=").append(documents);
        sb.append(", unclassified=").append(unclassified);
        sb.append(", terms=").append(terms);
        sb.append(", DFI=").append(DFI);
        sb.append(", IDF=").append(IDF);
        sb.append(", k=").append(k);
        sb.append('}');
        return sb.toString();
    }
}
