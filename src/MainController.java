import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartPanel;

public class MainController {

    private final FinderModel model;
    private MainGUI gui;
    private List cars = new ArrayList();

    public MainController(final FinderModel model, final MainGUI gui) {
        this.model = model;
        this.gui = gui;
        long start = System.currentTimeMillis();
        model.load();

        cars.addAll(model.getList());
        gui.setCurrentList(model.getList());
        gui.setFuelFilterList(model.getFuelTypes());
        gui.setCylinderFilterList(model.getCylinderTypes());
        displayGraph();

        //Populates the Class ComboBox with all the vehicle classes and 
        //assigns a listener to them for when a selection is made.
        gui.populateClassListBox(model.getVehicleClasses());
        gui.addActionListenerToRevmoveButton(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                gui.removeFromGraphList();
                displayGraph();
            }
            
        });
        gui.addActionListenerToClassComboBox(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (gui.getClassComboBoxSelectedValue() != null) {
                    gui.setCurrentList(gui.getClassComboBoxSelectedValue().vehicles);
                    gui.clearSearchBox();
                } else {
                    gui.setCurrentList(model.getFullList());
                }
            }

        });

        gui.addActionListenerToSearchButton(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!gui.getSearchBoxText().isEmpty() || !gui.getSearchBoxText().equals("Search")) {
                    gui.setCurrentList(model.searchVehicleName(gui.getSearchBoxText()));

                }
            }
        });

        gui.addActionListenerToFilterButton(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                model.filterBySelected(gui.getFilterValues());
                gui.setCurrentList(model.getList());
            }
        });
        //adds to jlist for graphing and display panel
        gui.addActionListenerToAddToGraphListButton(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Vehicle veh = gui.getSelectedVehicle();
                gui.addToGraphList(veh);
                displayGraph();
            }
           
        });
        //clear graphing selection jlist and display empty graph
        gui.addActionListenerToRevmoveButton(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                gui.clearSelection();
                displayGraph();
            }
            
        });
        
        gui.setVisible(true);
    }
    //method for displaying bar graph to gui panel
    public void displayGraph() {
        //construct data structure for bar graph
        DefaultCategoryDataset barChartData = new DefaultCategoryDataset();
        //use for loop here to add data from chartList to BarCharData
        for (int i = 0; i < gui.getGraphList().size(); i++) {
            barChartData.setValue(gui.getGraphList().get(i).CO2Emission, "CO2 Emission", gui.getGraphList().get(i).Model);
            barChartData.setValue(gui.getGraphList().get(i).FuelConsumptionCTY, "City L/100KM", gui.getGraphList().get(i).Model);
            barChartData.setValue(gui.getGraphList().get(i).FuelConsumptionHWY, "HWY L/100KM", gui.getGraphList().get(i).Model);
        }

        //formats the data to bar cart
        JFreeChart barChart = ChartFactory.createBarChart3D("Vehicle Comparison", "Selected Cars", " ", barChartData, PlotOrientation.HORIZONTAL, true, true, false);
        CategoryPlot chartBars = barChart.getCategoryPlot();

        //adds barCart to ChartPanel Class
        ChartPanel barPanel = new ChartPanel(barChart);
        gui.getBarChartPanel().removeAll();

        //adds the chart panel onto the gui panel
        gui.getBarChartPanel().add(barPanel, BorderLayout.CENTER);
        barPanel.validate();
        gui.displayResults();
    }
}

