package equik.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;



public class ReasonerInterface {
	
	
	private OWLClassExpression inputExpression = null;
	private OWLClassExpression resultExpression = null;
	private OWLReasoner reasoner = null;
	private OWLDataFactory factory = null;
	private OWLOntology ontology = null;
	private OWLOntologyManager manager = null;
	private boolean ontologyLoaded = false;
	private ArrayList<OWLNamedIndividual> namedElements = null;
	
	
	public ReasonerInterface(OWLReasoner reasoner){
		this.factory = OWLManager.getOWLDataFactory();
		this.reasoner = reasoner;		
		this.manager = OWLManager.createOWLOntologyManager();
		this.ontology = reasoner.getRootOntology();
		init();
		System.out.println("Total Instances = " + this.namedElements.size());
	}

	public ReasonerInterface(OWLClassExpression exp, OWLReasoner reasoner){
		this.factory = OWLManager.getOWLDataFactory();
		this.inputExpression = exp;
		this.reasoner = reasoner;		
		this.manager = OWLManager.createOWLOntologyManager();
		this.ontology = reasoner.getRootOntology();
		init();
	}
	
	public ReasonerInterface(OWLClassExpression exp,  OWLOntology ontology, Reasoner reasonerType){
		this.factory = OWLManager.getOWLDataFactory();
		this.inputExpression = exp;
		this.manager = OWLManager.createOWLOntologyManager();
		this.ontology = ontology;
		createReasoner(reasonerType);
		init();
	}
	
//	private void loadOntologyFromURI(IRI iri){
//			try {
//				this.ontology = this.manager.loadOntology(iri);
//				this.ontologyLoaded = true;
//			} catch (OWLOntologyCreationException e) {
//				this.ontologyLoaded = false;
//				System.out.println("Unable to load Ontology: "+e.toString());
//				System.err.println("Unable to load Ontology: "+e.toString());
//			}
//	}
//	
//	
//	public void loadOntologyFromFile(File file){
//		try {
//			this.ontology = this.manager.loadOntologyFromOntologyDocument(file);
//			this.ontologyLoaded = true;
//		} catch (OWLOntologyCreationException e) {
//			this.ontologyLoaded = false;
//			System.out.println("Unable to load Ontology: "+e.toString());
//			System.err.println("Unable to load ontology: "+e.toString());
//		}
//	}
//	
	
	private void createReasoner(Reasoner reasonerType){
		if(reasonerType == Reasoner.PELLET){
			PelletReasonerFactory fac = new PelletReasonerFactory();
			this.reasoner = fac.createReasoner(this.ontology);
		}
//		Similary for the rest of the reasoners.
	}
	
	public Boolean answerQuery(OWLIndividual ind){
		if(this.ontologyLoaded){
		this.resultExpression=translate(this.inputExpression);
		OWLClassAssertionAxiom axiom = factory.getOWLClassAssertionAxiom(this.resultExpression, ind);
		return reasoner.isEntailed(axiom);
		}
		else{
			System.out.println("No ontology loaded......");
			System.err.println("No ontology loaded......");
			return true;
		}
	}
	
	
	public Boolean answerQuery(OWLClassExpression exp, OWLIndividual ind){
		OWLClassAssertionAxiom axiom=factory.getOWLClassAssertionAxiom(translate(exp), ind);
		return reasoner.isEntailed(axiom);
	}
	
	private void init(){
		
		Iterator<Node<OWLNamedIndividual>> it = reasoner.getInstances(this.factory.getOWLThing(), false).iterator();
		this.namedElements=new ArrayList<OWLNamedIndividual>();
		while(it.hasNext()) { namedElements.add(it.next().getRepresentativeElement()); }
		
		
	}
	
	public OWLClassExpression TranslateExpressoin(){
		return translate(this.inputExpression);
	}

	public OWLClassExpression TranslateExpression(OWLClassExpression exp){
		return translate(exp);
	}
	
	private OWLClassExpression translate(OWLClassExpression exp) {
		TranslatorVisitor translator = new TranslatorVisitor(exp,this.reasoner, this.namedElements);
		OWLClassExpression c = translator.translate();
		System.out.println("Number of times reasoner called = " + translator.kCount);
		return c;
	}

}
