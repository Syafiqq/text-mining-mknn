/*
 * Created by JFormDesigner on Thu Apr 27 16:43:03 WIB 2017
 */

package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.view;

import app.freelancer.syafiqq.text.classification.knn.core.Documents;
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
import com.opencsv.CSVReader;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.HorizontalLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Muhammad Syafiq
 */
@SuppressWarnings("Duplicates") public class IDashboard extends JFrame
{
    @Nullable MKNN mknn;
    private Int2ObjectMap<ClassImpl> classes;
    private Int2ObjectMap<DocumentImpl> documents;
    private Object2IntMap<String> classLookup;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Muhammad Syafiq
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel2;
    private JLabel lAccuracy;
    private JPanel panel4;
    private JButton button1;
    private JButton button2;
    private JTextField kVal;
    private JPanel panel3;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JButton button5;
    private JButton button4;
    private JButton button6;
    private JPanel hSpacer1;
    private JLabel lAccuracy2;
    private JComboBox<String> comboBox1;
    private JTextField testField;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public IDashboard()
    {
        initComponents();
        this.table1.setModel(new DefaultTableModel(new Object[][] {},
                new Object[] {"Feedback", "Processed Feedback", "Original Class", "Validity"}));
        this.table2.setModel(new DefaultTableModel(new Object[][] {},
                new Object[] {"Feedback", "Processed Feedback", "Original Class", "Classification Class"}));
        this.classLookup = new Object2IntLinkedOpenHashMap<>();
        this.classLookup.put("Positive", 1);
        this.classLookup.put("Negative", 2);
    }

