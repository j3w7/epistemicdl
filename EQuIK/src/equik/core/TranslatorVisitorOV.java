package equik.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyAxiom;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import equik.core.model.EpistemicDataFactory;
import equik.core.model.OWLObjectEpistemicConcept;
import equik.core.model.OWLObjectEpistemicRole;

/**
 * Author: Anees ul Mehdi<br>
 * AIFB Institute,<br>
 * Karlsruhe Institute of Technology<br>
 * Date: 25-May-2011<br>
 * <br>
 */
public class TranslatorVisitorOV implements
		OWLClassExpressionVisitorEx<OWLClassExpression> {

	private OWLClassExpression inputExpression = null, resultExpression = null;
	private OWLDataFactory factory;
	private EpistemicDataFactory efactory;
	private OWLReasoner reasoner;
	private ArrayList<OWLNamedIndividual> namedElements;
	public int kCount;

	public TranslatorVisitorOV(OWLClassExpression inputExp,
			OWLReasoner reasoner, ArrayList<OWLNamedIndividual> namedElements) {
		this.inputExpression = inputExp;
		factory = OWLManager.getOWLDataFactory();
		efactory = new EpistemicDataFactory(factory);
		this.reasoner = reasoner;
		this.namedElements = namedElements;
		this.kCount = 0;
	}

	public void setExpression(OWLClassExpression ce) {
		this.inputExpression = ce;
	}

	public OWLClassExpression translate() {
		return inputExpression.accept(this);
	}

	public OWLClassExpression getTranslatedExpression() {

		return this.resultExpression;
	}

	// ************************************** Base Case
	// ***********************************************************
	public OWLClassExpression visit(OWLClass ce) {
		return ce;
	}

	public OWLClassExpression visit(OWLObjectOneOf ce) {
		return ce;
	}

	public OWLClassExpression visit(OWLObjectHasSelf ce) {
		// \exists.KS.Self
		if (ce.getProperty() instanceof OWLObjectEpistemicRole) {
			OWLObjectPropertyExpression prop = ((OWLObjectEpistemicRole) ce
					.getProperty()).getEpistemicRole();
			// check if \Sigma\not|=\top\squbseteq 3S.Self
			OWLClassExpression tmp = this.factory.getOWLObjectHasSelf(prop);
			if (!(this.reasoner.isEntailed(this.factory.getOWLSubClassOfAxiom(
					this.factory.getOWLThing(), tmp)))) {
				Set<OWLIndividual> set = new HashSet<OWLIndividual>();
				OWLAxiom axiom;
				for (OWLNamedIndividual a : this.namedElements) {
					axiom = this.factory.getOWLObjectPropertyAssertionAxiom(
							prop, a, a);
					if (this.reasoner.isEntailed(axiom)) {
						set.add(a);
					}
				}
				if (set.isEmpty()) {
					return this.factory.getOWLNothing();
				} else {
					return this.factory.getOWLObjectOneOf(set);
				}
			} else {
				// else we return 3S.Self.
				return this.factory.getOWLObjectHasSelf(prop);
			}
		} else {
			// non-epistemic
			return ce;
		}
	}

	// ************************************** Two cases of KD
	// **********************************************************
	public OWLClassExpression visit(OWLObjectEpistemicConcept ce) {
		if (ce.getOperand() instanceof OWLObjectOneOf) {
			return ce.getOperand();
		}
		// translate the subClass
		if (!(ce.getOperand() instanceof OWLObjectEpistemicConcept)) {

		}
		OWLClassExpression d = ce.getOperand().accept(this);
		// checking if concept d is equivalent to TOP
		if (this.reasoner.isEntailed(this.factory.getOWLEquivalentClassesAxiom(
				d, this.factory.getOWLThing()))) {
			return this.factory.getOWLThing();
		} else {
			Set<OWLIndividual> set = getInstances(d);
			// Set<OWLIndividual> set = getInstances(d);
			if (set.isEmpty()) {
				return factory.getOWLNothing();
			} else {
				return factory.getOWLObjectOneOf(set);
			}
		}
	}

	// ************************************** Boolean
	// **********************************************************

	@Override
	/*
	 * This method recursivley translate the operands of a class expression
	 * (representing the intersection of these) operands and translate them into
	 * a non-epismitec class expression. Finally constructs a new class
	 * expression from the translated operands into a non-epistmeic intersection
	 * of these translated operand i.e., it returns the non-epistemic version of
	 * the input class expression ce
	 */
	// TODO
	// have to check whether we need distribute K on the Intersection of pull it
	// out.
	public OWLClassExpression visit(OWLObjectIntersectionOf ce) {
		Set<OWLClassExpression> operands, kFreeOperands, translatedOperands;
		operands = ce.getOperands();
		translatedOperands = new HashSet<OWLClassExpression>();
		kFreeOperands = new HashSet<OWLClassExpression>();
		Iterator<OWLClassExpression> it = operands.iterator();
		OWLClassExpression op;
		boolean kPushable = true;
		while (it.hasNext() && kPushable) {
			op = it.next();
			// checking if K can't be pulled out
			if (!(op instanceof OWLObjectEpistemicConcept)
					&& !(op instanceof OWLObjectOneOf)) {
				kPushable = false;
				break;
			}
			if (op instanceof OWLObjectOneOf) {
				kFreeOperands.add(op);
			} else {
				kFreeOperands
						.add(((OWLObjectEpistemicConcept) op).getOperand());
			}

		}
		if (kPushable) {
			return efactory.getOWLObjectEpistemicConcept(
					factory.getOWLObjectIntersectionOf(kFreeOperands)).accept(
					this);
		} else {
			// operands need to be translated
			it = operands.iterator();
			while (it.hasNext()) {
				op = it.next();
				translatedOperands.add(op.accept(this));
			}
			return factory.getOWLObjectIntersectionOf(translatedOperands);
		}
	}

	@Override
	public OWLClassExpression visit(OWLObjectUnionOf ce) {
		Set<OWLClassExpression> operands, translatedOperands;
		operands = ce.getOperands();
		translatedOperands = new HashSet<OWLClassExpression>();
		Iterator<OWLClassExpression> it = operands.iterator();
		OWLClassExpression op;
		while (it.hasNext()) {
			op = it.next();
			translatedOperands.add(op.accept(this));
		}
		return factory.getOWLObjectUnionOf(translatedOperands);
	}

	/*
	 * This method translates a class expression of form Not-C into
	 * non-epistemic equivalent by first translating C into a non-epistemic
	 * class
	 */

	@Override
	public OWLClassExpression visit(OWLObjectComplementOf ce) {
		OWLClassExpression translatedSubClass = ce.getOperand().accept(this);
		return factory.getOWLObjectComplementOf(translatedSubClass);
	}

	// ************************************** Existential Quantification \exist
	// R. D**********************************************************

	@Override
	public OWLClassExpression visit(OWLObjectSomeValuesFrom ce) {
		// translating the filler first

		OWLClassExpression newFiller = ce.getFiller().accept(this);
		if (ce.getProperty() instanceof OWLObjectEpistemicRole) {
			OWLObjectPropertyExpression prop = ((OWLObjectEpistemicRole) ce
					.getProperty()).getEpistemicRole();
			// checking if the role is equivalent to topProperty i.e., the
			// universal role, we just remove K
			// OWLPropertyAxiom tmp =
			// this.factory.getOWLEquivalentObjectPropertiesAxiom(ce.getProperty(),this.factory.getOWLTopObjectProperty());
			OWLPropertyAxiom tmp = this.factory.getOWLSubObjectPropertyOfAxiom(
					this.factory.getOWLTopObjectProperty(), prop);
			if (this.reasoner.isEntailed(tmp)) {
				return this.factory.getOWLObjectSomeValuesFrom(prop, newFiller);
			}
			Set<OWLClassExpression> disjuncts = new HashSet<OWLClassExpression>();

			// First Disjunct
			Set<OWLIndividual> outgoing = getInstances(this.factory
					.getOWLObjectSomeValuesFrom(prop, newFiller));
			Set<OWLIndividual> incoming = getInstances(this.factory
					.getOWLObjectSomeValuesFrom(
							this.factory.getOWLObjectInverseOf(prop),
							this.factory.getOWLThing()));

			System.out.println("OutGoing: " + outgoing.size());
			System.out.println("Incoming: " + incoming.size());
			OWLClassExpression tmp1, tmp2, tmp3;
			Set<OWLIndividual> oneOf = new HashSet<OWLIndividual>();

			for (OWLIndividual a : outgoing) {
				for (OWLIndividual b : this.namedElements) {
					if (this.reasoner.isEntailed(this.factory
							.getOWLObjectPropertyAssertionAxiom(prop, a, b))) {
						// System.out.println(prop.toString()+ "(" +
						// a.toString() + ", " + b.toString() + ")");
						oneOf.add(b);
					}
				}
				if (!oneOf.isEmpty()) {

					tmp1 = this.factory.getOWLObjectOneOf(a); // {b|KB|= P(a,b)}
					tmp2 = this.factory.getOWLObjectIntersectionOf(
							this.factory.getOWLObjectOneOf(oneOf), newFiller);// IntersectionOf({b|KB|=
																				// P(a,b)},Trans(D))
					tmp3 = this.factory.getOWLObjectSomeValuesFrom(prop, tmp2);// exist
																				// P
																				// (IntersectionOf({b|KB|=
																				// P(a,b)},Trans(D)))
					disjuncts.add(this.factory.getOWLObjectIntersectionOf(tmp1,
							tmp3));

				}

			}

			// Second disjunct: it is exp of the form \exists
			// P.({b|KB|=top\sqsubseteq\exists P.{b}}\sqcap Trans(D))
			Set<OWLIndividual> tmpOneOf1 = new HashSet<OWLIndividual>();
			OWLClassExpression tmpExp;
			for (OWLIndividual b : incoming) {
				tmpExp = this.factory.getOWLObjectSomeValuesFrom(prop,
						this.factory.getOWLObjectOneOf(b));
				if (this.reasoner.isEntailed(this.factory
						.getOWLSubClassOfAxiom(this.factory.getOWLThing(),
								tmpExp))) {
					tmpOneOf1.add(b);
				}
				if (!tmpOneOf1.isEmpty()) {
					disjuncts.add(this.factory.getOWLObjectSomeValuesFrom(prop,
							this.factory.getOWLObjectIntersectionOf(
									this.factory.getOWLObjectOneOf(tmpOneOf1),
									newFiller)));

				}
			}
			// Third disjunct: it is expression of the form
			// {a|KB|=\top\sqsubseteq\exist P^-{a}}\sqcap Trans(D)
			Set<OWLIndividual> tmpOneOf2 = new HashSet<OWLIndividual>();
			for (OWLIndividual a : outgoing) {
				tmpExp = this.factory.getOWLObjectSomeValuesFrom(
						this.factory.getOWLObjectInverseOf(prop),
						this.factory.getOWLObjectOneOf(a));
				if (this.reasoner.isEntailed(this.factory
						.getOWLSubClassOfAxiom(this.factory.getOWLThing(),
								tmpExp))) {
					tmpOneOf2.add(a);
				}
				if (!tmpOneOf2.isEmpty()) {
					disjuncts.add(this.factory
							.getOWLObjectOneOf(getInstances(this.factory
									.getOWLObjectIntersectionOf(this.factory
											.getOWLObjectOneOf(tmpOneOf2),
											newFiller))));
				}
			}
			// Forth Disjunct: Trans(D) when KB|=\top\sqsubseteq
			tmpExp = this.factory.getOWLObjectHasSelf(prop);
			if (this.reasoner.isEntailed(this.factory.getOWLSubClassOfAxiom(
					this.factory.getOWLThing(), tmpExp))) {
				disjuncts.add(newFiller);
			}
			return this.factory.getOWLObjectUnionOf(disjuncts);

		} else {
			return factory.getOWLObjectSomeValuesFrom(ce.getProperty(),
					newFiller);
		}
	}

	// ************************************** Universal Quantification
	// **********************************************************

	@Override
	public OWLClassExpression visit(OWLObjectAllValuesFrom ce) {
		// if the property is epistemic i.e., \forall KR.D
		if (ce.getProperty() instanceof OWLObjectEpistemicRole) {
			OWLObjectPropertyExpression prop = ((OWLObjectEpistemicRole) ce
					.getProperty()).getEpistemicRole();
			// get instances of the translation of not D
			OWLClassExpression notD = this.factory.getOWLObjectComplementOf(
					ce.getFiller()).accept(this);
			Set<OWLIndividual> instSet = getInstances(notD);
			if (instSet.isEmpty()) {
				return this.factory.getOWLNothing();
			}
			OWLClassExpression newD = this.factory.getOWLObjectOneOf(instSet);
			Set<OWLIndividual> set = getInstances(this.factory
					.getOWLObjectSomeValuesFrom(prop, newD));
			if (set.isEmpty()) {
				return this.factory.getOWLNothing();
			} else {
				OWLClassExpression tmp = this.factory.getOWLObjectOneOf(set);
				return this.factory.getOWLObjectComplementOf(tmp);
			}

		}
		// The property is non-epistemic
		else {
			OWLClassExpression res = ce.getFiller().accept(this);
			return factory.getOWLObjectAllValuesFrom(ce.getProperty(), res);
		}
	}

	// ************************************** Qualified Number Restriction \geq
	// nKR.D **********************************************************
	@Override
	public OWLClassExpression visit(OWLObjectMinCardinality ce) {
		// translate the filler
		OWLClassExpression newFiller = ce.getFiller().accept(this);
		if (ce.getProperty() instanceof OWLObjectEpistemicRole) {
			OWLObjectPropertyExpression prop = ((OWLObjectEpistemicRole) ce
					.getProperty()).getEpistemicRole();
			Set<OWLIndividual> instSet = getInstances(newFiller);
			if (instSet.isEmpty()) {
				return this.factory.getOWLNothing();
			}
			OWLClassExpression newD = this.factory.getOWLObjectOneOf(instSet);
			OWLClassExpression newCE = this.factory.getOWLObjectMinCardinality(
					ce.getCardinality(), prop, newD);
			Set<OWLIndividual> set = getInstances(newCE);
			if (set.isEmpty()) {
				return this.factory.getOWLNothing();
			} else {
				return this.factory.getOWLObjectOneOf(set);
			}
		} else {
			return this.factory.getOWLObjectMinCardinality(ce.getCardinality(),
					ce.getProperty(), newFiller);
		}
	}

	@Override
	public OWLClassExpression visit(OWLObjectMaxCardinality ce) {
		// translate the filler
		OWLClassExpression newFiller = ce.getFiller().accept(this);
		if (ce.getProperty() instanceof OWLObjectEpistemicRole) {
			OWLObjectPropertyExpression prop = ((OWLObjectEpistemicRole) ce
					.getProperty()).getEpistemicRole();
			Set<OWLIndividual> instSet = getInstances(newFiller);
			if (instSet.isEmpty()) {
				return this.factory.getOWLNothing();
			}
			if (instSet.isEmpty()) {
				return this.factory.getOWLNothing();
			}
			OWLClassExpression newD = this.factory.getOWLObjectOneOf(instSet);
			OWLClassExpression newCE = this.factory.getOWLObjectMinCardinality(
					ce.getCardinality() + 1, prop, newD);
			Set<OWLIndividual> set = getInstances(newCE);
			if (set.isEmpty()) {
				return this.factory.getOWLNothing();
			} else {
				OWLClassExpression tmp = this.factory.getOWLObjectOneOf(set);
				return this.factory.getOWLObjectComplementOf(tmp);
			}
		} else {
			return this.factory.getOWLObjectMaxCardinality(ce.getCardinality(),
					ce.getProperty(), newFiller);
		}
	}

	@Override
	public OWLClassExpression visit(OWLObjectHasValue ce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OWLClassExpression visit(OWLObjectExactCardinality ce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OWLClassExpression visit(OWLDataSomeValuesFrom ce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OWLClassExpression visit(OWLDataAllValuesFrom ce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OWLClassExpression visit(OWLDataHasValue ce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OWLClassExpression visit(OWLDataMinCardinality ce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OWLClassExpression visit(OWLDataExactCardinality ce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OWLClassExpression visit(OWLDataMaxCardinality ce) {
		// TODO Auto-generated method stub
		return null;
	}

	private Set<OWLIndividual> getInstances(OWLClassExpression ce) {
		// System.out.println("Reasoner Called............................................................");
		this.kCount++;
		Set<OWLIndividual> set = new HashSet<OWLIndividual>();
		Iterator<Node<OWLNamedIndividual>> it = this.reasoner.getInstances(ce,
				false).iterator();
		while (it.hasNext()) {
			set.add(it.next().getRepresentativeElement());
		}
		return set;
	}

}
