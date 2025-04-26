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

    private GUI gui;
    private XmlHandler xml;
    private JsonHandler json;
    private YamlHandler yaml;
    private Storage storage;

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

}
