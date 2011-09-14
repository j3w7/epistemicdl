package equik.test;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import equik.core.model.EpistemicDataFactory;

public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OWLDataFactory factory = OWLManager.getOWLDataFactory();
		EpistemicDataFactory efactory = new EpistemicDataFactory(factory);
		File file;
		Testor testor1;

		OWLClassExpression wineDescriptor = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#WineDescriptor"));
		OWLObjectProperty	hasWineDescriptor = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasWineDescriptor"));

		//		C1
		OWLClassExpression c1 = factory.getOWLObjectSomeValuesFrom(hasWineDescriptor, wineDescriptor);
		OWLClassExpression kWineDescriptor = efactory.getOWLObjectEpistemicConcept(wineDescriptor);
		OWLObjectPropertyExpression kHasWineDescriptor = efactory.getOWLObjectEpistemicRole(hasWineDescriptor);
		//		EC1
		OWLClassExpression ec1 = factory.getOWLObjectSomeValuesFrom(kHasWineDescriptor, kWineDescriptor);
		//		C2
		OWLClassExpression c2 = factory.getOWLObjectAllValuesFrom(hasWineDescriptor, wineDescriptor);
		//		EC2
		OWLClassExpression ec2 = factory.getOWLObjectAllValuesFrom(kHasWineDescriptor, kWineDescriptor);
		//		C3
		OWLObjectProperty	madeFromFruit = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#madeFromFruit"));
		OWLClassExpression wineGrape = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#WineGrape"));
		OWLClassExpression c4tmp = factory.getOWLObjectSomeValuesFrom(madeFromFruit, wineGrape);
		OWLClassExpression c3 = factory.getOWLObjectIntersectionOf(c1,c4tmp);
		//		EC3
		OWLObjectPropertyExpression kMadeFromFruit = efactory.getOWLObjectEpistemicRole(madeFromFruit);
		OWLClassExpression kWineGrape = efactory.getOWLObjectEpistemicConcept(wineGrape);
		OWLClassExpression c3tmp = factory.getOWLObjectSomeValuesFrom(kMadeFromFruit, kWineGrape);
		OWLClassExpression ec3 = factory.getOWLObjectIntersectionOf(ec1,c3tmp);
		//		C4
		OWLObjectProperty	locatedIn = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#locatedIn"));
		OWLObjectProperty 	hasMaker = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasMaker"));
		OWLClassExpression whiteWine = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#WhiteWine"));
		OWLNamedIndividual frenchInd = factory.getOWLNamedIndividual(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#FrenchRegion"));
		OWLClassExpression frenchRegion = factory.getOWLObjectOneOf(frenchInd);
		OWLClassExpression tmpc4 = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(locatedIn, frenchRegion));
		OWLClassExpression c4 = factory.getOWLObjectIntersectionOf(whiteWine,tmpc4);
		//		EC4
		OWLObjectPropertyExpression kLocatedIn = efactory.getOWLObjectEpistemicRole(locatedIn);
		OWLObjectPropertyExpression kHasMaker = efactory.getOWLObjectEpistemicRole(hasMaker);
		OWLClassExpression kWhiteWine = efactory.getOWLObjectEpistemicConcept(whiteWine);
		OWLClassExpression tmp = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(kLocatedIn, frenchRegion));
		OWLClassExpression ec4 = factory.getOWLObjectIntersectionOf(kWhiteWine, tmp);
		//		C5
		OWLClassExpression wine = factory.getOWLClass(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Wine"));
		OWLNamedIndividual dry = factory.getOWLNamedIndividual(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Dry"));
		OWLNamedIndividual offDry = factory.getOWLNamedIndividual(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#OffDry"));
		OWLNamedIndividual sweet= factory.getOWLNamedIndividual(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#Sweet"));
		OWLClassExpression cDry =  factory.getOWLObjectOneOf(dry);
		OWLClassExpression cOffDry =  factory.getOWLObjectOneOf(offDry);
		OWLClassExpression cSweet =  factory.getOWLObjectOneOf(sweet);
		OWLObjectProperty hasSugar = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#hasSugar"));	
		OWLClassExpression d1 = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(hasSugar, cDry));
		OWLClassExpression d2 = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(hasSugar, cOffDry));
		OWLClassExpression d3 = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(hasSugar, cSweet));
		OWLClassExpression c5 = factory.getOWLObjectIntersectionOf(wine,d1,d2,d3);
		//		EC5
		OWLClassExpression kWine = efactory.getOWLObjectEpistemicConcept(wine);		
		OWLObjectPropertyExpression kHasSugar = efactory.getOWLObjectEpistemicRole(hasSugar);	
		OWLClassExpression tmp1 = factory.getOWLObjectSomeValuesFrom(kHasSugar, cDry);
		OWLClassExpression t1 = factory.getOWLObjectComplementOf(tmp1);
		OWLClassExpression t2 = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(kHasSugar, cOffDry));
		OWLClassExpression t3 = factory.getOWLObjectComplementOf(factory.getOWLObjectSomeValuesFrom(kHasSugar, cSweet));
		OWLClassExpression ec5 = factory.getOWLObjectIntersectionOf(kWine,t1,t2,t3);
		
//		EC6
		OWLObjectProperty adjRegion = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#adjacentRegion"));
		OWLObjectPropertyExpression kAdjRegion = efactory.getOWLObjectEpistemicRole(adjRegion);
		OWLClassExpression ec6 = factory.getOWLObjectSomeValuesFrom(kLocatedIn, factory.getOWLObjectSomeValuesFrom(kAdjRegion, frenchRegion));
		OWLObjectProperty producesWine = factory.getOWLObjectProperty(IRI.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#producesWine"));
		OWLObjectPropertyExpression kProducesWine = efactory.getOWLObjectEpistemicRole(producesWine);
		
		OWLClassExpression ec7  = factory.getOWLObjectSomeValuesFrom(kProducesWine, ec6);
		

		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		//													Testing on Wine_1
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("Results for Ontology Wine 1");
		file = new File("wine_1.owl");
		testor1 = new Testor(file);
		
		System.out.println("Input Class C1 = " + c1);
		testor1.test(c1, false);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
//
		System.out.println("Input Class CE1 = " + ec1);
		testor1.etest(ec1, false);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

//		System.out.println("Input Class C2 = " + c2);
//		testor1.test(c2, false);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
//		
//		System.out.println("Input Class EC2 = " + ec2);
//		testor1.etest(ec2, false);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
//		
//		System.out.println("Input Class C3 = " + c3);
//		testor1.test(c3, false);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
//		
//		System.out.println("Input Class EC3 = " + ec3);
//		testor1.etest(ec3, false);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
//		
//		System.out.println("Input Class C4 = " + c4);
//		testor1.test(c4, false);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
////		
//		System.out.println("Input Class EC4 = " + ec4);
//		testor1.etest(ec4, true);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		
//		System.out.println("Input Class C5 = " + c5);
//		testor1.test(c5, false);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
//
//		System.out.println("Input Class EC5 = " + ec5);
//		testor1.etest(ec5, false);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
//		
//		System.out.println("Input Class EC5 = " + ec5);
//		testor1.etest(ec5, true);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

	

	}


}
