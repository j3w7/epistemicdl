package equik.core.model;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

import uk.ac.manchester.cs.owl.owlapi.OWLAnonymousClassExpressionImpl;
import equik.core.UnsupportedFeatureException;
import equik.core.visitors.EpistemicClassExpressionVisitor;
import equik.core.visitors.EpistemicClassExpressionVisitorEx;

/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br>
 * <br>
 */
public class OWLObjectEpistemicConceptImpl extends
		OWLAnonymousClassExpressionImpl implements OWLObjectEpistemicConcept {

	private OWLClassExpression operand;

	public OWLObjectEpistemicConceptImpl(OWLDataFactory dataFactory,
			OWLClassExpression operand) {
		super(dataFactory);
		this.operand = operand;
	}

	/**
	 * Gets the class expression type for this class expression
	 * 
	 * @return The class expression type
	 */
	public ClassExpressionType getClassExpressionType() {
		// FIXME Does this raise any problems?
		return null;
		// return ClassExpressionType.OBJECT_EPISTEMIC_OF;
	}

	public boolean isClassExpressionLiteral() {
		return !operand.isAnonymous();
	}

	public OWLClassExpression getOperand() {
		return operand;
	}

	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (!(obj instanceof OWLObjectEpistemicConcept)) {
				return false;
			}
			return ((OWLObjectEpistemicConcept) obj).getOperand().equals(
					operand);
		}
		return false;
	}

	public void accept(OWLClassExpressionVisitor visitor) {
		if (visitor instanceof EpistemicClassExpressionVisitor)
			((EpistemicClassExpressionVisitor) visitor).visit(this);
		else
			UnsupportedFeatureException.fire();
	}

	public void accept(OWLObjectVisitor visitor) {
		if (visitor instanceof EpistemicClassExpressionVisitor)
			((EpistemicClassExpressionVisitor) visitor).visit(this);
		else if (visitor instanceof OWLObjectTypeIndexProvider) {
			((OWLObjectTypeIndexProvider) visitor).type = OWLObjectTypeIndexProvider.ENTITY_TYPE_INDEX_BASE + 102;
			return;
		}
		else
			UnsupportedFeatureException.fire();
	}

	public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
		if (visitor instanceof EpistemicClassExpressionVisitorEx)
			return ((EpistemicClassExpressionVisitorEx<O>) visitor).visit(this);
		UnsupportedFeatureException.fire();
		return null;
	}

	public <O> O accept(OWLObjectVisitorEx<O> visitor) {
		if (visitor instanceof EpistemicClassExpressionVisitorEx)
			return ((EpistemicClassExpressionVisitorEx<O>) visitor).visit(this);
		UnsupportedFeatureException.fire();
		return null;
	}

	protected int compareObjectOfSameType(OWLObject object) {
		OWLObjectEpistemicConcept other = (OWLObjectEpistemicConcept) object;
		return operand.compareTo(other.getOperand());
	}

    private static final int MULT = 37;
    private int hashCode = 0;

    @Override
	public int hashCode() {
		if (hashCode == 0) {
	        hashCode = 953;
	        hashCode = hashCode * MULT + operand.hashCode();
		}
		return hashCode;
	}

	final public int compareTo(OWLObjectEpistemicConcept o) {
		return compareObjectOfSameType(o);
	}
}
