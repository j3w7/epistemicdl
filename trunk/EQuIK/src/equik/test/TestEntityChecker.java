package equik.test;

import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class TestEntityChecker implements OWLEntityChecker {

	private String ns;
	private OWLDataFactory df;

	public TestEntityChecker(OWLDataFactory df, String ns) {
		this.df = df;
		this.ns = ns;
	}

	@Override
	public OWLClass getOWLClass(String name) {
		return df.getOWLClass(IRI.create(ns + name));
	}

	@Override
	public OWLObjectProperty getOWLObjectProperty(String name) {
		return df.getOWLObjectProperty(IRI.create(ns + name));
	}

	@Override
	public OWLDataProperty getOWLDataProperty(String name) {
		return df.getOWLDataProperty(IRI.create(ns + name));
	}

	@Override
	public OWLNamedIndividual getOWLIndividual(String name) {
		return df.getOWLNamedIndividual(IRI.create(ns + name));
	}

	@Override
	public OWLDatatype getOWLDatatype(String name) {
		return df.getOWLDatatype(IRI.create(ns + name));
	}

	@Override
	public OWLAnnotationProperty getOWLAnnotationProperty(String name) {
		return df.getOWLAnnotationProperty(IRI.create(ns + name));
	}

}
