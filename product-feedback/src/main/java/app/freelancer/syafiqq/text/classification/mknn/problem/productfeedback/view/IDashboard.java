/*
 * Created by JFormDesigner on Thu Apr 27 16:43:03 WIB 2017
 */

package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.view;

import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.dataset.MClass;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.dataset.MQuery;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn.BagOfWordsImpl;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn.ClassImpl;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn.DocumentImpl;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn.MKNN;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn.TermContainerImpl;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.mknn.TermImpl;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.orm.AbstractORM;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.orm.ORMClass;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.orm.ORMQuery;
import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.model.setting.DBSetting;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.HorizontalLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Muhammad Syafiq
 */
@SuppressWarnings("Duplicates") public class IDashboard extends JFrame
{
    @Nullable MKNN                        mknn;
    private   Int2ObjectMap<ClassImpl>    classes;
    private   Int2ObjectMap<DocumentImpl> documents;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Muhammad Syafiq
    private   JTabbedPane                 tabbedPane1;
    private   JPanel                      panel1;
    private   JScrollPane                 scrollPane1;
    private   JTable                      table1;
    private   JPanel                      panel2;
    private   JLabel                      lAccuracy;
    private   JPanel                      panel4;
    private   JButton                     button1;
    private   JButton                     button2;
    private   JTextField                  kVal;
    private   JPanel                      panel3;
    private   JScrollPane                 scrollPane2;
    private   JTable                      table2;
    private   JPanel                      panel5;
    private   JTextField                  testField;
    private   JButton                     button3;

    public IDashboard()
    {
        initComponents();
        this.table1.setModel(new DefaultTableModel(new Object[][] {},
                new Object[] {"Feedback", "Processed Feedback", "Original Class", "Classification Class"}));
        this.table2.setModel(new DefaultTableModel(new Object[][] {},
                new Object[] {"Feedback", "Processed Feedback", "Original Class", "Classification Class"}));
    }

