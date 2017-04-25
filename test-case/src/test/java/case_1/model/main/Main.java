package case_1.model.main;

import case_1.model.dataset.MClass;
import case_1.model.dataset.MQuery;
import case_1.model.mknn.BagOfWordsImpl;
import case_1.model.mknn.ClassImpl;
import case_1.model.mknn.DocumentImpl;
import case_1.model.mknn.MKNN;
import case_1.model.mknn.TermContainerImpl;
import case_1.model.orm.AbstractORM;
import case_1.model.orm.ORMClass;
import case_1.model.orm.ORMQuery;
import case_1.model.setting.DBSetting;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 5:22 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Main
{
    @Test public void it_success() throws UnsupportedEncodingException, SQLException
    {
        @NotNull final AbstractORM              orm      = new AbstractORM(DBSetting.getDBUrl(DBSetting.path, DBSetting.type));
        @NotNull final Int2ObjectMap<MClass>    _classes = ORMClass.getAll(orm);
        @NotNull final Int2ObjectMap<ClassImpl> classes  = new Int2ObjectLinkedOpenHashMap<>(_classes.size());
        _classes.values().forEach(clazz -> classes.put(clazz.getId(), new ClassImpl(clazz)));

        @NotNull final Int2ObjectMap<MQuery> _documents = ORMQuery.getAll(orm, _classes);
        Int2ObjectMap<DocumentImpl>          documents  = new Int2ObjectLinkedOpenHashMap<>(_documents.size());
        _documents.values().forEach(query -> documents.put(query.getId(), new DocumentImpl(query, classes.get(query.getClazz().getId()), new BagOfWordsImpl())));

        MKNN mknn = new MKNN();
        mknn.getClassifiedDocument().addAll(documents.values());
        mknn.getClasses().addAll(classes.values());
        mknn.setTerms(new TermContainerImpl());
        mknn.setDFI(new BagOfWordsImpl());
        mknn.setIDF(new BagOfWordsImpl());
        mknn.setK(5);

        mknn.train();
        DocumentImpl test = new DocumentImpl(new MQuery(""), null, new BagOfWordsImpl());
        mknn.test(test);

        System.out.println(mknn.getAccuracy());

        //System.out.println(mknn.getIDF());
        //documents.values().forEach(document -> System.out.println(document.getValidity()));
        //System.out.println(documents.get(1).getSimilarity().values());
    }
}
