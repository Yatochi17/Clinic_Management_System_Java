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


public class TCourseController{

    @FXML
    private TextField tfNationalid;
    @FXML
    private TextField tfTname;
    @FXML
    private DatePicker tfSdate;
    @FXML
    private DatePicker tfEdate;
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
    private Button btnDiag;
    @FXML
    private Button btnProMed;
    @FXML
    private TableView<TCourseInfo> tvTCourse;
    @FXML
    private TableColumn<TCourseInfo, Integer> colNationalid;
    @FXML
    private TableColumn<TCourseInfo, String> colTname;
    @FXML
    private TableColumn<TCourseInfo, LocalDate> colSdate;
    @FXML
    private TableColumn<TCourseInfo, LocalDate> colEdate;
    @FXML
    private Button btnClear;

    private ObservableList<TCourseInfo> TCourse;
    @FXML
    private TextField tfSearch;
    
    public void initialize() {
        TCourse = FXCollections.observableArrayList();
        loadTCourseFromFile();
        
        colNationalid.setCellValueFactory(new PropertyValueFactory<>("nationalid"));
        colTname.setCellValueFactory(new PropertyValueFactory<>("Tname"));
        colSdate.setCellValueFactory(new PropertyValueFactory<>("Sdate"));
        colEdate.setCellValueFactory(new PropertyValueFactory<>("Edate"));
        
        
        tvTCourse.setItems(TCourse);
        
        tvTCourse.setOnMouseClicked(event -> {
        TCourseInfo selectedTC = tvTCourse.getSelectionModel().getSelectedItem();
        if(selectedTC != null){
        tfNationalid.setText(String.valueOf(selectedTC.getNationalid()));
        tfTname.setText(selectedTC.getTname());
        tfSdate.setValue(selectedTC.getSdate());
        tfEdate.setValue(selectedTC.getEdate());
     }
        });
    }    

    @FXML
    private void userLogOut(ActionEvent event) throws IOException {
        saveTCourseToFile();
        Main m = new Main();
        m.changeScene("Login.fxml");
    }

    @FXML
    private void viewPatient(ActionEvent event) throws IOException {
        saveTCourseToFile();
        Main m = new Main();
        m.changeScene("Patient.fxml");
    }

    @FXML
    private void viewMedHis(ActionEvent event) throws IOException{
        saveTCourseToFile();
        Main m = new Main();
        m.changeScene("MedicalHistory.fxml");
    }

    @FXML
    private void viewAnal(ActionEvent event) throws IOException{
        saveTCourseToFile();
        Main m = new Main();
        m.changeScene("Analysis.fxml");
    }

    @FXML
    private void viewDiag(ActionEvent event) throws IOException{
        saveTCourseToFile();
        Main m = new Main();
        m.changeScene("Diagnosis.fxml");
    }

    @FXML
    private void viewProMed(ActionEvent event) throws IOException{
        saveTCourseToFile();
        Main m = new Main();
        m.changeScene("ProMed.fxml");
    }

    @FXML
    private void Insert(ActionEvent event) {
        //Retrieve the values from tf
        int nationalid;
        String Tname = tfTname.getText();
        LocalDate Sdate =  tfSdate.getValue();
        LocalDate Edate =  tfEdate.getValue();
        
        if(Tname.isEmpty() || Sdate == null || Edate == null){
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
        TCourseInfo tcourse = new TCourseInfo ( nationalid, Tname, Sdate, Edate);
        TCourse.add(tcourse);
        
        clearFields();
    }

    @FXML
    private void Update(ActionEvent event) {
        TCourseInfo selectedTC = tvTCourse.getSelectionModel().getSelectedItem();
     if(selectedTC != null){
        
        int nationalid = Integer.parseInt(tfNationalid.getText());
        String Tname = tfTname.getText();
        LocalDate Sdate =  tfSdate.getValue();
        LocalDate Edate =  tfEdate.getValue();
        
        selectedTC.setNationalid(nationalid);
        selectedTC.setTname(Tname);
        selectedTC.setSdate(Sdate);
        selectedTC.setEdate(Edate);
        
        
        tvTCourse.refresh();
    }
    }

    @FXML
    private void Delete(ActionEvent event) {
        TCourseInfo selectedTC = tvTCourse.getSelectionModel().getSelectedItem();
        if(selectedTC != null){
            TCourse.remove(selectedTC);
        }
    }
    
    private void loadTCourseFromFile(){
        try (Scanner scanner = new Scanner(new File("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\TCourse.txt"))){
            
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 4){
                    int nationalid = Integer.parseInt(parts[0]);
                    String Tname = parts[1];
                    LocalDate Sdate =  LocalDate.parse(parts[2]);
                    LocalDate Edate =  LocalDate.parse(parts[3]);
                TCourseInfo tcourse = new TCourseInfo ( nationalid, Tname, Sdate, Edate);
                TCourse.add(tcourse);  
                }
            }
        } 
        catch (FileNotFoundException ex) {
            //TO DO
        }
    }
    
    private void saveTCourseToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\user\\Documents\\Taylor's University\\Advanced Programming\\Assignment\\Main\\src\\main\\Txt\\TCourse.txt"))){
            for( TCourseInfo tcourse : TCourse){
                writer.println(tcourse.getNationalid() + "," + tcourse.getTname() + "," + tcourse.getSdate() + "," + tcourse.getEdate());
            } 
        }
        
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void clearFields(){
        tfNationalid.clear();
        tfTname.clear();
        tfSdate.setValue(null);
        tfEdate.setValue(null);
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
        for(TCourseInfo tcourse : TCourse){
            if(String.valueOf(tcourse.getNationalid()).equals(searchid)){
                //select matching ID
                tvTCourse.getSelectionModel().select(tcourse);
                tvTCourse.scrollTo(tcourse);
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
