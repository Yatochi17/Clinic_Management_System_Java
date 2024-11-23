
package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



public class MedicalHistoryController  {

    @FXML
    private TextField tfNationalid;
    @FXML
    private DatePicker tfDate;
    @FXML
    private TextField tfTime;
    @FXML
    private TextField tfWard;
    @FXML
    private TextField tfTreat;
    @FXML
    private TextField tfObser;
    @FXML
    private TextField tfMajor;
    @FXML
    private TextField tfAtten;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<MedHisinfo> tvMedHis;
    @FXML
    private TableColumn<MedHisinfo, Integer> colNationalid;
    @FXML
    private TableColumn<MedHisinfo, Date> colDate;
    @FXML
    private TableColumn<MedHisinfo, String> colTime;
    @FXML
    private TableColumn<MedHisinfo, String> colWard;
    @FXML
    private TableColumn<MedHisinfo, String> colTreat;
    @FXML
    private TableColumn<MedHisinfo, String> colObser;
    @FXML
    private TableColumn<MedHisinfo, String> colMajor;
    @FXML
    private TableColumn<MedHisinfo, String> colAtten;
    @FXML
    private Button btnlogout;
    @FXML
    private Button btnPatient;
    @FXML
    private Button btnTcour;
    @FXML
    private Button btnAnal;
    @FXML
    private Button btnDiag;
    @FXML
    private Button btnProMed;
    @FXML
    private Button btnClear;

