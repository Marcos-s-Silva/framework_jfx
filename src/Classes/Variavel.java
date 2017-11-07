package Classes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Variavel {
	
	private SimpleStringProperty nome;
	
	private SimpleIntegerProperty universoStart;
	private SimpleIntegerProperty universoEnd;
	private SimpleBooleanProperty objetiva;
	ObservableList<Termos> variaveisInseridas;
	
	
	public Variavel(){
		this.nome = new SimpleStringProperty();
		this.universoStart = new SimpleIntegerProperty();
		this.universoEnd = new SimpleIntegerProperty();
		this.objetiva = new SimpleBooleanProperty();
		this.variaveisInseridas = FXCollections.observableArrayList();
	}
	
	public void inserirTermo(int inicioNucleo, int fimNucleo, int inicioSuporte, int fimSuporte){
		Termos t = new Termos();
		t.setInicioNucleo(inicioNucleo);
		t.setFimNucleo(fimNucleo);
		t.setInicioSuporte(inicioSuporte);
		t.setFimSuporte(fimSuporte);
		this.variaveisInseridas.add(t);
	}
	
	public ObservableList<Termos> returnTemos(){
		return this.variaveisInseridas;
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
/////////UNIVERO START
	public int getUniversoStart(){
		return universoStart.get();
	}
	public void setUniversoStart(int us){
		this.universoStart.set(us);
	}
	public IntegerProperty universoStartProperty(){
		return this.universoStart;
	}
	
//////////////UNIVERSO END
	public int getUniversoEnd(){
		return universoEnd.get();
	}
	public void setUniversoEnd(int ue){
		this.universoEnd.set(ue);
	}
	public IntegerProperty universoEndProperty(){
		return this.universoEnd;
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
	
	
	
	
}