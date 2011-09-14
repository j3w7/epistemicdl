package equik.core.model;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

public class EpistemicDataFactory {

	private OWLDataFactory factory;

	public EpistemicDataFactory(OWLDataFactory factory) {
		this.factory = factory;
	}

	public OWLObjectEpistemicRole getOWLObjectEpistemicRole(
			OWLObjectPropertyExpression property) {
		return new OWLObjectEpistemicRoleImpl(factory, property);
	}

	public OWLObjectEpistemicConcept getOWLObjectEpistemicConcept(
			OWLClassExpression operand) {
		return new OWLObjectEpistemicConceptImpl(factory, operand);
	}

}
