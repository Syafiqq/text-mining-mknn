import app.freelancer.syafiqq.text.classification.knn.core.KNN;
import app.freelancer.syafiqq.text.classification.knn.core.TermContainer;
import case_0.StringTerm;
import case_0.clazz.IntegerClass;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 8:16 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ClassTermTest
{
    @Test public void it_term_count_3()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertNotNull(cl1);
        cl1.addTerm(new StringTerm("Adik"));
        cl1.addTerm(new StringTerm("Abang"));
        cl1.addTerm(new StringTerm("Abah"));
        Assert.assertEquals(3, cl1.getTermContainer().getTerms().size());
        Assert.assertEquals(1, cl1.getClazz());
    }

    @Test public void it_term_count_1()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertNotNull(cl1);
        cl1.addTerm(new StringTerm("Adik"));
        cl1.addTerm(new StringTerm("Adik"));
        cl1.addTerm(new StringTerm("Adik"));
        Assert.assertEquals(1, cl1.getTermContainer().getTerms().size());
        Assert.assertEquals(1, cl1.getClazz());
    }

    @Test public void it_term_accumulator_size_is_6()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertNotNull(cl1);
        cl1.addTerm(new StringTerm("Adik"));
        cl1.addTerm(new StringTerm("Abang"));
        cl1.addTerm(new StringTerm("Abah"));
        Assert.assertEquals(3, cl1.getTermContainer().getTerms().size());
        Assert.assertEquals(1, cl1.getClazz());

        @NotNull final IntegerClass cl2 = new IntegerClass(1);
        Assert.assertNotNull(cl2);
        cl2.addTerm(new StringTerm("Antena"));
        cl2.addTerm(new StringTerm("Ampas"));
        cl2.addTerm(new StringTerm("Arwah"));
        Assert.assertEquals(3, cl2.getTermContainer().getTerms().size());
        Assert.assertEquals(1, cl2.getClazz());

        KNN knn = new KNN();
        knn.addClass(cl1);
        knn.addClass(cl2);
        knn.setTerms(new TermContainer());
        knn.collectTerms();

        Assert.assertEquals(6, knn.getTermContainer().getTerms().size());
    }

    @Test public void it_term_accumulator_size_is_5()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertNotNull(cl1);
        cl1.addTerm(new StringTerm("Adik"));
        cl1.addTerm(new StringTerm("Abang"));
        cl1.addTerm(new StringTerm("Abah"));
        Assert.assertEquals(3, cl1.getTermContainer().getTerms().size());
        Assert.assertEquals(1, cl1.getClazz());

        @NotNull final IntegerClass cl2 = new IntegerClass(1);
        Assert.assertNotNull(cl2);
        cl2.addTerm(new StringTerm("Adik"));
        cl2.addTerm(new StringTerm("Ampas"));
        cl2.addTerm(new StringTerm("Arwah"));
        Assert.assertEquals(3, cl2.getTermContainer().getTerms().size());
        Assert.assertEquals(1, cl2.getClazz());

        KNN knn = new KNN();
        knn.addClass(cl1);
        knn.addClass(cl2);
        knn.setTerms(new TermContainer());
        knn.collectTerms();

        Assert.assertEquals(5, knn.getTermContainer().getTerms().size());
    }

    @Test public void it_term_accumulator_size_is_3()
    {
        @NotNull final IntegerClass cl1 = new IntegerClass(1);
        Assert.assertNotNull(cl1);
        cl1.addTerm(new StringTerm("Adik"));
        cl1.addTerm(new StringTerm("Abang"));
        cl1.addTerm(new StringTerm("Abah"));
        Assert.assertEquals(3, cl1.getTermContainer().getTerms().size());
        Assert.assertEquals(1, cl1.getClazz());

        @NotNull final IntegerClass cl2 = new IntegerClass(1);
        Assert.assertNotNull(cl2);
        cl2.addTerm(new StringTerm("Adik"));
        cl2.addTerm(new StringTerm("Abang"));
        cl2.addTerm(new StringTerm("Abah"));
        Assert.assertEquals(3, cl2.getTermContainer().getTerms().size());
        Assert.assertEquals(1, cl2.getClazz());

        KNN knn = new KNN();
        knn.addClass(cl1);
        knn.addClass(cl2);
        knn.setTerms(new TermContainer());
        knn.collectTerms();

        Assert.assertEquals(3, knn.getTermContainer().getTerms().size());
    }
}
