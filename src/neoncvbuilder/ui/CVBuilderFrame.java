package neoncvbuilder.ui;

import neoncvbuilder.model.*;
import neoncvbuilder.util.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class CVBuilderFrame extends JFrame {
    private JTextField txtFullName, txtEmail, txtPhone, txtAddress, txtAvatarPath;
    private JTextArea txtSummary, txtSkills, txtExperience, txtEducation, txtProjects, txtCertifications;

    public CVBuilderFrame() {
        setTitle("CV Builder - Phiên bản nâng cấp");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtFullName = new JTextField(); txtEmail = new JTextField(); txtPhone = new JTextField();
        txtAddress = new JTextField(); txtAvatarPath = new JTextField();
        txtSummary = new JTextArea(3, 20); txtSkills = new JTextArea(3, 20); txtExperience = new JTextArea(3, 20);
        txtEducation = new JTextArea(3, 20); txtProjects = new JTextArea(3, 20); txtCertifications = new JTextArea(3, 20);

        // Đặt màu trắng cho các vùng nhập
        JTextArea[] areas = {txtSummary, txtSkills, txtExperience, txtEducation, txtProjects, txtCertifications};
        for (JTextArea area : areas) {
            area.setBackground(Color.WHITE);
            area.setForeground(Color.BLACK);
        }

        form.add(new JLabel("Họ tên")); form.add(txtFullName);
        form.add(new JLabel("Email")); form.add(txtEmail);
        form.add(new JLabel("SĐT")); form.add(txtPhone);
        form.add(new JLabel("Địa chỉ")); form.add(txtAddress);
        form.add(new JLabel("Ảnh đại diện")); form.add(txtAvatarPath);
        form.add(new JLabel("Tóm tắt")); form.add(new JScrollPane(txtSummary));
        form.add(new JLabel("Kỹ năng")); form.add(new JScrollPane(txtSkills));
        form.add(new JLabel("Kinh nghiệm")); form.add(new JScrollPane(txtExperience));
        form.add(new JLabel("Học vấn")); form.add(new JScrollPane(txtEducation));
        form.add(new JLabel("Dự án")); form.add(new JScrollPane(txtProjects));
        form.add(new JLabel("Chứng chỉ")); form.add(new JScrollPane(txtCertifications));

        JButton btnSave = new JButton("Lưu CV (.dat)");
        JButton btnOpen = new JButton("Mở CV (.dat)");
        JButton btnPreview = new JButton("Xem trước CV");
        JButton btnExportHtml = new JButton("Xuất ra HTML");

        btnSave.addActionListener(e -> saveCV());
        btnOpen.addActionListener(e -> openCV());
        btnPreview.addActionListener(e -> previewHtml());
        btnExportHtml.addActionListener(e -> exportHtml());

        JPanel buttons = new JPanel();
        buttons.add(btnSave); buttons.add(btnOpen);
        buttons.add(btnPreview); buttons.add(btnExportHtml);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private CVData collectData() {
        PersonalInfo p = new PersonalInfo(
            txtFullName.getText(),
            txtEmail.getText(),
            txtPhone.getText(),
            txtAddress.getText(),
            txtSummary.getText()
        );

        CVData cv = new CVData();
        cv.setPersonalInfo(p);
        cv.setSkills(txtSkills.getText());
        cv.setExperience(txtExperience.getText());
        cv.setEducation(txtEducation.getText());
        cv.setProjects(txtProjects.getText());
        cv.setCertifications(txtCertifications.getText());
        cv.setAvatarPath(txtAvatarPath.getText());

        return cv;
    }

    private void applyCVToForm(CVData cv) {
        if (cv == null || cv.getPersonalInfo() == null) return;
        PersonalInfo p = cv.getPersonalInfo();

        txtFullName.setText(p.getFullName());
        txtEmail.setText(p.getEmail());
        txtPhone.setText(p.getPhone());
        txtAddress.setText(p.getAddress());
        txtSummary.setText(p.getSummary());

        txtSkills.setText(cv.getSkills());
        txtExperience.setText(cv.getExperience());
        txtEducation.setText(cv.getEducation());
        txtProjects.setText(cv.getProjects());
        txtCertifications.setText(cv.getCertifications());
        txtAvatarPath.setText(cv.getAvatarPath());
    }

    private void saveCV() {
        CVData cv = collectData();
        FileUtils.saveObjectToFile(cv, this);
    }

    private void openCV() {
        CVData cv = FileUtils.openObjectFromFile(this);
        if (cv != null) {
            applyCVToForm(cv);
            JOptionPane.showMessageDialog(this, "Đã nạp CV thành công!");
        }
    }

    private void previewHtml() {
        CVData cv = collectData();
        String html = HtmlUtils.buildHtml(cv);
        try {
            File tempFile = File.createTempFile("cv_preview", ".html");
            try (PrintWriter writer = new PrintWriter(tempFile)) {
                writer.write(html);
            }
            Desktop.getDesktop().browse(tempFile.toURI());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xem trước: " + ex.getMessage());
        }
    }

    private void exportHtml() {
        CVData cv = collectData();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Xuất CV ra HTML");
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".html")) {
                file = new File(file.getAbsolutePath() + ".html");
            }
            try (PrintWriter writer = new PrintWriter(file)) {
                String html = HtmlUtils.buildHtml(cv);
                writer.write(html);
                JOptionPane.showMessageDialog(this, "Đã xuất HTML thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất HTML: " + ex.getMessage());
            }
        }
    }
}