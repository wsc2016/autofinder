import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nktdr
 */
public class MainGUI extends javax.swing.JFrame {

    DefaultListModel resultsListModel;
    DefaultListModel selectedToGraphModel;
    private Vehicle selectedVehicle;
    private Vehicle selectedGraphListVehicle;

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        resultsListModel = new DefaultListModel();
        selectedToGraphModel = new DefaultListModel();
        initComponents();

        listResults.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    if (listResults.getSelectedValue() instanceof String) {
                        listResults.clearSelection();
                    }

                    selectedVehicle = (Vehicle) listResults.getSelectedValue();
                    displayResults();
                }
            }
        });

        graphList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    selectedGraphListVehicle = (Vehicle) graphList.getSelectedValue();
                }
            }
        });

        clearResults();
        setLocationRelativeTo(null);
        buildSliderTicks();
    }

    void clearResults() {
        ImageIcon icon = null;
        resultsPhoto.setIcon(icon);

        resultsManufacturer.setText("");
        resultsModel.setText("");
        resultsYear.setText("");
        resultsClass.setText("");

        resultsTransmission.setText("");
        resultsEngineSize.setText("");
        resultsCylinder.setText("");
        resultsFuelType.setText("");
        resultsFuel.setText("");
        resultsHighway.setText("");
        resultsCity.setText("");
        resultsCO2.setText("");
    }

    void displayResults() {

        resultsPhoto.setIcon(new ImageIcon());

        if (selectedVehicle != null) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    ImageIcon icon = selectedVehicle.getIcon();
                    resultsPhoto.setIcon(icon);
                }
            });

            resultsManufacturer.setText("Manufacturer: " + selectedVehicle.Manufacturer);
            resultsModel.setText("Model: " + selectedVehicle.Model);
            resultsYear.setText("Year: " + selectedVehicle.Year);
            resultsClass.setText("Class: " + selectedVehicle.Class.name);

            resultsTransmission.setText("Transmission: " + selectedVehicle.Transmission);
            resultsEngineSize.setText("Engine Size: " + selectedVehicle.EngineSize);
            resultsCylinder.setText("Cylinder Type: " + selectedVehicle.Cylinder);
            resultsFuelType.setText("Fuel Type: " + selectedVehicle.FuelType);
            resultsFuel.setText("Fuel (L/yr): " + selectedVehicle.Fuel);
            resultsHighway.setText("Highway (L/100km): " + selectedVehicle.FuelConsumptionHWY);
            resultsCity.setText("City (L/100km): " + selectedVehicle.FuelConsumptionCTY);
            resultsCO2.setText("CO² Emissions: " + selectedVehicle.CO2Emission);
        }
    }

    void buildSliderTicks() {

        //build city slider ticks
        sldrCity.setMajorTickSpacing(5);
        Hashtable<Integer, JLabel> lblCityTable = new Hashtable<>();
        JLabel lblCityLabel1 = new JLabel("10");
        JLabel lblCityLabel2 = new JLabel("15");
        JLabel lblCityLabel3 = new JLabel("20");
        JLabel lblCityLabel4 = new JLabel("All");
        lblCityLabel1.putClientProperty("JComponent.sizeVariant", "mini");
        lblCityLabel2.putClientProperty("JComponent.sizeVariant", "mini");
        lblCityLabel3.putClientProperty("JComponent.sizeVariant", "mini");
        lblCityLabel4.putClientProperty("JComponent.sizeVariant", "mini");
        lblCityTable.put(new Integer(10), lblCityLabel1);
        lblCityTable.put(new Integer(15), lblCityLabel2);
        lblCityTable.put(new Integer(20), lblCityLabel3);
        lblCityTable.put(new Integer(25), lblCityLabel4);
        sldrCity.setLabelTable(lblCityTable);
        sldrCity.setValue(sldrCity.getMaximum());
        sldrCity.setPaintLabels(true);

        //build highway slider ticks
        sldrHighway.setMajorTickSpacing(5);
        Hashtable<Integer, JLabel> lblHwyTable = new Hashtable<Integer, JLabel>();
        JLabel lblHwyLabel1 = new JLabel("5");
        JLabel lblHwyLabel2 = new JLabel("10");
        JLabel lblHwyLabel3 = new JLabel("15");
        JLabel lblHwyLabel4 = new JLabel("All");
        lblHwyLabel1.putClientProperty("JComponent.sizeVariant", "mini");
        lblHwyLabel2.putClientProperty("JComponent.sizeVariant", "mini");
        lblHwyLabel3.putClientProperty("JComponent.sizeVariant", "mini");
        lblHwyLabel4.putClientProperty("JComponent.sizeVariant", "mini");
        lblHwyTable.put(new Integer(5), lblHwyLabel1);
        lblHwyTable.put(new Integer(10), lblHwyLabel2);
        lblHwyTable.put(new Integer(15), lblHwyLabel3);
        lblHwyTable.put(new Integer(20), lblHwyLabel4);
        sldrHighway.setLabelTable(lblHwyTable);
        sldrHighway.setValue(sldrHighway.getMaximum());
        sldrHighway.setPaintLabels(true);

        //build CO2 slider ticks
        sldrCO2.setMajorTickSpacing(100);
        Hashtable<Integer, JLabel> lblCO2Table = new Hashtable<Integer, JLabel>();
        JLabel lblCO2Label1 = new JLabel("100");
        JLabel lblCO2Label2 = new JLabel("200");
        JLabel lblCO2Label3 = new JLabel("300");
        JLabel lblCO2Label4 = new JLabel("All");
        lblCO2Label1.putClientProperty("JComponent.sizeVariant", "mini");
        lblCO2Label2.putClientProperty("JComponent.sizeVariant", "mini");
        lblCO2Label3.putClientProperty("JComponent.sizeVariant", "mini");
        lblCO2Label4.putClientProperty("JComponent.sizeVariant", "mini");
        lblCO2Table.put(new Integer(100), lblCO2Label1);
        lblCO2Table.put(new Integer(200), lblCO2Label2);
        lblCO2Table.put(new Integer(300), lblCO2Label3);
        lblCO2Table.put(new Integer(400), lblCO2Label4);
        sldrCO2.setLabelTable(lblCO2Table);
        sldrCO2.setValue(sldrCO2.getMaximum());
        sldrCO2.setPaintLabels(true);

    }

    void populateClassListBox(HashMap<String, VehicleClass> vehicleClasses) {
        classComboBox.addItem("");
        for (Entry<String, VehicleClass> entry : vehicleClasses.entrySet()) {
            VehicleClass vClass = entry.getValue();
            classComboBox.addItem(vClass);
        }
    }

    void addActionListenerToClassComboBox(ActionListener listener) {
        classComboBox.addActionListener(listener);
    }

    void addActionListenerToAddToGraphListButton(ActionListener listener) {
        addToGraphButton.addActionListener(listener);
    }
    void addActionListenerToRevmoveButton(ActionListener listener){
        removeButton.addActionListener(listener);
    }

    VehicleClass getClassComboBoxSelectedValue() {
        if (classComboBox.getSelectedItem() instanceof String) {
            return null;
        }

        return (VehicleClass) classComboBox.getSelectedItem();
    }

    void setCurrentList(ArrayList<Vehicle> list) {
        resultsListModel.clear();
        if (list.isEmpty()) {
            resultsListModel.addElement("No results found.");
            //resultsListModel
        } else {
            for (Vehicle veh : list) {
                resultsListModel.addElement(veh);
            }
        }
    }

    void addToGraphList(Vehicle veh) {
        if(selectedToGraphModel.contains(veh))
            return;
        selectedToGraphModel.addElement(veh);
    }

    void removeFromGraphList() {
        selectedToGraphModel.removeElement(selectedGraphListVehicle);
    }

    void setFuelFilterList(HashSet<String> types) {
        fuelComboBox.removeAllItems();
        fuelComboBox.addItem("");
        for (String type : types) {
            fuelComboBox.addItem(type);
        }
    }

    void setCylinderFilterList(HashSet<Integer> types) {
        cylinderComboBox.removeAllItems();
        cylinderComboBox.addItem("");
        for (int type : types) {
            cylinderComboBox.addItem(type);
        }
    }

    FilterSet getFilterValues() {
        return new FilterSet(fuelComboBox.getSelectedItem(),
                cylinderComboBox.getSelectedItem(),
                sldrCity.getValue(),
                sldrHighway.getValue(),
                sldrCO2.getValue());
    }

    void addActionListenerToSearchButton(ActionListener listener) {
        searchButton.addActionListener(listener);
        searchBox.addActionListener(listener);
    }

    void addActionListenerToFilterButton(ActionListener listener) {
        filterButton.addActionListener(listener);
    }
    //listener for clear button 
    void addActionListenerToClearButton(ActionListener Listener) {
        clearButton.addActionListener(Listener);
    }
    //clears jlist for graphing selection
    void clearSelection(){
        selectedToGraphModel.clear();
    }
    //gets the panel for bar chart
    public JPanel getBarChartPanel() {
        return barChartPanel;
    }
    
    String getSearchBoxText() {
        return searchBox.getText();
    }

    Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }
    
    ArrayList<Vehicle> getGraphList(){
        return Collections.list(selectedToGraphModel.elements());
    }

    void clearSearchBox() {
        searchBox.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        resultsPanel = new javax.swing.JPanel();
        resultsPhoto = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        resultsCity = new javax.swing.JLabel();
        resultsHighway = new javax.swing.JLabel();
        resultsCO2 = new javax.swing.JLabel();
        resultsFuel = new javax.swing.JLabel();
        resultsFuelType = new javax.swing.JLabel();
        resultsCylinder = new javax.swing.JLabel();
        resultsEngineSize = new javax.swing.JLabel();
        resultsTransmission = new javax.swing.JLabel();
        resultsYear = new javax.swing.JLabel();
        resultsManufacturer = new javax.swing.JLabel();
        resultsModel = new javax.swing.JLabel();
        resultsClass = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listResults = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        searchBox = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        sldrHighway = new javax.swing.JSlider();
        sldrCity = new javax.swing.JSlider();
        sldrCO2 = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        fuelComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cylinderComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        classComboBox = new javax.swing.JComboBox();
        filterButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        addToGraphButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        graphList = new javax.swing.JList();
        barChartPanel = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Auto Finder");
        setPreferredSize(new java.awt.Dimension(1400, 786));

        resultsPanel.setBackground(new java.awt.Color(255, 255, 255));
        resultsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        resultsPhoto.setBackground(new java.awt.Color(0, 0, 0));
        resultsPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resultsPhoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        resultsPhoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        resultsCity.setText("resultsCity");

        resultsHighway.setText("resultsHighway");

        resultsCO2.setText("resultsCO2");

        resultsFuel.setText("resultsFuel");

        resultsFuelType.setText("resultsFuelType");

        resultsCylinder.setText("resultsCylinder");

        resultsEngineSize.setText("resultsEngineSize");

        resultsTransmission.setText("resultsTransmission");

        resultsYear.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        resultsYear.setText("resultsYear");

        resultsManufacturer.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        resultsManufacturer.setText("resultsManufacturer");

        resultsModel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        resultsModel.setText("resultsModel");

        resultsClass.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        resultsClass.setText("resultsClass");

        javax.swing.GroupLayout resultsPanelLayout = new javax.swing.GroupLayout(resultsPanel);
        resultsPanel.setLayout(resultsPanelLayout);
        resultsPanelLayout.setHorizontalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(resultsManufacturer, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(resultsPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resultsClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsTransmission, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsEngineSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsCylinder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsFuelType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsFuel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsHighway, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsCO2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        resultsPanelLayout.setVerticalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultsPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(resultsPanelLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(resultsPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(resultsModel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(resultsYear, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(resultsClass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsTransmission)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsEngineSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsCylinder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsFuelType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsFuel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsHighway)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsCity)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultsCO2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resultsManufacturer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        resultsModel.getAccessibleContext().setAccessibleName("resultsName");

        listResults.setModel(resultsListModel);
        listResults.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listResults);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/autofinder.3.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

        searchBox.setText("Search");
        searchBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchBoxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchBoxFocusLost(evt);
            }
        });
        searchBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchBoxKeyPressed(evt);
            }
        });

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        searchButton.setMaximumSize(new java.awt.Dimension(50, 50));
        searchButton.setPreferredSize(new java.awt.Dimension(50, 50));

        sldrHighway.setMaximum(20);
        sldrHighway.setMinimum(5);
        sldrHighway.setPaintLabels(true);
        sldrHighway.setPaintTicks(true);
        sldrHighway.setSnapToTicks(true);

        sldrCity.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        sldrCity.setMaximum(25);
        sldrCity.setMinimum(10);
        sldrCity.setPaintLabels(true);
        sldrCity.setPaintTicks(true);
        sldrCity.setSnapToTicks(true);

        sldrCO2.setMaximum(400);
        sldrCO2.setMinimum(100);
        sldrCO2.setPaintLabels(true);
        sldrCO2.setPaintTicks(true);
        sldrCO2.setSnapToTicks(true);

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel3.setText("Fuel Type:");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel4.setText("Cylinder Type:");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel5.setText("City (L/100km):");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel6.setText("Highway (L/100km):");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel7.setText("CO² Emissions:");

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel9.setText("Vehicle Type:");

        classComboBox.setModel(classComboBox.getModel());
        classComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classComboBoxActionPerformed(evt);
            }
        });

        filterButton.setText("Search");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fuelComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sldrCO2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sldrHighway, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sldrCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cylinderComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filterButton, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(searchBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fuelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cylinderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sldrCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(11, 11, 11)
                .addComponent(sldrHighway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sldrCO2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(filterButton))
        );

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        addToGraphButton.setText("Add");

        jLabel8.setText("Graph:");

        jLabel10.setText("Search Results:");

        graphList.setModel(selectedToGraphModel);
        graphList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(graphList);

        barChartPanel.setBackground(new java.awt.Color(255, 255, 255));
        barChartPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        barChartPanel.setLayout(new java.awt.BorderLayout());

        clearButton.setText("Clear");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addToGraphButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(barChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 717, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(resultsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(addToGraphButton)
                                    .addComponent(jLabel8)
                                    .addComponent(removeButton)
                                    .addComponent(clearButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3))
                            .addComponent(barChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void classComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_classComboBoxActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        removeFromGraphList();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void searchBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBoxFocusGained
        if(searchBox.getText().equals("Search"))
        searchBox.setText("");
    }//GEN-LAST:event_searchBoxFocusGained

    private void searchBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBoxFocusLost
        if(searchBox.getText().isEmpty())
            searchBox.setText("Search");
    }//GEN-LAST:event_searchBoxFocusLost

    private void searchBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBoxKeyPressed

    }//GEN-LAST:event_searchBoxKeyPressed

    public void loadVehicleClass(Object o) {
        //cbxVClass.addItem(o);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToGraphButton;
    private javax.swing.JPanel barChartPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox classComboBox;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox cylinderComboBox;
    private javax.swing.JButton filterButton;
    private javax.swing.JComboBox fuelComboBox;
    private javax.swing.JList graphList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList listResults;
    private javax.swing.JButton removeButton;
    private javax.swing.JLabel resultsCO2;
    private javax.swing.JLabel resultsCity;
    private javax.swing.JLabel resultsClass;
    private javax.swing.JLabel resultsCylinder;
    private javax.swing.JLabel resultsEngineSize;
    private javax.swing.JLabel resultsFuel;
    private javax.swing.JLabel resultsFuelType;
    private javax.swing.JLabel resultsHighway;
    private javax.swing.JLabel resultsManufacturer;
    private javax.swing.JLabel resultsModel;
    private javax.swing.JPanel resultsPanel;
    private javax.swing.JLabel resultsPhoto;
    private javax.swing.JLabel resultsTransmission;
    private javax.swing.JLabel resultsYear;
    private javax.swing.JTextField searchBox;
    private javax.swing.JButton searchButton;
    private javax.swing.JSlider sldrCO2;
    private javax.swing.JSlider sldrCity;
    private javax.swing.JSlider sldrHighway;
    // End of variables declaration//GEN-END:variables

}
