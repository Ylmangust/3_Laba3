/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;

/**
 *
 * @author Регина
 */
public class GUI extends JFrame {

    private final Controller controller;
    private final String dir;
    private final FileNameExtensionFilter filter = new FileNameExtensionFilter("XML, YAML, JSON Files", "xml", "yaml", "json");
    private final JTree creatureTree;
    private final JScrollPane scrollP;

    public GUI(Controller controller) throws URISyntaxException {
        this.controller = controller;
        this.dir = new File(Lab3.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

        JFrame frame = new JFrame("Библиотека нечисти");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLayout(new BorderLayout());

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Бестиарий");
        DefaultTreeModel modelTree = new DefaultTreeModel(root);
        creatureTree = new JTree(modelTree);
        creatureTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = ((JTree) e.getSource()).getSelectionPath();
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (node.isLeaf() && node.getLevel() == 1) {
                        Creature selected = (Creature) node.getUserObject();
                        InfoWindow(selected);
                    }
                }
            }
        });

        scrollP = new JScrollPane(creatureTree);
        scrollP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton importBtn = new JButton("Импортировать");
        importBtn.addActionListener((ActionEvent e) -> {
            String path = getPathForReading();
            if (path != null) {
                root.removeAllChildren();
                List<Creature> creatures = controller.importData(path);
                for (Creature creature : creatures) {
                    root.add(new DefaultMutableTreeNode(creature));
                }
                modelTree.reload();
            }
        });

        JButton exportBtn = new JButton("Экспортировать");
        exportBtn.addActionListener((e) -> {
            String path = getPathForExport();
            int dotIndex = path.lastIndexOf('.');
            String type = (dotIndex == -1) ? "" : path.substring(dotIndex);
            if(!Storage.isEmpty(type)){
                System.out.println(path);
                controller.exportData(path);
            }else {
                JOptionPane.showMessageDialog(null, "Хранилище этого формата пустое, сначала импортируйте данные!", null, JOptionPane.WARNING_MESSAGE);
            }
            
        });
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(importBtn);
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(exportBtn);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(scrollP, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private String getPathForReading() {
        JFileChooser fileChooser = new JFileChooser(dir);
        fileChooser.setFileFilter(filter);
        int ret = fileChooser.showOpenDialog(null);
        String path = null;
        if (ret == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
        }
        return path;
    }

    private String getPathForExport() {
        JFileChooser fileChooser = new JFileChooser(dir);
        fileChooser.setFileFilter(filter);
        int ret = fileChooser.showSaveDialog(null);
        String path = null;
        if (ret == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
            String[] formats = {".xml", ".json", ".yaml"};
            if (!path.endsWith(formats[2]) & !path.endsWith(formats[0]) & !path.endsWith(formats[1])) {
                int selected = JOptionPane.showOptionDialog(null, "Choose file format", "Выбор расширения", 0, JOptionPane.QUESTION_MESSAGE, null, formats, null);
                path += formats[selected];
            }
        } else if (ret == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        return path;
    }

    private void InfoWindow(Creature creature) {
        JFrame frame = new JFrame("Информация о существе");
        frame.setSize(780, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        String[][] labelsAndValues = {
            {"Идентификатор:", Integer.toString(creature.getId())},
            {"Название:", creature.getName()},
            {"Описание:", creature.getDescription()},
            {"Уровень опасности:", Integer.toString(creature.getDangerLevel())},
            {"Места обитания:", creature.getHabitat()},
            {"Активен:", creature.getActivity()},
            {"Первое упоминание:", creature.getFirstMentioned()},
            {"Иммунитеты:", creature.getImmunities()},
            {"Уязвимости:", creature.getVulnerabilities()},
            {"Рост:", creature.getHeight()},
            {"Вес:", creature.getWeight()},
            {"Рецепт средства (зелье, масло, яд):", creature.getPoisonRecipe()},
            {"Время приготовления:", Integer.toString(creature.getTime()) + " минут"},
            {"Эффективность:", creature.getEfficiency()},
            {"Источник данных:", creature.getRecievedFrom() + " файл"}
        };

        for (int i = 0; i < labelsAndValues.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(new JLabel(labelsAndValues[i][0]), gbc);
            gbc.gridx = 1;
            JLabel valueLabel = new JLabel(labelsAndValues[i][1]);
            panel.add(valueLabel, gbc);
        }

        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton editButton = new JButton("Редактировать данные");
        btnPanel.add(editButton);
        editButton.addActionListener((e) -> {
            editWindow();
        });
        frame.add(btnPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void editWindow() {
        JFrame frame = new JFrame("Изменение данных о существе");
        frame.setSize(500, 250);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));

        JPanel panel1 = new JPanel();
        String[] dangerOptions = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        JComboBox danger = new JComboBox(dangerOptions);
        JLabel dangerLabel = new JLabel("Новое значение уровня опасности: ");
        panel1.add(dangerLabel);
        panel1.add(danger);

        JLabel vulnerabilityLabel = new JLabel("Новая информация об уязвимостях: ");
        JTextArea vulnerabilityArea = new JTextArea(3, 15);
        JScrollPane scrollPane = new JScrollPane(vulnerabilityArea);
        vulnerabilityArea.setBorder(new LineBorder(Color.BLACK));
        JPanel panel2 = new JPanel();
        panel2.add(vulnerabilityLabel);
        panel2.add(scrollPane);

        panel.add(panel1);
        panel.add(panel2);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));

        JPanel btnPanel = new JPanel();
        JButton okBtn = new JButton("Сохранить");
        btnPanel.add(okBtn);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
