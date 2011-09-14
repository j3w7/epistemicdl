package equik.core.visitors;

import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;

import equik.core.model.OWLObjectEpistemicConcept;

public interface EpistemicClassExpressionVisitor extends
		OWLClassExpressionVisitor, EpistemicVisitor {

	void visit(OWLObjectEpistemicConcept ce);

}
