import case_0.clazz.Class1;
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
    @Test public void it_success_create()
    {
        @NotNull final String  dummy   = "Lorem Ipsum Dolor Sir Amet";
        @NotNull final Journal journal = new Journal(null, dummy);
        Assert.assertNotNull(journal);
    }

    @Test public void it_documents_return_lorem_ipsum_dolor_sir_amet()
    {
        @NotNull final String  dummy   = "Lorem Ipsum Dolor Sir Amet";
        @NotNull final Journal journal = new Journal(null, dummy);
        Assert.assertNotNull(dummy, journal.getDocuments());
    }

    @Test public void it_document_class_is_null()
    {
        @NotNull final String  dummy   = "Lorem Ipsum Dolor Sir Amet";
        @NotNull final Journal journal = new Journal(null, dummy);
        Assert.assertNull(journal.getClazz());
    }

    @Test public void it_document_class_is_not_null()
    {
        @NotNull final String  dummy   = "Lorem Ipsum Dolor Sir Amet";
        @NotNull final Journal journal = new Journal(new Class1(1), dummy);
        Assert.assertNotNull(journal.getClazz());
    }
}
