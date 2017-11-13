package Classes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Termos {

	private SimpleStringProperty nomeTermo;
	
	private SimpleIntegerProperty inicioNucleo;
	
	private SimpleIntegerProperty fimNucleo;
	
	private SimpleIntegerProperty inicioSuporte;
	
	private SimpleIntegerProperty fimSuporte;
	
	private SimpleDoubleProperty grauDePertinencia;
	
	
	public Termos(){
		this.nomeTermo = new SimpleStringProperty();
		this.inicioNucleo = new SimpleIntegerProperty();
		this.fimNucleo = new SimpleIntegerProperty();
		this.inicioSuporte = new SimpleIntegerProperty();
		this.fimSuporte = new SimpleIntegerProperty();
		this.grauDePertinencia = new SimpleDoubleProperty();
	}
	
	
	public Termos clone(){
		Termos t = new Termos();
		t.setNomeTermo(this.getNomeTermo());
		t.setInicioNucleo(this.getInicioNucleo());
		t.setFimNucleo(this.getFimNucleo());
		t.setInicioSuporte(this.getInicioSuporte());
		t.setFimSuporte(this.getFimSuporte());
		t.setGrauDePertinencia(this.getGrauDePertinencia());
		return t;
	}
	
	
	///NOME TERMO
	public String getNomeTermo() {
		return nomeTermo.get();
	}
	public void setNomeTermo(String nome) {
		this.nomeTermo.set(nome);
	}
	public StringProperty nomeTermoProperty(){
		return this.nomeTermo;
	}
	
	///INICIO NUCLEO
	public Integer getInicioNucleo() {
		return inicioNucleo.get();
	}
	public void setInicioNucleo(int in) {
		this.inicioNucleo.set(in);
	}
	public IntegerProperty inicioNucleoProperty(){
		return this.inicioNucleo;
	}
	//FIM NUCLEO
	public Integer getFimNucleo() {
		return fimNucleo.get();
	}
	public void setFimNucleo(int out) {
		this.fimNucleo.set(out);
	}
	public IntegerProperty fimNucleoProperty(){
		return this.fimNucleo;
	}
	//INICIO SUPORTE
	public Integer getInicioSuporte() {
		return inicioSuporte.get();
	}
	public void setInicioSuporte(int in) {
		this.inicioSuporte.set(in);
	}
	public IntegerProperty inicioSuporteProperty(){
		return this.inicioSuporte;
	}
	//FIM SUPORTE
	public Integer getFimSuporte() {
		return fimSuporte.get();
	}
	public void setFimSuporte(int in) {
		this.fimSuporte.set(in);
	}
	public IntegerProperty fimSuporteProperty(){
		return this.fimSuporte;
	}
	//GRAU DE PERTINENCIA
	public Double getGrauDePertinencia() {
		return grauDePertinencia.get();
	}
	public void setGrauDePertinencia(double in) {
		this.grauDePertinencia.set(in);
	}
	public DoubleProperty grauDePertinenciaProperty(){
		return this.grauDePertinencia;
	}
}
