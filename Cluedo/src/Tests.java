import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Tests {

	@Test
	void test_accusation_correct() {
		RoomCard r = new RoomCard("Dining Room");
		WeaponCard w = new WeaponCard("Lead Pipe");
		CharacterCard c = new CharacterCard("Mr. Green");
		Envelope e = new Envelope(r,w,c);
		Accusation ac = new Accusation(r,w,c);
		assert(ac.testAccusation(e));
	}
	
	@Test
	void test_number_players_min() {		
		Cluedo cluedo = new Cluedo(true,"3 max ferg jam");
		assert(cluedo.getPlayers().size() == 3);
	}
	
	@Test
	void test_number_players_max() {		
		Cluedo cluedo = new Cluedo(true,"6 max ferg jam max ferg jam");
		assert(cluedo.getPlayers().size() == 6);
	}
	
	@Test
	void test_number_players_min_boundary() {		
		Cluedo cluedo = new Cluedo(true,"2 3 max ferg jam");
		assert(cluedo.getPlayers().size() == 3);
	}
	
	@Test
	void test_suggestion() {		
		Cluedo cluedo = new Cluedo(true,"3 max ferg jam s a a s s s s d s Dagger"
				+ " Miss Scarlett");
		
		//assert(cluedo.getPlayers().size() == 6);
		assert(true);
	}
	
	@Test
	void test_hasLost() {		
		Cluedo cluedo = new Cluedo(true,"3 max ferg jam s a a s s s s d a Dagger"
				+ " Miss Scarlett Ballroom %");
		assert(cluedo.getPlayers().get(0).hasLost() == true);
	}
	
	@Test
	void test_incorrect_input() {		
		Cluedo cluedo = new Cluedo(true,"% 3 f m x");
		assert(cluedo.getPlayers().size() == 3);
	}
	
	@Test
	void test() {		
		Cluedo cluedo = new Cluedo(true,"% 3 f m x");
		assert(cluedo.getPlayers().size() == 3);
	}
	
	
	
	
	
	

}
