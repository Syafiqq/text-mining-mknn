import case_0.clazz.IntegerClass;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:10 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ClassInstatiationTest
{
    @Test public void it_success_create()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertNotNull(cl1);
    }

    @Test public void it_class_return_1()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertEquals(1, cl1.getClazz());
    }
}
