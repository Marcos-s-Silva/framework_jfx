package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.org.apache.bcel.internal.generic.NEW;

import Classes.Termos;
import Classes.Variavel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SampleController implements Initializable{
	
	
	@FXML
	TextField txtFieldUniversoNome = new TextField(); 
	@FXML
	TextField txtFieldUniversoStart = new TextField();
	@FXML
	TextField txtFieldUniversoEnd = new TextField(); 
	@FXML
	TextField txtFieldTermoNome = new TextField(); 
	@FXML
	TextField txtFieldTermoSuporteInicio = new TextField();
	@FXML
	TextField txtFieldTermoSuporteFim = new TextField(); 
	@FXML
	TextField txtFieldTermoNucleoInicio = new TextField();
	@FXML
	TextField txtFieldTermoNucleoFim = new TextField(); 
	@FXML
	CheckBox chckBoxVariavelObjetiva = new CheckBox();
	@FXML
	TreeView tableVariavels = new TreeView();
	@FXML
	ObservableList<Variavel> variaveisInseridas = FXCollections.observableArrayList();
	@FXML
	Button btnInserirVariavel = new Button();
	@FXML
	Button btnInserirTermo = new Button();
	
	
	Variavel variavelSelecionadaDaTreeView = new Variavel();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void editingTree(){
		//System.out.println(tableVariavels.getSelectionModel().getSelectedIndex());
		TreeItem tSelecionada = (TreeItem) tableVariavels.getSelectionModel().getSelectedItem();
		if (tSelecionada.isLeaf()) {
			System.out.println("FOLHA");
		}else if (tSelecionada == tableVariavels.getRoot()){
			System.out.println("RAIZ");
		}else{
			System.out.println("SUBRAIZ");
		}
		
		//variavelSelecionadaDaTreeView = variaveisInseridas.get(tableVariavels.getSelectionModel().getSelectedIndex()-1);
		//System.out.println(variavelSelecionadaDaTreeView.getNome());
		//System.out.println("DSDSD"+tableVariavels.getSelectionModel().getSelectedIndex());
	}
	
	public void loadTreeItems() {
	    TreeItem<String> root = new TreeItem<String>("Variaveis");
	    root.setExpanded(true);
	    for (Variavel variavel : variaveisInseridas) {
	    	TreeItem var =	new TreeItem<String>(variavel.getNome());
	    	var.getChildren().add(new TreeItem<String>("Começo do Universo: "+variavel.getUniversoStart()));
	    	var.getChildren().add(new TreeItem<String>("Fim do Universo: "+variavel.getUniversoEnd()));
	    	var.getChildren().add(new TreeItem<String>("Objetiva: "+variavel.getObjetiva()));
	    	TreeItem termosFilhos =	new TreeItem<String>("Termos");
	    	var.getChildren().add(termosFilhos);
	    	for (Termos term : variavel.returnTemos()) {
	    		TreeItem termoUnico = new TreeItem<String>(term.getNomeTermo());
	    		termoUnico.getChildren().add(new TreeItem<String>("Inicio nucleo: "+term.getInicioNucleo()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Fim nucleo: "+term.getFimNucleo()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Inicio suporte: "+term.getInicioSuporte()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Fim suporte: "+term.getFimSuporte()));
	    		
	    		
			}
	      root.getChildren().add(var);
	      
	    }

	    tableVariavels.setRoot(root);
	  }
	
	
	@FXML
	public void inserirVariavel(){
		Variavel v = new Variavel();
		v.setNome(txtFieldUniversoNome.getText());
		txtFieldUniversoNome.setText("");
		v.setUniversoStart(Integer.parseInt(txtFieldUniversoStart.getText()));
		txtFieldUniversoStart.setText("");
		v.setUniversoEnd(Integer.parseInt(txtFieldUniversoEnd.getText()));
		txtFieldUniversoEnd.setText("");
		v.setObjetiva(chckBoxVariavelObjetiva.isSelected());
		chckBoxVariavelObjetiva.setSelected(false);
		
		variaveisInseridas.add(v);
		
		this.loadTreeItems();
		
	}
	@FXML
	public void inserirTermo(){
		Termos termoAInserir = new Termos();
		termoAInserir.setNomeTermo(txtFieldTermoNome.getText());
		txtFieldTermoNome.setText("");
		termoAInserir.setInicioNucleo(Integer.parseInt(txtFieldTermoNucleoInicio.getText()));
		txtFieldTermoNucleoInicio.setText("");
		termoAInserir.setFimNucleo(Integer.parseInt(txtFieldTermoNucleoFim.getText()));
		txtFieldTermoNucleoFim.setText("");
		termoAInserir.setInicioSuporte(Integer.parseInt(txtFieldTermoSuporteInicio.getText()));
		txtFieldTermoSuporteInicio.setText("");
		termoAInserir.setFimSuporte(Integer.parseInt(txtFieldTermoSuporteFim.getText()));
		txtFieldTermoSuporteFim.setText("");
		
		variavelSelecionadaDaTreeView.inserirTermo(termoAInserir);
		
		this.loadTreeItems();
	}
	
	
	
	
	
}
