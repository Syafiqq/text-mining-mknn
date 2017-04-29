package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.orm;

import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.dataset.MClass;
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
 * Date / Time  : 22 April 2017, 7:05 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ORMClass
{
    public static MClass insert(@NotNull AbstractORM orm, @NotNull MClass mClass)
    {
        final int id = ORMClass._insert(orm, mClass.getName());
        mClass.setId(id);
        return mClass;
    }

    public static MClass insert(@NotNull AbstractORM orm, @NotNull String name)
    {
        final int id = ORMClass._insert(orm, name);
        return ORMClass.getByID(orm, id);
    }

    public static MClass getByID(@NotNull AbstractORM orm, int id)
    {
        return ORMClass._getByID(orm, id);
    }

    public @NotNull static Int2ObjectMap<MClass> getAll(@NotNull AbstractORM orm)
    {
        return ORMClass._getAll(orm);
    }

    private static int _insert(@NotNull AbstractORM orm, @NotNull String name)
    {
        orm.setProperties(orm);
        int mClassID = MClass.UNASSIGNED_PK;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("INSERT INTO `class`(`id`, `name`) VALUES (NULL, ?)");
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

    private static MClass _getByID(@NotNull AbstractORM orm, int id)
    {
        @Nullable MClass clazz = null;
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `name` FROM `class` WHERE `id` = ? LIMIT 1");
            statement.setInt(1, id);
            @NotNull final ResultSet result = statement.executeQuery();
            while(result.next())
            {
                clazz = new MClass(
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

    private static Int2ObjectMap<MClass> _getAll(@NotNull AbstractORM orm)
    {
        @Nullable Int2ObjectMap<MClass> classes = new Int2ObjectLinkedOpenHashMap<>();
        try
        {
            if(orm.isClosed())
            {
                orm.reconnect();
            }
            @NotNull final PreparedStatement statement = orm.connection.prepareStatement("SELECT `id`, `name` FROM `class`");
            @NotNull final ResultSet         result    = statement.executeQuery();
            while(result.next())
            {
                final int id = result.getInt("id");
                classes.put(id, new MClass(
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
