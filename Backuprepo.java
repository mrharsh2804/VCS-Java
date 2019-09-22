/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backuprepo;

import java.io.BufferedReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.nio.file.Files;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import static javafx.application.Application.launch;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author Himani
 */
public class Backuprepo extends Application {

    static String path = "";
    static String tarPath = "";
    static String activitypath="";
    static String manipath="";
    static String sdirpath="";
    static String tdirpath="";
    static String fileName = "";
    static String filePath = "D:\\BackupRepo";

    static int i = 0;
    static List<File> breadcrum = new ArrayList<File>();
    static List<String> lines = new ArrayList<String>();
    static List<String> csvlines = new ArrayList<String>();

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        // repo creation
        Button createRepobtn = new Button();
        createRepobtn.setText("Create Repository");
        createRepobtn.setPadding(new Insets(15, 5, 15, 5));
        createRepobtn.setMaxWidth(Double.MAX_VALUE);
        createRepobtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // create repo here
                createRepo(primaryStage);
            }
        });
        // check-in repo
        Button checkInRepobtn = new Button();
        checkInRepobtn.setText("Check-In Repository");
        checkInRepobtn.setPadding(new Insets(15, 5, 15, 5));
        checkInRepobtn.setMaxWidth(Double.MAX_VALUE);
        checkInRepobtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // Check-in repo here
                checkInRepo(primaryStage);
            }
        });
        //check-out repo
        Button checkOutRepobtn = new Button();
        checkOutRepobtn.setText("Check-Out Repository");
        checkOutRepobtn.setPadding(new Insets(15, 5, 15, 5));
        checkOutRepobtn.setMaxWidth(Double.MAX_VALUE);
        checkOutRepobtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // Check-out repo here
                checkOutRepo(primaryStage);
            }
        });
        //Merge
        Button mergebtn = new Button();
        mergebtn.setText("Merge");
        mergebtn.setPadding(new Insets(15, 5, 15, 5));
        mergebtn.setMaxWidth(Double.MAX_VALUE);
        mergebtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // merge here
                mergeRepo(primaryStage);
            }
        });
        root.setSpacing(10);
        root.setPadding(new Insets(20, 20, 10, 20));
        root.getChildren().addAll(createRepobtn, checkInRepobtn, checkOutRepobtn,mergebtn);

        Scene scene = new Scene(root, 400, 280);

        primaryStage.setTitle("Repository");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
 public static void createRepo(Stage CreateRepoStage) {
      
        path = "";
        tarPath = "";
        VBox creatRepoBox = new VBox();
        Scene secondScene = new Scene(creatRepoBox, 500, 400);
        Stage secondStage = new Stage();
        Label manifIDlbl = new Label("MANIFEST ID:  ");
        TextField manifestID = new TextField ();

        Label sourcelbl = new Label("SOURCE:  ");
        Label targetlbl = new Label("TARGET:  ");

        Button selectSource = new Button();
        selectSource.setText("Select Source");
        selectSource.setPadding(new Insets(15, 5, 15, 5));
        selectSource.setMaxWidth(Double.MAX_VALUE);
        selectSource.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of source path here
                sourcelbl.setText("SOURCE:  ");
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Source path");
                File defaultDirectory = new File("D:\\");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(secondStage);
                if (selectedDirectory == null) {
                    System.out.println("No Directory selected");
                } else {
                    sourcelbl.setText(sourcelbl.getText() + selectedDirectory.getAbsolutePath());
                    path = selectedDirectory.getAbsolutePath();
                }
            }
        });
        Button selectTarget = new Button();
        selectTarget.setText("Select Target");
        selectTarget.setPadding(new Insets(15, 5, 15, 5));
        selectTarget.setMaxWidth(Double.MAX_VALUE);
        selectTarget.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of Target path here
                targetlbl.setText("TARGET:  ");
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Target path");
                File defaultDirectory = new File("D:\\");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(secondStage);
                if (selectedDirectory == null) {
                    System.out.println("No Directory selected");
                } else {
                    targetlbl.setText(targetlbl.getText() + selectedDirectory.getAbsolutePath());
                    tarPath = selectedDirectory.getAbsolutePath();
                }
            }
        });

        Button okbtn = new Button();
        okbtn.setText("OK");
        okbtn.setPadding(new Insets(15, 5, 15, 5));
        okbtn.setMaxWidth(Double.MAX_VALUE);
        okbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    // Check-out repo here
                    createRepoMain(path, tarPath,manifestID.getText());
                } catch (IOException ex) {
                    Logger.getLogger(Backuprepo.class.getName()).log(Level.SEVERE, null, ex);
                }
                secondStage.close();
            }
        });
        Button cnclbtn = new Button();
        cnclbtn.setText("CANCEL");
        cnclbtn.setPadding(new Insets(15, 5, 15, 5));
        cnclbtn.setMaxWidth(Double.MAX_VALUE);
        cnclbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                secondStage.close();
            }
        });

        creatRepoBox.setSpacing(10);
        creatRepoBox.setPadding(new Insets(20, 20, 10, 20));
        creatRepoBox.getChildren().addAll(manifIDlbl,manifestID);
        creatRepoBox.getChildren().addAll(sourcelbl, selectSource, targetlbl, selectTarget, okbtn, cnclbtn);
        secondStage.setTitle("Create Repository");
        secondStage.setScene(secondScene);
        CreateRepoStage.toFront();
        secondStage.show();

    }
    public static void mergeRepo(Stage CreateRepoStage) {
        path = "";
        tarPath = "";
        activitypath="";
        sdirpath="";
        tdirpath="";
        VBox creatRepoBox = new VBox();
        Scene secondScene = new Scene(creatRepoBox, 500, 400);
        Stage secondStage = new Stage();
        Label activitylbl = new Label("REPO LOG PATH:  ");
        Label sourcelbl = new Label("SOURCE:  ");
        Label targetlbl = new Label("TARGET:  ");
        
        Button selectActivity = new Button();
        selectActivity.setText("Select repo log path");
        selectActivity.setPadding(new Insets(15, 5, 15, 5));
        selectActivity.setMaxWidth(Double.MAX_VALUE);
        selectActivity.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of source path here
                activitylbl.setText("REPO LOG PATH:  ");
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Activity Path");
                File file = chooser.showOpenDialog(secondStage);
                if(file != null)
                {
                   activitylbl.setText(activitylbl.getText() + file.getAbsolutePath());
                   activitypath=file.getAbsolutePath();
                }
            }
        });
        Button selectSource = new Button();
        selectSource.setText("Select Source");
        selectSource.setPadding(new Insets(15, 5, 15, 5));
        selectSource.setMaxWidth(Double.MAX_VALUE);
        selectSource.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of source path here
                sourcelbl.setText("SOURCE:  ");
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Source");
                File file = chooser.showOpenDialog(secondStage);
                if(file != null)
                {
                   sourcelbl.setText(sourcelbl.getText() + file.getAbsolutePath());
                   path=file.getAbsolutePath();
                   sdirpath=file.getParent();
                }
            }
        });
        Button selectTarget = new Button();
        selectTarget.setText("Select Target");
        selectTarget.setPadding(new Insets(15, 5, 15, 5));
        selectTarget.setMaxWidth(Double.MAX_VALUE);
        selectTarget.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of Target path here
                targetlbl.setText("TARGET:  ");
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Target");
                File file = chooser.showOpenDialog(secondStage);
                if(file != null)
                {
                   targetlbl.setText(targetlbl.getText() + file.getAbsolutePath());
                   tarPath=file.getAbsolutePath();
                   tdirpath=file.getParent();
                }
            }
        });
        Button okbtn = new Button();
        okbtn.setText("OK");
        okbtn.setPadding(new Insets(15, 5, 15, 5));
        okbtn.setMaxWidth(Double.MAX_VALUE);
        okbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // merge here
                    mergeRepoMain(path,tarPath,activitypath,sdirpath,tdirpath);
                }
                catch (IOException ex) {
                    Logger.getLogger(Backuprepo.class.getName()).log(Level.SEVERE, null, ex);
                }
                secondStage.close();
            }
        });
        Button cnclbtn = new Button();
        cnclbtn.setText("CANCEL");
        cnclbtn.setPadding(new Insets(15, 5, 15, 5));
        cnclbtn.setMaxWidth(Double.MAX_VALUE);
        cnclbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                secondStage.close();
            }
        });

        creatRepoBox.setSpacing(10);
        creatRepoBox.setPadding(new Insets(20, 20, 10, 20));
      
        creatRepoBox.getChildren().addAll(activitylbl,selectActivity,sourcelbl, selectSource, targetlbl, selectTarget, okbtn, cnclbtn);
        secondStage.setTitle("Merge");
        secondStage.setScene(secondScene);
        CreateRepoStage.toFront();
        secondStage.show();

    }
    public static void checkOutRepo(Stage CreateRepoStage) {
        path = "";
        tarPath = "";
	manipath="";
        VBox creatRepoBox = new VBox();
        Scene secondScene = new Scene(creatRepoBox, 500, 400);
        Stage secondStage = new Stage();
		
        TextField manifestID = new TextField ();
        Label sourcelbl = new Label("SOURCE:  ");
	Label manifestlbl = new Label("MANIFEST:  ");
        Label targetlbl = new Label("TARGET:  ");

        Button selectSource = new Button();
        selectSource.setText("Select Source Folder");
        selectSource.setPadding(new Insets(15, 5, 15, 5));
        selectSource.setMaxWidth(Double.MAX_VALUE);
        selectSource.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of source path here
                sourcelbl.setText("SOURCE:  ");
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Select Manifest file");
                File defaultDirectory = new File("D:\\");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(secondStage);
                if (selectedDirectory == null) {
                    System.out.println("No Directory selected");
                } else {
                    sourcelbl.setText(sourcelbl.getText() + selectedDirectory.getAbsolutePath());
                    path = selectedDirectory.getAbsolutePath();
                }
            }
        });
         Button selectSource1 = new Button();
        selectSource1.setText("Select Source for Check-Out");
        selectSource1.setPadding(new Insets(15, 5, 15, 5));
        selectSource1.setMaxWidth(Double.MAX_VALUE);
        selectSource1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of source path here
                manifestlbl.setText("MANIFEST:  ");
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Select Manifest file");
                File defaultDirectory = new File("D:\\");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showOpenDialog(secondStage);
                if (selectedDirectory == null) {
                    System.out.println("No Directory selected");
                } else {
                    manifestlbl.setText(sourcelbl.getText() + selectedDirectory.getAbsolutePath());
                    manipath = selectedDirectory.getAbsolutePath();
                }
            }
        });
	
        Button selectTarget = new Button();
        selectTarget.setText("Select Repository");
        selectTarget.setPadding(new Insets(15, 5, 15, 5));
        selectTarget.setMaxWidth(Double.MAX_VALUE);
        selectTarget.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of Target path here
                targetlbl.setText("TARGET:  ");
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Target path");
                File defaultDirectory = new File("D:\\");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(secondStage);
                if (selectedDirectory == null) {
                    System.out.println("No Directory selected");
                } else {
                    targetlbl.setText(targetlbl.getText() + selectedDirectory.getAbsolutePath());
                    tarPath = selectedDirectory.getAbsolutePath();
                }
            }
        });

        Button okbtn = new Button();
        okbtn.setText("OK");
        okbtn.setPadding(new Insets(15, 5, 15, 5));
        okbtn.setMaxWidth(Double.MAX_VALUE);
        okbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
              try {
                    // Check-out repo here
                   checkOutRepoMain(path, tarPath,manifestID.getText());
                } catch (IOException ex) {
                    Logger.getLogger(Backuprepo.class.getName()).log(Level.SEVERE, null, ex);
                }
                secondStage.close();
            }
        });
        Button cnclbtn = new Button();
        cnclbtn.setText("CANCEL");
        cnclbtn.setPadding(new Insets(15, 5, 15, 5));
        cnclbtn.setMaxWidth(Double.MAX_VALUE);
        cnclbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                secondStage.close();
            }
        });

        creatRepoBox.setSpacing(10);
        creatRepoBox.setPadding(new Insets(20, 20, 10, 20));

        creatRepoBox.getChildren().addAll(sourcelbl, selectSource,manifestlbl,selectSource1,  targetlbl, selectTarget,  okbtn, cnclbtn);
        secondStage.setTitle("Check-Out Repository");
        secondStage.setScene(secondScene);
        CreateRepoStage.toFront();
        secondStage.show();

    }
    public static void checkInRepo(Stage CreateRepoStage) {
        path = "";
        tarPath = "";
        VBox creatRepoBox = new VBox();
        Scene secondScene = new Scene(creatRepoBox, 500, 500);
        Stage secondStage = new Stage();
        Label manifIDlbl = new Label("MANIFEST ID:  ");
        TextField manifestID = new TextField ();
        Label userlabel = new Label("USERNAME: ");
        TextField userName = new TextField ();
        Label sourcelbl = new Label("SOURCE:  ");
        Label targetlbl = new Label("TARGET:  ");

        Button selectSource1 = new Button();
        selectSource1.setText("Select Source for Check-In");
        selectSource1.setPadding(new Insets(15, 5, 15, 5));
        selectSource1.setMaxWidth(Double.MAX_VALUE);
        selectSource1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of source path here
                sourcelbl.setText("SOURCE:  ");
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Source path");
                File defaultDirectory = new File("D:\\");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(secondStage);
                if (selectedDirectory == null) {
                    System.out.println("No Directory selected");
                } else {
                    sourcelbl.setText(sourcelbl.getText() + selectedDirectory.getAbsolutePath());
                    path = selectedDirectory.getAbsolutePath();
                }
            }
        });
        Button selectTarget = new Button();
        selectTarget.setText("Select Repository");
        selectTarget.setPadding(new Insets(15, 5, 15, 5));
        selectTarget.setMaxWidth(Double.MAX_VALUE);
        selectTarget.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                // selection of Target path here
                targetlbl.setText("TARGET:  ");
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Target path");
                File defaultDirectory = new File("D:\\");
                chooser.setInitialDirectory(defaultDirectory);
                File selectedDirectory = chooser.showDialog(secondStage);
                if (selectedDirectory == null) {
                    System.out.println("No Directory selected");
                } else {
                    targetlbl.setText(targetlbl.getText() + selectedDirectory.getAbsolutePath());
                    tarPath = selectedDirectory.getAbsolutePath();
                }
            }
        });

        Button okbtn = new Button();
        okbtn.setText("OK");
        okbtn.setPadding(new Insets(15, 5, 15, 5));
        okbtn.setMaxWidth(Double.MAX_VALUE);
        okbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    // Check-In repo here
                    checkInRepoMain(path, tarPath,manifestID.getText(),userName.getText());
                } catch (IOException ex) {
                    Logger.getLogger(Backuprepo.class.getName()).log(Level.SEVERE, null, ex);
                }
                secondStage.close();
            }
        });
        Button cnclbtn = new Button();
        cnclbtn.setText("CANCEL");
        cnclbtn.setPadding(new Insets(15, 5, 15, 5));
        cnclbtn.setMaxWidth(Double.MAX_VALUE);
        cnclbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                secondStage.close();
            }
        });

        creatRepoBox.setSpacing(10);
        creatRepoBox.setPadding(new Insets(20, 20, 10, 20));
        creatRepoBox.getChildren().addAll(userlabel,userName);
        creatRepoBox.getChildren().addAll(manifIDlbl,manifestID);
        creatRepoBox.getChildren().addAll(sourcelbl, selectSource1, targetlbl, selectTarget, okbtn, cnclbtn);
        secondStage.setTitle("Check-In Repository");
        secondStage.setScene(secondScene);
        CreateRepoStage.toFront();
        secondStage.show();

    }

    //get all the files from a directory
    public static void createRepoMain(String path, String tarPath, String maniID) throws IOException {
        Date date = new Date();        
        lines.clear();
        lines.add("HDH\t\t");
        lines.add("ProjectBackup\t\t");
        lines.add(String.valueOf(date) + "\n");
        lines.add("----------------------CREATION OF REPOSITORY---------------------------\n");
        lines.add("Source : " + path);
        lines.add("Target : " + tarPath + "\n");
        lines.add(String.format("%-30s %-30s %-50s %-50s%n", "Atifact ID", "Original file name", "Source Path", "Target Path"));

        listAllDirandSubDir(path);
        writeToManifest(lines, "CreatRepo_"+maniID);
        csvFileGenr(csvlines, "CreatRepo_"+maniID);
    }
    public static void mergeRepoMain(String path, String tarPath, String activityPath,String sdirPath, String tdirPath) throws IOException{
        
        String line = "";
        String csvSplitBy = ",";
        String[] spathdetails= new String[100];
        String[] tpathdetails=new String[100];
        String[] sartdetails=new String[100];
        String[] sfiledetails=new String[100];
        String[] gpathdetails=new String[100];
        String[] gartdetails=new String[100];
        String[] gfiledetails=new String[100];
        String[] tartdetails=new String[100];
        String[] tfiledetails=new String[100];
        int p=0;
        int i=0;
        int k=0;
        int l=0;
        try (BufferedReader br = new BufferedReader(new FileReader(activityPath))) {
            
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] fileDetails = line.split(csvSplitBy);
                gfiledetails[l] = fileDetails[1];
                gartdetails[l]=fileDetails[0];
                gpathdetails[l]=fileDetails[3];
                l++;
             }
        } catch (IOException e) {
             e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] fileDetails = line.split(csvSplitBy);
                sfiledetails[k] = fileDetails[1];
                sartdetails[k]=fileDetails[0];
                spathdetails[k]=fileDetails[3];
                k++;
             }
        } catch (IOException e) {
             e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(tarPath))) {
            int j=0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] fileDetails1 = line.split(csvSplitBy);
                tfiledetails[j] = fileDetails1[1];
                tartdetails[j]=fileDetails1[0];
                tpathdetails[j]=fileDetails1[3];
                j++;
            }
        } catch (IOException e) {
         e.printStackTrace();
        }
        int r=0;
        int s;
        int e=0;
        String st="";
        int flag=0;
        while(sfiledetails[r]!=null)
        {
            st= sfiledetails[r];
            s=0;
            while(tfiledetails[s]!=null)
            {
                if(st.equalsIgnoreCase(tfiledetails[s])){
                    flag=1;
                    mergefilecopy(sfiledetails[r],spathdetails[r],sartdetails[r],tdirpath,flag);
                    mergefilecopy(tfiledetails[s],tpathdetails[s],tartdetails[s],tdirpath,flag);
                    if(gfiledetails[e]!=null && gfiledetails[e].equalsIgnoreCase(sfiledetails[r]))
                    {
                        flag=2;
                        mergefilecopy(gfiledetails[e],gpathdetails[e],gartdetails[e],tdirpath,flag);
                        e++;
                    }
                    else
                    {
                        e++;
                    }
                    s++;
                    break;
                }
                else{
                    s++;
                }
            }
            r++;
        }
        if(flag==0){
            System.out.println("Merge can be done.. No Files are conflicting");
        }
        else{
            System.out.println("Conflicting files are in Activity folder of Target");
        }
    }
    public static void mergefilecopy(String fileNme,String tpath,String artid,String tdirPath,int flagv) throws IOException
    {
        // create directory called merge files decision in 
        String ext;
        String filename;
        File directory = new File(tpath);
        if (directory.exists()) {
            File[] fList = directory.listFiles();
            if (fList != null && fList.length > 0) {
                for (File file : fList) {
                    String fileN= file.getName();
                    if(fileN.equals(artid)){
                        tdirPath=tdirPath+"//"+"Merge Result";
                        createRepo(tdirPath);
                        ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                        filename=fileNme.substring(0,fileNme.lastIndexOf("."));
                        File directory1 = new File(tdirPath);
                        if(directory1.exists()){
                            File f= new File(tdirPath + "//" + filename+"_MR"+"."+ext);
                            File f1= new File(tdirPath + "//" + filename+"_MT"+"."+ext);
                            File f2= new File(tdirPath + "//" + filename+"_MG"+"."+ext);
                            if(!f.exists()){
                                Files.copy(file.toPath(),f.toPath());
                            }
                            else if(!f1.exists())
                            {
                                Files.copy(file.toPath(),new File(tdirPath + "//" + filename+"_MT"+"."+ext).toPath());
                            }
                            else if(flagv==2 && !f2.exists())
                            {
                                Files.copy(file.toPath(),new File(tdirPath + "//" + filename+"_MG"+"."+ext).toPath());
                            }
                        }
                    }
                }
            }
        }
    }
    public static void checkInRepoMain(String path, String tarPath, String maniID,String userN) throws IOException {
        Date date = new Date();
        lines.clear();
        lines.add("HDH\t\t");
        lines.add("ProjectBackup\t\t");
        lines.add(String.valueOf(date) + "\n");
        lines.add("----------------------CHECK-IN OF REPOSITORY---------------------------\n");
        lines.add("Source : " + path);
        lines.add("Target : " + tarPath + "\n");
        lines.add("UserName :"+ userN+"\n");
        lines.add(String.format("%-30s %-30s %-50s %-80s%n", "Atifact ID", "Original file name", "Source Path","Target Path"));

        listAllDirandSubDir(path);
        writeToManifest(lines, "CheckInRepo_"+maniID);
        csvFileGenr(csvlines, "CheckInRepo_"+maniID);
    }
	public static void checkOutRepoMain(String path, String tarPath, String maniID ) throws IOException {
        Date date = new Date();
        lines.clear();
        lines.add("HDH\t\t");
        lines.add("ProjectBackup\t\t");
        lines.add(String.valueOf(date) + "\n");
        lines.add("----------------------CHECK-OUT OF REPOSITORY---------------------------\n");
        lines.add("Source : " + path);
        lines.add("Target : " + tarPath + "\n");
        lines.add(String.format(" %-30s %-50s %-50s%n", "Original file name", "Source Path", "Destination Path"));
	readCSV(manipath);
        writeToManifest(lines, "CheckOutRepo_"+maniID);
        csvFileGenr(csvlines, "CheckOutRepo_"+maniID);
    }
	//reading the manifest file
	public static void readCSV(String mpath) {
        String csvFile = mpath;
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] fileDetails = line.split(cvsSplitBy);
                fileName = fileDetails[1];
                filePath = fileDetails[3];              
                listAllDirandSubDircheckout(filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	//get all files for checkout
	
	private static void listAllDirandSubDircheckout(String dpath) {//throws IOException {
        File directory = new File(dpath);
        if (directory.exists()) {
            File[] fList = directory.listFiles();
            if (fList != null && fList.length > 0) {
                for (File file : fList) {
                    if (!"Activity".equals(file.getName())) {
                        breadcrum.add(i, file);
                        String newTar = null;
                        if (file.getName().lastIndexOf(".") != -1) {  
                            newTar = createTargetForCheckout(tarPath, breadcrum);
                            createRepo(newTar);
                        }
                        if (file.isDirectory() && file.getName().lastIndexOf(".") == -1) { //to detect if the folder or file name has . in it
                            listAllDirandSubDircheckout(dpath + "\\" + breadcrum.get(i++).getName());
                            i--;
                            breadcrum.remove(i);
                        } else if(newTar != null) {
                           createRepo(newTar);
                            try {
                                 if (!new File(newTar + "//" + fileName).exists()) {
                                    Files.copy(file.toPath(), new File(newTar + "//" + fileName).toPath());
                                    lines.add(String.format("%-30s %-50s %-50s%n", fileName, filePath, newTar));
                                }
                                breadcrum.remove(i);
                            } catch (Exception ex) {
                                Logger.getLogger(Backuprepo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    }
                }
            }
        } else {
            System.out.println("Source directory not found.");
        }
        
    }

    //get all the files from a directory
    private static void listAllDirandSubDir(String dpath) throws IOException {

        File directory = new File(dpath);
        if (directory.exists()) {
            File[] fList = directory.listFiles();
            if (fList != null && fList.length > 0) {
                for (File file : fList) {
                    
                    breadcrum.add(i, file);
                    String newTar = createTarget(tarPath, breadcrum);
                    createRepo(newTar);
                    String artiId = "";
                    String ext = "";
                    if (file.isDirectory()) {
                        
                        listAllDirandSubDir(dpath + "\\" + breadcrum.get(i++).getName());
                        i--;
                        breadcrum.remove(i);
                    } else {
                        createRepo(newTar);
                        try {
                            ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                            artiId = targetFileName(file.getPath());
                            if (!new File(newTar + "//" + artiId + ext).exists()) {
                                Files.copy(file.toPath(), new File(newTar + "//" + artiId + ext).toPath());
                            }
                            breadcrum.remove(i);
                        } catch (IOException ex) {
                            Logger.getLogger(Backuprepo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (!file.isDirectory()){
                        lines.add(String.format("%-30s %-30s %-50s %-80s%n", artiId + ext, file.getName(), file.getAbsolutePath(), newTar));
                        csvlines.add(artiId + ext+","+ file.getName()+","+file.getAbsolutePath()+","+newTar);
                    }

                }
            }
        } else {
            System.out.println("Source directory not found.");
        }
    }

    private static void createRepo(String string) {
        File newRepo = new File(string);
        if (!newRepo.exists()) {
            newRepo.mkdir();
        }
    }

    private static String createTarget(String base, List<File> breadcrum) {
        for (File breadcrum1 : breadcrum) {
            base += "\\" + breadcrum1.getName();
        }
         return base;
    }
	//create target for checkout
	
	 private static String createTargetForCheckout(String base, List<File> breadcrum) {
        String str= null;
        str=str+breadcrum.get(0);
        String[] split = str.split("\\\\");
        for (int i = 2; i < split.length - 2; i++) {
            base += "\\" + split[i];
         }       
     return base;
    }
	
    // creation of Artifact id for the target file name with the calculation of check sum of the file content with given weights.

    private static String targetFileName(String newtar) throws IOException {

        FileReader inputStream = null;
        String rename;
        int c;
        int asciiVal = 0;
        int j = 0;
        long checksum = 0;
        int[] wts = new int[]{1, 3, 7, 11, 17};
        int chars = 0;

        inputStream = new FileReader(newtar);

        while ((c = inputStream.read()) != -1) {
            asciiVal = (int) c;
            if (j > 4) {
                j = 0;
            }
            checksum = checksum + (wts[j++] * asciiVal);

            chars++;
        }
        rename = String.valueOf(checksum) + "." + String.valueOf(chars) + ".";
        return rename;
    }
    // creation of Manifest file for the repository

    private static void writeToManifest(List<String> lines, String mfestCMD) throws IOException {
        String logFileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String manifestname = mfestCMD + "_" + logFileName;  
        File newRepo = new File(tarPath + "\\Activity");
        if (!newRepo.exists()) {
            newRepo.mkdir();
        }
        Path file = Paths.get(newRepo.getAbsolutePath() + "\\" + manifestname + ".txt");
        if (new File(newRepo.getAbsolutePath() + "\\" + manifestname + ".txt").exists()) {
            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } else {
            Files.write(file, lines, Charset.forName("UTF-8"));
        }
    }
    private static void csvFileGenr(List<String> lines, String mfestCMD) throws IOException {
        String logFileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String manifestname = mfestCMD + "_" + logFileName;  
        File newRepo = new File(tarPath + "\\Activity");
        if (!newRepo.exists()) {
            newRepo.mkdir();
        }
        Path file = Paths.get(newRepo.getAbsolutePath() + "\\" + manifestname + ".csv");
        if (new File(newRepo.getAbsolutePath() + "\\" + manifestname + ".csv").exists()) {
            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } else {
            Files.write(file, lines, Charset.forName("UTF-8"));
        }
    }
    
}
