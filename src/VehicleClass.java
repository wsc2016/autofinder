


import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Class
 */
public class VehicleClass {

    String name;
    ArrayList<Vehicle> vehicles = new ArrayList<>();

    VehicleClass(String name) {
        this.name = name;
    }
    
    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }
    
    @Override
    public String toString(){
      return this.name; 
    }
}
