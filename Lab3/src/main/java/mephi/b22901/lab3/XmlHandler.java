/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Регина
 */
public class XmlHandler extends BaseHandler {

    public XmlHandler() {
        super(".xml");
        
    }

    @Override
    protected List<Creature> readData(String path) {
        List<Creature> creatures = new ArrayList<>();
        Creature creature = null;
        XMLInputFactory input = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = input.createXMLEventReader(new FileInputStream(path));
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startEl = event.asStartElement();
                    if (startEl.getName().getLocalPart().equals("creature")) {
                        creature = new Creature();
                        creature.setRecievedFrom(super.extension);
                    } else if (startEl.getName().getLocalPart().equals("id")) {
                        event = reader.nextEvent();
                        creature.setId(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startEl.getName().getLocalPart().equals("name")) {
                        event = reader.nextEvent();
                        creature.setName(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("description")) {
                        event = reader.nextEvent();
                        creature.setDescription(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("dangerLevel")) {
                        event = reader.nextEvent();
                        creature.setDangerLevel(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startEl.getName().getLocalPart().equals("habitat")) {
                        event = reader.nextEvent();
                        creature.setHabitat(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("activity")) {
                        event = reader.nextEvent();
                        creature.setActivity(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("firstMentioned")) {
                        event = reader.nextEvent();
                        creature.setFirstMentioned(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("immunities")) {
                        event = reader.nextEvent();
                        creature.setImmunities(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("vulnerabilities")) {
                        event = reader.nextEvent();
                        creature.setVulnerabilities(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("height")) {
                        event = reader.nextEvent();
                        creature.setHeight(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("weight")) {
                        event = reader.nextEvent();
                        creature.setWeight(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("poisonRecipe")) {
                        event = reader.nextEvent();
                        creature.setPoisonRecipe(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("time")) {
                        event = reader.nextEvent();
                        creature.setTime(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startEl.getName().getLocalPart().equals("efficiency")) {
                        event = reader.nextEvent();
                        creature.setEfficiency(event.asCharacters().getData());
                    } else if (startEl.getName().getLocalPart().equals("efficiency")) {
                        event = reader.nextEvent();
                        creature.setEfficiency(event.asCharacters().getData());
                    }
                }
                if (event.isEndElement()) {
                    EndElement endEl = event.asEndElement();
                    if (endEl.getName().getLocalPart().equals("creature")) {
                        creatures.add(creature);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return creatures;
    }

    @Override
    protected void writeData(String path, List<Creature> creatures
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
