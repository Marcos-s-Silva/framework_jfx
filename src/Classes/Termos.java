package Classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class Termos {

	
	private SimpleIntegerProperty inicioNucleo;
	
	private SimpleIntegerProperty fimNucleo;
	
	private SimpleIntegerProperty inicioSuporte;
	
	private SimpleIntegerProperty fimSuporte;
	
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
}
