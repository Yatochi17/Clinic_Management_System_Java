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


public class DiagnosisController{

    @FXML
    private TextField tfNationalid;
    @FXML
    private TextField tfTDiagName;
    @FXML
    private DatePicker tfDate;
    @FXML
    private TextField tfDiagSick;
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
    private Button btnMedHis;
    @FXML
    private Button btnAnal;
    @FXML
    private Button btnTCourse;
    @FXML
    private Button btnProMed;
    @FXML
    private TableView<DiagInfo> tvDiag;
    @FXML
    private TableColumn<DiagInfo, Integer> colNationalid;
    @FXML
    private TableColumn<DiagInfo, String> colDiagName;
    @FXML
    private TableColumn<DiagInfo, LocalDate> colDate;
    @FXML
    private TableColumn<DiagInfo, String> colDiagSick;
    @FXML
    private Button btnClear;

    private ObservableList<DiagInfo> Diag;
    @FXML
    private TextField tfSearch;
   
    public void initialize() {
        Diag = FXCollections.observableArrayList();
        loadDiagFromFile();
        
        colNationalid.setCellValueFactory(new PropertyValueFactory<>("nationalid"));
        colDiagName.setCellValueFactory(new PropertyValueFactory<>("DiagName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDiagSick.setCellValueFactory(new PropertyValueFactory<>("DiagSick"));
        
        
        tvDiag.setItems(Diag);
        
        tvDiag.setOnMouseClicked(event -> {
        DiagInfo selectedDiag = tvDiag.getSelectionModel().getSelectedItem();
        if(selectedDiag != null){
        tfNationalid.setText(String.valueOf(selectedDiag.getNationalid()));
        tfDate.setValue(selectedDiag.getDate());
        tfTDiagName.setText(selectedDiag.getDiagName());
        tfDiagSick.setText(selectedDiag.getDiagSick());
    
     }
        });
    }    

    @FXML
    private void userLogOut(ActionEvent event) throws IOException{
        saveDiagToFile();
        Main m = new Main();
        m.changeScene("Login.fxml");
    }

    @FXML
    private void viewPatient(ActionEvent event) throws IOException{
        saveDiagToFile();
        Main m = new Main();
        m.changeScene("Patient.fxml");
    }

    @FXML
    private void viewMedHis(ActionEvent event) throws IOException{
        saveDiagToFile();
        Main m = new Main();
        m.changeScene("MedicalHistory.fxml");
    }

    @FXML
    private void viewAnal(ActionEvent event) throws IOException{
        saveDiagToFile();
        Main m = new Main();
        m.changeScene("Analysis.fxml");
    }

    @FXML
    private void viewTCourse(ActionEvent event) throws IOException{
        saveDiagToFile();
        Main m = new Main();
        m.changeScene("TCourse.fxml");
    }

    @FXML
    private void viewProMed(ActionEvent event) throws IOException{
        saveDiagToFile();
        Main m = new Main();
        m.changeScene("ProMed.fxml");
    }

    @FXML
    private void Insert(ActionEvent event) {
        //Retrieve the values from tf
        int nationalid;
        LocalDate date =  tfDate.getValue();
        String DiagName = tfTDiagName.getText();
        String DiagSick = tfDiagSick.getText();
        
        if( DiagName.isEmpty() || DiagSick.isEmpty() || date == null){
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
        DiagInfo diag = new DiagInfo ( nationalid, DiagName, date, DiagSick);
        Diag.add(diag);
        
        clearFields();
    }

    @FXML
    private void Update(ActionEvent event) {
        DiagInfo selectedDiag = tvDiag.getSelectionModel().getSelectedItem();
     if(selectedDiag != null){
        
         int nationalid = Integer.parseInt(tfNationalid.getText());
        LocalDate date =  tfDate.getValue();
        String DiagName = tfTDiagName.getText();
        String DiagSick = tfDiagSick.getText();
        
        
        selectedDiag.setNationalid(nationalid);
        selectedDiag.setDate(date);
        selectedDiag.setDiagName(DiagName);
        selectedDiag.setDiagSick(DiagSick);
        
        
        
        tvDiag.refresh();
    }
    }

    @FXML
    private void Delete(ActionEvent event) {
        DiagInfo selectedDiag = tvDiag.getSelectionModel().getSelectedItem();
        if(selectedDiag != null){
            Diag.remove(selectedDiag);
        }
    }
    
    private void loadDiagFromFile(){
        try (Scanner scanner = new Scanner(new File("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\Diagnosis.txt"))){
            
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 4){
                    int nationalid = Integer.parseInt(parts[0]);
                    String DiagName = parts[1];
                    LocalDate date =  LocalDate.parse(parts[2]);
                    String DiagSick = parts[3];
                    
                DiagInfo diag = new DiagInfo ( nationalid, DiagName, date, DiagSick);
                Diag.add(diag);
                }
            }
        } 
        catch (FileNotFoundException ex) {
            //TO DO
        }
    }
    
    private void saveDiagToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\Diagnosis.txt"))){
            for( DiagInfo diag : Diag){
                writer.println(diag.getNationalid() + "," + diag.getDiagName() + "," + diag.getDate() + "," + diag.getDiagSick());
            } 
        }
        
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void clearFields(){
        tfNationalid.clear();
        tfDate.setValue(null);
        tfTDiagName.clear();
        tfDiagSick.clear();
    }

    @FXML
    private void Clear(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void Search(ActionEvent event) {
        String searchid = tfSearch.getText();
        
        //find matching ID
        for(DiagInfo diag : Diag){
            if(String.valueOf(diag.getNationalid()).equals(searchid)){
                //select matching ID
                tvDiag.getSelectionModel().select(diag);
                tvDiag.scrollTo(diag);
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
