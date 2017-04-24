package case_0;

import app.freelancer.syafiqq.text.dummy.generator.DocumentDummyGenerator;
import case_0.generator.DummyDocuments;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 13 April 2017, 8:13 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class TokenizingTest1
{
    @Test public void it_test_token()
    {
        final DocumentDummyGenerator dummy     = new DummyDocuments();
        final String[]               terms     = dummy.generateTerms();
        final String[]               documents = dummy.generateDocuments();
        final int[][]                termsMap  = dummy.generateTermsMap();

        final Map<String, Integer> actual = new LinkedHashMap<>(terms.length);

        Arrays.stream(terms).forEach(System.out::println);
        Arrays.stream(documents).forEach(System.out::println);

        int counter = -1;
        for(final String document : documents)
        {
            ++counter;
            for(final String term : terms)
            {
                actual.put(term, 0);
            }
            final String[] token = document.split(" ");
            for(final String t : token)
            {
                if(actual.containsKey(t))
                {
                    actual.put(t, actual.get(t) + 1);
                }
            }
            for(int j = -1, js = terms.length; ++j < js; )
            {
                try
                {
                    Assert.assertEquals(termsMap[counter][j], actual.get(terms[j]).intValue());
                }
                catch(ArrayIndexOutOfBoundsException ignored)
                {
                    Assert.assertEquals(0, actual.get(terms[j]).intValue());
                }
            }
        }
    }
}
