import app.freelancer.syafiqq.text.classification.knn.core.KNN;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import app.freelancer.syafiqq.text.dummy.generator.DocumentDummyGenerator;
import case_0.StringTerm;
import case_0.clazz.IntegerClass;
import case_0.document.BagOfWordsImpl;
import case_0.document.Journal;
import case_0.generator.DummyDocuments;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 18 April 2017, 11:11 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DocumentCollectingTermTest
{
    @Test public void it_valid()
    {
        final DocumentDummyGenerator dummy     = new DummyDocuments();
        final String[]               terms     = dummy.generateTerms();
        final String[]               documents = dummy.generateDocuments();
        final int[][]                termsMap  = dummy.generateTermsMap();

/*        Arrays.stream(terms).forEach(System.out::println);
        Arrays.stream(documents).forEach(System.out::println);
        System.out.println(Arrays.deepToString(termsMap));*/

        List<Journal> journals = new ArrayList<>(documents.length);
        for(@NotNull final String document : documents)
        {
            journals.add(new Journal(null, new BagOfWordsImpl(), document));
        }

        final int          _CLASS_SIZE_ = 3;
        List<IntegerClass> classes      = new ArrayList<>(_CLASS_SIZE_);

        for(int i = -1; ++i < _CLASS_SIZE_; )
        {
            @NotNull final IntegerClass _clz = new IntegerClass(i + 1);
            for(@NotNull final String _t : terms)
            {
                _clz.addTerm(new StringTerm(_t));
            }
            classes.add(_clz);
        }

        journals.get(0).setClazz(classes.get(1));
        journals.get(1).setClazz(classes.get(2));
        journals.get(2).setClazz(classes.get(1));
        journals.get(3).setClazz(classes.get(2));
        journals.get(4).setClazz(classes.get(0));
        journals.get(5).setClazz(classes.get(2));
        journals.get(6).setClazz(classes.get(0));

        //journals.forEach(System.out::println);

        KNN knn = new KNN();
        knn.getDocuments().addAll(journals);
        knn.getClasses().addAll(classes);
        knn.setTerms(new TermContainer());
        knn.collectTerms();

        knn.tokenizeDocument();

        journals.forEach(journal -> System.out.println(journal.getBagOfWords()));

    }
}
