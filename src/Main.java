import neoncvbuilder.ui.CVBuilderFrame;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            CVBuilderFrame frame = new CVBuilderFrame();
            frame.setVisible(true);
        });
    }
}