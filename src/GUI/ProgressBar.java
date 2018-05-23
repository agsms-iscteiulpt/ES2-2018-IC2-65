package GUI;

//import java.awt.BorderLayout;
//import java.awt.Container;

//import javax.swing.BorderFactory;
//import javax.swing.JFrame;
//import javax.swing.JProgressBar;
//import javax.swing.border.Border;

//public class ProgressBar {
//	public static void main(String args[]) {
//		JFrame f = new JFrame("JProgressBar Sample");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		Container content = f.getContentPane();
//		JProgressBar progressBar = new JProgressBar();
//		progressBar.setValue(25);
//		progressBar.setStringPainted(true);
//		Border border = BorderFactory.createTitledBorder("Reading...");
//		progressBar.setBorder(border);
//		content.add(progressBar, BorderLayout.NORTH);
//		f.setSize(300, 100);
//		f.setVisible(true);
//	}
//}

//@SuppressWarnings("serial")
//public class ProgressBar extends JPanel {
// 
//  JProgressBar pbar;
//  static final int MY_MINIMUM=0;
//  static final int MY_MAXIMUM=100;
// 
//  public ProgressBar() {
//     pbar = new JProgressBar();
//     pbar.setMinimum(MY_MINIMUM);
//     pbar.setMaximum(MY_MAXIMUM);
//     add(pbar);
//  }
// 
//  public void updateBar(int newValue) {
//    pbar.setValue(newValue);
//  }
// 
//  public void start() {
// 
//     final ProgressBar pb = new ProgressBar();
// 
//     JFrame frame = new JFrame("Progress Bar");
//     frame.setSize(265, 50);
//     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
//     frame.setContentPane(pb);
//     frame.pack();
//     frame.setVisible(true);
// 
//     for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
//       final int percent=i;
//       try {
//         SwingUtilities.invokeLater(new Runnable() {
//             public void run() {
//               pb.updateBar(percent);
//             }
//         });
//         java.lang.Thread.sleep(100);
//       } catch (InterruptedException e) {;}
//     } 
//  }
//}
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

//@SuppressWarnings("serial")
//public class ProgressBar extends JFrame {
//	// Define a Barra de Progresso
//	JProgressBar prbConta = new JProgressBar();
//	// Define uma Thread para simular rodando
//	Thread roda;
//	
//	public ProgressBar() { 
//		this.setSize(264, 50);
//		this.setLocationRelativeTo(null);
//		this.setTitle("ProgressBar");
//		// Define o valor inicial da Barra
//		prbConta.setMinimum(0);
//		// Define o valor final da Barra de Progresso   
//		prbConta.setMaximum(100);
//		// Mostra o valor na barra
//		prbConta.setStringPainted(true); 
//		// Insere a barra
//		this.getContentPane().add(prbConta, BorderLayout.CENTER); 
////		// Cria um botão para iniciar o processo
////		JButton butInicia = new JButton("Inicia"); 
//		// Ao acionar o botão
////		butInicia.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				// Inicia o valor da Barra
////				prbConta.setValue(prbConta.getMinimum());
////				// Inicia o processo
////				if (roda == null) {
////					roda = new roda();
////					roda.start(); 
////				}
////			}});
////		// Insere o botão na Janela
////		this.getContentPane().add(butInicia, BorderLayout.NORTH); 
//		
//		this.addWindowListener(
//				new WindowAdapter() {
//					public void windowClosing(WindowEvent e) {
//						System.exit(0);
//					}
//				});
//	}
//	
//	
//	class roda extends Thread { 
//		public void run() {
//			// Cria um objeto para atualizar a Barra
//			Runnable runner = new Runnable() {
//				public void run() {
//					// Obtém o resultado atual da Barra
//					int valor = prbConta.getValue();
//					// Atualiza a Barra
//					prbConta.setValue(valor+1);
//				}
//			};
//			for (int i = 0; i < 100; i++) {
//				// ---------------------------------
//				// Faça aqui o processo a realizar
//				
////				new OptimizationProcess(GUI.getProblem_type_selected());
//
//				// ---------------------------------
//				// Atualiza a Barra de Progresso
//				try {
//					SwingUtilities.invokeAndWait(runner); 
//				} catch (java.lang.reflect.InvocationTargetException e) { 
//					break; 
//				} catch (InterruptedException e) {
//				}
//			}
//			roda = null; 
//		}     
//	}
//	
////	public void start() {
////		// Inicia o valor da Barra
////		prbConta.setValue(prbConta.getMinimum());
////		// Inicia o processo
////		if (roda == null) {
////			roda = new roda();
////			roda.start(); 
////		}
////	}
//	
//	public static void main(String args[]) {
//		ProgressBar nova = new ProgressBar();
//		nova.show();
//	}
//}


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class ProgressBar extends JFrame {
  // Define a Barra de Progresso
  JProgressBar prbConta = new JProgressBar();
  // Define uma Thread para simular rodando
  Thread roda;
  public ProgressBar() { 
    this.setSize(265, 50);
    this.setTitle("JProgressBar");
    // Define o valor inicial da Barra
    prbConta.setMinimum(0);
    // Define o valor final da Barra de Progresso   
    prbConta.setMaximum(50);
    // Mostra o valor na barra
    prbConta.setStringPainted(true); 
    // Insere a barra
	 this.getContentPane().add(prbConta, BorderLayout.CENTER); 
//	 // Cria um botão para iniciar o processo
//    JButton butInicia = new JButton("Inicia"); 
//    // Ao acionar o botão
//    butInicia.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        // Inicia o valor da Barra
//        prbConta.setValue(prbConta.getMinimum());
//        // Inicia o processo
//        if (roda == null) {
//          roda = new roda();
//          roda.start(); 
//        }
//    }});
//    // Insere o botão na Janela
//	 this.getContentPane().add(butInicia, BorderLayout.NORTH); 
//    this.addWindowListener(
//      new WindowAdapter() {
//        public void windowClosing(WindowEvent e) {
//          System.exit(0);
//      }
//    });
  }
//  class roda extends Thread { 
//    public void run() {
//      // Cria um objeto para atualizar a Barra
//      Runnable runner = new Runnable() {
//        public void run() {
//          // Obtém o resultado atual da Barra
//          int valor = prbConta.getValue();
//          // Atualiza a Barra
//          prbConta.setValue(valor+1);
//        }
//      };
//      for (int i = 0; i < 100; i++) {
//        // ---------------------------------
//        // Faça aqui o processo a realizar
//        // ---------------------------------
//        // Atualiza a Barra de Progresso
//        try {
//          SwingUtilities.invokeAndWait(runner); 
//        } catch (java.lang.reflect.InvocationTargetException e) { 
//          break; 
//        } catch (InterruptedException e) {
//        }
//      }
//     roda = null; 
//    }     
//  }
  public static void main(String args[]) {
    ProgressBar nova = new ProgressBar();
    nova.show();
  }
}

