import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

public class Vehicle implements Comparable<Vehicle> {

    VehicleManufacturer Manufacturer;
    String Model;
    VehicleClass Class;
    int Year;
    double EngineSize;
    int Cylinder;
    String Transmission;
    String FuelType;
    double FuelConsumptionCTY;
    double FuelConsumptionHWY;
    int Fuel;
    int CO2Emission;
    String imgPath;

    Vehicle(String[] vehicle, VehicleManufacturer manufacturer, VehicleClass vehicleClass) throws IOException {
        this.Year = Integer.parseInt(vehicle[0]);
        this.Manufacturer = manufacturer;
        this.Model = vehicle[2];
        this.Class = vehicleClass;
        this.EngineSize = Double.parseDouble(vehicle[4]);
        this.Cylinder = Integer.parseInt(vehicle[5]);
        this.Transmission = vehicle[6];

        switch (vehicle[7]) {
            case "X":
                this.FuelType = "Regular";
                break;
            case "Z":
                this.FuelType = "Premium";
                break;
            case "D":
                this.FuelType = "Diesel";
                break;
            case "E":
                this.FuelType = "Ethanol";
                break;
            case "N":
                this.FuelType = "Natural Gas";
                break;
        }

        this.FuelConsumptionCTY = Double.parseDouble(vehicle[8]);
        this.FuelConsumptionHWY = Double.parseDouble(vehicle[9]);
        this.Fuel = Integer.parseInt(vehicle[10]);
        this.CO2Emission = Integer.parseInt(vehicle[11]);
        this.imgPath = "";

    }

    @Override
    public String toString() {
        return "(" + Year + ") " + Manufacturer.name + " " + Model;
    }

    public String getImagePath() {
        if (imgPath.isEmpty() || imgPath.equals("invalid")) {
            imgPath = FindImagePath();
        }

        return "http://media.ed.edmunds-media.com" + imgPath;
    }

    private String FindImagePath() {
        InputStream input = null;
        try {
            String url = "https://api.edmunds.com/api/vehicle/v2/" + Manufacturer.name + "/" + Model.split(" ")[0] + "/" + Year + "?fmt=json&api_key=jvf6dvh9tuz7abx67bjqfmvr";
            input = new URL(url).openStream();
            Scanner scan = new Scanner(input);
            String str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();
            JSONObject obj = new JSONObject(str);
            JSONArray array = obj.getJSONArray("styles");
            JSONObject style = (JSONObject) array.get(0);
            String styleKey = Integer.toString((Integer) style.get("id"));

            url = "https://api.edmunds.com/v1/api/vehiclephoto/service/findphotosbystyleid?styleId=" + styleKey + "&fmt=json&comparator=simple&api_key=jvf6dvh9tuz7abx67bjqfmvr";
            input = new URL(url).openStream();
            scan = new Scanner(input);
            str = new String();
            while (scan.hasNext()) {
                str += scan.nextLine();
            }
            scan.close();
            String imagePath = "";
            array = new JSONArray(str);
            if(array.length() == 0)
                return "invalid";
            obj = (JSONObject) array.get(0);
            JSONArray array2 = obj.getJSONArray("photoSrcs");
            imagePath = (String) array2.get(0);
            String[] tokens = imagePath.split("_");
            tokens[tokens.length - 1] = "396.jpg";
            //System.out.println("http://media.ed.edmunds-media.com" + String.join("_", tokens));
            return (String) String.join("_", tokens);
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
            }
        }
        return "invalid";
    }

    ImageIcon getIcon() {
        try {
            URL url = new URL(getImagePath());
            Image image = ImageIO.read(url);
            return new ImageIcon(image);
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
        }

        try {
            return new ImageIcon(ImageIO.read(new File("unavailable.png")));
        } catch (IOException ex) {
        }

        return new ImageIcon();
    }

    @Override
    public int compareTo(Vehicle t) {
        if (this.Year < t.Year) {
            return 1;
        } else if (this.Year > t.Year) {
            return -1;
        } else if (this.Manufacturer.name.compareTo(t.Manufacturer.name) != 0) {
            return this.Manufacturer.name.compareTo(t.Manufacturer.name);
        } else if (this.Model.compareTo(t.Model) != 0) {
            return this.Model.compareTo(t.Model);
        } else {
            return 0;
        }
    }
}