    private ObservableList<MedHisinfo> MedHis;
    @FXML
    private TextField tfSearch;
    
    
    
    
    public void initialize() {
        MedHis = FXCollections.observableArrayList();
        loadMedHisFromFile();
        
        colNationalid.setCellValueFactory(new PropertyValueFactory<>("nationalid"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colWard.setCellValueFactory(new PropertyValueFactory<>("ward"));
        colTreat.setCellValueFactory(new PropertyValueFactory<>("tresult"));
        colObser.setCellValueFactory(new PropertyValueFactory<>("observation"));
        colMajor.setCellValueFactory(new PropertyValueFactory<>("major"));
        colAtten.setCellValueFactory(new PropertyValueFactory<>("Attend"));
        
        tvMedHis.setItems(MedHis);
        
        tvMedHis.setOnMouseClicked(event -> {
        MedHisinfo selectedMedHis = tvMedHis.getSelectionModel().getSelectedItem();
        if(selectedMedHis != null){
        tfNationalid.setText(String.valueOf(selectedMedHis.getNationalid()));
        tfDate.setValue(selectedMedHis.getDate());
        tfTime.setText(selectedMedHis.getTime());
        tfWard.setText(selectedMedHis.getWard());
        tfTreat.setText(selectedMedHis.getTresult());
        tfObser.setText(selectedMedHis.getObservation());
        tfMajor.setText(selectedMedHis.getMajor());
        tfAtten.setText(selectedMedHis.getAttend());
     }
        });
        
       
    }    

    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        saveMedHisToFile();
        Main m = new Main();
        m.changeScene("Login.fxml");
    }

    @FXML
    private void viewPatient(ActionEvent event) throws IOException{
        saveMedHisToFile();
        Main m = new Main();
        m.changeScene("Patient.fxml");  
    }

    @FXML
    private void viewTcourse(ActionEvent event) throws IOException{
        saveMedHisToFile();
        Main m = new Main();
        m.changeScene("TCourse.fxml");  
    }

    @FXML
    private void viewAnal(ActionEvent event) throws IOException{
        saveMedHisToFile();
        Main m = new Main();
        m.changeScene("Analysis.fxml");  
    }

    @FXML
    private void viewDiag(ActionEvent event) throws IOException{
        saveMedHisToFile();
        Main m = new Main();
        m.changeScene("Diagnosis.fxml");  
    }

    @FXML
    private void viewProMed(ActionEvent event) throws IOException{
        saveMedHisToFile();
        Main m = new Main();
        m.changeScene("ProMed.fxml");  
    }

    @FXML
    private void Insert(ActionEvent event) {
        //Retrieve the values from tf
        int nationalid;
        LocalDate date =  tfDate.getValue();
        String time = tfTime.getText();
        String ward = tfWard.getText();
        String tresult = tfTreat.getText();
        String observation = tfObser.getText();
        String major = tfMajor.getText();
        String Attend = tfAtten.getText();
        
        if(time.isEmpty() || ward.isEmpty() || tresult.isEmpty() || observation.isEmpty() || major.isEmpty() || Attend.isEmpty() || date == null){
            showWarning("Error!!!","Please enter all the required fields.");
            return;
        }
        
        
        try{
            
            nationalid = Integer.parseInt(tfNationalid.getText());
            
        }
        catch(NumberFormatException e){
            showWarning("Error!!!","Please enter the valid format for ID(Integer).");
            return;
        }
        
        if(!isIdValid(nationalid)){
            showWarning("Error","Invalid National ID. Patient not found.Please enter the national ID that is in the Patient List.");
            return;
        }

        //create new patient and add it
        MedHisinfo medhis = new MedHisinfo ( nationalid, date, time, ward, tresult, observation, major, Attend);
        MedHis.add(medhis);
        
        clearFields();
    }

    @FXML
    private void Update(ActionEvent event) {
        MedHisinfo selectedMedHis = tvMedHis.getSelectionModel().getSelectedItem();
     if(selectedMedHis != null){
        
         int nationalid = Integer.parseInt(tfNationalid.getText());
        LocalDate date =  tfDate.getValue();
        String time = tfTime.getText();
        String ward = tfWard.getText();
        String tresult = tfTreat.getText();
        String observation = tfObser.getText();
        String major = tfMajor.getText();
        String Attend = tfAtten.getText();
        
        selectedMedHis.setNationalid(nationalid);
        selectedMedHis.setDate(date);
        selectedMedHis.setWard(ward);
        selectedMedHis.setTime(time);
        selectedMedHis.setTresult(tresult);
        selectedMedHis.setObservation(observation);
        selectedMedHis.setMajor(major);
        selectedMedHis.setAttend(Attend);
        
        
        tvMedHis.refresh();
    }
    }

    @FXML
    private void Delete(ActionEvent event) {
        MedHisinfo selectedMedHis = tvMedHis.getSelectionModel().getSelectedItem();
        if(selectedMedHis != null){
            MedHis.remove(selectedMedHis);
        }
    }

    
    private void loadMedHisFromFile(){
        try (Scanner scanner = new Scanner(new File("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\MedHis.txt"))){
            
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 8){
                    int nationalid = Integer.parseInt(parts[0]);
                    LocalDate date =  LocalDate.parse(parts[1]);
                    String time = parts[2];
                    String ward = parts[3];
                    String tresult = parts[4];
                    String observation = parts[5];
                    String major = parts[6];
                    String Attend = parts[7];
                MedHisinfo medhis = new MedHisinfo ( nationalid, date, time, ward, tresult, observation, major, Attend);
                MedHis.add(medhis);  
                }
            }
        } 
        catch (FileNotFoundException ex) {
            //TO DO
        }
    }
    
    private void saveMedHisToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\MedHis.txt"))){
            for( MedHisinfo medhis : MedHis){
                writer.println(medhis.getNationalid() + "," + medhis.getDate() + "," + medhis.getTime() + "," + medhis.getWard() + "," + medhis.getTresult() + "," + medhis.getObservation()+ "," + medhis.getMajor()+ "," + medhis.getAttend());
            } 
        }
        
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void clearFields(){
        tfNationalid.clear();
        tfDate.setValue(null);
        tfTime.clear();
        tfWard.clear();
        tfTreat.clear();
        tfObser.clear();
        tfMajor.clear();
        tfAtten.clear();
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
        for(MedHisinfo medhis : MedHis){
            if(String.valueOf(medhis.getNationalid()).equals(searchid)){
                //select matching ID
                tvMedHis.getSelectionModel().select(medhis);
                tvMedHis.scrollTo(medhis);
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
    
    private boolean isIdValid(int nationalid){
        try{
            File file = new File("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\Patient.txt");
            Scanner scanner = new Scanner(file);
            
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                int storednationalid = Integer.parseInt(fields[1]);
                
                if(storednationalid == nationalid){
                    scanner.close();
                    return true;
                }
            }
            
            scanner.close();
            return false;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }
    
}

