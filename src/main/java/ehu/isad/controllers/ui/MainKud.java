package ehu.isad.controllers.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ehu.isad.controllers.db.OrriKud;
import ehu.isad.model.Orrialde;
import ehu.isad.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainKud {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldId;

    @FXML
    private Button checkId;

    @FXML
    private TableView<Orrialde> tableId;

    @FXML
    private TableColumn<Orrialde, String> urlId;

    @FXML
    private TableColumn<Orrialde, String> md5Id;

    @FXML
    private TableColumn<Orrialde, String> versionId;

    @FXML
    private Label labelId;

    private List<Orrialde> orrialdeList = new ArrayList<>();
    private ObservableList<Orrialde> obsLista = FXCollections.observableArrayList();
    private Utils utils = new Utils();
    OrriKud ok = OrriKud.getInstance();

    @FXML
    void checkClick(ActionEvent event) throws IOException, SQLException {
        String url = textFieldId.getText();
        String kodetuta = url+"/README";
        kodetuta = utils.readFromUrl(url);
        String bertsioa = ok.getBertsioa(kodetuta);
        if(bertsioa.equals("")){
            labelId.setText("Ez dago datu basean");
        }
        else{
            labelId.setText("Datu basean dago");
        }
        Orrialde o = new Orrialde(url,kodetuta,bertsioa);
        orrialdeList.add(o);
        kargatu();
    }


    @FXML
    void initialize() {
        versionId.setEditable(true);
        urlId.setCellValueFactory(new PropertyValueFactory<>("url"));
        md5Id.setCellValueFactory(new PropertyValueFactory<>("md5"));
        versionId.setCellValueFactory(new PropertyValueFactory<>("version"));


        kargatu();

    }

    private void kargatu(){
        obsLista.addAll(orrialdeList);
        tableId.setItems(obsLista);
    }
}
