/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Регина
 */
public class YamlHandler extends BaseHandler {

    public YamlHandler() {
        super(".yaml");
    }

    @Override
    protected List<Creature> readData(String path) {
        List<Creature> creatures = null;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File file = new File(path);
        try {
            creatures = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Creature.class));
            for (Creature creature : creatures) {
                if (creature.getRecievedFrom() == null) {
                    creature.setRecievedFrom(extension);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(YamlHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return creatures;
    }

    @Override
    protected void writeData(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            mapper.writeValue(new File(path), Storage.getYamlStorage());
        } catch (IOException ex) {
            Logger.getLogger(YamlHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
