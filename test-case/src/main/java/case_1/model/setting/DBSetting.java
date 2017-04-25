package case_1.model.setting;

import case_1.model.util.DBType;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 7:48 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("unused") public class DBSetting
{
    public static final URL    path;
    public static final DBType type;
    private static DBSetting ourInstance = new DBSetting();

    static
    {
        path = ClassLoader.getSystemClassLoader().getResource("db/sentiment.mcrypt");
        type = DBType.DEFAULT;
    }

    private DBSetting()
    {

    }

    @Contract(pure = true) @NotNull public static DBSetting getInstance()
    {
        return ourInstance;
    }

    public static String getDBUrl(URL path, DBType type) throws UnsupportedEncodingException
    {
        final String dbUrl;
        switch(type)
        {
            case JAR:
            {
                dbUrl = java.net.URLDecoder.decode("jdbc:sqlite::resource:jar:" + path.getPath(), "UTF-8");
            }
            break;
            case DEFAULT:
            {
                dbUrl = java.net.URLDecoder.decode("jdbc:sqlite:" + path.getPath(), "UTF-8");
            }
            break;
            default:
            {
                dbUrl = null;
            }
        }
        return dbUrl;
    }
}
