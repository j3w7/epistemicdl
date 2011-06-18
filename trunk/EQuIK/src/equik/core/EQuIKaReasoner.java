package equik.core;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.ClassExpressionNotInProfileException;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;
import org.semanticweb.owlapi.util.Version;

/**
 * Epistemic Reasoner Interface
 * 
 * TODO transform epistemic concepts 
 * 
 * @author Jens Wissmann
 * created on 18.06.2011
 */
public class EQuIKaReasoner implements OWLReasoner {

	private OWLReasoner coreReasoner;

	public EQuIKaReasoner(OWLReasoner coreReasoner) {
		this.coreReasoner = coreReasoner;
	}

	@Override
	public String getReasonerName() {
		return "EQuIKa";
	}

	@Override
	public Version getReasonerVersion() {
		return new Version(1, 0, 0, 0);
	}

	@Override
	public BufferingMode getBufferingMode() {
		return coreReasoner.getBufferingMode();
	}

	@Override
	public void flush() {
		coreReasoner.flush();
	}

	@Override
	public List<OWLOntologyChange> getPendingChanges() {
		return coreReasoner.getPendingChanges();
	}

	@Override
	public Set<OWLAxiom> getPendingAxiomAdditions() {
		return coreReasoner.getPendingAxiomAdditions();
	}

	@Override
	public Set<OWLAxiom> getPendingAxiomRemovals() {
		return coreReasoner.getPendingAxiomRemovals();
	}

	@Override
	public OWLOntology getRootOntology() {
		return coreReasoner.getRootOntology();
	}

	@Override
	public void interrupt() {
		coreReasoner.interrupt();
	}

	@Override
	public void precomputeInferences(InferenceType... inferenceTypes)
			throws ReasonerInterruptedException, TimeOutException,
			InconsistentOntologyException {
		coreReasoner.precomputeInferences(inferenceTypes);
	}

	@Override
	public boolean isPrecomputed(InferenceType inferenceType) {
		return coreReasoner.isPrecomputed(inferenceType);
	}

	@Override
	public Set<InferenceType> getPrecomputableInferenceTypes() {
		return coreReasoner.getPrecomputableInferenceTypes();
	}

	@Override
	public boolean isConsistent() throws ReasonerInterruptedException,
			TimeOutException {
		return coreReasoner.isConsistent();
	}

	@Override
	public boolean isSatisfiable(OWLClassExpression classExpression)
			throws ReasonerInterruptedException, TimeOutException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		return coreReasoner.isSatisfiable(classExpression);
	}

	@Override
	public Node<OWLClass> getUnsatisfiableClasses()
			throws ReasonerInterruptedException, TimeOutException,
			InconsistentOntologyException {
		return coreReasoner.getUnsatisfiableClasses();
	}

	@Override
	public boolean isEntailed(OWLAxiom axiom)
			throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		return coreReasoner.isEntailed(axiom);
	}

	@Override
	public boolean isEntailed(Set<? extends OWLAxiom> axioms)
			throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException {
		return coreReasoner.isEntailed(axioms);
	}

	@Override
	public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
		return coreReasoner.isEntailmentCheckingSupported(axiomType);
	}

	@Override
	public Node<OWLClass> getTopClassNode() {
		return coreReasoner.getTopClassNode();
	}

	@Override
	public Node<OWLClass> getBottomClassNode() {
		return coreReasoner.getBottomClassNode();
	}

	@Override
	public NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct)
			throws ReasonerInterruptedException, TimeOutException,
			FreshEntitiesException, InconsistentOntologyException,
			ClassExpressionNotInProfileException {
		return coreReasoner.getSubClasses(ce, direct);
	}

	@Override
	public NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce,
			boolean direct) throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getSuperClasses(ce, direct);
	}

	@Override
	public Node<OWLClass> getEquivalentClasses(OWLClassExpression ce)
			throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getEquivalentClasses(ce);
	}

	@Override
	public NodeSet<OWLClass> getDisjointClasses(OWLClassExpression ce)
			throws ReasonerInterruptedException, TimeOutException,
			FreshEntitiesException, InconsistentOntologyException {
		return coreReasoner.getDisjointClasses(ce);
	}

	@Override
	public Node<OWLObjectPropertyExpression> getTopObjectPropertyNode() {
		return coreReasoner.getTopObjectPropertyNode();
	}

	@Override
	public Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode() {
		return coreReasoner.getBottomObjectPropertyNode();
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getSubObjectProperties(pe, direct);
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getSuperObjectProperties(pe, direct);
	}

	@Override
	public Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getEquivalentObjectProperties(pe);
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getDisjointObjectProperties(pe);
	}

	@Override
	public Node<OWLObjectPropertyExpression> getInverseObjectProperties(
			OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getInverseObjectProperties(pe);
	}

	@Override
	public NodeSet<OWLClass> getObjectPropertyDomains(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getObjectPropertyDomains(pe, direct);
	}

	@Override
	public NodeSet<OWLClass> getObjectPropertyRanges(
			OWLObjectPropertyExpression pe, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getObjectPropertyRanges(pe, direct);
	}

	@Override
	public Node<OWLDataProperty> getTopDataPropertyNode() {
		return coreReasoner.getTopDataPropertyNode();
	}

	@Override
	public Node<OWLDataProperty> getBottomDataPropertyNode() {
		return coreReasoner.getBottomDataPropertyNode();
	}

	@Override
	public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return coreReasoner.getSubDataProperties(pe, direct);
	}

	@Override
	public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return coreReasoner.getSuperDataProperties(pe, direct);
	}

	@Override
	public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getEquivalentDataProperties(pe);
	}

	@Override
	public NodeSet<OWLDataProperty> getDisjointDataProperties(
			OWLDataPropertyExpression pe) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return coreReasoner.getDisjointDataProperties(pe);
	}

	@Override
	public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty pe,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return coreReasoner.getDataPropertyDomains(pe, direct);
	}

	@Override
	public NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getTypes(ind, direct);
	}

	@Override
	public NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression ce,
			boolean direct) throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getInstances(ce, direct);
	}

	@Override
	public NodeSet<OWLNamedIndividual> getObjectPropertyValues(
			OWLNamedIndividual ind, OWLObjectPropertyExpression pe)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getObjectPropertyValues(ind, pe);
	}

	@Override
	public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind,
			OWLDataProperty pe) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return coreReasoner.getDataPropertyValues(ind, pe);
	}

	@Override
	public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		return coreReasoner.getSameIndividuals(ind);
	}

	@Override
	public NodeSet<OWLNamedIndividual> getDifferentIndividuals(
			OWLNamedIndividual ind) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		return coreReasoner.getDifferentIndividuals(ind);
	}

	@Override
	public long getTimeOut() {
		return coreReasoner.getTimeOut();
	}

	@Override
	public FreshEntityPolicy getFreshEntityPolicy() {
		return coreReasoner.getFreshEntityPolicy();
	}

	@Override
	public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
		return coreReasoner.getIndividualNodeSetPolicy();
	}

	@Override
	public void dispose() {
		coreReasoner.dispose();
	}

}
