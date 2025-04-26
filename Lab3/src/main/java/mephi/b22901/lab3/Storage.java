/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.lab3;

import java.util.List;

/**
 *
 * @author Регина
 */
public class Storage {

    public static List<Creature> jsonStorage;
    public static List<Creature> yamlStorage;
    public static List<Creature> xmlStorage;

    public static void writeData(List<Creature> creatures, String ex) {
        switch (ex) {
            case ".xml":
                xmlStorage = creatures;
                break;
            case ".json":
                jsonStorage = creatures;
                break;
            case ".yaml":
                yamlStorage = creatures;
                break;
        }
    }

}
