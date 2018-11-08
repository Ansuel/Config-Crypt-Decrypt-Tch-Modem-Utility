package decrypt_config;

import javafx.application.Application;
import javafx.stage.Stage;

public class gui_inizializer extends Application {

	static Stage stage;
	
    @Override
    public void start(Stage stage) {
    	gui_inizializer.stage = stage;
    	initUI(stage);
    }
    
    private void initUI(Stage stage) {

    	final String titolo = "Technicolor modem Crypt / Decrypt config utiliy";
    	
        stage.setTitle(titolo);
        stage.setScene(new gui_construct().getScene());
        stage.show();
    }

}
