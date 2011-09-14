package equik.core.visitors;

import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;

import equik.core.model.OWLObjectEpistemicConcept;

public interface EpistemicClassExpressionVisitorEx<O> extends
		OWLClassExpressionVisitorEx<O>, EpistemicVisitor {

	O visit(OWLObjectEpistemicConcept ce);

}
