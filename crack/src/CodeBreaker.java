import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import client.view.ProgressItem;
import client.view.StatusWindow;
import client.view.WorklistItem;
import network.Sniffer;
import network.SnifferCallback;


import rsa.ProgressTracker;
import rsa.Crypto;
import rsa.Factorizer;


public class CodeBreaker implements SnifferCallback {

    private final JPanel workList;
    private final JPanel progressList;
    
    private final JProgressBar mainProgressBar; // fortsätt här - uppgift 19
    
    // -----------------------------------------------------------------------
    
    private CodeBreaker() {
        StatusWindow w  = new StatusWindow();

        workList        = w.getWorkList();
        progressList    = w.getProgressList();
        mainProgressBar = w.getProgressBar();
    }
    
    // -----------------------------------------------------------------------
    
    public static void main(String[] args) {

        /*
         * Most Swing operations (such as creating view elements) must be performed in
         * the Swing EDT (Event Dispatch Thread).
         * 
         * That's what SwingUtilities.invokeLater is for.
         */

        SwingUtilities.invokeLater(() -> {
            CodeBreaker codeBreaker = new CodeBreaker();
            new Sniffer(codeBreaker).start();
        });
    }

    // -----------------------------------------------------------------------

    /** Called by a Sniffer thread when an encrypted message is obtained. */
    @Override
    public void onMessageIntercepted(String message, BigInteger n) {
    	SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				WorklistItem wli = new WorklistItem(n, message);
				workList.add(wli);
				JButton button = new JButton("Chill");
				button.addActionListener(e -> {
					workList.remove(wli);
					ProgressItem item = new ProgressItem(n, message);
					progressList.add(item);
					
					ProgressTracker tracker = new Tracker(item);
					
					try {
						String fac = Factorizer.crack(message, n, tracker);
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								item.getTextArea().setText(fac);
								JButton button = new JButton("Remove");
								button.addActionListener(e -> {
									progressList.remove(item);
								});
								item.add(button);
							}
						});
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				});
					
				wli.add(button);
				//workList.add(wli);
			}
    	});
    }
}
