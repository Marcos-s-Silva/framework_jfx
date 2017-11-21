package Classes;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Variavel {
	
	private SimpleStringProperty nome;
	
	private SimpleBooleanProperty objetiva;
	private SimpleIntegerProperty valor;
	private SimpleIntegerProperty universoStart;
	private SimpleIntegerProperty universoEnd;
	ObservableList<Termos> variaveisInseridas;
	public ArrayList<Termos> termosToManipulate = new ArrayList<>();
	
	
	
	
	public Variavel(){
		this.nome = new SimpleStringProperty();
		this.objetiva = new SimpleBooleanProperty();
		this.variaveisInseridas = FXCollections.observableArrayList();
		this.valor = new SimpleIntegerProperty();
		this.universoStart = new SimpleIntegerProperty();
		this.universoEnd = new SimpleIntegerProperty();
	}
	
	public void inserirTermo(Termos t){
		this.variaveisInseridas.add(t);
		this.defineUniversos();
	}
	
	public ObservableList<Termos> returnTemos(){
		return this.variaveisInseridas;
	}
	
	public Termos retornaTermoUnico(int index){
		return variaveisInseridas.get(index);
	}
	
	public void removeTermoUnico(int index){
		this.variaveisInseridas.remove(index);
	}
	
	public int retornaIndiceDoTermo(String nomeDoTermo){
		for (int i = 0; i < this.variaveisInseridas.size(); i++) {
			if (this.variaveisInseridas.get(i).getNomeTermo().equalsIgnoreCase(nomeDoTermo)) {
				return i;
			}
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Termo não encontrado");
		alert.setHeaderText("Termo não encontrado na variável "+this.getNome());

		return -1;
	}
	
	public void resetPertinenciaTermos(){
		for (Termos termos : variaveisInseridas) {
			termos.setGrauDePertinencia(0);
		}
	}
	
	public void setVariaveisInseridas(ObservableList<Termos> tInseridas){
		this.variaveisInseridas = tInseridas;
	}
	
	public void inverteListaTermos(){
		Collections.reverse(variaveisInseridas);
	}
	
	public void insereGrauDePertinenciaEmTermoByNome(String nomeDoTermo, double grauDePertinencia){
		for (Termos termos : variaveisInseridas) {
			if (termos.getNomeTermo().equalsIgnoreCase(nomeDoTermo)) {
				Termos aux = termos.clone();
				aux.setGrauDePertinencia(grauDePertinencia);
				this.termosToManipulate.add(aux);
			}
		}
	}
	
	public Termos retornaTermoByNome(String nomeDoTermo){
		Termos termoToReturn = new Termos();
		for (Termos termos : variaveisInseridas) {
			if (termos.getNomeTermo().equalsIgnoreCase(nomeDoTermo)) {
				termoToReturn= termos.clone();
				break;
			}
		}
		return termoToReturn;
	}
	
	public void defineUniversos(){
		for (Termos termos : variaveisInseridas) {
			if (this.getUniversoEnd() < termos.getFimSuporte()) {
				this.setUniversoEnd(termos.getFimSuporte()); 
			}
			if (this.getUniversoStart() > termos.getInicioSuporte()) {
				this.universoStart.set(termos.getInicioSuporte());
			}
		}
	}
	public Variavel clone(){
		Variavel vRetorna = new Variavel();
		vRetorna.setNome(this.getNome());
		vRetorna.setValor(this.getValor());
		vRetorna.setObjetiva(this.getObjetiva());
		vRetorna.setVariaveisInseridas(this.returnTemos());
		return vRetorna;
	}

	public void salvacaoFinal(){
		for (Termos termos : variaveisInseridas) {
			for (Termos termos2 : termosToManipulate) {
				if (termos.getNomeTermo().equalsIgnoreCase(termos2.getNomeTermo())) {
					if (termos.getGrauDePertinencia()<termos2.getGrauDePertinencia()) {
						termos.setGrauDePertinencia(termos2.getGrauDePertinencia());
					}
				}
			}
		}
	}
	
/////////NOME	
	public String getNome() {
		return nome.get();
	}
	public void setNome(String nome) {
		this.nome.set(nome);
	}
	public StringProperty nomeProperty(){
		return this.nome;
	}
//////////OBJETIVA
	public boolean getObjetiva(){
		return objetiva.get();
	}
	public void setObjetiva(Boolean ob){
		this.objetiva.set(ob);
	}
	public BooleanProperty objetivaProperty(){
		return this.objetiva;
	}
	///////////VALOR
	public int getValor(){
		return valor.get();
	}
	public void setValor(int v){
		this.valor.set(v);
	}
	public IntegerProperty valorProperty(){
		return this.valor;
	}
	///UNIVERSO START
	public int getUniversoStart(){
		return universoStart.get();
	}
	public void setUniversoStart(int v){
		this.universoStart.set(v);
	}
	public IntegerProperty universoStartProperty(){
		return this.universoStart;
	}
	////UNIVERSO END
	public int getUniversoEnd(){
		return universoEnd.get();
	}
	public void setUniversoEnd(int v){
		this.universoEnd.set(v);
	}
	public IntegerProperty universoEndProperty(){
		return this.universoEnd;
	}
	
	
	
}