package case_1.model.dataset;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 23 April 2017, 10:21 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MStopWordsTest
{
    @Test public void it_success_create()
    {
        @NotNull final MStopWords stopWord1 = new MStopWords("anda", new MLocale(1, "indonesia"));
        Assert.assertEquals("anda", stopWord1.getWord());
        @NotNull final MStopWords stopWord2 = new MStopWords("anda", new MLocale(2, "english"));
        Assert.assertEquals("anda", stopWord2.getWord());
        Assert.assertNotEquals(true, stopWord1.equals(stopWord2));
        @NotNull final MStopWords stopWord3 = new MStopWords("anda", new MLocale(2, "english"));
        Assert.assertEquals("english", stopWord3.getLocale().getName());
        Assert.assertEquals("anda", stopWord3.getWord());
        Assert.assertEquals(true, stopWord2.equals(stopWord3));
    }
}
