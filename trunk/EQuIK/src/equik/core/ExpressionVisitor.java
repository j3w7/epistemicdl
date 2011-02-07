package equik.core;


import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectEpistemicConcept;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

public class ExpressionVisitor implements OWLClassExpressionVisitor{

	@Override
	public void visit(OWLClass exp) {
		// TODO Auto-generated method stub
		System.out.println("visiting expression "+exp);
		
	}

	@Override
	public void visit(OWLObjectIntersectionOf arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectUnionOf exp) {
		System.out.println("This is union with operands:");
		int cnt=1;
		Set<OWLClassExpression> operands=exp.getOperands();		
		Iterator<OWLClassExpression> it=operands.iterator();
		OWLClassExpression op;
		while(it.hasNext()){
			op=it.next();
			System.out.println("Operand "+cnt++ +":"+op);
			op.accept(this);
		}
	}

	@Override
	public void visit(OWLObjectComplementOf exp) {
		// TODO Auto-generated method stub
		System.out.println("Visiting ComplementOf Expression and the operand is "+exp.getOperand());
	}

	@Override
	public void visit(OWLObjectSomeValuesFrom arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectAllValuesFrom arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectHasValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectMinCardinality arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectExactCardinality arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectMaxCardinality arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectHasSelf arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectOneOf arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataSomeValuesFrom arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataAllValuesFrom arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataHasValue arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataMinCardinality arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataExactCardinality arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataMaxCardinality arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectEpistemicConcept ce) {
		System.out.println("Visiting epistemicOf Expression and the operand is "+ce.getOperand());
		System.out.println("Successful");
		
	}

}
