package case_0;

import case_0.clazz.IntegerClass;
import case_0.document.DoubleBagOfWords;
import case_0.document.Journal;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:07 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DocumentInstantiationTest
{
    @Test public void it_document_class_is_not_null()
    {
        @NotNull final String           dummy   = "Lorem Ipsum Dolor Sir Amet";
        @NotNull final DoubleBagOfWords bow     = new DoubleBagOfWords();
        @NotNull final Journal          journal = new Journal(dummy, new IntegerClass(1), bow);
        Assert.assertNotNull(journal.getClazz());
    }

    @Test public void it_documents_bow_is_not_null()
    {
        @NotNull final String           dummy   = "Lorem Ipsum Dolor Sir Amet";
        @NotNull final DoubleBagOfWords bow     = new DoubleBagOfWords();
        @NotNull final Journal          journal = new Journal(dummy, new IntegerClass(1), bow);
        Assert.assertNotNull(journal.getBagOfWords());
    }
}
