package case_0.generator;

import app.freelancer.syafiqq.text.dummy.generator.DocumentDummyGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 13 April 2017, 8:41 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DummyDocuments extends DocumentDummyGenerator
{
    private final List<String> dictionary;


    int[][] documents = new int[][]
            {
                    {6, 4, 1, 3, 1, 5, 6, 4, 5, 5, 3, 1, 5},
                    {1, 1, 2, 6, 3, 0, 2, 3, 4, 3, 4, 5, 3},
                    {4, 2, 1, 2, 3, 1, 3, 2, 0, 6, 4, 6, 3},
                    {3, 3, 1, 6, 0, 3, 0, 2, 0, 0, 4, 6, 6},
                    {2, 5, 1, 2, 0, 5, 4, 1, 1, 1, 0, 4, 5},
                    {4, 0, 0, 0, 0, 2, 5, 5, 5, 3, 1, 1, 1},
                    {6, 4, 3, 0, 2, 5, 3, 3, 1, 3, 3, 6, 1},
                    {1, 5, 0, 1, 6, 6, 4, 1, 4, 3, 3, 6, 1}
            };
    private String[] terms;


    public DummyDocuments()
    {
        dictionary = new ArrayList<>();

        dictionary.add("Ayah");
        dictionary.add("Ayam");
        dictionary.add("Adik");
        dictionary.add("Abang");
        dictionary.add("Abah");
        dictionary.add("Antena");
        dictionary.add("Ampas");
        dictionary.add("Arwah");
        dictionary.add("Asap");
        dictionary.add("Amerika");
        dictionary.add("Akar");
        dictionary.add("Abu");
        dictionary.add("Atap");
        dictionary.add("Agar");
        dictionary.add("Baju");
        dictionary.add("Beruang");
        dictionary.add("Babe");
        dictionary.add("Bantal");
        dictionary.add("Bandara");
        dictionary.add("Bakat");
        dictionary.add("Bandeng");
        dictionary.add("Batu");
        dictionary.add("Bendi");
        dictionary.add("Bunda");
        dictionary.add("Bintang");
        dictionary.add("Busa");
        dictionary.add("Bebek");
        dictionary.add("Binatang");
        dictionary.add("Buaya");
        dictionary.add("Bencana");
        dictionary.add("Bubuk");
        dictionary.add("Badai");
        dictionary.add("Bakul");
        dictionary.add("Becak");
        dictionary.add("Bingkisan");
        dictionary.add("Boneka");
        dictionary.add("Bekantan");
        dictionary.add("Bemo");
        dictionary.add("Botol");
        dictionary.add("Cacing");
        dictionary.add("Cicak");
        dictionary.add("Centong");
        dictionary.add("Cobek");
        dictionary.add("Cinta");
        dictionary.add("Cendana");
        dictionary.add("Ciliwung");
        dictionary.add("Cuaca");
        dictionary.add("Combro");
        dictionary.add("Ceker");
        dictionary.add("Cakar");
        dictionary.add("Cikungunya");
        dictionary.add("Duku");
        dictionary.add("Darat");
        dictionary.add("Datuk");
        dictionary.add("Domba");
        dictionary.add("Desa");
        dictionary.add("Dora");
        dictionary.add("Didi");
        dictionary.add("Deni");
        dictionary.add("Dila");
        dictionary.add("Dedek");
        dictionary.add("Dodol");
        dictionary.add("Dosa");
        dictionary.add("Donat");
        dictionary.add("Dewa");
        dictionary.add("Dokar");
        dictionary.add("Dendeng");
        dictionary.add("Elang");
        dictionary.add("Endang");
        dictionary.add("Emas");
        dictionary.add("Entok");
        dictionary.add("Empang");
        dictionary.add("Engkong");
        dictionary.add("Ember");
        dictionary.add("Fifit");
        dictionary.add("Fitri");
        dictionary.add("Gading");
        dictionary.add("Gajah");
        dictionary.add("Gayung");
        dictionary.add("Golok");
        dictionary.add("Genteng");
        dictionary.add("Gembala");
        dictionary.add("Gua");
        dictionary.add("Gong");
        dictionary.add("Gang");
        dictionary.add("Gas");
        dictionary.add("Garam");
        dictionary.add("Gelang");
        dictionary.add("Gunung");
        dictionary.add("Gendang");
        dictionary.add("Giwang");
        dictionary.add("Guntur");
        dictionary.add("Gelombang");
        dictionary.add("Gelar");
        dictionary.add("Gergaji");
        dictionary.add("Gerai");
        dictionary.add("Gundukan");
        dictionary.add("Gambar");
        dictionary.add("Harimau");
        dictionary.add("Hari");
        dictionary.add("Humas");
        dictionary.add("Hendro");
        dictionary.add("Hermawan");
        dictionary.add("Herlambang");
        dictionary.add("Hutan");
        dictionary.add("Hidung");
        dictionary.add("Ikan");
        dictionary.add("Intan");
        dictionary.add("Ilmu");
        dictionary.add("Itik");
        dictionary.add("Iblis");
        dictionary.add("Iman");
        dictionary.add("Istana");
        dictionary.add("Imbuhan");
        dictionary.add("Implan");
        dictionary.add("Jakarta");
        dictionary.add("Jejak");
        dictionary.add("Kambig");
        dictionary.add("Kuda");
        dictionary.add("Kakak");
        dictionary.add("Kendaraan");
        dictionary.add("Kembang");
        dictionary.add("Kursi");
        dictionary.add("Lintah");
        dictionary.add("Lubang");
        dictionary.add("Matahari");
        dictionary.add("Meja");
        dictionary.add("Mie");
        dictionary.add("Minyak");
        dictionary.add("Mantra");
        dictionary.add("Malika");
        dictionary.add("Nenek");
        dictionary.add("Niat");
        dictionary.add("Nektar");
        dictionary.add("Ngengat");
        dictionary.add("Neraka");
        dictionary.add("Obat");
        dictionary.add("Otak");
        dictionary.add("Paris");
        dictionary.add("Parit");
        dictionary.add("Panda");
        dictionary.add("Pedang");
        dictionary.add("Pupuk");
        dictionary.add("Robot");
        dictionary.add("Remote");
        dictionary.add("Rusa");
        dictionary.add("Ririn");
        dictionary.add("Renda");
        dictionary.add("Rumput");
        dictionary.add("Sambal");
        dictionary.add("Sikat");
        dictionary.add("Sholat");
        dictionary.add("Shinta");
        dictionary.add("Tenda");
        dictionary.add("Tikus");
        dictionary.add("Telepon");
        dictionary.add("Tikar");
        dictionary.add("Tuti");
        dictionary.add("Udang");
        dictionary.add("Udara");
        dictionary.add("Umbi");
        dictionary.add("Umi");
        dictionary.add("Upah");
        dictionary.add("Video");
        dictionary.add("Wadah");
        dictionary.add("Wahyu");
        dictionary.add("Yani");
        dictionary.add("Yuni");
        dictionary.add("Zebra");
        dictionary.add("Zakat");

        Collections.shuffle(dictionary);
    }

    @Override public int getDocumentCount()
    {
        return this.documents.length;
    }

    @Override public int getTermCount()
    {
        int max = 0;
        for(int[] sp : this.documents)
        {
            max = Math.max(max, sp.length);
        }
        return max;
    }

    @Override public String[] generateDocuments()
    {
        final Random   random       = ThreadLocalRandom.current();
        final String[] documents    = new String[this.getDocumentCount()];
        final String[] terms        = this.generateTerms();
        final int      maxTermCount = 200;

        for(int i = -1, is = documents.length; ++i < is; )
        {
            final List<String> rawDoc = new ArrayList<>(maxTermCount);
            int                k      = -1;
            for(final int js : this.documents[i])
            {
                ++k;
                for(int j = -1; ++j < js; )
                {
                    rawDoc.add(terms[k]);
                }
            }
            Collections.shuffle(dictionary);
            for(int j = rawDoc.size() - 1; ++j < maxTermCount; )
            {
                rawDoc.add(dictionary.get(random.nextInt(dictionary.size())));
            }
            for(int j = -1, js = 50; ++j < js; )
            {
                Collections.shuffle(rawDoc);
            }
            documents[i] = String.join(" ", rawDoc);
        }

        return documents;
    }

    @Override public String[] generateTerms()
    {
        if(this.terms == null)
        {
            final String[] terms = new String[this.getTermCount()];
            for(int i = -1, is = terms.length; ++i < is; )
            {
                terms[i] = dictionary.remove(0);
            }
            this.terms = terms;
        }
        return this.terms;
    }

    @Override public int[][] generateTermsMap()
    {
        return this.documents;
    }
}
