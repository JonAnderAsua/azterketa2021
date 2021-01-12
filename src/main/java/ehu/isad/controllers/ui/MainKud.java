package ehu.isad.controllers.ui;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import javafx.scene.control.cell.TextFieldTableCell;

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
    void checkClick(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {
        String url = textFieldId.getText();
        String md5 = url+"/README";
        md5 = utils.readFromUrl(md5);
        String bertsioa = ok.getBertsioa(md5);
        if(bertsioa.equals("")){
            labelId.setText("Ez dago datu basean");
        }
        else{
            labelId.setText("Datu basean dago");
        }
        Orrialde o = new Orrialde(url,md5,bertsioa);
        orrialdeList.add(o);
        kargatu();
    }


    @FXML
    void initialize() {
        urlId.setCellValueFactory(new PropertyValueFactory<>("url"));
        md5Id.setCellValueFactory(new PropertyValueFactory<>("md5"));
        versionId.setCellValueFactory(new PropertyValueFactory<>("version"));

        versionId.setEditable(true);


        versionId.setCellFactory(TextFieldTableCell.forTableColumn());
        versionId.setOnEditCommit(data -> {

            Orrialde o = new Orrialde(tableId.getSelectionModel().getSelectedItem().getUrl(),tableId.getSelectionModel().getSelectedItem().getMd5(),"");
            String s = data.getNewValue();
            o.setVersion(s);
            if(labelId.equals("Ez dago datu basean")){ //Datu basean ez badago
                ok.sartuDatuBasean(o);
            }
        });


        kargatu();

    }

    private void kargatu(){
        obsLista.clear();
        obsLista.addAll(orrialdeList);
        tableId.setItems(obsLista);
    }
}
