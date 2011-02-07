package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.net.URI;


/**
 * Author: Anees ul Mehdi<br>
 * Karlsruhe Institute of Technology<br>
 * Institute of Applied Informatics and Formal Methods<br>
 * Date: 10-Nov-2010<br><br>
 * <p/>
 * Translates a set of triples that represent an <code>EpistemicOf</code>
 * class expression.
 */
public class epistemicOfTranslator extends AbstractClassExpressionTranslator {

    public epistemicOfTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public OWLClassExpression translate(IRI mainNode) {
        IRI epistemicOfObject = getResourceObject(mainNode, OWLRDFVocabulary.OWL_COMPLEMENT_OF.getIRI(), true);
        OWLClassExpression operand = translateToClassExpression(epistemicOfObject);
        return getDataFactory().getOWLObjectEpistemicConcept(operand);
    }
}