    private void loadResource(MouseEvent e)
    {
        try
        {
            @NotNull final AbstractORM orm = new AbstractORM(DBSetting.getDBUrl(DBSetting.path, DBSetting.type));
            @NotNull final Int2ObjectMap<MClass> _classes = ORMClass.getAll(orm);
            this.classes = new Int2ObjectLinkedOpenHashMap<>(_classes.size());
            _classes.values().forEach(clazz -> classes.put(clazz.getId(), new ClassImpl(clazz)));

            @NotNull final Int2ObjectMap<MQuery> _documents = ORMQuery.getAll(orm, _classes);
            this.documents = new Int2ObjectLinkedOpenHashMap<>(_documents.size());
            _documents.values().forEach(query -> documents.put(query.getId(), new DocumentImpl(query, classes.get(query.getClazz().getId()), new BagOfWordsImpl())));

            @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table1.getModel();
            tableModel.setRowCount(documents.size());
            @NotNull final AtomicInteger counter = new AtomicInteger(-1);
            System.out.println("Data Latih");
            documents.values().forEach(document ->
            {
                final int row = counter.incrementAndGet();
                System.out.printf("%-5d %s [%s]\n", row, document.getDocuments().getQuery().replace("\r\n", " ").replace('\n', ' '), document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative");
                tableModel.setValueAt(document.getDocuments().getQuery(), row, 0);
                tableModel.setValueAt(Arrays.toString(document.getTokenize().stream().map(TermImpl::getTerm).toArray()), row, 1);
                tableModel.setValueAt(document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 2);
                //tableModel.setValueAt(document.getClassified() == null ? "-" : ((ClassImpl) document.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 3);
                tableModel.setValueAt("-", row, 3);
            });
            System.out.println("======================================");
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
            @NotNull final AtomicInteger counter = new AtomicInteger(-1);
            @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table1.getModel();
            this.documents.values().forEach(document ->
            {
                final int row = counter.incrementAndGet();
                tableModel.setValueAt(document.getDocuments().getQuery(), row, 0);
                tableModel.setValueAt(Arrays.toString(document.getTokenize().stream().map(TermImpl::getTerm).toArray()), row, 1);
                tableModel.setValueAt(document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 2);
                //tableModel.setValueAt(document.getClassified() == null ? "-" : ((ClassImpl) document.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 3);
                tableModel.setValueAt(String.format("%f", document.getValidity()), row, 3);
            });
        }
        catch(InterruptedException | ExecutionException e1)
        {
            e1.printStackTrace();
        }
    }

    private void doTest(MouseEvent e)
    {
        if(this.mknn != null)
        {
            String clazz = (String) this.comboBox1.getSelectedItem();
            this.mknn.test(new DocumentImpl(new MQuery(this.testField.getText()), clazz.contentEquals("Tidak Tahu") ? null : this.classes.get(this.classLookup.get(clazz).intValue()), new BagOfWordsImpl()));
            @NotNull final AtomicInteger counter = new AtomicInteger(-1);
            @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table2.getModel();
            tableModel.setRowCount(this.mknn.getUnclassified().size());
            this.mknn.getUnclassified().forEach(rawDocument ->
            {
                @NotNull final DocumentImpl document = (DocumentImpl) rawDocument;
                final int row = counter.incrementAndGet();
                tableModel.setValueAt(document.getDocuments().getQuery(), row, 0);
                tableModel.setValueAt(Arrays.toString(document.getTokenize().stream().map(TermImpl::getTerm).toArray()), row, 1);
                tableModel.setValueAt(document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 2);
                tableModel.setValueAt(document.getClassified() == null ? "-" : ((ClassImpl) document.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 3);
            });

            this.getUnclassifiedAccuracy();
        }
    }

    private void getUnclassifiedAccuracy()
    {
        if(this.mknn != null)
        {
            Double accuracy = this.mknn.getUnclassifiedAccuracy();
            this.lAccuracy2.setText(String.format("Akurasi : %f%%", Double.isNaN(accuracy) ? 0 : accuracy * 100));
        }
    }

    private void onTestingButtonPressed(ActionEvent e)
    {
        if(this.mknn != null)
        {
            this.mknn.getUnclassified().clear();
            this.mknn.calculateUnclassifiedAccuracy();
            @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table2.getModel();
            tableModel.setRowCount(this.mknn.getUnclassified().size());
            this.getUnclassifiedAccuracy();
        }
    }

    private void onTestTrainingDataPressed(ActionEvent e)
    {
        if(this.mknn != null)
        {
            this.mknn.getClassifiedDocument().forEach(documents1 -> this.mknn.test(documents1));
            @NotNull final AtomicInteger counter = new AtomicInteger(-1);
            @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table2.getModel();
            tableModel.setRowCount(this.mknn.getUnclassified().size());
            this.mknn.getUnclassified().forEach(rawDocument ->
            {
                @NotNull final DocumentImpl document = (DocumentImpl) rawDocument;
                final int row = counter.incrementAndGet();
                tableModel.setValueAt(document.getDocuments().getQuery(), row, 0);
                tableModel.setValueAt(Arrays.toString(document.getTokenize().stream().map(TermImpl::getTerm).toArray()), row, 1);
                tableModel.setValueAt(document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 2);
                tableModel.setValueAt(document.getClassified() == null ? "-" : ((ClassImpl) document.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 3);
            });

            this.getUnclassifiedAccuracy();
        }
    }

    private void onExternalDataTesting(ActionEvent e)
    {
        if(this.mknn != null)
        {
            JFileChooser fileopen = new JFileChooser();
            fileopen.setCurrentDirectory(new File(System.getProperty("user.dir")));
            FileFilter filter = new FileNameExtensionFilter("CSV", "csv");
            fileopen.setFileFilter(filter);
            fileopen.setAcceptAllFileFilterUsed(false);

            int ret = fileopen.showDialog(null, "Open file");

            if(ret == JFileChooser.APPROVE_OPTION)
            {
                CSVReader reader;
                try
                {
                    reader = new CSVReader(new FileReader(fileopen.getSelectedFile()));
                    List<String[]> entries = reader.readAll();
                    List<DocumentImpl> docs = new ArrayList<>(entries.size());
                    for(@NotNull final String[] entry : entries)
                    {
                        try
                        {
                            if(entry[1].contentEquals("-") || entry[1].contentEquals("Positive") || entry[1].contentEquals("Negative"))
                            {
                                docs.add(new DocumentImpl(new MQuery(entry[0]), entry[1].contentEquals("-") ? null : this.classes.get(this.classLookup.get(entry[1]).intValue()), new BagOfWordsImpl()));
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(this, "An error occurred while operating this file", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        catch(Exception ignored)
                        {
                            JOptionPane.showMessageDialog(this, "An error occurred while operating this file", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    for(@NotNull final Documents doc : docs)
                    {
                        this.mknn.test(doc);
                    }
                    @NotNull final AtomicInteger counter = new AtomicInteger(-1);
                    @NotNull final DefaultTableModel tableModel = (DefaultTableModel) this.table2.getModel();
                    tableModel.setRowCount(this.mknn.getUnclassified().size());
                    this.mknn.getUnclassified().forEach(rawDocument ->
                    {
                        @NotNull final DocumentImpl document = (DocumentImpl) rawDocument;
                        final int row = counter.incrementAndGet();
                        tableModel.setValueAt(document.getDocuments().getQuery(), row, 0);
                        tableModel.setValueAt(Arrays.toString(document.getTokenize().stream().map(TermImpl::getTerm).toArray()), row, 1);
                        tableModel.setValueAt(document.getClazz() == null ? "-" : ((ClassImpl) document.getClazz()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 2);
                        tableModel.setValueAt(document.getClassified() == null ? "-" : ((ClassImpl) document.getClassified()).getClazz().getId() == 1 ? "Positive" : "Negative", row, 3);
                    });

                    this.getUnclassifiedAccuracy();
                }
                catch(IOException e1)
                {
                    JOptionPane.showMessageDialog(this, "An error occurring while opening this file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
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
        panel6 = new JPanel();
        panel7 = new JPanel();
        button5 = new JButton();
        button4 = new JButton();
        button6 = new JButton();
        hSpacer1 = new JPanel(null);
        lAccuracy2 = new JLabel();
        comboBox1 = new JComboBox<>();
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
                    lAccuracy.setVisible(false);
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
                    panel5.setMinimumSize(new Dimension(68, 80));
                    panel5.setPreferredSize(new Dimension(68, 80));
                    panel5.setLayout(new BorderLayout());

                    //======== panel6 ========
                    {
                        panel6.setLayout(new BorderLayout(5, 5));

                        //======== panel7 ========
                        {
                            panel7.setLayout(new HorizontalLayout(20));

                            //---- button5 ----
                            button5.setText("Uji Data Latih");
                            button5.addActionListener(e -> onTestTrainingDataPressed(e));
                            panel7.add(button5);

                            //---- button4 ----
                            button4.setText("Uji Dari Dokumen");
                            button4.addActionListener(e -> onExternalDataTesting(e));
                            panel7.add(button4);

                            //---- button6 ----
                            button6.setText("Hapus Pengujian");
                            button6.addActionListener(e -> onTestingButtonPressed(e));
                            panel7.add(button6);
                        }
                        panel6.add(panel7, BorderLayout.WEST);
                        panel6.add(hSpacer1, BorderLayout.CENTER);

                        //---- lAccuracy2 ----
                        lAccuracy2.setText("Akurasi : 0%");
                        lAccuracy2.setFont(new Font("Dialog", Font.PLAIN, 30));
                        panel6.add(lAccuracy2, BorderLayout.EAST);
                    }
                    panel5.add(panel6, BorderLayout.NORTH);

                    //---- comboBox1 ----
                    comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                            "Tidak Tahu",
                            "Positive",
                            "Negative"
                    }));
                    panel5.add(comboBox1, BorderLayout.WEST);
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
}
