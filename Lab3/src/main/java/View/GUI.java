/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.Controller;
import Model.Storage;
import Model.Creature;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;
import mephi.b22901.lab3.Lab3;

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

        try {
            InputStream fontStream = getClass().getResourceAsStream("/chronicles.ttf");
            if (fontStream == null) {
                throw new IOException("Font not found in resources");
            }
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.BOLD, 16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            UIManager.put("Label.font", customFont);
            UIManager.put("Button.font", customFont);
            UIManager.put("TextField.font", customFont);
            UIManager.put("TextArea.font", customFont);
            UIManager.put("ComboBox.font", customFont);
            UIManager.put("FileChooser.listFont", customFont);
            UIManager.put("Panel.background", new Color(3, 3, 47));
            UIManager.put("Button.background", new Color(3, 3, 47));
            UIManager.put("ScrollPane.background", new Color(3, 3, 47));

            UIManager.put("FileChooser.listBackground", new Color(213, 215, 221)); // цвет фона списка
            UIManager.put("FileChooser.listForeground", Color.BLACK);

            UIManager.put("TextField.background", new Color(213, 215, 221));
            UIManager.put("TextField.foreground", Color.BLACK);

            UIManager.put("ComboBox.background", new Color(213, 215, 221));
            UIManager.put("ComboBox.foreground", Color.BLACK);

            UIManager.put("TextArea.background", new Color(213, 215, 221));
            UIManager.put("TextArea.foreground", Color.BLACK);

            UIManager.put("Tree.background", new Color(213, 215, 221));
            UIManager.put("Tree.foreground", Color.BLACK);

            UIManager.put("OptionPane.background", new Color(3, 3, 47));
            UIManager.put("OptionPane.messageForeground", new Color(243, 219, 88));

            Border coloredBorder = new LineBorder(new Color(243, 219, 88), 1);
            Border margin = new EmptyBorder(5, 5, 5, 5);
            Border compoundBorder = new CompoundBorder(coloredBorder, margin);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.border", compoundBorder);

            UIManager.put("Label.foreground", new Color(243, 219, 88));

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                        InfoWindow(node);
                    }
                }
            }
        });

        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setBackgroundNonSelectionColor(new Color(213, 215, 221));
        renderer.setBackgroundSelectionColor(Color.GRAY);
        renderer.setTextSelectionColor(Color.WHITE);
        creatureTree.setCellRenderer(renderer);

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
            if (path != null) {
                int dotIndex = path.lastIndexOf('.');
                String type = (dotIndex == -1) ? "" : path.substring(dotIndex);
                if (!Storage.isEmpty(type)) {
                    controller.exportData(path);
                } else {
                    JOptionPane.showMessageDialog(null, "Хранилище этого формата пустое, сначала импортируйте данные!", null, JOptionPane.WARNING_MESSAGE);
                }
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

    private void InfoWindow(DefaultMutableTreeNode node) {
        Creature creature = (Creature) node.getUserObject();

        JFrame frame = new JFrame("Информация о существе");
        frame.setSize(1000, 550);

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
            editWindow(node);
            frame.dispose();
        });
        frame.add(btnPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void editWindow(DefaultMutableTreeNode node) {
        Creature creature = (Creature) node.getUserObject();

        JFrame frame = new JFrame("Изменение данных о существе");
        frame.setSize(600, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));

        JPanel panel1 = new JPanel();
        Integer[] dangerOptions = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox danger = new JComboBox(dangerOptions);
        danger.setSelectedIndex(creature.getDangerLevel());
        JLabel dangerLabel = new JLabel("Новое значение уровня опасности: ");
        panel1.add(dangerLabel);
        panel1.add(danger);

        JLabel vulnerabilityLabel = new JLabel("Новая информация об уязвимостях: ");
        JTextArea vulnerabilityArea = new JTextArea(creature.getVulnerabilities(), 3, 15);
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
        okBtn.addActionListener((e) -> {
            int selectedDangerLevel = (int) danger.getSelectedItem();
            String newVulnerabilities = vulnerabilityArea.getText();
            creature.setDangerLevel(selectedDangerLevel);
            creature.setVulnerabilities(newVulnerabilities);
            node.setUserObject(creature);
            ((DefaultTreeModel) creatureTree.getModel()).nodeChanged(node);
            controller.saveToStorage(creature.getId(), newVulnerabilities, selectedDangerLevel, creature.getRecievedFrom());
            frame.dispose();
        });
        btnPanel.add(okBtn);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
