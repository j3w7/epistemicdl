package equik.core.model;

import org.semanticweb.owlapi.model.OWLBooleanClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpression;

public interface OWLObjectEpistemicConcept extends OWLBooleanClassExpression {
    OWLClassExpression getOperand();
}
