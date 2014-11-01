package exampleResponses;

public class TestClass {

	public ServerResponses sr;
	
	public TestClass() {
		sr = new ServerResponses();
		
		sr.addToGapList("Bonobo - Black Sands");
		sr.addToGapList("Bonobo - Kiara");
		sr.addToGapList("Charlie Boulala - Sonnenkind");
		
		sr.addToWishList("Monkey Safari - Jorg - Original Mix");
		sr.addToWishList("Jay Z, Alicia Keys - Empire State Of Mind");
		sr.addToWishList("Yelle - Ce Jeu - Tepr Remix");
		sr.addToWishList("Tiesto - Say Something");
		sr.addToWishList("The Avener - Fade Out Lines");
	}
}
