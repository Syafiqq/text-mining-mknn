import app.freelancer.syafiqq.text.classification.knn.core.KNN;
import app.freelancer.syafiqq.text.dummy.generator.DocumentDummyGenerator;
import case_0.KNNImpl;
import case_0.StringTerm;
import case_0.StringTermContainer;
import case_0.clazz.IntegerClass;
import case_0.document.DoubleBagOfWords;
import case_0.document.IntBagOfWords;
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
        Arrays.stream(classified).forEach(System.out::println);
        System.out.println(Arrays.deepToString(termsMap));*/

        List<Journal> journals = new ArrayList<>(documents.length);
        for(@NotNull final String document : documents)
        {
            journals.add(new Journal(null, new IntBagOfWords(), document));
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

        KNN knn = new KNNImpl();
        knn.addClassifiedDocument(journals.get(0));
        knn.addClassifiedDocument(journals.get(1));
        knn.addClassifiedDocument(journals.get(2));
        knn.addClassifiedDocument(journals.get(3));
        knn.addClassifiedDocument(journals.get(4));
        knn.addClassifiedDocument(journals.get(5));
        knn.addClassifiedDocument(journals.get(6));
        knn.addUnclassifiedDocument(journals.get(7));
        knn.getClasses().addAll(classes);
        knn.setTerms(new StringTermContainer());
        knn.setDFI(new IntBagOfWords());
        knn.setIDF(new DoubleBagOfWords());

        knn.compile();
        knn.collectTerms();
        knn.cleaningDocument();
        knn.calculateTFIDF();
        knn.calculateSimilarity();

/*        System.out.println(knn.getDFI());
        System.out.println(knn.getIDF());
        System.out.println();

        journals.forEach(journal -> System.out.println(journal.getTfIdf()));
        System.out.println();*/
        journals.forEach(journal -> System.out.println(journal.getSimilarity()));
    }
}
