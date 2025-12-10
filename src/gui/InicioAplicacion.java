package gui;
import javax.swing.SwingUtilities;
public class InicioAplicacion {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PanelManager(1);
            }
        });
    }
}