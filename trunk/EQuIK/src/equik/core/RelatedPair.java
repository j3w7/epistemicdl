package equik.core;

import org.semanticweb.owlapi.model.OWLIndividual;

public class RelatedPair {
	public OWLIndividual p1;
	public OWLIndividual p2;
	
	public RelatedPair(){
		p1 = null;
		p2 = null;
	}
	
	public RelatedPair(OWLIndividual p1, OWLIndividual p2){
		this.p1 = p1;
		this.p2 = p2;
	}

}
