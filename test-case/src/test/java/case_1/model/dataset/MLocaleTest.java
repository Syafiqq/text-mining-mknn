package case_1.model.dataset;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 23 April 2017, 10:18 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MLocaleTest
{
    @Test public void it_successfully_created()
    {
        @NotNull final MLocale locale = new MLocale("Indonesia");
        Assert.assertEquals("Indonesia", locale.getName());
        @NotNull final MLocale locale1 = new MLocale("English");
        Assert.assertEquals("English", locale1.getName());
        Assert.assertNotEquals(true, locale.equals(locale1));

    }
}
