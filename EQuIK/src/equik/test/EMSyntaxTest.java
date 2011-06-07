package equik.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import equik.parser.EMSyntaxClassExpressionParser;

public class EMSyntaxTest {

	private OWLDataFactory dataFactory;
	private OWLEntityChecker checker;

	@Before
	public void init() throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		dataFactory = manager.getOWLDataFactory();
		checker = new TestEntityChecker(dataFactory, "http://www.example.com#");
	}

	private void check(String toParse, String expectedParseResult) {
		EMSyntaxClassExpressionParser parser = new EMSyntaxClassExpressionParser(
				dataFactory, checker);

		OWLClassExpression desc = null;
		try {
			desc = parser.parse(toParse);
		} catch (ParserException e) {
			e.printStackTrace();
			assertTrue(false);
		}

		System.out.println(desc);
		assertEquals(desc.toString(), expectedParseResult);
	}

	@Test
	public void parseNonEpistemicClassExpression() {
		check("hasChild some Man",
				"ObjectSomeValuesFrom(<http://www.example.com#hasChild> <http://www.example.com#Man>)");
	}

	@Test
	public void parseEpistemicConceptExpression() {
		check("hasChild some (KnownConcept Man)",
				"ObjectSomeValuesFrom(<http://www.example.com#hasChild> ObjectEpistemicConcept(<http://www.example.com#Man>))");
	}

	@Test
	public void parseEpistemicRoleExpression() {
		check("(KnownRole hasChild) some Man",
		"ObjectSomeValuesFrom(<http://www.example.com#hasChild> ObjectEpistemicConcept(<http://www.example.com#Man>))");
	}

}
