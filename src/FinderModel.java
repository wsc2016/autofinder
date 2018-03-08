
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinderModel {

    private ArrayList<Vehicle> vehicleList = new ArrayList<>();
    private ArrayList classList = new ArrayList<>();
    private ArrayList<Vehicle> displayVehicleList;

    private HashMap<String, VehicleManufacturer> ManufacturerList = new HashMap<>();
    private HashMap<String, VehicleClass> ClassList = new HashMap<>();
    
    private HashSet<String> fuelTypes = new HashSet<>();
    private HashSet<Integer> cylinderTypes = new HashSet<>();

    public void load() {
        BufferedReader br = null;
        String line;
        ArrayList<String> usedNames = new ArrayList<>();
        String cvsSplitBy = ",";
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        //String[] filesToRead = new String[]{"data.csv"};
        String[] filesToRead = new String[]{"1999.csv", "2000.csv", "2001.csv", "2002.csv", "2003.csv",
            "2004.csv", "2005.csv", "2006.csv", "2007.csv", "2008.csv", "2009.csv",
            "2010.csv", "2011.csv", "2012.csv", "2013.csv", "2014.csv"};

        try {

            for (String fileName : filesToRead) {
                br = new BufferedReader(new FileReader(fileName));
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] vehicle = line.split(cvsSplitBy);

                    if (vehicle.length != 0 && isNumeric(vehicle[0])) {

                        if (!ManufacturerList.containsKey(vehicle[1])) {
                            ManufacturerList.put(vehicle[1], new VehicleManufacturer(vehicle[1]));
                        }
                        if (!ClassList.containsKey(vehicle[3])) {
                            ClassList.put(vehicle[3], new VehicleClass(vehicle[3]));
                        }
                        Vehicle newVehicle = new Vehicle(vehicle, ManufacturerList.get(vehicle[1]), ClassList.get(vehicle[3]));
                        if(!usedNames.contains(newVehicle.toString()))
                        {
                            usedNames.add(newVehicle.toString());
                            vehicles.add(newVehicle);
                            ManufacturerList.get(vehicle[1]).addVehicle(newVehicle);
                            ClassList.get(vehicle[3]).addVehicle(newVehicle);
                            fuelTypes.add(newVehicle.FuelType);
                            cylinderTypes.add(newVehicle.Cylinder);
                        }
                    }

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(vehicles);{
        
    }
        vehicleList = vehicles;
        displayVehicleList = vehicles;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public ArrayList<Vehicle> getList() {
        return displayVehicleList;
    }

    public ArrayList<Vehicle> getFullList() {
        return vehicleList;
    }
    
    public HashSet<String> getFuelTypes(){
        return fuelTypes;
    }
    
    public HashSet<Integer> getCylinderTypes(){
        return cylinderTypes;
    }

    public ArrayList<Vehicle> getByClass(String vehicleClass) {
        return ClassList.get(vehicleClass).vehicles;
    }

    public HashMap<String, VehicleClass> getVehicleClasses() {
        return ClassList;
    }

    public void filterBySelected(FilterSet filters) {
        ArrayList<Vehicle> filteredList = new ArrayList<>();

        for (Vehicle vehicle : vehicleList) {
            if ((filters.fuelType.isEmpty() || filters.fuelType.equals(vehicle.FuelType))
                    && (filters.cylinderType == vehicle.Cylinder || filters.cylinderType == -1 || filters.cylinderType == 0)
                    && (filters.city >= vehicle.FuelConsumptionCTY)
                    && (filters.highway >= vehicle.FuelConsumptionHWY)
                    && (filters.co2 >= vehicle.CO2Emission)) {
                filteredList.add(vehicle);
            }
        }
        displayVehicleList = filteredList;
    }

    public ArrayList<Vehicle> searchVehicleName(String searchString) {
        ArrayList<Vehicle> results = new ArrayList<>();
        String[] searchTokens = searchString.toUpperCase().split(" ");
        int matchesNeeded = searchTokens.length;
        int matches;

        for (Vehicle vehicle : displayVehicleList) {
            matches = 0;
            for (String string : searchTokens) {
                if (vehicle.Manufacturer.name.contains(string)
                        || (isNumeric(string) && vehicle.Year == Integer.parseInt(string))
                        || vehicle.Model.contains(string) || vehicle.Transmission.equals(string)) {
                    matches++;
                }
            }
            if (matches == matchesNeeded) {
                results.add(vehicle);
            }
        }

        return results;
    }
}
