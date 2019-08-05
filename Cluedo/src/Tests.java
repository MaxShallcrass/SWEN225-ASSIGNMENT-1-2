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
	void test_game_over() {
		RoomCard r = new RoomCard("Dining Room");
		WeaponCard w = new WeaponCard("Lead Pipe");
		CharacterCard c = new CharacterCard("Mr. Green");
		Envelope e = new Envelope(r,w,c);
		Accusation ac = new Accusation(r,w,c);
		assert(ac.testAccusation(e));
	}

}
