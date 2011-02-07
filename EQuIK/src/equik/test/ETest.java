package equik.test;

import java.io.File;
import java.util.Iterator;

import org.mindswap.pellet.PelletOptions;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import equik.core.ReasonerInterface;

public class ETest{
	
	public static void main(String[] args) {

		//		**********************************************  Initializing ***********************************************************
		Iterator<Node<OWLNamedIndividual>> it;
		long  stopTime = 0, startTime = 0;
		NodeSet<OWLNamedIndividual> set;
//		Testor testor = new Testor(); 

		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		File file = new File("wine_1.owl");

		//		Loading the Ontology
		startTime = System.currentTimeMillis();
		OWLOntology wineOntology = null;

		try {
			wineOntology = m.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			System.out.println("Unable to load ontology: " + e.toString());
			e.printStackTrace();
		}
		stopTime = System.currentTimeMillis();
		System.out.println("Time Taken to Load the Ontology = " + (stopTime-startTime)/1000.0 + "sec.");

		//		Creating a (Pellet) reasoner
		PelletOptions.USE_UNIQUE_NAME_ASSUMPTION = true;
		PelletReasonerFactory rfac = new PelletReasonerFactory();		
		OWLReasoner reasoner = rfac.createReasoner(wineOntology);
		startTime = System.currentTimeMillis();
		ReasonerInterface translator = new ReasonerInterface(reasoner);
		stopTime = System.currentTimeMillis();
		System.out.println("Translator Created in " + (stopTime-startTime)/1000.0 + "ms.");
		OWLDataFactory factory = m.getOWLDataFactory();
		//
		//		it = reasoner.getInstances(factory.getOWLThing(), false).iterator();
		//		int cnt = 0;
		//		while(it.hasNext()){
		//			it.next();
		//			cnt++;
		//			}
		//		
		//		System.out.println("Total number of instances " + cnt);
		//		**********************************************  EC1 ***********************************************************
		OWLClassExpression wineDescriptor = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#WineDescriptor"));
		OWLObjectProperty	hasWineDescriptor = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasWineDescriptor"));
		OWLClassExpression kWineDescriptor = factory.getOWLObjectEpistemicConcept(wineDescriptor);
		OWLObjectPropertyExpression kHasWineDescriptor = factory.getOWLObjectEpistemicRole(hasWineDescriptor);
		OWLClassExpression ec1 = factory.getOWLObjectSomeValuesFrom(kHasWineDescriptor, kWineDescriptor);
		//		**********************************************  EC2 ***********************************************************
		OWLClassExpression ec2 = factory.getOWLObjectAllValuesFrom(kHasWineDescriptor, kWineDescriptor);



		//		**********************************************  EC3 ***********************************************************
		OWLObjectProperty	madeFromFruit = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#madeFromFruit"));
		OWLClassExpression wineGrape = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#WineGrape"));
		OWLObjectPropertyExpression kMadeFromFruit = factory.getOWLObjectEpistemicRole(madeFromFruit);
		OWLClassExpression kWineGrape = factory.getOWLObjectEpistemicConcept(wineGrape);
		OWLClassExpression c3tmp = factory.getOWLObjectSomeValuesFrom(kMadeFromFruit, kWineGrape);
		OWLClassExpression ec3 = factory.getOWLObjectIntersectionOf(ec1,c3tmp);
		OWLClassExpression tc ;
		
		//		**********************************************  EC4 ***********************************************************
		OWLObjectProperty	locatedIn = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#locatedIn"));
		OWLObjectPropertyExpression kLocatedIn = factory.getOWLObjectEpistemicRole(locatedIn);
		OWLObjectProperty 	hasMaker = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasMaker"));
		OWLObjectPropertyExpression kHasMaker = factory.getOWLObjectEpistemicRole(hasMaker);
		OWLClassExpression whiteWine = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#WhiteWine"));
		OWLClassExpression kWhiteWine = factory.getOWLObjectEpistemicConcept(whiteWine);

		OWLNamedIndividual frenchInd = factory.getOWLNamedIndividual(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#FrenchRegion"));
		OWLClassExpression frenchRegion = factory.getOWLObjectOneOf(frenchInd);
		OWLClassExpression kFrenchRegion = factory.getOWLObjectEpistemicConcept(frenchRegion);
		OWLClassExpression ec4tmp = factory.getOWLObjectSomeValuesFrom(kLocatedIn, kFrenchRegion);
		OWLClassExpression ec4tmp1 = factory.getOWLObjectSomeValuesFrom(kHasMaker, factory.getOWLObjectComplementOf(ec4tmp));
		OWLClassExpression ec4 = factory.getOWLObjectIntersectionOf(kWhiteWine, ec4tmp1);
//		**********************************************  EC5 ***********************************************************
		OWLClassExpression wine = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Wine"));
		OWLClassExpression kWine = factory.getOWLObjectEpistemicConcept(wine);
		OWLNamedIndividual dry = factory.getOWLNamedIndividual(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Dry"));
		OWLNamedIndividual offDry = factory.getOWLNamedIndividual(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#OffDry"));
		OWLNamedIndividual sweet= factory.getOWLNamedIndividual(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Sweet"));
		
		OWLClassExpression cDry =  factory.getOWLObjectOneOf(dry);
		OWLClassExpression cOffDry =  factory.getOWLObjectOneOf(offDry);
		OWLClassExpression cSweet =  factory.getOWLObjectOneOf(sweet);
		
		OWLObjectProperty hasSugar = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasSugar"));
		OWLObjectPropertyExpression kHasSugar = factory.getOWLObjectEpistemicRole(hasSugar);	
		OWLClassExpression t1 = factory.getOWLObjectSomeValuesFrom(kHasSugar, cDry);
		OWLClassExpression c1 = factory.getOWLObjectComplementOf(t1);
		OWLClassExpression c2 = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(kHasSugar, cOffDry));
		OWLClassExpression c3 = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(kHasSugar, cSweet));
		OWLClassExpression mc = factory.getOWLObjectIntersectionOf(kWine,c1,c2,c3);
		

//		System.out.println("EC1");
//		for(int i=1;i<4;i++){
//			System.out.println("Reading " + i);
//			startTime = System.currentTimeMillis();
//			tc = translator.TranslateExpression(ec1);
//			stopTime = System.currentTimeMillis();
//			System.out.println("Translated in " + (stopTime-startTime)/1000.0 + "sec.");
//			
//			startTime = System.currentTimeMillis();
//			set = reasoner.getInstances(tc, false);
//			stopTime = System.currentTimeMillis();
//			it = set.iterator();
//			testor.test(it, startTime, stopTime);
//		}
//		
//		System.out.println("EC2");
//		for(int i=1;i<4;i++){
//			System.out.println("Reading " + i);
//			startTime = System.currentTimeMillis();
//			tc = translator.TranslateExpression(ec2);
//			stopTime = System.currentTimeMillis();
//			System.out.println("Translated in " + (stopTime-startTime)/1000.0 + "sec.");
//			
//			startTime = System.currentTimeMillis();
//			set = reasoner.getInstances(tc, false);
//			stopTime = System.currentTimeMillis();
//			it = set.iterator();
//			testor.test(it, startTime, stopTime);
//		}
//		
//		
//		System.out.println("EC3");
//		for(int i=1;i<4;i++){
//			System.out.println("Reading " + i);
//			startTime = System.currentTimeMillis();
//			tc = translator.TranslateExpression(ec3);
//			stopTime = System.currentTimeMillis();
//			System.out.println("Translated in " + (stopTime-startTime)/1000.0 + "sec.");
//			
//			startTime = System.currentTimeMillis();
//			set = reasoner.getInstances(tc, false);
//			stopTime = System.currentTimeMillis();
//			it = set.iterator();
//			testor.test(it, startTime, stopTime);
//		}
		
//		System.out.println("EC4");
//		for(int i=1;i<4;i++){
		OWLClassExpression tm = factory.getOWLObjectComplementOf(kWine);
		System.out.println("Input " + tm);
//			System.out.println("Reading " + i);
			startTime = System.currentTimeMillis();
			tc = translator.TranslateExpression(tm);
			stopTime = System.currentTimeMillis();
			System.out.println("Translated in " + (stopTime-startTime)/1000.0 + "sec.");
			System.out.println("Output " + tc);
			startTime = System.currentTimeMillis();
			set = reasoner.getInstances(tc, false);
			stopTime = System.currentTimeMillis();
			System.out.println("Retrieved in " + (stopTime-startTime)/1000.0 + "sec.");
			it = set.iterator();
			while(it.hasNext()){
				System.out.println(it.next().getRepresentativeElement());
			}
//			testor.test(it, startTime, stopTime);
//		}
	}
}
