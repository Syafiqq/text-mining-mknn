package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.orm;

import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.dataset.MLocale;
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
 * Date / Time  : 23 April 2017, 10:31 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ORMLocale
{
    public static MLocale insert(@NotNull AbstractORM orm, @NotNull MLocale mClass)
    {
        final int id = ORMLocale._insert(orm, mClass.getName());
        mClass.setId(id);
        return mClass;
    }

    public static MLocale insert(@NotNull AbstractORM orm, @NotNull String name)
    {
        final int id = ORMLocale._insert(orm, name);
        return ORMLocale.getByID(orm, id);
    }

    public static MLocale getByID(@NotNull AbstractORM orm, int id)
    {
        return ORMLocale._getByID(orm, id);
    }

    public @NotNull static Int2ObjectMap<MLocale> getAll(@NotNull AbstractORM orm)
    {
        return ORMLocale._getAll(orm);
    }

    private static int _insert(@NotNull AbstractORM orm, @NotNull String name)
    {
        orm.setProperties(orm);
        int mClassID = MLocale.UNASSIGNED_PK;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("INSERT INTO `locale`(`id`, `name`) VALUES (NULL, ?)");
            statement.setString(1, name);
            statement.execute();
            statement.close();

            @NotNull final PreparedStatement callStatement = orm.connection.prepareStatement("SELECT last_insert_rowid()");
            @NotNull final ResultSet         callResult    = callStatement.executeQuery();
            callResult.next();
            mClassID = callResult.getInt("last_insert_rowid()");
            callResult.close();
            callStatement.close();
            orm.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return mClassID;
    }

    private static MLocale _getByID(@NotNull AbstractORM orm, int id)
    {
        @Nullable MLocale clazz = null;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `name` FROM `locale` WHERE `id` = ? LIMIT 1");
            statement.setInt(1, id);
            @NotNull final ResultSet result = statement.executeQuery();
            while(result.next())
            {
                clazz = new MLocale(
                        result.getInt("id"),
                        result.getString("name")
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
        return clazz;
    }

    private static Int2ObjectMap<MLocale> _getAll(@NotNull AbstractORM orm)
    {
        @Nullable Int2ObjectMap<MLocale> classes = new Int2ObjectLinkedOpenHashMap<>();
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `name` FROM `locale`");
            @NotNull final ResultSet         result    = statement.executeQuery();
            while(result.next())
            {
                final int id = result.getInt("id");
                classes.put(id, new MLocale(
                        id,
                        result.getString("name")
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
        return classes;
    }
}