    private void loadResource(MouseEvent e)
    {
        try
        {
            @NotNull final AbstractORM           orm      = new AbstractORM(DBSetting.getDBUrl(DBSetting.path, DBSetting.type));
            @NotNull final Int2ObjectMap<MClass> _classes = ORMClass.getAll(orm);
            this.classes = new Int2ObjectLinkedOpenHashMap<>(_classes.size());
            _classes.values().forEach(clazz -> classes.put(clazz.getId(), new ClassImpl(clazz)));

            @NotNull final Int2ObjectMap<MQuery> _documents = ORMQuery.getAll(orm, _classes);
            this.documents = new Int2ObjectLinkedOpenHashMap<>(_documents.size());
            _documents.values().forEach(query -> documents.put(query.getId(), new DocumentImpl(query, classes.get(query.getClazz().getId()), new BagOfWordsImpl())));

            @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table1.getModel();
            tableModel.setRowCount(documents.size());
            @NotNull final AtomicInteger counter = new AtomicInteger(-1);
            documents.values().forEach(document ->
            {
                final int row = counter.incrementAndGet();
                tableModel.setValueAt(document.getDocuments().getQuery(), row, 0);
                tableModel.setValueAt(Arrays.toString(document.getTokenize().stream().map(TermImpl::getTerm).toArray()), row, 1);
                tableModel.setValueAt(document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 2);
                tableModel.setValueAt(document.getClassified() == null ? "-" : ((ClassImpl) document.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 3);
            });
        }
        catch(SQLException | UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
    }

    private void doTrain(MouseEvent e)
    {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Double> future = executor.submit(() ->
        {
            this.documents.values().forEach(document -> document.setClassified(null));
            this.mknn = new MKNN();
            this.mknn.getClassifiedDocument().clear();
            this.mknn.getClasses().clear();
            this.mknn.getClassifiedDocument().addAll(documents.values());
            this.mknn.getClasses().addAll(classes.values());
            this.mknn.setTerms(new TermContainerImpl());
            this.mknn.setDFI(new BagOfWordsImpl());
            this.mknn.setIDF(new BagOfWordsImpl());
            this.mknn.setK(Integer.parseInt(this.kVal.getText()));
            this.mknn.train();
            return this.mknn.getAccuracy();
        });

        try
        {
            Double accuracy = future.get();
            this.lAccuracy.setText(String.format(Locale.getDefault(), "Akurasi : %f%%", accuracy * 100));
            @NotNull final AtomicInteger     counter    = new AtomicInteger(-1);
            @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table1.getModel();
            this.documents.values().forEach(document ->
            {
                final int row = counter.incrementAndGet();
                tableModel.setValueAt(document.getDocuments().getQuery(), row, 0);
                tableModel.setValueAt(Arrays.toString(document.getTokenize().stream().map(TermImpl::getTerm).toArray()), row, 1);
                tableModel.setValueAt(document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 2);
                tableModel.setValueAt(document.getClassified() == null ? "-" : ((ClassImpl) document.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 3);
            });
        }
        catch(InterruptedException | ExecutionException e1)
        {
            e1.printStackTrace();
        }
    }

    private void doTest(MouseEvent e)
    {
        this.mknn.test(new DocumentImpl(new MQuery(this.testField.getText()), null, new BagOfWordsImpl()));
        @NotNull final AtomicInteger     counter    = new AtomicInteger(-1);
        @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table2.getModel();
        tableModel.setRowCount(this.mknn.getUnclassified().size());
        this.mknn.getUnclassified().forEach(rawDocument ->
        {
            @NotNull final DocumentImpl document = (DocumentImpl) rawDocument;
            final int                   row      = counter.incrementAndGet();
            tableModel.setValueAt(document.getDocuments().getQuery(), row, 0);
            tableModel.setValueAt(Arrays.toString(document.getTokenize().stream().map(TermImpl::getTerm).toArray()), row, 1);
            tableModel.setValueAt(document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 2);
            tableModel.setValueAt(document.getClassified() == null ? "-" : ((ClassImpl) document.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 3);
        });
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Muhammad Syafiq
        ResourceBundle bundle = ResourceBundle.getBundle("ui.resource");
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel2 = new JPanel();
        lAccuracy = new JLabel();
        panel4 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        kVal = new JTextField();
        panel3 = new JPanel();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        panel5 = new JPanel();
        testField = new JTextField();
        button3 = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Product Feedback");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                // JFormDesigner evaluation mark
                panel1.setBorder(new javax.swing.border.CompoundBorder(
                        new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                                "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                                javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                                java.awt.Color.red), panel1.getBorder()));
                panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener()
                {
                    public void propertyChange(java.beans.PropertyChangeEvent e)
                    {
                        if("border".equals(e.getPropertyName()))
                        {
                            throw new RuntimeException();
                        }
                    }
                });

                panel1.setLayout(new BorderLayout(5, 5));

                //======== scrollPane1 ========
                {

                    //---- table1 ----
                    table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    scrollPane1.setViewportView(table1);
                }
                panel1.add(scrollPane1, BorderLayout.CENTER);

                //======== panel2 ========
                {
                    panel2.setLayout(new BorderLayout());

                    //---- lAccuracy ----
                    lAccuracy.setText("Akurasi : 0%");
                    lAccuracy.setFont(new Font("Dialog", Font.PLAIN, 30));
                    panel2.add(lAccuracy, BorderLayout.EAST);

                    //======== panel4 ========
                    {
                        panel4.setLayout(new HorizontalLayout(10));

                        //---- button1 ----
                        button1.setText("Load Data Latih");
                        button1.addMouseListener(new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e)
                            {
                                loadResource(e);
                            }
                        });
                        panel4.add(button1);

                        //---- button2 ----
                        button2.setText("Pelatihan");
                        button2.addMouseListener(new MouseAdapter()
                        {
                            @Override
                            public void mouseClicked(MouseEvent e)
                            {
                                doTrain(e);
                            }
                        });
                        panel4.add(button2);

                        //---- kVal ----
                        kVal.setPreferredSize(new Dimension(50, 22));
                        kVal.setMinimumSize(new Dimension(50, 22));
                        kVal.setText("3");
                        kVal.setFont(new Font("Dialog", Font.PLAIN, 20));
                        panel4.add(kVal);
                    }
                    panel2.add(panel4, BorderLayout.WEST);
                }
                panel1.add(panel2, BorderLayout.NORTH);
            }
            tabbedPane1.addTab(bundle.getString("IDashboard.panel1.tab.title"), panel1);

            //======== panel3 ========
            {
                panel3.setLayout(new BorderLayout(10, 10));

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(table2);
                }
                panel3.add(scrollPane2, BorderLayout.CENTER);

                //======== panel5 ========
                {
                    panel5.setMinimumSize(new Dimension(68, 36));
                    panel5.setPreferredSize(new Dimension(68, 36));
                    panel5.setLayout(new BorderLayout());
                    panel5.add(testField, BorderLayout.CENTER);

                    //---- button3 ----
                    button3.setText(bundle.getString("IDashboard.button3.text"));
                    button3.setMinimumSize(new Dimension(100, 30));
                    button3.setPreferredSize(new Dimension(100, 30));
                    button3.setMaximumSize(new Dimension(100, 30));
                    button3.addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mouseClicked(MouseEvent e)
                        {
                            doTest(e);
                        }
                    });
                    panel5.add(button3, BorderLayout.EAST);
                }
                panel3.add(panel5, BorderLayout.PAGE_START);
            }
            tabbedPane1.addTab(bundle.getString("IDashboard.panel3.tab.title"), panel3);
        }
        contentPane.add(tabbedPane1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
