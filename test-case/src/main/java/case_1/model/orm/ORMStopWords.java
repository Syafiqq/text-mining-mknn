package case_1.model.orm;

import case_1.model.dataset.MLocale;
import case_1.model.dataset.MStopWords;
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
 * Date / Time  : 23 April 2017, 10:37 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("unused") public class ORMStopWords
{
    public static MStopWords insert(@NotNull AbstractORM orm, @NotNull MStopWords stopWord)
    {
        final int id = ORMStopWords._insert(orm, stopWord.getWord(), stopWord.getLocale());
        stopWord.setId(id);
        return stopWord;
    }

    public static MStopWords insert(@NotNull AbstractORM orm, @NotNull String name, @NotNull MLocale locale)
    {
        @NotNull final Int2ObjectMap<MLocale> locales = new Int2ObjectLinkedOpenHashMap<>(1);
        locales.put(locale.getId(), locale);
        final int id = ORMStopWords._insert(orm, name, locale);
        return ORMStopWords.getByID(orm, id, locales);
    }

    public static MStopWords getByID(@NotNull AbstractORM orm, int id, @NotNull Int2ObjectMap<MLocale> locales)
    {
        return ORMStopWords._getByID(orm, id, locales);
    }

    private static MStopWords _getByID(@NotNull AbstractORM orm, int id, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @Nullable MStopWords stopWord = null;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `word`, `locale` FROM `stopwords` WHERE `id` = ? LIMIT 1");
            statement.setInt(1, id);
            @NotNull final ResultSet result = statement.executeQuery();
            while(result.next())
            {
                @NotNull final MLocale locale = locales.getOrDefault(result.getInt("locale"), null);
                assert locale != null;
                stopWord = new MStopWords(
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
        return stopWord;
    }

    private static int _insert(@NotNull AbstractORM orm, @NotNull String name, @NotNull MLocale locale)
    {
        orm.setProperties(orm);
        int mQueryID = MStopWords.UNASSIGNED_PK;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("INSERT INTO `stopwords`(`id`, `word`, `locale`) VALUES (NULL, ?, ?)");
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

    public static Int2ObjectMap<MStopWords> getAll(@NotNull AbstractORM orm, @NotNull Int2ObjectMap<MLocale> locales)
    {
        return ORMStopWords._getAll(orm, locales);
    }

    private static Int2ObjectMap<MStopWords> _getAll(@NotNull AbstractORM orm, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @Nullable Int2ObjectMap<MStopWords> stopWord = new Int2ObjectLinkedOpenHashMap<>();
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `word`, `locale`  FROM `stopwords`");
            @NotNull final ResultSet         result    = statement.executeQuery();
            stopWord.putAll(ORMStopWords.fetchStopWords(statement.executeQuery(), locales));
            statement.close();
            orm.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return stopWord;
    }

    public static Int2ObjectMap<MStopWords> getByLocaleID(@NotNull AbstractORM orm, IntList ids, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @NotNull final Int2ObjectMap<MStopWords> stopWords = new Int2ObjectLinkedOpenHashMap<>();
        ids.forEach(id -> stopWords.putAll(getByLocaleID(orm, id, locales)));
        return stopWords;
    }

    public static Int2ObjectMap<MStopWords> getByLocaleID(@NotNull AbstractORM orm, int localeID, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @Nullable Int2ObjectMap<MStopWords> stopWord = new Int2ObjectLinkedOpenHashMap<>();
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `word`, `locale`  FROM `stopwords` WHERE `locale` = ?");
            statement.setInt(1, localeID);
            stopWord.putAll(ORMStopWords.fetchStopWords(statement.executeQuery(), locales));
            statement.close();
            orm.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return stopWord;
    }

    private static Int2ObjectMap<MStopWords> fetchStopWords(ResultSet result, @NotNull Int2ObjectMap<MLocale> locales)
    {
        @Nullable Int2ObjectMap<MStopWords> stopWord = new Int2ObjectLinkedOpenHashMap<>();

        try
        {
            while(result.next())
            {
                final int              id     = result.getInt("id");
                @NotNull final MLocale locale = locales.getOrDefault(result.getInt("locale"), null);
                assert locale != null;
                stopWord.put(id, new MStopWords(
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

        return stopWord;
    }
}
