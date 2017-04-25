package case_1.model.orm;

import case_1.model.dataset.MLocale;
import case_1.model.dataset.MVocabulary;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 23 April 2017, 6:44 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("unused") public class ORMVocabulary
{
    public static MVocabulary insert(@NotNull AbstractORM orm, @NotNull MVocabulary vocabulary)
    {
        final int id = ORMVocabulary._insert(orm, vocabulary.getWord(), vocabulary.getLocale());
        vocabulary.setId(id);
        return vocabulary;
    }

    public static MVocabulary insert(@NotNull AbstractORM orm, @NotNull String name, @NotNull MLocale locale)
    {
        @NotNull final Int2ObjectMap<MLocale> locales = new Int2ObjectLinkedOpenHashMap<>(1);
        locales.put(locale.getId(), locale);
        final int id = ORMVocabulary._insert(orm, name, locale);
        return ORMVocabulary.getByID(orm, id, locales);
    }

    public static MVocabulary getByID(@NotNull AbstractORM orm, int id, @NotNull Int2ObjectMap<MLocale> locales)
    {
        return ORMVocabulary._getByID(orm, id, locales);
    }

    private static MVocabulary _getByID(@NotNull AbstractORM orm, int id, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @Nullable MVocabulary vocabulary = null;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `word`, `locale` FROM `vocabulary` WHERE `id` = ? LIMIT 1");
            statement.setInt(1, id);
            @NotNull final ResultSet result = statement.executeQuery();
            while(result.next())
            {
                @NotNull final MLocale locale = locales.getOrDefault(result.getInt("locale"), null);
                assert locale != null;
                vocabulary = new MVocabulary(
                        result.getInt("id"),
                        result.getString("word"),
                        locale
                );
            }
            result.close();
            statement.close();
            orm.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return vocabulary;
    }

    private static int _insert(@NotNull AbstractORM orm, @NotNull String name, @NotNull MLocale locale)
    {
        orm.setProperties(orm);
        int mQueryID = MVocabulary.UNASSIGNED_PK;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("INSERT INTO `vocabulary`(`id`, `word`, `locale`) VALUES (NULL, ?, ?)");
            statement.setString(1, name);
            statement.setInt(2, locale.getId());
            statement.execute();
            statement.close();

            @NotNull final PreparedStatement callStatement = orm.connection.prepareStatement("SELECT last_insert_rowid()");
            @NotNull final ResultSet         callResult    = callStatement.executeQuery();
            callResult.next();
            mQueryID = callResult.getInt("last_insert_rowid()");
            callResult.close();
            callStatement.close();
            orm.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return mQueryID;
    }

    public static Int2ObjectMap<MVocabulary> getAll(@NotNull AbstractORM orm, @NotNull Int2ObjectMap<MLocale> locales)
    {
        return ORMVocabulary._getAll(orm, locales);
    }

    private static Int2ObjectMap<MVocabulary> _getAll(@NotNull AbstractORM orm, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @Nullable Int2ObjectMap<MVocabulary> vocabulary = new Int2ObjectLinkedOpenHashMap<>();
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `word`, `locale`  FROM `vocabulary`");
            @NotNull final ResultSet         result    = statement.executeQuery();
            vocabulary.putAll(ORMVocabulary.fetchStopWords(statement.executeQuery(), locales));
            statement.close();
            orm.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return vocabulary;
    }

    public static Int2ObjectMap<MVocabulary> getByLocaleID(@NotNull AbstractORM orm, IntList ids, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @NotNull final Int2ObjectMap<MVocabulary> vocabularys = new Int2ObjectLinkedOpenHashMap<>();
        ids.forEach(id -> vocabularys.putAll(getByLocaleID(orm, id, locales)));
        return vocabularys;
    }

    public static Int2ObjectMap<MVocabulary> getByLocaleID(@NotNull AbstractORM orm, int localeID, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @Nullable Int2ObjectMap<MVocabulary> vocabulary = new Int2ObjectLinkedOpenHashMap<>();
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `word`, `locale`  FROM `vocabulary` WHERE `locale` = ?");
            statement.setInt(1, localeID);
            vocabulary.putAll(ORMVocabulary.fetchStopWords(statement.executeQuery(), locales));
            statement.close();
            orm.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return vocabulary;
    }

    private static Int2ObjectMap<MVocabulary> fetchStopWords(ResultSet result, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @Nullable Int2ObjectMap<MVocabulary> vocabulary = new Int2ObjectLinkedOpenHashMap<>();

        try
        {
            while(result.next())
            {
                final int              id     = result.getInt("id");
                @NotNull final MLocale locale = locales.getOrDefault(result.getInt("locale"), null);
                assert locale != null;
                vocabulary.put(id, new MVocabulary(
                        id,
                        result.getString("word"),
                        locale
                ));
            }
            result.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return vocabulary;
    }
}
