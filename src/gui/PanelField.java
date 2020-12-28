package gui;

import logic.UnZip;
import logic.ZipUp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PanelField extends JPanel {

    List<File> filesList = null;
    String message = "<html><head></head><body>" +
            "Программа предназначенна для упаковки электронных документов<br>" +
            "в один или несколько архивных файлов для <br>" +
            "удобного перемещения или хранения в условиях<br>" +
            "ограниченного объёма памяти на носителях цифровой информации,<br>" +
            "а также для распаковки архивов.<br>" +
            "<br>" +
            "Программа создает архивы в формате <b>.zip</b><br>" +
            "разархивирует архивы в формате: <b>.gz, .zip, .jar</b><br>" +
            "<br>" +
            "P.S. При разархивации архивов, если формат файлов иной,<br>" +
            "то данные файлы игнорируются."
            + "</body></html>";

    public PanelField() {
        filesList = new ArrayList<>();

        setBackground(Color.white);
        setLayout(null);

        JButton btnAddFile = new JButton("Добавить файл");
        btnAddFile.setBounds(10, 10, 150, 30);
        btnAddFile.setBorder(new EmptyBorder(5, 5, 5, 5));
        btnAddFile.setSelected(false);
        btnAddFile.setBackground(new Color(11, 148, 19));
        btnAddFile.setForeground(Color.white);
        add(btnAddFile);

        JButton infoBtn = new JButton("?");
        infoBtn.setBounds(745, 10, 30, 30);
        infoBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
        infoBtn.setSelected(false);
        infoBtn.setBackground(new Color(218, 209, 3));
        infoBtn.setForeground(Color.white);
        infoBtn.setToolTipText(message);
        add(infoBtn);

        JButton clearList = new JButton("Очистить список");
        clearList.setBounds(180, 10, 150, 30);
        clearList.setBorder(new EmptyBorder(5, 5, 5, 5));
        clearList.setSelected(false);
        clearList.setBackground(new Color(250, 20, 100));
        clearList.setForeground(Color.white);
        add(clearList);

        JButton savePathBtn = new JButton("Указать путь для сохранения");
        savePathBtn.setBounds(10, 370, 250, 30);
        savePathBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
        savePathBtn.setSelected(false);
        savePathBtn.setBackground(new Color(11, 148, 19));
        savePathBtn.setForeground(Color.white);
        add(savePathBtn);

        final JTable fileBox = new JTable();
        fileBox.setFont(new Font("Arial", Font.PLAIN, 16));
        fileBox.setBorder(new BasicBorders.FieldBorder(Color.black, Color.blue, Color.blue, Color.lightGray));
        createModelTable((DefaultTableModel) fileBox.getModel());
        setWidthColumn(fileBox);

        JScrollPane scrollPane = new JScrollPane(fileBox);
        scrollPane.setBounds(10, 50, 765, 300);
        add(scrollPane);

        JFileChooser fileChooser = new JFileChooser();
        final JTextField saveFileBox = new JTextField(String.valueOf(fileChooser.getCurrentDirectory()) + "\\ name");
        saveFileBox.setBounds(270, 370, 505, 30);
        add(saveFileBox);

        JButton createBtn = new JButton("Сжать в архив");
        createBtn.setBounds(270, 420, 230, 30);
        createBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
        createBtn.setSelected(false);
        createBtn.setBackground(new Color(11, 148, 19));
        createBtn.setForeground(Color.white);
        add(createBtn);

        JButton unZipBtn = new JButton("Разархивировать");
        unZipBtn.setBounds(545, 420, 230, 30);
        unZipBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
        unZipBtn.setSelected(false);
        unZipBtn.setBackground(new Color(11, 148, 19));
        unZipBtn.setForeground(Color.white);
        add(unZipBtn);

        btnAddFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JFileChooser fc = new JFileChooser();
                fc.setMultiSelectionEnabled(true);
                int returnVal = fc.showOpenDialog(PanelField.this);

                if (returnVal == JFileChooser.APPROVE_OPTION){
                    File files[] = fc.getSelectedFiles();
                    for(int i = 0; i < files.length; i++){
                        File file = files[i];
                        filesList.add(file);
                        addInfo((DefaultTableModel) fileBox.getModel());
                        setWidthColumn(fileBox);
                    }
                }
            }
        });

        clearList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createModelTable((DefaultTableModel) fileBox.getModel());
                filesList.clear();
            }
        });

        savePathBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(PanelField.this);
                if (returnVal == JFileChooser.APPROVE_OPTION){
                    File file  = fc.getSelectedFile();
                    saveFileBox.setText(file.getAbsolutePath());
                }
            }
        });

        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (checkList()) {
                    ZipUp zip = new ZipUp((ArrayList<File>) filesList, saveFileBox.getText());
                    String result = zip.zipFiles();
                    JOptionPane.showMessageDialog(null, result);
                } else {
                    JOptionPane.showMessageDialog(null, "Список файлов пуст!");
                }
            }
        });

        unZipBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkList()) {
                    UnZip unZip = new UnZip((ArrayList<File>) filesList, saveFileBox.getText());
                    String result = unZip.unzip();
                    JOptionPane.showMessageDialog(null, result);
                } else {
                    JOptionPane.showMessageDialog(null, "Список файлов пуст!");
                }
             }
        });

        infoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, message);
            }
        });
    }

    private boolean checkList() {
        if (filesList.size() >= 1) {
            return true;
        }
        return false;
    }

    private DefaultTableModel createModelTable(DefaultTableModel model) {
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn("№");
        model.addColumn("Имя файла");
        model.addColumn("Путь");

        return model;
    }

    private void addInfo(DefaultTableModel model) {
        createModelTable(model);
        for (int i = 0; i < filesList.size(); i++) {
            model.addRow(new Object[]{i+1, filesList.get(i).getName(), filesList.get(i).getAbsolutePath()});
        }
    }

    private void setWidthColumn(JTable table) {
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.setRowHeight(35);
    }

}
