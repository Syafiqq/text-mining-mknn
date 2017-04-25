package case_1.model.orm;

import case_1.model.dataset.MClass;
import case_1.model.dataset.MQuery;
import case_1.model.setting.DBSetting;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 11:29 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ORMQueryTest
{
    @Test public void it_successfully_get() throws UnsupportedEncodingException, SQLException
    {
        @NotNull final AbstractORM           orm     = new AbstractORM(DBSetting.getDBUrl(DBSetting.path, DBSetting.type));
        @NotNull final Int2ObjectMap<MClass> classes = ORMClass.getAll(orm);

        final IntList idIntLists = new IntArrayList();
        idIntLists.add(1);
        idIntLists.forEach(id ->
        {
            @NotNull final MQuery query = ORMQuery.getByID(orm, id, classes);
            Assert.assertNotNull(query);
            Assert.assertEquals((int) id, query.getId());
            Assert.assertEquals("bca bca memang oke", query.getQuery());
            Assert.assertEquals(1, query.getClazz().getId());
            Assert.assertEquals("Kelas 1", query.getClazz().getName());
        });
    }
}
