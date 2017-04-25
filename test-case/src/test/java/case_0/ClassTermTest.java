package case_0;

import case_0.clazz.IntegerClass;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 8:16 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ClassTermTest
{
    @Test public void it_term_count_3()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertNotNull(cl1);
        Assert.assertEquals(1, cl1.getClazz());
    }

    @Test public void it_term_count_1()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertNotNull(cl1);
        Assert.assertEquals(1, cl1.getClazz());
    }
}
