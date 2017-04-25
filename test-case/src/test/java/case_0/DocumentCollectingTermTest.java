package case_0;

import app.freelancer.syafiqq.text.dummy.generator.DocumentDummyGenerator;
import case_0.clazz.IntegerClass;
import case_0.document.DoubleBagOfWords;
import case_0.document.Journal;
import case_0.generator.DummyDocuments;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
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

        final int                                  _CLASS_SIZE_ = 3;
        @NotNull final Int2ObjectMap<IntegerClass> classes      = new Int2ObjectLinkedOpenHashMap<>(_CLASS_SIZE_);
        for(int i = -1; ++i < _CLASS_SIZE_; )
        {
            classes.put(i + 1, new IntegerClass(i + 1));
        }

        Int2ObjectMap<Journal> journals = new Int2ObjectLinkedOpenHashMap<>(documents.length);
        for(int i = -1; ++i < documents.length - 1; )
        {
            journals.put(i + 1, new Journal(documents[i], classes.get(1), new DoubleBagOfWords()));
        }

        KNNImpl mknn = new KNNImpl();
        mknn.getClassifiedDocument().addAll(journals.values());
        mknn.getClasses().addAll(classes.values());
        mknn.setTerms(new StringTermContainer());
        mknn.setDFI(new DoubleBagOfWords());
        mknn.setIDF(new DoubleBagOfWords());
        mknn.setK(2);

        Journal x = new Journal(documents[3], null, new DoubleBagOfWords());

        mknn.train();
        mknn.test(x);

        System.out.println(x.getClazz());
        System.out.println(x.getClassified());
    }
}
