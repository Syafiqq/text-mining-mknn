package case_1.model.orm;

import case_1.model.dataset.MLocale;
import case_1.model.dataset.MVocabulary;
import case_1.model.setting.DBSetting;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 23 April 2017, 6:50 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ORMVocabularyTest
{
    @Test public void it_successfully_get() throws UnsupportedEncodingException, SQLException
    {
        @NotNull final AbstractORM            orm     = new AbstractORM(DBSetting.getDBUrl(DBSetting.path, DBSetting.type));
        @NotNull final Int2ObjectMap<MLocale> locales = ORMLocale.getAll(orm);

        final IntList idIntLists = new IntArrayList(Collections.singletonList(1));
        idIntLists.forEach(id ->
        {
            @NotNull final MVocabulary vocabulary = ORMVocabulary.getByID(orm, id, locales);
            Assert.assertNotNull(vocabulary);
            Assert.assertEquals((int) id, vocabulary.getId());
            Assert.assertEquals("a", vocabulary.getWord());
            Assert.assertEquals(1, vocabulary.getLocale().getId());
            Assert.assertEquals("indonesia", vocabulary.getLocale().getName());
        });
    }
}
