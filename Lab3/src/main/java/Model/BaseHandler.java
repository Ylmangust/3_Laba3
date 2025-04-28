/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Регина
 */
public abstract class BaseHandler implements Handler{
    private Handler next;
    protected String extension;
    
    public BaseHandler(String ext){
        this.extension = ext;
    }
    
    public Handler setNext(Handler nextHandler) {
        this.next = nextHandler;
        return nextHandler;
    }
    
    public List<Creature> doImport(String path){
        List <Creature> bestiarium = new ArrayList<>();
        if (path.endsWith(extension)){
          bestiarium =  readData(path);  
          Storage.writeData(bestiarium, extension);
        }
        else if (next != null){
            bestiarium = next.doImport(path);
        }
        return bestiarium;
    }
    
    public void doExport(String path){
        if (path.endsWith(extension)){
          writeData(path);
        }
        else if (next != null){
            next.doExport(path);
        }
    }
    
    protected abstract List<Creature> readData(String path);
    protected abstract void writeData(String path);
}
