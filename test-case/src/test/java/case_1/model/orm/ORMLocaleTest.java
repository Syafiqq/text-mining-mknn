package case_1.model.orm;

import case_1.model.dataset.MLocale;
import case_1.model.setting.DBSetting;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 23 April 2017, 10:35 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ORMLocaleTest
{
    @Test public void it_successfully_get() throws UnsupportedEncodingException, SQLException
    {
        @NotNull final AbstractORM orm     = new AbstractORM(DBSetting.getDBUrl(DBSetting.path, DBSetting.type));
        final List<Integer>        idLists = Arrays.asList(1, 2);
        idLists.forEach(id ->
        {
            @NotNull final MLocale mClass = ORMLocale.getByID(orm, id);
            Assert.assertEquals((int) id, mClass.getId());
            Assert.assertEquals(id == 1 ? "indonesia" : "english", mClass.getName());
        });
    }
}
