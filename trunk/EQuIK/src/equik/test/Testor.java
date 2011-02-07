package equik.test;

import java.io.File;
import java.util.Iterator;

import org.mindswap.pellet.PelletOptions;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import equik.core.ReasonerInterface;

public class Testor {
	private File file;
	private NodeSet<OWLNamedIndividual> set;
	private 	Iterator<Node<OWLNamedIndividual>> it;
	private OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	private OWLOntology ontology = null;
	private OWLReasoner reasoner = null;
	private ReasonerInterface translator;



	public Testor(File f){
		this.file = f;	
		PelletOptions.USE_UNIQUE_NAME_ASSUMPTION = true;
		PelletReasonerFactory rfac = new PelletReasonerFactory();
		try {
			this.ontology = manager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			System.out.println("Unable to load ontology: " + e.toString());
			e.printStackTrace();
		}
		this.reasoner = rfac.createReasoner(this.ontology);

		long startTime = System.currentTimeMillis();
		this.translator = new ReasonerInterface(this.reasoner);
		long stopTime = System.currentTimeMillis();
		System.out.println("Translator created in " + (stopTime - startTime)/1000.0 + "sec");
	}

	public void test(OWLClassExpression ce, boolean verbose){
		long startTime = System.currentTimeMillis();
		this.set = this.reasoner.getInstances(ce, false);
		long stopTime = System.currentTimeMillis();
		it = set.iterator();
		if(!verbose){
			test(it, startTime, stopTime);
		}
		else{
			testPrint(it, startTime, stopTime);
		}
	}



	public void etest(OWLClassExpression ce, boolean verbose){
		long startTime = System.currentTimeMillis();
		long st = System.currentTimeMillis();
		OWLClassExpression c = this.translator.TranslateExpression(ce);
		long et = System.currentTimeMillis();
		System.out.println("Translation done in  " + ((et-st)/1000) + " sec");
		this.set = this.reasoner.getInstances(c, false);
		long stopTime = System.currentTimeMillis();
		it = set.iterator();
		if(!verbose){
			test(it, startTime, stopTime);
		}
		else{
			testPrint(it, startTime, stopTime);
		}
	}

	private void testPrint(Iterator<Node<OWLNamedIndividual>> it2,
			long startTime, long stopTime) {
		int cnt = 0;
		System.out.println("The instances  are: ");
		while(it.hasNext()){
			System.out.println(it.next().getRepresentativeElement());
			cnt++;
		}
		System.out.println("The number of instances retrieved = " + cnt);
		long normalTime = stopTime - startTime;
		System.out.println("Time taken = " + normalTime/1000.0 +  "sec.");
	}

	private void test(Iterator<Node<OWLNamedIndividual>> it, long startTime, long stopTime){
		int cnt = 0;
		while(it.hasNext()){
			it.next();
			cnt++;
		}
		System.out.println("The number of instances retrieved = " + cnt);
		long normalTime = stopTime - startTime;
		System.out.println("Time taken = " + normalTime/1000.0 +  "sec.");
	}

}
