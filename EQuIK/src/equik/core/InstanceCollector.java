package equik.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


public class InstanceCollector {

	private OWLReasoner reasoner;

	public InstanceCollector(OWLReasoner reasoner){
		this.reasoner = reasoner;

	}


	public Set<OWLIndividual> getClassInstances(OWLClassExpression exp){
		//		returns the set of intances belong to class exp using the reasoner,
		return collect(exp);
	}

	private Set<OWLIndividual>	collect(OWLClassExpression exp){
		//
		NodeSet<OWLNamedIndividual> nodeset =  reasoner.getInstances(exp, false);
		Iterator<Node<OWLNamedIndividual>> i=nodeset.iterator();
		Set<OWLIndividual> set=new HashSet<OWLIndividual>();
		while(i.hasNext()){ 
			set.add(i.next().getRepresentativeElement());
		}

		return set;

	}
}
