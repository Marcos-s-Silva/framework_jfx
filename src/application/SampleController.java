package application;

import java.util.ArrayList;

import Classes.Variavel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SampleController {
	
	
	@FXML
	TextField txtFieldUniversoNome = new TextField();
	
	@FXML
	TextField txtFieldUniversoStart= new TextField();
	@FXML
	TextField txtFieldUniversoEnd= new TextField();
	@FXML
	CheckBox chckBoxVariavelObjetiva = new CheckBox();
	@FXML
	TableView<String> tableVariavels = new TableView<>();
	
	ObservableList<String> variaveisInseridas =  FXCollections.observableArrayList();
	
	
	
	
	
	@FXML
	public void inserirVariavel(){
		Variavel v = new Variavel();
		v.setNome(txtFieldUniversoNome.getText());
		txtFieldUniversoNome.setText("");
		v.setUniversoStart(Integer.parseInt(txtFieldUniversoStart.getText()));
		txtFieldUniversoStart.setText("");
		v.setUniversoEnd(Integer.parseInt(txtFieldUniversoEnd.getText()));
		txtFieldUniversoEnd.setText("");
		
		variaveisInseridas.add(v.getNome());
		
		this.atualizaTableDeVariaveis();
	}
	
	
	@FXML
	public void atualizaTableDeVariaveis(){
		
		for (String string : variaveisInseridas) {
			tableVariavels.getItems().add(string);
		}
		System.out.println(tableVariavels.);
	}
	
	
	
	
}
