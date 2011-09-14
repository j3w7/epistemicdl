package equik.core.util;

import org.semanticweb.owlapi.util.SimpleRenderer;

import equik.core.model.OWLObjectEpistemicConcept;
import equik.core.model.OWLObjectEpistemicRole;
import equik.core.visitors.EpistemicClassExpressionVisitor;
import equik.core.visitors.EpistemicRoleExpressionVisitor;

public class SimpleEpistemicRenderer extends SimpleRenderer implements
		EpistemicRoleExpressionVisitor, EpistemicClassExpressionVisitor {

	@Override
	public void visit(OWLObjectEpistemicConcept ce) {
		append("ObjectEpistemicConcept(");
		ce.getOperand().accept(this);
		append(")");
	}

	@Override
	public void visit(OWLObjectEpistemicRole property) {
		append("EpistemicRole(");
		property.getEpistemicRole().accept(this);
		append(")");
	}
}
