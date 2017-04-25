package case_1.model.orm;

import case_1.model.dataset.MClass;
import case_1.model.dataset.MQuery;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 9:15 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ORMQuery
{
    public static MQuery insert(@NotNull AbstractORM orm, @NotNull MQuery query)
    {
        assert query.getClazz() != null;
        final int id = ORMQuery._insert(orm, query.getQuery(), query.getClazz());
        query.setId(id);
        return query;
    }

    public static MQuery insert(@NotNull AbstractORM orm, @NotNull String name, @NotNull MClass clazz)
    {
        @NotNull final Int2ObjectMap<MClass> classes = new Int2ObjectLinkedOpenHashMap<>(1);
        classes.put(clazz.getId(), clazz);
        final int id = ORMQuery._insert(orm, name, clazz);
        return ORMQuery.getByID(orm, id, classes);
    }

    public static MQuery getByID(@NotNull AbstractORM orm, int id, @NotNull Int2ObjectMap<MClass> classes)
    {
        return ORMQuery._getByID(orm, id, classes);
    }

    private static MQuery _getByID(@NotNull AbstractORM orm, int id, @NotNull Int2ObjectMap<MClass> classes)
    {
        @Nullable MQuery query = null;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `query`, `class` FROM `sentiment` WHERE `id` = ? LIMIT 1");
            statement.setInt(1, id);
            @NotNull final ResultSet result = statement.executeQuery();
            while(result.next())
            {
                @NotNull final MClass clazz = classes.getOrDefault(result.getInt("class"), null);
                assert clazz != null;
                query = new MQuery(
                        result.getInt("id"),
                        result.getString("query"),
                        clazz
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
        return query;
    }

    private static int _insert(@NotNull AbstractORM orm, @NotNull String name, @NotNull MClass clazz)
    {
        orm.setProperties(orm);
        int mQueryID = MQuery.UNASSIGNED_PK;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("INSERT INTO `sentiment`(`id`, `query`, `class`) VALUES (NULL, ?, ?)");
            statement.setString(1, name);
            statement.setInt(2, clazz.getId());
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

    public static Int2ObjectMap<MQuery> getAll(@NotNull AbstractORM orm, @NotNull Int2ObjectMap<MClass> classes)
    {
        return ORMQuery._getAll(orm, classes);
    }

    private static Int2ObjectMap<MQuery> _getAll(@NotNull AbstractORM orm, @NotNull Int2ObjectMap<MClass> classes)
    {
        @Nullable Int2ObjectMap<MQuery> query = new Int2ObjectLinkedOpenHashMap<>();
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `query`, `class`  FROM `sentiment`");
            @NotNull final ResultSet         result    = statement.executeQuery();
            while(result.next())
            {
                final int             id    = result.getInt("id");
                @NotNull final MClass clazz = classes.getOrDefault(result.getInt("class"), null);
                assert clazz != null;
                query.put(id, new MQuery(
                        id,
                        result.getString("query"),
                        clazz
                ));
            }
            result.close();
            statement.close();
            orm.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return query;
    }
}
