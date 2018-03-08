


import java.util.ArrayList;

/**
 *
 * @author Class
 */
public class VehicleManufacturer {

    String name;
    ArrayList<Vehicle> vehicles = new ArrayList<>();

    VehicleManufacturer(String name) {
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
