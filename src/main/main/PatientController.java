package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class PatientController {

    @FXML
    private Button logout;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfGender;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfCont;
    @FXML
    private Button btnmedicalhistory;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Pttinfo> tvPatient;
    @FXML
    private TableColumn<Pttinfo, String> colName;
    @FXML
    private TableColumn<Pttinfo, Integer> colId;
    @FXML
    private TableColumn<Pttinfo, Integer> colAge;
    @FXML
    private TableColumn<Pttinfo, String> colGender;
    @FXML
    private TableColumn<Pttinfo, String> colAddress;
    @FXML
    private TableColumn<Pttinfo, String> colCont;

    private ObservableList<Pttinfo> Patients;
    @FXML
    private Button btnClear;
    @FXML
    private TextField tfSearch;
    
    public void initialize(){
        Patients = FXCollections.observableArrayList();
        loadPatientsFromFile();
        
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colId.setCellValueFactory(new PropertyValueFactory<>("nationalid"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCont.setCellValueFactory(new PropertyValueFactory<>("contact"));
        
        tvPatient.setItems(Patients);
        
        tvPatient.setOnMouseClicked(event -> {
        Pttinfo selectedPatient = tvPatient.getSelectionModel().getSelectedItem();
        if(selectedPatient != null){
        tfName.setText(selectedPatient.getName());
        tfId.setText(String.valueOf(selectedPatient.getNationalid()));
        tfAge.setText(String.valueOf(selectedPatient.getAge()));
        tfGender.setText(selectedPatient.getGender());
        tfAddress.setText(selectedPatient.getAddress());
        tfCont.setText(selectedPatient.getContact());
     }
        });
    }
    
    
    @FXML
    public void userLogOut(ActionEvent event) throws IOException {
        savePatientsToFile();
        Main m = new Main();
        m.changeScene("Login.fxml");

    }
    
   @FXML
    void viewmedhis(ActionEvent event) throws IOException{
        savePatientsToFile();
        Main m = new Main();
        m.changeScene("MedicalHistory.fxml");
    }

    @FXML
    private void Insert(ActionEvent event) {
        //Retrieve the values from tf
        String name = tfName.getText();
        int nationalid;
        int age;
        String gender = tfGender.getText();
        String address = tfAddress.getText();
        String contact = tfCont.getText();
        
        
        if(name.isEmpty() || gender.isEmpty() || address.isEmpty() || contact.isEmpty()){
            showWarning("Error!!!","Please enter all the required fields.");
            return;
        }
        
        try{
            
            nationalid = Integer.parseInt(tfId.getText());
            age = Integer.parseInt(tfAge.getText());
        }
        catch(NumberFormatException e){
            showWarning("Error!!!","Please enter the valid format for ID and Age(Integer).");
            return;
        }
        
        //create new patient and add it
        Pttinfo patients = new Pttinfo(name, nationalid, age, gender, address, contact);
        Patients.add(patients);
        
        clearFields();
        
        
        
    }

    @FXML
    private void Update(ActionEvent event) {
     Pttinfo selectedPatient = tvPatient.getSelectionModel().getSelectedItem();
     if(selectedPatient != null){
        String name = tfName.getText();
        int nationalid = Integer.parseInt(tfId.getText());
        int age = Integer.parseInt(tfAge.getText());
        String gender = tfGender.getText();
        String address = tfAddress.getText();
        String contact = tfCont.getText();
        
        selectedPatient.setName(name);
        selectedPatient.setNationalid(nationalid);
        selectedPatient.setAge(age);
        selectedPatient.setGender(gender);
        selectedPatient.setAddress(address);
        selectedPatient.setContact(contact);
        
        tvPatient.refresh();
     }
      
    }
    

    @FXML
    private void Delete(ActionEvent event) {
        Pttinfo selectedPatient = tvPatient.getSelectionModel().getSelectedItem();
        if(selectedPatient != null){
            Patients.remove(selectedPatient);
        }
    }
    
    private void loadPatientsFromFile(){
        try (Scanner scanner = new Scanner(new File("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\Patient.txt"))){
            
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 6){
                  String name = parts[0];
                  int nationalid = Integer.parseInt(parts[1]);
                  int age = Integer.parseInt(parts[2]);
                  String gender = parts[3];
                  String address = parts[4];
                  String contact = parts[5];
                  Pttinfo patients = new Pttinfo(name, nationalid, age, gender, address, contact);
                  Patients.add(patients);
                }
            }
        } 
        catch (FileNotFoundException ex) {
            //TO DO
        }
    }
    
    private void savePatientsToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\Patient.txt"))){
            for( Pttinfo patients : Patients){
                writer.println(patients.getName() + "," + patients.getNationalid() + "," + patients.getAge() + "," + patients.getGender() + "," + patients.getAddress() + "," + patients.getContact());
            } 
        }
        
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void clearFields(){
        tfName.clear();
        tfId.clear();
        tfAge.clear();
        tfGender.clear();
        tfAddress.clear();
        tfCont.clear();
        tfSearch.clear();
    }

    @FXML
    private void Clear(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void Search(ActionEvent event) {
        String searchid = tfSearch.getText();
        
        //find matching ID
        for(Pttinfo patients : Patients){
            if(String.valueOf(patients.getNationalid()).equals(searchid)){
                //select matching ID
                tvPatient.getSelectionModel().select(patients);
                tvPatient.scrollTo(patients);
                return;//Exit
                
            }
        }
        
        //If no match, show alert
        showAlert("The ID cannot be found!", "Please enter the correct ID");
    }
    
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showWarning(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
