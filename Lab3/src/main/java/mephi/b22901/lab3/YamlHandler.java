/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab3;

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
        /* InputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(path));
            Yaml yaml = new Yaml();
            List<Map<String, Object>> data = yaml.load(inputStream);
            for (Map<String, Object> creatureData : data) {
                Creature creature = new Creature();
                creature.setId((Integer) creatureData.get("id"));
                creature.setName((String) creatureData.get("name"));
                creature.setDescription((String) creatureData.get("description"));
                creature.setDangerLevel((Integer) creatureData.get("dangerLevel"));
                creature.setHabitat((String) creatureData.get("habitat"));
                creature.setActivity((String) creatureData.get("activity"));
                creature.setFirstMentioned((String) creatureData.get("firstMentioned"));
                creature.setImmunities((String) creatureData.get("immunities"));
                creature.setVulnerabilities((String) creatureData.get("vulnerabilities"));
                creature.setHeight((String) creatureData.get("height"));
                creature.setWeight((String) creatureData.get("weight"));
                creature.setPoisonRecipe((String) creatureData.get("poisonRecipe"));
                creature.setTime((Integer) creatureData.get("time"));
                creature.setEfficiency((String) creatureData.get("efficiency"));
                creature.setRecievedFrom(extension);
                bestiarium.add(creature);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(YamlHandler.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return creatures;
    }

    @Override
    protected void writeData(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            mapper.writeValue(new File(path), Storage.yamlStorage);
        } catch (IOException ex) {
            Logger.getLogger(YamlHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
