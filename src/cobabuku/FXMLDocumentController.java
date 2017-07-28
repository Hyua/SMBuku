/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobabuku;

import cobabuku.exceptions.NonexistentEntityException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author asus
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField idbuku;
    @FXML
    private TextField namabuku;
    @FXML
    private TextField penerbitbuku;
    @FXML
    private DatePicker tanggalmasukbuku;
    @FXML
    private TableView tablebuku;
    @FXML
    private TableColumn tcid;
    @FXML
    private TableColumn tcnama;
    @FXML
    private TableColumn tcpenerbit;
    @FXML
    private TableColumn tctanggal;

    EntityManagerFactory emf;
    DatabukuJpaController jpa;
    ObservableList<Databuku> listbuku;

    @FXML
    private void handleButtonClearAction(ActionEvent event) {
//        Databuku x = new Databuku(idbuku.getText(), namabuku.getText(),
//                penerbitbuku.getText(), 
//                java.sql.Date.valueOf(tanggalmasukbuku.getValue()));
//
//        try {
//            jpa.create(x);
//            listbuku.add(x);
//            clearForm();
//        } catch (cobabuku.exceptions.PreexistingEntityException ex) {
//            showAlreadyExistMsg();
//        } catch (Exception ex) {
//            showMessage(AlertType.ERROR, "ex = " + ex, ButtonType.OK);
//        }
        clearForm();
    }

    @FXML
    private void handleDeleteBuku() {
        int selectedIndex = tablebuku.getSelectionModel().getSelectedIndex();
        Databuku buku = (Databuku) tablebuku.getItems().get(selectedIndex);
        try {
            jpa.destroy(buku.getId());
            tablebuku.getItems().remove(selectedIndex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        emf = Persistence.createEntityManagerFactory("CobaBukuPU");
        jpa = new DatabukuJpaController(emf);
        
        inputValidation();

        tcid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcnama.setCellValueFactory(new PropertyValueFactory<>("namaBuku"));
        tcpenerbit.setCellValueFactory(new PropertyValueFactory<>("penerbitBuku"));
        tctanggal.setCellValueFactory(new PropertyValueFactory<>("simpleTanggalMasukBuku"));
        
        listbuku = FXCollections.observableArrayList(jpa.findDatabukuEntities());
        tablebuku.setItems(listbuku);

        clearForm();
    }

    public void clearForm() {
        show("", "", "", LocalDate.now());
    }

    public void show(String id, String nama, String pnbt, LocalDate tgl) {
        idbuku.setText(id);
        namabuku.setText(nama);
        penerbitbuku.setText(pnbt);
        tanggalmasukbuku.setValue(tgl);
    }

    public void show(Databuku buku) {
        show(buku.getId(), buku.getNamaBuku(), buku.getPenerbitBuku(),
                new java.sql.Date(buku.getTanggalMasukBuku().getTime()).toLocalDate());
    }

    @FXML
    public void onClick(MouseEvent event) {
        int selectedIndex = tablebuku.getSelectionModel().getSelectedIndex();
        Databuku buku = (Databuku) tablebuku.getItems().get(selectedIndex);
        show(buku);
    }
    
    public void showAlreadyExistMsg()
    {
        showMessage(AlertType.NONE, 
                "Id Sudah Ada. Gunakan Id yang lain", ButtonType.OK);
    }
    
    public void showMessage(AlertType type,String text,ButtonType... buttons)
    {
        Alert alert = new Alert(type,text,buttons);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().getStylesheets()
                .add(getClass().getResource("modena_dark.css").toExternalForm());
//        alert.getDialogPane().getStyleClass().add("modena_dark");
        alert.showAndWait();
    }
    
    @FXML
    private void handleButtonEditAction(ActionEvent event) {
        Databuku x = new Databuku(idbuku.getText(), namabuku.getText(),
                penerbitbuku.getText(), 
                java.sql.Date.valueOf(tanggalmasukbuku.getValue()));

        try {
            jpa.edit(x);
            refreshAll();
            clearForm();
//        } catch (cobabuku.exceptions.PreexistingEntityException ex) {
//            showAlreadyExistMsg();
        } catch (Exception ex) {
            showMessage(AlertType.ERROR, "ex = " + ex, ButtonType.OK);
        }
    }
    
    public void refreshAll()
    {
        listbuku.clear();
        listbuku.addAll(jpa.findDatabukuEntities());
    }

    private void inputValidation() {

        idbuku.textProperty().addListener( (observable, oldValue, newValue) -> {
            if(newValue.length()>10)
                ((StringProperty)observable).setValue(oldValue);
        });
        
        namabuku.textProperty().addListener( (observable, oldValue, newValue) -> {
            if(newValue.length()>100)
                ((StringProperty)observable).setValue(oldValue);
        });
        
        penerbitbuku.textProperty().addListener( (observable, oldValue, newValue) -> {
            if(newValue.length()>50)
                ((StringProperty)observable).setValue(oldValue);
        });
    }
    
}
