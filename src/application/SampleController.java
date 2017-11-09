package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.javafx.geom.Area;
import com.sun.org.apache.bcel.internal.generic.NEW;

import Classes.Termos;
import Classes.Variavel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.stage.Stage;

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
	Button btnEditarVariavel = new Button();
	@FXML
	Button btnExcluirVariavel = new Button();
	@FXML
	Button btnInserirTermo = new Button();
	@FXML
	Button btnEditarTermo = new Button();
	@FXML
	Button btnExcluirTermo = new Button();
	
	Variavel variavelSelecionadaDaTreeView = new Variavel();
	
	Termos termoSelecionadoDaTreeView = new Termos();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void editingTree(){
		//System.out.println(tableVariavels.getSelectionModel().getSelectedIndex());
		
		TreeItem tSelecionada = (TreeItem) tableVariavels.getSelectionModel().getSelectedItem();
		
		if (tSelecionada != null) {
			if (tSelecionada == tableVariavels.getRoot()) {
				variavelSelecionadaDaTreeView = new Variavel();
			}else if (tSelecionada.isLeaf()){
				variavelSelecionadaDaTreeView = new Variavel();
			}else{
				if (tSelecionada.getValue().equals("Termos")) {
					variavelSelecionadaDaTreeView = new Variavel();
				}else if(tSelecionada.getParent().getValue().equals("Termos")){ // UM TERMO 
					variavelSelecionadaDaTreeView = variaveisInseridas.get(this.returnIndexOfSelectedVariable((String) tSelecionada.getParent().getParent().getValue()));
					termoSelecionadoDaTreeView = variavelSelecionadaDaTreeView.retornaTermoUnico(this.returnIndexOfSelectedTerm((String) tSelecionada.getValue()));
				}else{
					variavelSelecionadaDaTreeView = variaveisInseridas.get(this.returnIndexOfSelectedVariable((String) tSelecionada.getValue()));
				}
			}	
		}		
	}
	public int returnIndexOfSelectedVariable(String nomeDaVariavel){
		for (int i = 0; i < variaveisInseridas.size(); i++) {
			if (variaveisInseridas.get(i).getNome().equals(nomeDaVariavel)) {
				return i;
			}
		}
		return 0;
	}
	
	public int returnIndexOfSelectedTerm(String nomeDoTermo){
		return variavelSelecionadaDaTreeView.retornaIndiceDoTermo(nomeDoTermo);
	}
	
	public void loadTreeItems() {
	    TreeItem<String> root = new TreeItem<String>("Variaveis");
	    root.setExpanded(true);
	    for (Variavel variavel : variaveisInseridas) {
	    	TreeItem var =	new TreeItem<String>(variavel.getNome());
	    	var.getChildren().add(new TreeItem<String>("Começo do Universo: "+variavel.getUniversoStart()));
	    	var.getChildren().add(new TreeItem<String>("Fim do Universo: "+variavel.getUniversoEnd()));
	    	var.getChildren().add(new TreeItem<String>("Objetiva: "+variavel.getObjetiva()));
	    	var.getChildren().add(new TreeItem<AreaChart>(this.creatingGraph(variavel)));
	    	TreeItem termosFilhos =	new TreeItem<String>("Termos");
	    	var.getChildren().add(termosFilhos);
	    	for (Termos term : variavel.returnTemos()) {
	    		TreeItem termoUnico = new TreeItem<String>(term.getNomeTermo());
	    		
	    		termoUnico.getChildren().add(new TreeItem<String>("Inicio nucleo: "+term.getInicioNucleo()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Fim nucleo: "+term.getFimNucleo()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Inicio suporte: "+term.getInicioSuporte()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Fim suporte: "+term.getFimSuporte()));
	    		
	    		termosFilhos.getChildren().add(termoUnico);
	    		
			}
	      root.getChildren().add(var);
	      
	    }

	    
	    tableVariavels.setRoot(root);
	  }
	
	
	private AreaChart creatingGraph(Variavel v){
		NumberAxis eixoX =  new NumberAxis(v.getUniversoStart(),v.getUniversoEnd(),1);
		NumberAxis eixoY = new NumberAxis();
		AreaChart<Number, Number> ac = new AreaChart<>(eixoX, eixoY);
		ac.setTitle("TITULO");
		
		XYChart.Series serieDoGrafico = new XYChart.Series<>();
		serieDoGrafico.getData().add(new XYChart.Data(1, 4));
		serieDoGrafico.getData().add(new XYChart.Data(3, 10));
		serieDoGrafico.getData().add(new XYChart.Data(6, 15));
		serieDoGrafico.getData().add(new XYChart.Data(9, 8));
		
	    ac.getData().addAll(serieDoGrafico); 
	    
	    return ac;
	}
	
	@FXML
	public void inserirVariavel(){
		Variavel v = new Variavel();
		if (!txtFieldUniversoNome.getText().isEmpty()) {
			v.setNome(txtFieldUniversoNome.getText());
		}
		txtFieldUniversoNome.setText("");
		if (!txtFieldUniversoStart.getText().isEmpty()) {
			v.setUniversoStart(Integer.parseInt(txtFieldUniversoStart.getText()));
		}
		txtFieldUniversoStart.setText("");
		
		if (!txtFieldUniversoEnd.getText().isEmpty()) {
			v.setUniversoEnd(Integer.parseInt(txtFieldUniversoEnd.getText()));
		}
		
		txtFieldUniversoEnd.setText("");

		v.setObjetiva(chckBoxVariavelObjetiva.isSelected());
		chckBoxVariavelObjetiva.setSelected(false);
		
		variaveisInseridas.add(v);
		
		this.loadTreeItems();
		
	}
	@FXML
	public void inserirTermo(){
		Termos termoAInserir = new Termos();
		if (!txtFieldTermoNome.getText().isEmpty()) {
			termoAInserir.setNomeTermo(txtFieldTermoNome.getText());
		}
		txtFieldTermoNome.setText("");
		if (!txtFieldTermoNucleoInicio.getText().isEmpty()) {
			termoAInserir.setInicioNucleo(Integer.parseInt(txtFieldTermoNucleoInicio.getText()));
		}
		txtFieldTermoNucleoInicio.setText("");
		if (!txtFieldTermoNucleoFim.getText().isEmpty()) {
			termoAInserir.setFimNucleo(Integer.parseInt(txtFieldTermoNucleoFim.getText()));	
		}
		txtFieldTermoNucleoFim.setText("");
		if (!txtFieldTermoSuporteInicio.getText().isEmpty()) {
			termoAInserir.setInicioSuporte(Integer.parseInt(txtFieldTermoSuporteInicio.getText()));	
		}
		txtFieldTermoSuporteInicio.setText("");
		if (!txtFieldTermoSuporteFim.getText().isEmpty()) {
			termoAInserir.setFimSuporte(Integer.parseInt(txtFieldTermoSuporteFim.getText()));	
		}
		txtFieldTermoSuporteFim.setText("");
			
		
		variavelSelecionadaDaTreeView.inserirTermo(termoAInserir);
		
		
		this.loadTreeItems();
	}
	@FXML
	public void editarVariavel(){
		if (!txtFieldUniversoNome.getText().isEmpty()) {
			variavelSelecionadaDaTreeView.setNome(txtFieldUniversoNome.getText());
		}
		txtFieldUniversoNome.setText("");
		if (!txtFieldUniversoStart.getText().isEmpty()) {
			variavelSelecionadaDaTreeView.setUniversoStart(Integer.parseInt(txtFieldUniversoStart.getText()));
		}
		txtFieldUniversoStart.setText("");
		
		if (!txtFieldUniversoEnd.getText().isEmpty()) {
			variavelSelecionadaDaTreeView.setUniversoEnd(Integer.parseInt(txtFieldUniversoEnd.getText()));
		}
		
		txtFieldUniversoEnd.setText("");

		variavelSelecionadaDaTreeView.setObjetiva(chckBoxVariavelObjetiva.isSelected());
		chckBoxVariavelObjetiva.setSelected(false);
		
		
		this.loadTreeItems();
		
	}
	@FXML
	public void editarTermo(){
		if (!txtFieldTermoNome.getText().isEmpty()) {
			termoSelecionadoDaTreeView.setNomeTermo(txtFieldTermoNome.getText());
		}
		txtFieldTermoNome.setText("");
		if (!txtFieldTermoNucleoInicio.getText().isEmpty()) {
			termoSelecionadoDaTreeView.setInicioNucleo(Integer.parseInt(txtFieldTermoNucleoInicio.getText()));
		}
		txtFieldTermoNucleoInicio.setText("");
		if (!txtFieldTermoNucleoFim.getText().isEmpty()) {
			termoSelecionadoDaTreeView.setFimNucleo(Integer.parseInt(txtFieldTermoNucleoFim.getText()));	
		}
		txtFieldTermoNucleoFim.setText("");
		if (!txtFieldTermoSuporteInicio.getText().isEmpty()) {
			termoSelecionadoDaTreeView.setInicioSuporte(Integer.parseInt(txtFieldTermoSuporteInicio.getText()));	
		}
		txtFieldTermoSuporteInicio.setText("");
		if (!txtFieldTermoSuporteFim.getText().isEmpty()) {
			termoSelecionadoDaTreeView.setFimSuporte(Integer.parseInt(txtFieldTermoSuporteFim.getText()));	
		}
		txtFieldTermoSuporteFim.setText("");
	
		
		this.loadTreeItems();
	}
	@FXML
	public void excluirVariavel(){
		int indexToDelete = variaveisInseridas.indexOf(variavelSelecionadaDaTreeView);
		variaveisInseridas.remove(indexToDelete);
		variavelSelecionadaDaTreeView = new Variavel();
		
		this.loadTreeItems();
		
	}
	@FXML
	public void exlcuirTermo(){
		int indexToDelete = variavelSelecionadaDaTreeView.retornaIndiceDoTermo(this.termoSelecionadoDaTreeView.getNomeTermo());
		variavelSelecionadaDaTreeView.removeTermoUnico(indexToDelete);
		termoSelecionadoDaTreeView = new Termos();
		
		this.loadTreeItems();
	}
	
	
	
	
	
}
