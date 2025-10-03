package neoncvbuilder.util;

import neoncvbuilder.model.CVData;

import javax.swing.*;
import java.awt.Component;
import java.io.*;

public class FileUtils {

    public static void saveObjectToFile(CVData cv, Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Lưu CV dưới dạng .dat");
        int res = chooser.showSaveDialog(parent);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".dat")) {
                file = new File(file.getAbsolutePath() + ".dat");
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(cv);
                JOptionPane.showMessageDialog(parent, "Đã lưu: " + file.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent, "Lỗi lưu file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static CVData openObjectFromFile(Component parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Mở file CV (.dat)");
        int res = chooser.showOpenDialog(parent);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (CVData) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(parent, "Lỗi đọc file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}