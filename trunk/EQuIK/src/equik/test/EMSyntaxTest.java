package equik.test;

import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import equik.parser.EMSyntaxClassExpressionParser;
import equik.parser.EMSyntaxEditorParser;

public class EMSyntaxTest {

	private OWLDataFactory dataFactory;
	private OWLEntityChecker checker;

	@Before
	public void init() throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		dataFactory = manager.getOWLDataFactory();
		EMSyntaxEditorParser editorParser = new EMSyntaxEditorParser(
				dataFactory, "");

		OWLOntology testOntology = manager.createOntology();

		manager.addAxiom(testOntology, dataFactory
				.getOWLDeclarationAxiom(dataFactory.getOWLClass(IRI
						.create("http://www.example.com#Man"))));
		manager.addAxiom(testOntology, dataFactory
				.getOWLDeclarationAxiom(dataFactory.getOWLObjectProperty(IRI
						.create("http://www.example.com#hasChild"))));

		editorParser.setDefaultOntology(testOntology);

		checker = editorParser.getOWLEntityChecker();
	}

	@Test
	public void parseEpistemicRoleExpression() {
		EMSyntaxClassExpressionParser parser = new EMSyntaxClassExpressionParser(
				dataFactory, checker);
		try {
			parser.parse("<http://www.example.com#hasChild> some Man");
			// parser.parse("hasChild some Man");
		} catch (ParserException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void parseEpistemicConceptExpression() {
		assertTrue(false);

	}

}
