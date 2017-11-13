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
	
	private SimpleBooleanProperty objetiva;
	private SimpleIntegerProperty valor;
	ObservableList<Termos> variaveisInseridas;
	
	
	
	
	public Variavel(){
		this.nome = new SimpleStringProperty();
		this.objetiva = new SimpleBooleanProperty();
		this.variaveisInseridas = FXCollections.observableArrayList();
		this.valor = new SimpleIntegerProperty();
	}
	
	public void inserirTermo(Termos t){
		this.variaveisInseridas.add(t);
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
		return 0;
	}
	
	public void resetPertinenciaTermos(){
		for (Termos termos : variaveisInseridas) {
			termos.setGrauDePertinencia(0);
		}
	}
	
	public void setVariaveisInseridas(ObservableList<Termos> tInseridas){
		this.variaveisInseridas = tInseridas;
	}
	
	
	public void insereGrauDePertinenciaEmTermoByNome(String nomeDoTermo, double grauDePertinencia){
		for (Termos termos : variaveisInseridas) {
			if (termos.getNomeTermo().equalsIgnoreCase(nomeDoTermo)) {
				if (termos.getGrauDePertinencia()<grauDePertinencia) {
					termos.setGrauDePertinencia(grauDePertinencia);
				}
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
	
	
	
}