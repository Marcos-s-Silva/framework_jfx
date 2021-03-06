package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import com.sun.javafx.geom.Area;
import com.sun.javafx.scene.control.TableColumnSortTypeWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;

import Classes.Brain;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class SampleController implements Initializable{
	
	
	@FXML
	TextField txtFieldUniversoNome = new TextField(); 
	@FXML
	TextField txtFieldUniversoValue = new TextField();
	@FXML
	TextField txtFieldTermoNome = new TextField(); 
	@FXML
	TextField txtFieldDeFuzzy = new TextField(); 
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
	@FXML
	Button btnPadrao = new Button();
	@FXML
	Button btnSetaUniverso = new Button();
	@FXML
	Button btnAplicarRegras = new Button();
	@FXML
	TextArea areaRegras = new TextArea();
	@FXML
	TextField txtFieldVariavelSelecionada = new TextField();
	@FXML
	TextField txtFieldTermoSelecionado = new TextField();
	Brain b = new Brain();
	
	int universoStart = 0;
	int universoEnd = 0;
	int lastTickUnite = 0;
	
	ArrayList<XYChart.Series> arrayDeDadosObjetiva = new ArrayList<>();
	
	Variavel variavelSelecionadaDaTreeView = new Variavel();
	
	Termos termoSelecionadoDaTreeView = new Termos();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@FXML
	public void desabilitarValorVariavel(){
		if (chckBoxVariavelObjetiva.isSelected()) {
			this.txtFieldUniversoValue.setDisable(true);
		}else{
			this.txtFieldUniversoValue.setDisable(false);
		}
	}
	
	public ObservableList atualizandoTabelaDeValores(){
		ObservableList<Variavel> arrayRetorna = FXCollections.observableArrayList();
		for (Variavel variavel : variaveisInseridas) {
			if (variavel.getObjetiva() == false) {
				arrayRetorna.add(variavel);
			}
		}
		return arrayRetorna;
	}
	
	

	
	public void editingTree(){
		
		TreeItem tSelecionada = (TreeItem) tableVariavels.getSelectionModel().getSelectedItem();
		
		if (tSelecionada != null) {
			if (tSelecionada == tableVariavels.getRoot()) {
				variavelSelecionadaDaTreeView = new Variavel();
			}else if (tSelecionada.isLeaf()){
				variavelSelecionadaDaTreeView = new Variavel();
			}else{
				if (tSelecionada.getValue().equals("Termos")) {
					variavelSelecionadaDaTreeView = variaveisInseridas.get(this.returnIndexOfSelectedVariable((String) tSelecionada.getParent().getValue()));
				}else if(tSelecionada.getParent().getValue().equals("Termos")){ // UM TERMO 
					variavelSelecionadaDaTreeView = variaveisInseridas.get(this.returnIndexOfSelectedVariable((String) tSelecionada.getParent().getParent().getValue()));
					termoSelecionadoDaTreeView = variavelSelecionadaDaTreeView.retornaTermoUnico(this.returnIndexOfSelectedTerm((String) tSelecionada.getValue()));
					
				}else if(tSelecionada.getValue().equals("Gr�fico")){
					variavelSelecionadaDaTreeView = variaveisInseridas.get(this.returnIndexOfSelectedVariable((String) tSelecionada.getParent().getValue()));
				}else if(tSelecionada.getParent().getValue().equals("Gr�fico")){
					variavelSelecionadaDaTreeView = variaveisInseridas.get(this.returnIndexOfSelectedVariable((String) tSelecionada.getParent().getParent().getValue()));
					termoSelecionadoDaTreeView = variavelSelecionadaDaTreeView.retornaTermoUnico(this.returnIndexOfSelectedTerm((String) tSelecionada.getValue()));
				
				}else{
					variavelSelecionadaDaTreeView = variaveisInseridas.get(this.returnIndexOfSelectedVariable((String) tSelecionada.getValue()));
				}
			}	
		}
		
		if (variavelSelecionadaDaTreeView.getNome() != null && !variavelSelecionadaDaTreeView.getNome().equals("")) {
			txtFieldVariavelSelecionada.setText(variavelSelecionadaDaTreeView.getNome());
		}
		if (termoSelecionadoDaTreeView.getNomeTermo() != null && !termoSelecionadoDaTreeView.getNomeTermo().equals("")) {
			txtFieldTermoSelecionado.setText(termoSelecionadoDaTreeView.getNomeTermo());
		}
		
	}
	
	public int returnIndexOfSelectedVariable(String nomeDaVariavel){
		for (int i = 0; i < variaveisInseridas.size(); i++) {
			if (variaveisInseridas.get(i).getNome().equalsIgnoreCase(nomeDaVariavel)) {
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
	    	var.getChildren().add(new TreeItem<String>("Valor: "+variavel.getValor()));
	    	var.getChildren().add(new TreeItem<String>("Objetiva: "+variavel.getObjetiva()));
	    	TreeItem termosFilhos =	new TreeItem<String>("Termos");
	    	for (Termos term : variavel.returnTemos()) {
	    		TreeItem termoUnico = new TreeItem<String>(term.getNomeTermo());
	    		termoUnico.getChildren().add(new TreeItem<String>("Grau de Pertin�ncia: "+term.getGrauDePertinencia()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Inicio nucleo: "+term.getInicioNucleo()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Fim nucleo: "+term.getFimNucleo()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Inicio suporte: "+term.getInicioSuporte()));
	    		termoUnico.getChildren().add(new TreeItem<String>("Fim suporte: "+term.getFimSuporte()));
	    		
	    		TreeItem graph = new TreeItem<String>("Gr�fico");
	    		graph.getChildren().add(new TreeItem<AreaChart>(this.creatingGraphTermo(term)));
	    		
	    		termoUnico.getChildren().add(graph);
	    		termosFilhos.getChildren().add(termoUnico);
	    		
	    		
			}
	    	var.getChildren().add(termosFilhos);
	    	TreeItem graph = new TreeItem<String>("Gr�fico");
    	
	    	
	    	if (variavel.getObjetiva()) {
	    		graph.getChildren().add(new TreeItem<AreaChart>(this.creatingGraphTermosPertinencia(variavel)));
				var.getChildren().add(graph);
			}else{
				graph.getChildren().add(new TreeItem<AreaChart>(this.creatingGraphTermos(variavel)));
				var.getChildren().add(graph);
			}
			
			
	    	
	    	
	      root.getChildren().add(var);
	      
	    }

	    
	    tableVariavels.setRoot(root);
	  }
	
	private AreaChart creatingGraphTermo(Termos t){
		NumberAxis eixoX =  new NumberAxis(t.getInicioSuporte(), t.getFimSuporte(), (Math.abs(t.getInicioSuporte())+Math.abs(t.getFimSuporte()))/10);
		NumberAxis eixoY = new NumberAxis();
		AreaChart<Number, Number> ac = new AreaChart<>(eixoX, eixoY);
		
		XYChart.Series serieDoGrafico = new XYChart.Series<>();
		serieDoGrafico.setName(t.getNomeTermo());
		
		if (t.getInicioSuporte() == t.getInicioNucleo()) {
			serieDoGrafico.getData().add(new XYChart.Data(t.getInicioNucleo(), 1));
			serieDoGrafico.getData().add(new XYChart.Data(t.getFimNucleo(), 1));
			serieDoGrafico.getData().add(new XYChart.Data(t.getFimSuporte(), 0));
		}else if (t.getFimSuporte() == t.getFimNucleo()) {
			serieDoGrafico.getData().add(new XYChart.Data(t.getInicioSuporte(), 0));
			serieDoGrafico.getData().add(new XYChart.Data(t.getInicioNucleo(), 1));
			serieDoGrafico.getData().add(new XYChart.Data(t.getFimNucleo(), 1));
			
		}else{
			serieDoGrafico.getData().add(new XYChart.Data(t.getInicioNucleo(), 1));
			serieDoGrafico.getData().add(new XYChart.Data(t.getFimNucleo(), 1));
			serieDoGrafico.getData().add(new XYChart.Data(t.getInicioSuporte(), 0));
			serieDoGrafico.getData().add(new XYChart.Data(t.getFimSuporte(), 0));
		}
		
	    ac.getData().addAll(serieDoGrafico);
	    
	    
	    return ac;
	}
	
	private AreaChart creatingGraphTermos(Variavel v){
		NumberAxis eixoX =  new NumberAxis(v.getUniversoStart(), v.getUniversoEnd(), (Math.abs(v.getUniversoStart())+Math.abs(v.getUniversoEnd()))/10);
		NumberAxis eixoY = new NumberAxis();
		AreaChart<Number, Number> ac = new AreaChart<>(eixoX, eixoY);
		ArrayList<XYChart.Series> arrayDeDados = new ArrayList<>();

		
		for (Termos t : v.returnTemos()) {
			XYChart.Series serieDoGrafico = new XYChart.Series<>();
			serieDoGrafico.setName(t.getNomeTermo());
			if (t.getInicioSuporte() == t.getInicioNucleo()) {
				serieDoGrafico.getData().add(new XYChart.Data(t.getInicioNucleo(), 1));
				serieDoGrafico.getData().add(new XYChart.Data(t.getFimNucleo(), 1));
				serieDoGrafico.getData().add(new XYChart.Data(t.getFimSuporte(), 0));
			}else if (t.getFimSuporte() == t.getFimNucleo()) {
				serieDoGrafico.getData().add(new XYChart.Data(t.getInicioSuporte(), 0));
				serieDoGrafico.getData().add(new XYChart.Data(t.getInicioNucleo(), 1));
				serieDoGrafico.getData().add(new XYChart.Data(t.getFimNucleo(), 1));
			}else{
				serieDoGrafico.getData().add(new XYChart.Data(t.getInicioNucleo(), 1));
				serieDoGrafico.getData().add(new XYChart.Data(t.getFimNucleo(), 1));
				serieDoGrafico.getData().add(new XYChart.Data(t.getInicioSuporte(), 0));
				serieDoGrafico.getData().add(new XYChart.Data(t.getFimSuporte(), 0));
			}
			arrayDeDados.add(serieDoGrafico);
		}
		
		
		for (XYChart.Series series : arrayDeDados) {
			ac.getData().addAll(series);
		}
	    
	    return ac;
	}
	
	private AreaChart creatingGraphTermosPertinencia(Variavel v){
		NumberAxis eixoX =  new NumberAxis(v.getUniversoStart(), v.getUniversoEnd(), (Math.abs(v.getUniversoStart())+Math.abs(v.getUniversoEnd()))/10);
		NumberAxis eixoY = new NumberAxis();
		AreaChart<Number, Number> ac = new AreaChart<>(eixoX, eixoY);
	
		
		
		for (XYChart.Series series : this.arrayDeDadosObjetiva) {
			ac.getData().addAll(series);
		}
	    
	    
	    
	    return ac;
	}
	
	
	
	@FXML
	public void inserirVariavel(){
		Variavel v = new Variavel();
		if (!txtFieldUniversoNome.getText().isEmpty()) {
			v.setNome(txtFieldUniversoNome.getText());
		}
		txtFieldUniversoNome.setText("");

		v.setObjetiva(chckBoxVariavelObjetiva.isSelected());
		chckBoxVariavelObjetiva.setSelected(false);
		
		if (!txtFieldUniversoValue.getText().isEmpty()) {
			v.setValor(Integer.parseInt(txtFieldUniversoValue.getText()));
		}
		
		txtFieldUniversoValue.setText("");
		
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
		
		b.calcula(variavelSelecionadaDaTreeView);
		this.loadTreeItems();
	}
	@FXML
	public void editarVariavel(){
		if (!txtFieldUniversoNome.getText().isEmpty()) {
			variavelSelecionadaDaTreeView.setNome(txtFieldUniversoNome.getText());
		}
		txtFieldUniversoNome.setText("");
		
		variavelSelecionadaDaTreeView.setObjetiva(chckBoxVariavelObjetiva.isSelected());
		chckBoxVariavelObjetiva.setSelected(false);
		
		if (!txtFieldUniversoValue.getText().isEmpty()) {
			variavelSelecionadaDaTreeView.setValor(Integer.parseInt(txtFieldUniversoValue.getText()));
		}
		
		txtFieldUniversoValue.setText("");
		
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
	
		b.calcula(variavelSelecionadaDaTreeView);
		variavelSelecionadaDaTreeView.defineUniversos();
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
	
	
	
	
	
	@FXML
	public void preenchePadrao(){
		
		variaveisInseridas.clear();
		
		Variavel v = new Variavel();
		Termos t = new Termos();
		
		
		v.setNome("Temperatura");
		v.setValor(27);
		
		t.setNomeTermo("Baixa");
		t.setInicioNucleo(0);
		t.setFimNucleo(15);
		t.setInicioSuporte(0);
		t.setFimSuporte(25);
		
		Termos t2 = new Termos();
		t2.setNomeTermo("Ideal");
		t2.setInicioNucleo(25);
		t2.setFimNucleo(30);
		t2.setInicioSuporte(15);
		t2.setFimSuporte(35);
		
		Termos t3 = new Termos();
		t3.setNomeTermo("Alta");
		t3.setInicioNucleo(35);
		t3.setFimNucleo(45);
		t3.setInicioSuporte(30);
		t3.setFimSuporte(45);
		
		v.inserirTermo(t);
		v.inserirTermo(t2);
		v.inserirTermo(t3);
		
		b.calcula(v);
		variaveisInseridas.add(v);
		
		
		
		
		
		 v = new Variavel();
		t = new Termos();
		
		v.setNome("Luminosidade");
		v.setValor(70);
		
		t.setNomeTermo("Baixa");
		t.setInicioNucleo(0);
		t.setFimNucleo(30);
		t.setInicioSuporte(0);
		t.setFimSuporte(45);
		
		t2 = new Termos();
		t2.setNomeTermo("Media");
		t2.setInicioNucleo(45);
		t2.setFimNucleo(60);
		t2.setInicioSuporte(30);
		t2.setFimSuporte(75);
		
		t3 = new Termos();
		t3.setNomeTermo("Alta");
		t3.setInicioNucleo(75);
		t3.setFimNucleo(100);
		t3.setInicioSuporte(60);
		t3.setFimSuporte(100);
		
		v.inserirTermo(t);
		v.inserirTermo(t2);
		v.inserirTermo(t3);
		
		b.calcula(v);
		
		variaveisInseridas.add(v);
		
		
		
		v = new Variavel();
		t = new Termos();
		
		v.setNome("Chuva");
		v.setValor(60);
		
		t.setNomeTermo("Pouca");
		t.setInicioNucleo(0);
		t.setFimNucleo(25);
		t.setInicioSuporte(0);
		t.setFimSuporte(45);
		
		t2 = new Termos();
		t2.setNomeTermo("Media");
		t2.setInicioNucleo(45);
		t2.setFimNucleo(60);
		t2.setInicioSuporte(30);
		t2.setFimSuporte(75);
		
		t3 = new Termos();
		t3.setNomeTermo("Muita");
		t3.setInicioNucleo(75);
		t3.setFimNucleo(100);
		t3.setInicioSuporte(60);
		t3.setFimSuporte(100);
		
		v.inserirTermo(t);
		v.inserirTermo(t2);
		v.inserirTermo(t3);
		
		b.calcula(v);
		variaveisInseridas.add(v);
		
		
		
		
		v = new Variavel();
		t = new Termos();
		
		v.setNome("Qualidade");
		v.setObjetiva(true);
		
		t.setNomeTermo("Baixa");
		t.setInicioNucleo(0);
		t.setFimNucleo(30);
		t.setInicioSuporte(0);
		t.setFimSuporte(45);
		
		t2 = new Termos();
		t2.setNomeTermo("Media");
		t2.setInicioNucleo(45);
		t2.setFimNucleo(60);
		t2.setInicioSuporte(30);
		t2.setFimSuporte(70);
		
		t3 = new Termos();
		t3.setNomeTermo("Boa");
		t3.setInicioNucleo(70);
		t3.setFimNucleo(100);
		t3.setInicioSuporte(60);
		t3.setFimSuporte(100);
		
		v.inserirTermo(t);
		v.inserirTermo(t2);
		v.inserirTermo(t3);
		
		b.calcula(v);
		variaveisInseridas.add(v);
		
		
	
		
		
		this.loadTreeItems();
		
		this.areaRegras.setText("m");
		this.areaRegras.setText("se $temperatura alta & $luminosidade alta & $chuva pouca entao qualidade baixa\n"
								+"se $temperatura ideal & $luminosidade alta & $chuva media entao qualidade boa\n"
								+"se $temperatura baixa & $luminosidade alta & $chuva media entao qualidade media\n"
								+"se $temperatura ideal & $luminosidade baixa || $chuva pouca entao qualidade baixa\n"
								+"se $temperatura alta & $luminosidade media & $chuva muita entao qualidade boa\n");
		
		
		
	}
	
	@FXML
	public void capturaRegras(){
		
		this.arrayDeDadosObjetiva.clear();
		ArrayList<Termos> termosAtivos = new ArrayList<>();
		String[] allTextSplitted = this.areaRegras.getText().split("\n");
		Variavel variavelObjetiva = new Variavel();
		String auxiliarTermos = "";
		
		for (Variavel vv : variaveisInseridas) {
			if (vv.getObjetiva()) {
				variavelObjetiva = vv;
			}
		}
		
		variavelObjetiva.resetPertinenciaTermos();
		variavelObjetiva.termosToManipulate.clear();
		
		
		for (int i = 0; i < allTextSplitted.length; i++) {
			String[] linhaSplitted = allTextSplitted[i].split(" ");
			auxiliarTermos = linhaSplitted[linhaSplitted.length-1];
			
			for (int j = 0; j < linhaSplitted.length; j++) {
				if (linhaSplitted[j].startsWith("$")) {
					Variavel VariavelSel = variaveisInseridas.get(this.returnIndexOfSelectedVariable(linhaSplitted[j].substring(1,linhaSplitted[j].length())));
					
					if (linhaSplitted[j+1].startsWith("!")) {
						Termos termoSel = VariavelSel.retornaTermoUnico(VariavelSel.retornaIndiceDoTermo(linhaSplitted[j+2]));
						if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("muito")) {
							variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(linhaSplitted[linhaSplitted.length-1], Math.pow(termoSel.getGrauDePertinencia(), 2));
						}else if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("algo")) {
							variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(linhaSplitted[linhaSplitted.length-1], Math.pow(termoSel.getGrauDePertinencia(), 0.5));
							
						}else if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("defato")) {
							if (0 <= termoSel.getGrauDePertinencia() && termoSel.getGrauDePertinencia()<=0.5) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(linhaSplitted[linhaSplitted.length-1], (2*(Math.pow(termoSel.getGrauDePertinencia(), 2))));
							}else{
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(linhaSplitted[linhaSplitted.length-1], 1-(2*(Math.pow(termoSel.getGrauDePertinencia(), 2))));
							}
						}
					}else{
						Termos termoSel = VariavelSel.retornaTermoUnico(VariavelSel.retornaIndiceDoTermo(linhaSplitted[j+1]));
						variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(linhaSplitted[linhaSplitted.length-1], termoSel.getGrauDePertinencia());
					}
					
				}else if (linhaSplitted[j].equals("&")) {
					Termos anterior = new Termos();
					Termos posterior = new Termos();
					Termos posteriorAux = new Termos();
					Variavel variavelAnterior = new Variavel();
					Variavel variavelPosterior = new Variavel();
					
					
					if (linhaSplitted[j-2].startsWith("$")) {
						variavelAnterior = variaveisInseridas.get(this.returnIndexOfSelectedVariable(linhaSplitted[j-2].substring(1,linhaSplitted[j-2].length())));
					}else{
						variavelAnterior = variaveisInseridas.get(this.returnIndexOfSelectedVariable(linhaSplitted[j-3].substring(1,linhaSplitted[j-3].length())));
					}
					anterior = variavelAnterior.retornaTermoUnico(variavelAnterior.retornaIndiceDoTermo(linhaSplitted[j-1]));
					
					
					variavelPosterior = variavelAnterior = variaveisInseridas.get(this.returnIndexOfSelectedVariable(linhaSplitted[j+1].substring(1,linhaSplitted[j+1].length())));
					
					if (linhaSplitted[j+2].startsWith("!")) {
						posterior = variavelPosterior.retornaTermoUnico(variavelPosterior.retornaIndiceDoTermo(linhaSplitted[j+3]));
						if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("muito")) {
							posteriorAux = posterior.clone();
							posteriorAux.setGrauDePertinencia(Math.pow(posterior.getGrauDePertinencia(), 2));
							
						}else if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("algo")) {
							posteriorAux = posterior.clone();
							posteriorAux.setGrauDePertinencia(Math.pow(posterior.getGrauDePertinencia(), 0.5));
							
						}else if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("defato")) {
							if (0 <= posterior.getGrauDePertinencia() && posterior.getGrauDePertinencia()<=0.5) {
								posteriorAux = posterior.clone();
								posteriorAux.setGrauDePertinencia(Math.pow(posterior.getGrauDePertinencia(), 2));
							}else{
								posteriorAux = posterior.clone();
								posteriorAux.setGrauDePertinencia(Math.pow(posterior.getGrauDePertinencia(), 0.5));
							}
						}
					}else{
						posterior = variavelPosterior.retornaTermoUnico(variavelPosterior.retornaIndiceDoTermo(linhaSplitted[j+2]));
					}
					
					
					j+=2;

					double funcaoEAtivada = this.ativaFuncaoE(anterior, posterior).getGrauDePertinencia();

					if (j-3>0 && linhaSplitted[j-2].startsWith("!")){
						if (linhaSplitted[j-3].equals("&")) {
							Termos	termoOperacaoAnterior = variavelObjetiva.retornaTermoUnico(variavelObjetiva.retornaIndiceDoTermo(linhaSplitted[linhaSplitted.length-1]));
							if (termoOperacaoAnterior.getGrauDePertinencia()>funcaoEAtivada) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(termoOperacaoAnterior.getNomeTermo(),funcaoEAtivada );
							}
						}else if(linhaSplitted[j-3].equals("||")){
							Termos	termoOperacaoAnterior = variavelObjetiva.retornaTermoUnico(variavelObjetiva.retornaIndiceDoTermo(linhaSplitted[linhaSplitted.length-1]));
							if (termoOperacaoAnterior.getGrauDePertinencia()>funcaoEAtivada) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(termoOperacaoAnterior.getNomeTermo(),funcaoEAtivada );
							}
						}
					}else{
						if (linhaSplitted[j-2].equals("&")) {
							Termos	termoOperacaoAnterior = variavelObjetiva.retornaTermoUnico(variavelObjetiva.retornaIndiceDoTermo(linhaSplitted[linhaSplitted.length-1]));
							if (termoOperacaoAnterior.getGrauDePertinencia()>funcaoEAtivada) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(termoOperacaoAnterior.getNomeTermo(),funcaoEAtivada );
							}
						}else if(linhaSplitted[j-2].equals("||")){
							Termos	termoOperacaoAnterior = variavelObjetiva.retornaTermoUnico(variavelObjetiva.retornaIndiceDoTermo(linhaSplitted[linhaSplitted.length-1]));
							if (termoOperacaoAnterior.getGrauDePertinencia()>funcaoEAtivada) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(termoOperacaoAnterior.getNomeTermo(),funcaoEAtivada );
							}
						}
					}
					
					
				}else if (linhaSplitted[j].equals("||")) {
					Termos anterior = new Termos();
					Termos posterior = new Termos();
					Termos posteriorAux = new Termos();
					Variavel variavelAnterior = new Variavel();
					Variavel variavelPosterior = new Variavel();
					
					
					if (linhaSplitted[j-2].startsWith("$")) {
						variavelAnterior = variaveisInseridas.get(this.returnIndexOfSelectedVariable(linhaSplitted[j-2].substring(1,linhaSplitted[j-2].length())));
					}else{
						variavelAnterior = variaveisInseridas.get(this.returnIndexOfSelectedVariable(linhaSplitted[j-3].substring(1,linhaSplitted[j-3].length())));
					}
					anterior = variavelAnterior.retornaTermoUnico(variavelAnterior.retornaIndiceDoTermo(linhaSplitted[j-1]));
					
					variavelPosterior = variaveisInseridas.get(this.returnIndexOfSelectedVariable(linhaSplitted[j+1].substring(1,linhaSplitted[j+1].length())));
					
					if (linhaSplitted[j+2].startsWith("!")) {
						posterior = variavelPosterior.retornaTermoUnico(variavelPosterior.retornaIndiceDoTermo(linhaSplitted[j+3]));
						if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("muito")) {
							posteriorAux = posterior.clone();
							posteriorAux.setGrauDePertinencia(Math.pow(posterior.getGrauDePertinencia(), 2));
							
						}else if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("algo")) {
							posteriorAux = posterior.clone();
							posteriorAux.setGrauDePertinencia(Math.pow(posterior.getGrauDePertinencia(), 0.5));
							
						}else if (linhaSplitted[j+1].substring(1, linhaSplitted[j+1].length()).equalsIgnoreCase("defato")) {
							if (0 <= posterior.getGrauDePertinencia() && posterior.getGrauDePertinencia()<=0.5) {
								posteriorAux = posterior.clone();
								posteriorAux.setGrauDePertinencia(Math.pow(posterior.getGrauDePertinencia(), 2));
							}else{
								posteriorAux = posterior.clone();
								posteriorAux.setGrauDePertinencia(Math.pow(posterior.getGrauDePertinencia(), 0.5));
							}
						}
					}else{
						posterior = variavelPosterior.retornaTermoUnico(variavelPosterior.retornaIndiceDoTermo(linhaSplitted[j+2]));
					
					}
					
					
					j+=2;
					
					double funcaoOuAtivada = this.ativaFuncaoOu(anterior, posterior).getGrauDePertinencia();
					
					if (j-3>0 && linhaSplitted[j-2].startsWith("!")){
						if (linhaSplitted[j-3].equals("&")) {
							Termos	termoOperacaoAnterior = variavelObjetiva.retornaTermoUnico(variavelObjetiva.retornaIndiceDoTermo(linhaSplitted[linhaSplitted.length-1]));
							if (termoOperacaoAnterior.getGrauDePertinencia()<funcaoOuAtivada) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(termoOperacaoAnterior.getNomeTermo(),funcaoOuAtivada );
							}
						}else if(linhaSplitted[j-3].equals("||")){
							Termos	termoOperacaoAnterior = variavelObjetiva.retornaTermoUnico(variavelObjetiva.retornaIndiceDoTermo(linhaSplitted[linhaSplitted.length-1]));
							if (termoOperacaoAnterior.getGrauDePertinencia()<funcaoOuAtivada) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(termoOperacaoAnterior.getNomeTermo(),funcaoOuAtivada );
							}
						}
					}else{
						if (linhaSplitted[j-2].equals("&")) {
							Termos	termoOperacaoAnterior = variavelObjetiva.retornaTermoUnico(variavelObjetiva.retornaIndiceDoTermo(linhaSplitted[linhaSplitted.length-1]));
							if (termoOperacaoAnterior.getGrauDePertinencia()<funcaoOuAtivada) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(termoOperacaoAnterior.getNomeTermo(),funcaoOuAtivada );
							}
						}else if(linhaSplitted[j-2].equals("||")){
							Termos	termoOperacaoAnterior = variavelObjetiva.retornaTermoUnico(variavelObjetiva.retornaIndiceDoTermo(linhaSplitted[linhaSplitted.length-1]));
							if (termoOperacaoAnterior.getGrauDePertinencia()<funcaoOuAtivada) {
								variavelObjetiva.insereGrauDePertinenciaEmTermoByNome(termoOperacaoAnterior.getNomeTermo(),funcaoOuAtivada );
							}
						}
					}
				}
			}
			int index = variavelObjetiva.retornaIndiceDoTermo(auxiliarTermos);
			if (index != -1) {
				Termos aux = variavelObjetiva.retornaTermoUnico(index).clone();
				variavelObjetiva.termosToManipulate.add(aux);
				auxiliarTermos="";
				variavelObjetiva.resetPertinenciaTermos();
			}
			
		}
		this.loadTreeItems();
		this.defuzzy();
	}
	
	
	
	public void defuzzy(){
		Variavel variavelObjetiva = new Variavel();
		
		for (Variavel variavel : variaveisInseridas) {
			if (variavel.getObjetiva()) {
				variavelObjetiva = variavel;
			}
		}
		
		variavelObjetiva.salvacaoFinal();
		
		int tickUnit = (Math.abs(variavelObjetiva.getUniversoStart())+Math.abs(variavelObjetiva.getUniversoEnd()))/10;
		 
		
		double[][] soma = new double[variavelObjetiva.returnTemos().size()-1][variavelObjetiva.returnTemos().size()];
		int linhaSoma = 0;
		int colunaSoma = variavelObjetiva.returnTemos().size()-1;
		int tickfinal = variavelObjetiva.getUniversoEnd();
		
		
		for (int i = variavelObjetiva.returnTemos().size()-1; i > -1; i--) {
			Termos t = variavelObjetiva.returnTemos().get(i);
			
			XYChart.Series serieDoGrafico = new XYChart.Series<>();
			serieDoGrafico.setName(t.getNomeTermo());
			
			
			
			int qtdTick = 0;
			double somatorioSuporte =0;
			
			int qtdValorTick = tickfinal;
			
			
			int fimSup = t.getFimSuporte();
			
			if (i !=0 && variavelObjetiva.returnTemos().get(i-1).getGrauDePertinencia() != 0  ) {
				System.out.println("PRIMEIRO "+t.getNomeTermo());
				while (fimSup > (t.getInicioSuporte()-tickUnit)) {
					somatorioSuporte = somatorioSuporte + tickfinal;
					tickfinal = tickfinal-tickUnit;
					qtdTick++;
					fimSup=fimSup-tickUnit;
				}
			}else if(i==0 || variavelObjetiva.returnTemos().get(i-1).getGrauDePertinencia()==0){
				System.out.println("SEGUNDO "+t.getNomeTermo());
				while (fimSup > (t.getInicioSuporte())) {
					somatorioSuporte = somatorioSuporte + tickfinal;
					tickfinal = tickfinal-tickUnit;
					qtdTick++;
					fimSup=fimSup-tickUnit;	
				}
			}else{
				System.out.println("ESTUDAMAIS");
			}
			
			serieDoGrafico.getData().add(new XYChart.Data(tickfinal, t.getGrauDePertinencia()));
			serieDoGrafico.getData().add(new XYChart.Data(qtdValorTick, t.getGrauDePertinencia()));
			
			this.arrayDeDadosObjetiva.add(serieDoGrafico);
			
			somatorioSuporte = somatorioSuporte * t.getGrauDePertinencia();
		
			
			soma[linhaSoma][colunaSoma] = somatorioSuporte;
			soma[linhaSoma+1][colunaSoma] = (double) qtdTick*t.getGrauDePertinencia();
			colunaSoma--;
		}
		
		
		double qtdToDividir = 0;
		for (int i = soma.length; i > -1; i--) {
			qtdToDividir = qtdToDividir+soma[linhaSoma+1][i];
			
		}
		double qtdSomaTotal = 0;
		for (int i = soma.length; i > -1; i--) {
			qtdSomaTotal = qtdSomaTotal+soma[linhaSoma][i];
		}
		
		
		double total = (qtdSomaTotal/qtdToDividir);
		System.out.println("QTDSOMA "+qtdSomaTotal);
		System.out.println("QTDTODIV "+qtdToDividir);
		System.out.println("TOTAL "+total);
		
		txtFieldDeFuzzy.clear();
		txtFieldDeFuzzy.setText(total+"");
		
		Collections.reverse(this.arrayDeDadosObjetiva);
		this.loadTreeItems();
		
	}
	
	public Termos ativaFuncaoE(Termos termoAnterior, Termos termoPosterior){
		double menorValor = Math.min(termoAnterior.getGrauDePertinencia(), termoPosterior.getGrauDePertinencia());
		
		
		System.out.println(termoAnterior.getNomeTermo()+" "+termoPosterior.getNomeTermo()+" "+menorValor);
		if (termoAnterior.getGrauDePertinencia()==menorValor) {
			return termoAnterior;
		}else{
			return termoPosterior;
		}
		
	}
	
	public Termos ativaFuncaoOu(Termos termoAnterior, Termos termoPosterior){
		double maiorValor = Math.max(termoAnterior.getGrauDePertinencia(), termoPosterior.getGrauDePertinencia());
		
		if (termoAnterior.getGrauDePertinencia()==maiorValor) {
			return termoAnterior;
		}else{
			return termoPosterior;
		}
		
	}
	
}
