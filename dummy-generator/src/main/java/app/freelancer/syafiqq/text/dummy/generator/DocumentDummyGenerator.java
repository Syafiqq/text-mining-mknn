package app.freelancer.syafiqq.text.dummy.generator;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 15 April 2017, 6:02 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class DocumentDummyGenerator
{
    public abstract int getDocumentCount();

    public abstract int getTermCount();

    public abstract String[] generateDocuments();

    public abstract String[] generateTerms();

    public abstract int[][] generateTermsMap();
}
