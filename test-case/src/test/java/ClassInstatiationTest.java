import case_0.clazz.Class1;
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
        @NotNull final Class1 cl1 = new Class1(1);
        Assert.assertNotNull(cl1);
    }

    @Test public void it_class_return_1()
    {
        @NotNull final Class1 cl1 = new Class1(1);
        Assert.assertEquals(1, cl1.getClazz());
    }
}
