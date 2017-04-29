package app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback;

import app.freelancer.syafiqq.text.classification.mknn.problem.productfeedback.view.IDashboard;
import javax.swing.SwingUtilities;

/*
 * This <mknn> created by : 
 * Name         : syafiq
 * Date / Time  : 29 April 2017, 9:13 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            IDashboard dashboard = new IDashboard();
            dashboard.setVisible(true);
        });
    }
}
