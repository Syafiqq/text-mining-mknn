package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 7:09 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("WeakerAccess") public class AbstractORM
{
    @SuppressWarnings("NullableProblems")
    @NotNull protected Connection connection;
    @NotNull protected String     path;

    public AbstractORM(@NotNull String path) throws SQLException
    {
        this.path = path;
        this.reconnect();
    }

    public AbstractORM(@NotNull Connection connection, @NotNull String path)
    {
        this.connection = connection;
        this.path = path;
    }

    public AbstractORM(@NotNull AbstractORM orm)
    {
        this(orm.connection, orm.path);
    }

    public void reconnect() throws SQLException
    {
        this.connection = DriverManager.getConnection(this.path);
    }

    public void close() throws SQLException
    {
        this.connection.close();
    }

    public boolean isClosed() throws SQLException
    {
        return this.connection.isClosed();
    }

    public void setProperties(@NotNull AbstractORM properties)
    {
        this.connection = properties.connection;
        this.path = properties.path;
    }
}
