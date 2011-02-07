package equik.core;


import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class Translator{
	OWLClassExpression resultExpression=null;
	OWLClassExpression inputExpression=null;
	OWLReasoner reasoner;
	public Translator(){
		
	}
	
	public void setReasoner(OWLReasoner reasoner){
		this.reasoner=reasoner;
	}
	
	public void setInputExpression(OWLClassExpression ce){
		this.inputExpression=ce;
	}
	
	
	public OWLReasoner getReasoner(){
		return this.reasoner;
	}
	
	public OWLClassExpression getNonEpistemicConcept(){
		if(inputExpression==null){
			System.out.println("No Input Expression Provided");
			return null;
		}
		else {
		translate(inputExpression);
		return resultExpression;
		}
	}
	
	
	private void translate(OWLClassExpression expression) {
//		TranslatorVisitor visitor=new TranslatorVisitor(expression, resultExpression);
//		visitor.visit();		
	}
	
}
