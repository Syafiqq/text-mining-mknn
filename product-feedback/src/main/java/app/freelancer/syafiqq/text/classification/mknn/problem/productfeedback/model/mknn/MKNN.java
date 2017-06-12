package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn;

import app.freelancer.syafiqq.text.classification.knn.core.Documents;
import app.freelancer.syafiqq.text.classification.knn.core.KNN;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 22 April 2017, 8:01 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
@SuppressWarnings("ConstantConditions") public class MKNN extends KNN
{
    private double unclassifiedAccuracy;

    @Override protected void calculateIDF()
    {
        @NotNull final BagOfWordsImpl dfi = (BagOfWordsImpl) super.DFI;
        @NotNull final BagOfWordsImpl idf = (BagOfWordsImpl) super.IDF;
        final double size = (double) super.classified.size();
        dfi.getBow().forEach((term, occurrence) -> idf.put(term, FastMath.log10(size / occurrence)));
    }


    @Override public void train()
    {
        //===Compile===
        if(super.terms == null)
        {
            System.err.println("Specify Terms First");
            System.exit(-1);
        }

        if(super.IDF == null)
        {
            System.err.println("Specify IDF First");
            System.exit(-1);
        }

        if(super.DFI == null)
        {
            System.err.println("Specify DFI First");
            System.exit(-1);
        }

        if((super.k <= 0) || super.k >= (super.classified.size()))
        {
            System.err.println("1 >= k <= train_size");
            System.exit(-1);
        }

        super.terms.getTerms().clear();

        System.out.println("================Cleaning================================");
        //===Cleaning===
        super.classified.forEach(documents ->
        {
            System.out.printf("%-20s = %s\n", "Document", ((DocumentImpl) documents).getDocuments().getQuery().replace("\r\n", " ").replace('\n', ' '));
            ((DocumentImpl) documents).cleaning();
            System.out.printf("%4s%-16s = %s\n", "", "Cleaning", ((DocumentImpl) documents).getCleanedDocument());
            ((DocumentImpl) documents).tokenize();
            System.out.printf("%4s%-16s = %s\n", "", "Tokenize", Arrays.toString(((DocumentImpl) documents).getTokenize().stream().map(TermImpl::getTerm).toArray()));
            super.terms.collectTerms(documents);
        });
        System.out.println("========================================================");
        System.out.println("================Collected Terms=========================");
        System.out.println(Arrays.toString(super.terms.getTerms().stream().map(term -> ((TermImpl) term).getTerm()).sorted().toArray()));
        System.out.println("========================================================");


        //===Collect Terms===
        System.out.println("================Count Bag Of Words======================");
        AtomicInteger counter = new AtomicInteger(0);
        super.classified.forEach(documents ->
        {
            documents.countTerms(super.terms);
            System.out.printf("%-4d%-20s = %s\n", counter.incrementAndGet(), "Bag Of Words", Arrays.toString(((BagOfWordsImpl) documents.getBagOfWords()).getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%d]", termDoubleEntry.getKey().getTerm(), Math.round(termDoubleEntry.getValue()))).toArray()));
        });
        System.out.println("========================================================");
        System.out.println("================Initialize DFI==========================");
        counter.set(0);
        super.DFI.setTerms(super.terms);
        System.out.printf("%-24s = %s\n", "DFI", Arrays.toString(((BagOfWordsImpl) super.DFI).getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%d]", termDoubleEntry.getKey().getTerm(), Math.round(termDoubleEntry.getValue()))).toArray()));
        System.out.println("========================================================");
        System.out.println("================Initialize IDF==========================");
        counter.set(0);
        super.IDF.setTerms(super.terms);
        System.out.printf("%-24s = %s\n", "IDF", Arrays.toString(((BagOfWordsImpl) super.IDF).getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%d]", termDoubleEntry.getKey().getTerm(), Math.round(termDoubleEntry.getValue()))).toArray()));
        System.out.println("========================================================");


        //===Calculate TF-IDF===
        System.out.println("================Calculate TF-IDF========================");
        System.out.println("----------------Count Entry-----------------------------");
        counter.set(0);
        super.classified.forEach(document -> super.DFI.checkExistence(document));
        System.out.printf("%-24s = %s\n", "DFI Entry", Arrays.toString(((BagOfWordsImpl) super.DFI).getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%d]", termDoubleEntry.getKey().getTerm(), Math.round(termDoubleEntry.getValue()))).toArray()));
        System.out.println("--------------------------------------------------------");
        System.out.println("----------------Calculate IDF---------------------------");
        this.calculateIDF();
        System.out.printf("%-24s = %s\n", "DFI Entry", Arrays.toString(((BagOfWordsImpl) super.IDF).getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%f]", termDoubleEntry.getKey().getTerm(), termDoubleEntry.getValue())).toArray()));
        System.out.println("--------------------------------------------------------");
        counter.set(0);
        System.out.println("----------------Calculate TF-IDF------------------------");
        super.classified.forEach(document ->
        {
            document.calculateTFIDF(super.IDF);
            System.out.printf("%-4d%-20s = %s\n", counter.incrementAndGet(), "TF-IDF", Arrays.toString(((DocumentImpl) document).getTfIdf().getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%f]", termDoubleEntry.getKey().getTerm(), termDoubleEntry.getValue())).toArray()));
            System.out.printf("%-4s%-20s = %s\n", "", "TF-IDF^2", Arrays.toString(((DocumentImpl) document).getTfIdf2().getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%f]", termDoubleEntry.getKey().getTerm(), termDoubleEntry.getValue())).toArray()));

        });
        System.out.println("--------------------------------------------------------");
        System.out.println("========================================================");

        //===Calculate Validity===
        System.out.println("================Calculate Validity======================");
        System.out.printf("K-Value = %d\n", super.k);
        counter.set(0);
        super.classified.forEach(train ->
        {
            super.classified.stream().filter(document -> !document.equals(train)).forEach(document -> train.calculateSimilarity(document, super.terms));
            System.out.printf("%-4dDoc-%-10d%-20s = %s\n", counter.incrementAndGet(), ((DocumentImpl) train).getDocuments().getId(), "Similarity", Arrays.toString(((DocumentImpl) train).getSimilarity().entrySet().stream().map(doc -> String.format("[%d=%f]", ((DocumentImpl) doc.getKey()).getDocuments().getId(), doc.getValue())).toArray()));
            train.orderSimilarity();
            System.out.printf("%-18s%-20s = %s\n", "", "Similarity Ordered", Arrays.toString(((DocumentImpl) train).getSimilarity().entrySet().stream().map(doc -> String.format("[%d=%f]", ((DocumentImpl) doc.getKey()).getDocuments().getId(), doc.getValue())).toArray()));
            train.calculateValidity(super.k);
            System.out.printf("%-18s%-20s = %f\n", "", "Validity", ((DocumentImpl) train).getValidity());

        });
        System.out.println("========================================================");

        /*System.out.println("================Calculate Accuracy======================");
        super.calculateAccuracy();
        System.out.printf("Accuracy = %f%%\n", super.getAccuracy() * 100);
        System.out.println("========================================================");*/


    }

    @Override public void test(@NotNull Documents unclassified)
    {
        System.out.println("================Pengujian===============================");
        DocumentImpl impl = (DocumentImpl) unclassified;
        System.out.printf("%20s = %s\n", "Document", impl.getDocuments().getQuery().replace("\r\n", " ").replace('\n', ' '));
        impl.cleaning();
        System.out.printf("%20s = %s\n", "Cleaning", impl.getCleanedDocument());
        impl.tokenize();
        System.out.printf("%20s = %s\n", "Tokenize", Arrays.toString(impl.getTokenize().stream().map(TermImpl::getTerm).toArray()));
        impl.countTerms(this.terms);
        System.out.printf("%20s = %s\n", "Bag Of Words", Arrays.toString(((BagOfWordsImpl) impl.getBagOfWords()).getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%d]", termDoubleEntry.getKey().getTerm(), Math.round(termDoubleEntry.getValue()))).toArray()));
        impl.calculateTFIDF(this.IDF);
        System.out.printf("%20s = %s\n", "TF-IDF", Arrays.toString(impl.getTfIdf().getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%f]", termDoubleEntry.getKey().getTerm(), termDoubleEntry.getValue())).toArray()));
        System.out.printf("%20s = %s\n", "TF-IDF^2", Arrays.toString(impl.getTfIdf2().getBow().entrySet().stream().map(termDoubleEntry -> String.format("[%s=%f]", termDoubleEntry.getKey().getTerm(), termDoubleEntry.getValue())).toArray()));
        super.classified.forEach(document -> impl.calculateSimilarity(document, this.terms));
        System.out.printf("%20s = %s\n", "Similarity", Arrays.toString(impl.getSimilarity().entrySet().stream().map(doc -> String.format("[%d=%f]", ((DocumentImpl) doc.getKey()).getDocuments().getId(), doc.getValue())).toArray()));
        impl.orderSimilarity();
        System.out.printf("%20s = %s\n", "Similarity Ordered", Arrays.toString(impl.getSimilarity().entrySet().stream().map(doc -> String.format("[%d=%f]", ((DocumentImpl) doc.getKey()).getDocuments().getId(), doc.getValue())).toArray()));
        impl.calculateWeightVoting(this.k);
        System.out.printf("%20s = %s\n", "WeightVoting", Arrays.toString(impl.getWeightVoting().entrySet().stream().map(doc -> String.format("[%d=%f]", ((DocumentImpl) doc.getKey()).getDocuments().getId(), doc.getValue())).toArray()));
        impl.summarize(this.classes);
        impl.orderAndVote();
        System.out.printf("%20s = %s\n", "Class Voting", Arrays.toString(impl.getClassVoting().entrySet().stream().map(doc -> String.format("[%s=%f]", ((ClassImpl) doc.getKey()).getClazz().getId() == 1 ? "Positive" : "Negative", doc.getValue())).toArray()));
        System.out.printf("%20s = %s\n", "Vote", ((ClassImpl) impl.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative");
        super.addUnclassifiedDocument(impl);
        System.out.println("========================================================");
        this.calculateUnclassifiedAccuracy();
    }

    public void calculateUnclassifiedAccuracy()
    {
        @NotNull final AtomicInteger tp = new AtomicInteger(0);
        @NotNull final AtomicInteger total = new AtomicInteger(0);
        this.unclassified.forEach(document ->
        {
            if((document.getClazz() != null))
            {
                total.incrementAndGet();
                if(document.getClazz().equals(document.getClassified()))
                {
                    tp.incrementAndGet();
                }
            }
        });
        this.setUnclassifiedAccuracy((double) tp.get() / (double) total.get());
    }

    public double getUnclassifiedAccuracy()
    {
        return this.unclassifiedAccuracy;
    }

    public void setUnclassifiedAccuracy(double unclassifiedAccuracy)
    {
        this.unclassifiedAccuracy = unclassifiedAccuracy;
    }
}
