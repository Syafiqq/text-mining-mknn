package case_0.clazz;

import app.freelancer.syafiqq.text.classification.knn.core.Class;
import org.apache.commons.lang3.builder.ToStringBuilder;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:11 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */


public class Class1 extends Class
{
    private int clazz;

    public Class1(int clazz)
    {
        this.clazz = clazz;
    }

    public int getClazz()
    {
        return this.clazz;
    }

    public void setClazz(int clazz)
    {
        this.clazz = clazz;
    }

    @Override public String toString()
    {
        return new ToStringBuilder(this)
                .append("clazz", clazz)
                .toString();
    }
}
