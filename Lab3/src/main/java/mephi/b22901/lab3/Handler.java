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
public interface Handler {

    public Handler setNext(Handler h);
    public List<Creature> doImport(String path);
    public void doExport(String path);

}
