package case_1.model.dataset;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 10:59 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class MQueryTest
{
    @Test public void it_successfully_created()
    {
        @NotNull final MQuery query1 = new MQuery("Query 1", new MClass("Kelas 1"));
        Assert.assertEquals("Query 1", query1.getQuery());
        @NotNull final MQuery query2 = new MQuery("Query 1", new MClass("Kelas 1"));
        Assert.assertEquals("Query 1", query2.getQuery());
        Assert.assertEquals(true, query1.equals(query2));
        @NotNull final MQuery query3 = new MQuery("Query 1", new MClass("Kelas 1"));
        Assert.assertEquals("Kelas 1", query3.getClazz().getName());
        Assert.assertEquals("Query 1", query3.getQuery());
        Assert.assertEquals(false, query2.equals(query3));
    }
}
