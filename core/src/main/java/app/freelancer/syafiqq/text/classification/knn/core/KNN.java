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
public class KNN
{
    @NotNull protected  List<Documents> documents;
    @NotNull protected  List<Class>     classes;
    @Nullable protected TermContainer   terms;

    public KNN()
    {
        this.documents = new LinkedList<>();
        this.classes = new LinkedList<>();
    }

    public boolean addDocuments(Documents documents)
    {
        return this.documents.add(documents);
    }

    public boolean addClass(Class aClass)
    {
        return this.classes.add(aClass);
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

    @NotNull public List<Documents> getDocuments()
    {
        return this.documents;
    }

    public void setDocuments(@NotNull List<Documents> documents)
    {
        this.documents = documents;
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

    @Override public String toString()
    {
        return "KNN{" +
                "documents=" + documents +
                ", classes=" + classes +
                ", terms=" + terms +
                '}';
    }
}
