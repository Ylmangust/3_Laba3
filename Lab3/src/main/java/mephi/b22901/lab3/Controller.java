/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab3;

import java.net.URISyntaxException;
import java.util.List;

/**
 *
 * @author Регина
 */
public class Controller {

    private final GUI gui;
    private final XmlHandler xml;
    private final JsonHandler json;
    private final YamlHandler yaml;

    public Controller() throws URISyntaxException {
        this.gui = new GUI(this);
        xml = new XmlHandler();
        json = new JsonHandler();
        yaml = new YamlHandler();
        xml.setNext(json).setNext(yaml);
    }

    public List<Creature> importData(String path) {
        return (xml.doImport(path));
    }
    
    public void exportData(String path){
        xml.doExport(path);
    }

}
