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
public abstract class KNN
{
    @NotNull protected  List<Documents> classified;
    @NotNull protected  List<Class>     classes;
    @NotNull protected  List<Documents> documents;
    @Nullable protected Documents       unclassified;
    @Nullable protected TermContainer   terms;
    @Nullable protected TermCounter     maxTermFrequency;
    @Nullable protected BagOfWords      DFI;
    @Nullable protected BagOfWords      IDF;

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

        if(this.maxTermFrequency == null)
        {
            System.err.println("Specify Term Counter First");
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

        this.documents.clear();
        this.documents.addAll(this.classified);
        this.documents.add(this.unclassified);
    }

    public void collectTerms()
    {
        if(this.terms == null)
        {
            System.err.println("Terms not Instantiated");
        }
        else
        {
            for(@NotNull final Class clazz : this.classes)
            {
                clazz.collectTerms(this.terms);
            }
            this.DFI.setTerms(this.terms);
        }
    }

    public void tokenizeDocument()
    {
        for(@NotNull final Documents document : this.documents)
        {
            document.preProcess();
            document.tokenize();
            document.collectTerms(this.terms, document.bagOfWords);
        }
    }

    public void calculateTFIDF()
    {
        for(@NotNull final Documents document : this.documents)
        {
            document.getMaximumWord(this.maxTermFrequency);
        }
        for(@NotNull final Documents document : this.classified)
        {
            document.checkExistence(this.DFI);
        }
        this.calculateIDF();
        for(@NotNull final Documents document : this.documents)
        {
            document.normalizeBOW(this.maxTermFrequency);
        }
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

    @Nullable public TermCounter getMaxTermFrequency()
    {
        return this.maxTermFrequency;
    }

    public void setMaxTermFrequency(@NotNull TermCounter maxTermFrequency)
    {
        this.maxTermFrequency = maxTermFrequency;
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

    @Override public String toString()
    {
        return "KNN{" +
                "classified=" + classified +
                ", unclassified=" + unclassified +
                ", classes=" + classes +
                ", terms=" + terms +
                ", maxTermFrequency=" + maxTermFrequency +
                '}';
    }
}
