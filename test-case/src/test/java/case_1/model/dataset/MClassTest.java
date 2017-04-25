package case_1.model.dataset;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 7:43 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MClassTest
{
    @Test public void it_successfully_created()
    {
        @NotNull final MClass mClass = new MClass("Kelas 1");
        Assert.assertEquals("Kelas 1", mClass.getName());
        @NotNull final MClass mClass1 = new MClass("Kelas 1");
        Assert.assertEquals("Kelas 1", mClass1.getName());
        Assert.assertEquals(true, mClass.equals(mClass1));

    }
}
