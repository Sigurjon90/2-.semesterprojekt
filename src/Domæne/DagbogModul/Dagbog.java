package Domæne.DagbogModul;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Dagbog {
    
    
List <Indlæg> list = new ArrayList<>();

   

    public Dagbog() {
      
    }
    
   public void deleteEntry(Indlæg entry){
   list.remove(entry);
   }
    
  
    
    
  public List<Indlæg> getList() {
        return list;
    }
    
    
}
