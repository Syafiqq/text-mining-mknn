package case_1.model.dataset;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 23 April 2017, 6:41 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MVocabularyTest
{
    @Test public void it_success_create()
    {
        @NotNull final MVocabulary vocabulary1 = new MVocabulary("amerika", new MLocale(1, "indonesia"));
        Assert.assertEquals("amerika", vocabulary1.getWord());
        @NotNull final MVocabulary vocabulary2 = new MVocabulary("cina", new MLocale(2, "english"));
        Assert.assertEquals("cina", vocabulary2.getWord());
        Assert.assertNotEquals(true, vocabulary1.equals(vocabulary2));
        @NotNull final MVocabulary vocabulary3 = new MVocabulary("cina", new MLocale(2, "english"));
        Assert.assertEquals("english", vocabulary3.getLocale().getName());
        Assert.assertEquals("cina", vocabulary3.getWord());
        Assert.assertEquals(true, vocabulary2.equals(vocabulary3));
    }
}
