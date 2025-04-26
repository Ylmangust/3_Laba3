/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab3;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.yaml.snakeyaml.Yaml;

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
        List<Creature> bestiarium = new ArrayList<>();
        InputStream inputStream;
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
        }
        return bestiarium;
    }

    @Override
    protected void writeData(String path, List<Creature> creatures) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
