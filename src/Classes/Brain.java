package Classes;



public class Brain {
	
	
	public void calcula(Variavel v){
		
	
		for (Termos t : v.returnTemos()) {
			
			if (t.getInicioNucleo()<= v.getValor() && v.getValor() <= t.getFimNucleo()) {
				t.setGrauDePertinencia(1);
				
			}else if (t.getInicioSuporte() <= v.getValor() && v.getValor() <= t.getInicioNucleo()) {
				
				 double divisor = (v.getValor()-t.getInicioSuporte());
				 double dividendo = (t.getInicioNucleo() - t.getInicioSuporte());
				 double fimConta = divisor / dividendo;
				
				
				t.setGrauDePertinencia(fimConta);
				
				
			}else if(t.getFimNucleo() <= v.getValor() && v.getValor() <= t.getFimSuporte()){
				
				 double divisor = (t.getFimSuporte()-v.getValor());
				 double dividendo = (t.getFimSuporte()-t.getFimNucleo());
				 double fimConta = divisor / dividendo;
				
				t.setGrauDePertinencia(fimConta);
				
				
			}else{
				
				t.setGrauDePertinencia(0);
			}
		}
		
		
		
	}
	

}
