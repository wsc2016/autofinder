/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Class
 */
public class FilterSet {
    String fuelType;
    int cylinderType;
    int city;
    int highway;
    int co2;
    
    FilterSet(Object fuelType, Object cylinderType, int city, int highway, int co2){
        if(fuelType instanceof String)
            this.fuelType = (String)fuelType;
        
        if(cylinderType instanceof Integer)
            this.cylinderType = (Integer)cylinderType;
        
        this.city = city;
        this.highway = highway;
        this.co2 = co2;
        
    }
    
}
