
package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
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


public class ProMedController{

    @FXML
    private TextField tfNationalid;
    @FXML
    private DatePicker tfDate;
    @FXML
    private TextField tfTime;
    @FXML
    private TextField tfProType;
    @FXML
    private TextField tfMed;
    @FXML
    private TextField tfAmou;
    @FXML
    private TextField tfFreq;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnlogout;
    @FXML
    private Button btnPatient;
    @FXML
    private Button btnTcour;
    @FXML
    private Button btnMedHis;
    @FXML
    private Button btnDiag;
    @FXML
    private Button btnAnal;
    @FXML
    private TableView<ProMedInfo> tvProMed;
    @FXML
    private TableColumn<ProMedInfo, Integer> colNationalid;
    @FXML
    private TableColumn<ProMedInfo, LocalDate> colDate;
    @FXML
    private TableColumn<ProMedInfo, String> colTime;
    @FXML
    private TableColumn<ProMedInfo, String> colProType;
    @FXML
    private TableColumn<ProMedInfo, String> colMed;
    @FXML
    private TableColumn<ProMedInfo, String> colAmou;
    @FXML
    private TableColumn<ProMedInfo, String> colFreq;
    @FXML
    private Button btnClear;

    private ObservableList<ProMedInfo> ProMed;
    @FXML
    private TextField tfSearch;
    
    public void initialize() {
        ProMed = FXCollections.observableArrayList();
        loadProMedFromFile();
        
        colNationalid.setCellValueFactory(new PropertyValueFactory<>("nationalid"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colProType.setCellValueFactory(new PropertyValueFactory<>("ProType"));
        colMed.setCellValueFactory(new PropertyValueFactory<>("Medic"));
        colAmou.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colFreq.setCellValueFactory(new PropertyValueFactory<>("Frequency"));
        
        
        tvProMed.setItems(ProMed);
        
        tvProMed.setOnMouseClicked(event -> {
        ProMedInfo selectedPM = tvProMed.getSelectionModel().getSelectedItem();
        if(selectedPM != null){
        tfNationalid.setText(String.valueOf(selectedPM.getNationalid()));
        tfDate.setValue(selectedPM.getDate());
        tfTime.setText(selectedPM.getTime());
        tfProType.setText(selectedPM.getProType());
        tfMed.setText(selectedPM.getMedic());
        tfAmou.setText(selectedPM.getAmount());
        tfFreq.setText(selectedPM.getFrequency());
        
     }
        });
    }    

    @FXML
    private void userLogOut(ActionEvent event) throws IOException{
        saveProMedToFile();
        Main m = new Main();
        m.changeScene("Login.fxml");
    }

    @FXML
    private void viewPatient(ActionEvent event) throws IOException{
        saveProMedToFile();
        Main m = new Main();
        m.changeScene("Patient.fxml");
    }

    @FXML
    private void viewTcourse(ActionEvent event) throws IOException{
        saveProMedToFile();
        Main m = new Main();
        m.changeScene("TCourse.fxml");
    }

    @FXML
    private void viewMedHis(ActionEvent event) throws IOException{
        saveProMedToFile();
        Main m = new Main();
        m.changeScene("MedicalHistory.fxml");
    }

    @FXML
    private void viewDiag(ActionEvent event) throws IOException{
        saveProMedToFile();
        Main m = new Main();
        m.changeScene("Diagnosis.fxml");
    }

    @FXML
    private void viewAnal(ActionEvent event) throws IOException{
        saveProMedToFile();
        Main m = new Main();
        m.changeScene("Analysis.fxml");
    }

    @FXML
    private void Insert(ActionEvent event) {
        //Retrieve the values from tf
        int nationalid;
        LocalDate date =  tfDate.getValue();
        String time = tfTime.getText();
        String ProType = tfProType.getText();
        String Medic = tfMed.getText();
        String Amount = tfAmou.getText();
        String Frequency = tfFreq.getText();
        
        if( time.isEmpty() || ProType.isEmpty() || Medic.isEmpty() || Amount.isEmpty() ||Frequency.isEmpty() || date == null){
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
        ProMedInfo promed = new ProMedInfo ( nationalid, date, time, ProType, Medic, Amount, Frequency);
        ProMed.add(promed);
        
        clearFields();
    }

    @FXML
    private void Update(ActionEvent event) {
       ProMedInfo selectedPM = tvProMed.getSelectionModel().getSelectedItem();
     if(selectedPM != null){
        
         int nationalid = Integer.parseInt(tfNationalid.getText());
        LocalDate date =  tfDate.getValue();
        String time = tfTime.getText();
        String ProType = tfProType.getText();
        String Medic = tfMed.getText();
        String Amount = tfAmou.getText();
        String Frequency = tfFreq.getText();
        
        selectedPM.setNationalid(nationalid);
        selectedPM.setDate(date);
        selectedPM.setTime(time);
        selectedPM.setProType(ProType);
        selectedPM.setMedic(Medic);
        selectedPM.setAmount(Amount);
        selectedPM.setFrequency(Frequency);
        
        
        
        tvProMed.refresh();
    }
    }

    @FXML
    private void Delete(ActionEvent event) {
        ProMedInfo selectedPM = tvProMed.getSelectionModel().getSelectedItem();
        if(selectedPM != null){
            ProMed.remove(selectedPM);
        }
    }
    
    private void loadProMedFromFile(){
        try (Scanner scanner = new Scanner(new File("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\ProMed.txt"))){
            
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 7){
                    int nationalid = Integer.parseInt(parts[0]);
                    LocalDate date =  LocalDate.parse(parts[1]);
                    String time = parts[2];
                    String ProType = parts[3];
                    String Medic = parts[4];
                    String Amount = parts[5];
                    String Frequency = parts[6];
                 ProMedInfo promed = new ProMedInfo ( nationalid, date, time, ProType, Medic, Amount, Frequency);
                 ProMed.add(promed);   
                  
                }
            }
        } 
        catch (FileNotFoundException ex) {
            //TO DO
        }
        
        

    }
    
    private void saveProMedToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\ProMed.txt"))){
            for( ProMedInfo promed : ProMed){
                writer.println(promed.getNationalid() + "," + promed.getDate() + "," + promed.getTime() + "," + promed.getProType() + "," + promed.getMedic() + "," + promed.getAmount() + "," + promed.getFrequency());
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
        tfProType.clear();
        tfMed.clear();
        tfAmou.clear();
        tfFreq.clear();
        
    }

    @FXML
    private void Clear(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void Search(ActionEvent event) {
        String searchid = tfSearch.getText();
        
        //find matching ID
        for(ProMedInfo promed : ProMed){
            if(String.valueOf(promed.getNationalid()).equals(searchid)){
                //select matching ID
                tvProMed.getSelectionModel().select(promed);
                tvProMed.scrollTo(promed);
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
