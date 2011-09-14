package equik.core.model;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubPropertyAxiom;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyExpressionImpl;
import equik.core.UnsupportedFeatureException;
import equik.core.visitors.EpistemicRoleExpressionVisitor;

public class OWLObjectEpistemicRoleImpl extends OWLObjectPropertyExpressionImpl
		implements OWLObjectEpistemicRole {

	private OWLObjectPropertyExpression epistemicProperty;

	public OWLObjectEpistemicRoleImpl(OWLDataFactory dataFactory,
			OWLObjectPropertyExpression epistemicProperty) {
		super(dataFactory);
		this.epistemicProperty = epistemicProperty;
	}

	public OWLObjectPropertyExpression getEpistemicRole() {
		return epistemicProperty;
	}

	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (!(obj instanceof OWLObjectEpistemicRole)) {
				return false;
			}
			return ((OWLObjectEpistemicRole) obj).getEpistemicRole().equals(
					epistemicProperty);
		}
		return false;
	}

	protected Set<? extends OWLSubPropertyAxiom<OWLObjectPropertyExpression>> getSubPropertyAxiomsForRHS(
			OWLOntology ont) {
		return ont.getObjectSubPropertyAxiomsForSuperProperty(this);
	}

	@Override
	public void accept(OWLPropertyExpressionVisitor visitor) {
		// FIXME visitor.visit(this);
		UnsupportedFeatureException.fire();
	}

	@Override
	public void accept(OWLObjectVisitor visitor) {
		// FIXME visitor.visit(this);
		if (visitor instanceof EpistemicRoleExpressionVisitor) {
			((EpistemicRoleExpressionVisitor) visitor).visit(this);
		} else if (visitor instanceof OWLObjectTypeIndexProvider) {
			((OWLObjectTypeIndexProvider) visitor).type = OWLObjectTypeIndexProvider.ENTITY_TYPE_INDEX_BASE + 101;
		} else
			UnsupportedFeatureException.fire();
	}

	@Override
	public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
		// FIXME visitor.visit(this);
		UnsupportedFeatureException.fire();
		return null;
	}

	@Override
	public <O> O accept(OWLObjectVisitorEx<O> visitor) {
		// FIXME visitor.visit(this);
		UnsupportedFeatureException.fire();
		return null;
	}

	public boolean isAnonymous() {
		return true;
	}

	public OWLObjectProperty asOWLObjectProperty() {
		throw new OWLRuntimeException(
				"Property is not a named property.  Check using the isAnonymous method before calling this method!");
	}

	protected int compareObjectOfSameType(OWLObject object) {
		return epistemicProperty.compareTo(((OWLObjectEpistemicRole) object)
				.getEpistemicRole());
	}

	/**
	 * Determines if this is the owl:topObjectProperty
	 * 
	 * @return <code>true</code> if this property is the owl:topObjectProperty
	 *         otherwise <code>false</code>
	 */
	public boolean isOWLTopObjectProperty() {
		return false;
	}

	/**
	 * Determines if this is the owl:bottomObjectProperty
	 * 
	 * @return <code>true</code> if this property is the
	 *         owl:bottomObjectProperty otherwise <code>false</code>
	 */
	public boolean isOWLBottomObjectProperty() {
		return false;
	}

	/**
	 * Determines if this is the owl:topDataProperty
	 * 
	 * @return <code>true</code> if this property is the owl:topDataProperty
	 *         otherwise <code>false</code>
	 */
	public boolean isOWLTopDataProperty() {
		return false;
	}

	/**
	 * Determines if this is the owl:bottomDataProperty
	 * 
	 * @return <code>true</code> if this property is the owl:bottomDataProperty
	 *         otherwise <code>false</code>
	 */
	public boolean isOWLBottomDataProperty() {
		return false;
	}

	private static final int MULT = 37;
	private int hashCode = 0;

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = 947;
			hashCode = hashCode * MULT + epistemicProperty.hashCode();
		}
		return hashCode;
	}

	final public int compareTo(OWLObjectEpistemicRole o) {
		return compareObjectOfSameType(o);
	}
}