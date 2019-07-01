package sample;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Controller {
    public javafx.scene.control.TextField allParcels;
    public javafx.scene.control.TextField ASIParcels;
    public javafx.scene.control.TextField unbillable;
    public javafx.scene.control.TextField matchedName;
    public javafx.scene.control.TextField finalName;
    public javafx.scene.control.TextField unmatchedName;
    public javafx.scene.control.Label finished;
    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }


    public static void readFile(String fileName, ArrayList<String> data, int column) {
        String csvFile = fileName;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] lineData = line.split(cvsSplitBy);
                data.add(lineData[column].trim());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void compare(ArrayList<String> parcelID1, ArrayList<String> parcelID2, ArrayList<String> matched, ArrayList<String> unmatched) {
        matched.clear();
        unmatched.clear();
        for (int i = 0; i < parcelID1.size(); i++) {
            if (parcelID2.contains(parcelID1.get(i))) {
                matched.add(parcelID1.get(i));
            } else {
                unmatched.add(parcelID1.get(i));
            }
        }
    }

    public static void saveToFile(String fileName, ArrayList<String> parcel) {
        PrintWriter pw;

        try {
            pw = new PrintWriter(new File(fileName));
            StringBuilder sb = new StringBuilder();
            sb.append("Parcel");
            sb.append('\n');
            for (int i = 0; i < parcel.size(); i++) {
                sb.append(parcel.get(i));
                sb.append('\n');
            }
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void removeParcels(ArrayList<String> toBeRemovedFrom, ArrayList<String> toRemove) {
        for (int i = 0; i < toRemove.size(); i++) {
            toBeRemovedFrom.removeAll(Collections.singleton(toRemove.get(i)));
        }
    }

    public void runComparison(javafx.event.ActionEvent actionEvent) {
        ArrayList<String> compare1 = new ArrayList<String>();
        ArrayList<String> compare2 = new ArrayList<String>();
        ArrayList<String> matched = new ArrayList<String>();
        ArrayList<String> unmatched = new ArrayList<String>();

        String compare1FileName = allParcels.getText();
        String compare2FileName = ASIParcels.getText();
        String matchedFileName = matchedName.getText();
        String unmatchedFileName = unmatchedName.getText();
        String toRemoveFileName = unbillable.getText();
        String newFileName = finalName.getText();

        ArrayList<String> filenames = new ArrayList<String>(Arrays.asList(compare1FileName, compare2FileName, matchedFileName, unmatchedFileName, toRemoveFileName, newFileName));
        if(filenames.contains("")){
            if(compare1FileName.equals("") || !compare1FileName.endsWith(".csv")){
                allParcels.setText("Please choose valid file");
            }
            if(compare2FileName.equals("") || !compare2FileName.endsWith(".csv")){
                ASIParcels.setText("Please choose valid file");
            }
            if(matchedFileName.equals("")){
                matchedName.setText("Please choose valid file");
            }
            if(unmatchedFileName.equals("")){
                unmatchedName.setText("Please choose valid file");
            }
            if(toRemoveFileName.equals("") || !compare1FileName.endsWith(".csv")){
                unbillable.setText("Please choose valid file");
            }
            if(newFileName.equals("")){
                finalName.setText("Please choose valid file");
            }
            return;
        }

        //readFile("C://Users/Intern/Desktop/Parcel Comparison Files/Compare/" + compare1FileName + ".csv", compare1, 0);
        //readFile("C://Users/Intern/Desktop/Parcel Comparison Files/Compare/" + compare2FileName + ".csv", compare2, 0);

        readFile(compare1FileName,compare1,0);
        readFile(compare2FileName,compare2,0);

        compare(compare1, compare2, matched, unmatched);
        //saveToFile("C://Users/Intern/Desktop/Parcel Comparison Files/CompareResults/" + matchedFileName + ".csv", matched);
        //saveToFile("C://Users/Intern/Desktop/Parcel Comparison Files/CompareResults/" + unmatchedFileName + ".csv", unmatched);
        saveToFile(matchedFileName, matched);
        saveToFile(unmatchedFileName, unmatched);


        ArrayList<String> toBeRemovedFrom = new ArrayList<String>();
        ArrayList<String> toRemove = new ArrayList<String>();

        //readFile("C://Users/Intern/Desktop/Parcel Comparison Files/CompareResults/unmatched.csv", toBeRemovedFrom, 0);
        //readFile("C://Users/Intern/Desktop/Parcel Comparison Files/Remove/" + toRemoveFileName + ".csv", toRemove, 0);
        readFile(unmatchedFileName, toBeRemovedFrom, 0);
        readFile(toRemoveFileName, toRemove, 0);

        removeParcels(toBeRemovedFrom, toRemove);
        //saveToFile("C://Users/Intern/Desktop/Parcel Comparison Files/RemoveResults/" + newFileName + ".csv", toBeRemovedFrom);
        if (newFileName.endsWith(".csv")){
            saveToFile(newFileName, toBeRemovedFrom);
        }
        else saveToFile(newFileName + ".csv", toBeRemovedFrom);
        finished.setText("Finished!");
    }

    public void filechoose(Stage primaryStage, javafx.scene.control.TextField textfield){
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(primaryStage);
        textfield.setText(f.toString());
    }

    public void saveFileChoose(Stage primaryStage, javafx.scene.control.TextField textfield){
        FileChooser fc = new FileChooser();
        File f = fc.showSaveDialog(primaryStage);
        textfield.setText(f.toString());
    }

    public void handleAllParcels(){
        filechoose(this.stage, allParcels);
    }

    public void handleASIParcels(){
        filechoose(this.stage, ASIParcels);
    }

    public void handleUnbillable(){
        filechoose(this.stage, unbillable);
    }

    public void handleMatched(){
        saveFileChoose(this.stage, matchedName);
    }

    public void handleUnmatched(){
        saveFileChoose(this.stage, unmatchedName);
    }

    public void handleFinal(){
        saveFileChoose(this.stage, finalName);
    }
}


