
import java.io.FileNotFoundException;


public class MainApp {


    public static void main(String[] args) throws FileNotFoundException {
        FinderModel model = new FinderModel();
        MainGUI gui = new MainGUI();
        MainController controller = new MainController(model, gui); 
    }
}
