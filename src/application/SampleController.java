package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.org.apache.bcel.internal.generic.NEW;

import Classes.Variavel;
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
	CheckBox chckBoxVariavelObjetiva = new CheckBox();
	@FXML
	TreeView tableVariavels;
	@FXML
	TreeView arvore = new TreeView();
	@FXML
	ObservableList<Variavel> variaveisInseridas = FXCollections.observableArrayList();
	@FXML
	Button btnInserirVariavel = new Button();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// a nossa árvore
		TreeView arvore = new TreeView<>();
		// Esse é o item raiz da nossa árvore. Embaixo dele temos que colocar
		// mais itens
		TreeItem raiz = new TreeItem("Raiz");
		// Os itens podem ser aninhados, abaixo criamos dois items filhos no
		// nível 1 e para cada um desses, três filhotes no nível 2
		for (int i = 0; i < 2; i++) {
		 TreeItem lvl1 = new TreeItem("LVL1 " + i);
		 for (int j = 0; j < 3; j++) {
		  lvl1.getChildren().add(new TreeItem("LVL2 " + j));
		 }
		 raiz.getChildren().add(lvl1);
		}
		// você pode expandir para por padrão mostrar os filhos de um item.
		// Vamos fazer isso com a nossa raiz
		raiz.setExpanded(true);
		// agora setamos a raiz da nossa árvore
		arvore.setRoot(raiz);
		arvore.setVisible(true);
		
	}
	
	
	@FXML
	public void inserirVariavel(){
		Variavel v = new Variavel();
		v.setNome(txtFieldUniversoNome.getText());
		v.setUniversoStart(Integer.parseInt(txtFieldUniversoStart.getText()));
		v.setUniversoEnd(Integer.parseInt(txtFieldUniversoEnd.getText()));
		v.setObjetiva(chckBoxVariavelObjetiva.isSelected());
		
		variaveisInseridas.add(v);
		
		//tableVariavels.setItems(variaveisInseridas);
		
	}
	
	
	
}
