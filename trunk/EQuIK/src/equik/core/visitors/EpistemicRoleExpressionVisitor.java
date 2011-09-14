package equik.core.visitors;

import org.semanticweb.owlapi.model.OWLObjectVisitor;

import equik.core.model.OWLObjectEpistemicRole;

public interface EpistemicRoleExpressionVisitor extends OWLObjectVisitor{

	void visit(OWLObjectEpistemicRole property);

}
