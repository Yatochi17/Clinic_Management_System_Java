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


public class AnalysisController{

    @FXML
    private TextField tfNationalid;
    @FXML
    private DatePicker tfDate;
    @FXML
    private TextField tfTypetest;
    @FXML
    private TextField tfResult;
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
    private Button btnProMed;
    @FXML
    private TableView<AnalInfo> tvAnal;
    @FXML
    private TableColumn<AnalInfo, Integer> colNationalid;
    @FXML
    private TableColumn<AnalInfo, LocalDate> colDate;
    @FXML
    private TableColumn<AnalInfo, String> colTypeTest;
    @FXML
    private TableColumn<AnalInfo, String> colResult;
    @FXML
    private Button btnClear;

    private ObservableList<AnalInfo> Anal;
    @FXML
    private TextField tfSearch;
    
    public void initialize() {
        Anal = FXCollections.observableArrayList();
        loadAnalFromFile();
        
        colNationalid.setCellValueFactory(new PropertyValueFactory<>("nationalid"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTypeTest.setCellValueFactory(new PropertyValueFactory<>("TypeTest"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        
        
        tvAnal.setItems(Anal);
        
        tvAnal.setOnMouseClicked(event -> {
        AnalInfo selectedAnal = tvAnal.getSelectionModel().getSelectedItem();
        if(selectedAnal != null){
        tfNationalid.setText(String.valueOf(selectedAnal.getNationalid()));
        tfDate.setValue(selectedAnal.getDate());
        tfTypetest.setText(selectedAnal.getTypeTest());
        tfResult.setText(selectedAnal.getResult());
        
     }
        });
    }    

    @FXML
    private void userLogOut(ActionEvent event) throws IOException{
        saveAnalToFile();
        Main m = new Main();
        m.changeScene("Login.fxml");

    }

    @FXML
    private void viewPatient(ActionEvent event) throws IOException{
        saveAnalToFile();
        Main m = new Main();
        m.changeScene("Patient.fxml");

    }

    @FXML
    private void viewTcourse(ActionEvent event) throws IOException{
        saveAnalToFile();
        Main m = new Main();
        m.changeScene("TCourse.fxml");

    }

    @FXML
    private void viewMedHis(ActionEvent event) throws IOException{
        saveAnalToFile();
        Main m = new Main();
        m.changeScene("MedicalHistory.fxml");

    }

    @FXML
    private void viewDiag(ActionEvent event) throws IOException{
        saveAnalToFile();
        Main m = new Main();
        m.changeScene("Diagnosis.fxml");

    }

    @FXML
    private void viewProMed(ActionEvent event) throws IOException{
        saveAnalToFile();
        Main m = new Main();
        m.changeScene("ProMed.fxml");

    }

    @FXML
    private void Insert(ActionEvent event) {
         //Retrieve the values from tf
        int nationalid;
        LocalDate date =  tfDate.getValue();
        String TypeTest = tfTypetest.getText();
        String result = tfResult.getText();
        
        if( TypeTest.isEmpty() || result.isEmpty() || date == null){
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
        AnalInfo anal = new AnalInfo ( nationalid, date, TypeTest, result);
        Anal.add(anal);
        
        clearFields();
    }

    @FXML
    private void Update(ActionEvent event) {
        AnalInfo selectedAnal = tvAnal.getSelectionModel().getSelectedItem();
     if(selectedAnal != null){
        
         int nationalid = Integer.parseInt(tfNationalid.getText());
        LocalDate date =  tfDate.getValue();
        String TypeTest = tfTypetest.getText();
        String result = tfResult.getText();
        
        selectedAnal.setNationalid(nationalid);
        selectedAnal.setDate(date);
        selectedAnal.setTypeTest(TypeTest);
        selectedAnal.setResult(result);
        
        
        
        tvAnal.refresh();
    }
    }

    @FXML
    private void Delete(ActionEvent event) {
        AnalInfo selectedAnal = tvAnal.getSelectionModel().getSelectedItem();
        if(selectedAnal != null){
            Anal.remove(selectedAnal);
        }
    }
    
    private void loadAnalFromFile(){
        try (Scanner scanner = new Scanner(new File("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\Analysis.txt"))){
            
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 4){
                    int nationalid = Integer.parseInt(parts[0]);
                    LocalDate date =  LocalDate.parse(parts[1]);
                    String TypeTest = parts[2];
                    String result = parts[3];
                AnalInfo anal = new AnalInfo ( nationalid, date, TypeTest, result);
                Anal.add(anal);    
                
                }
            }
        } 
        catch (FileNotFoundException ex) {
            //TO DO
        }
    }
    
    private void saveAnalToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\Analysis.txt"))){
            for( AnalInfo anal : Anal){
                writer.println(anal.getNationalid() + "," + anal.getDate() + "," + anal.getTypeTest() + "," + anal.getResult());
            } 
        }
        
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void clearFields(){
        tfNationalid.clear();
        tfDate.setValue(null);
        tfTypetest.clear();
        tfResult.clear();
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
        for(AnalInfo anal : Anal){
            if(String.valueOf(anal.getNationalid()).equals(searchid)){
                //select matching ID
                tvAnal.getSelectionModel().select(anal);
                tvAnal.scrollTo(anal);
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
    

