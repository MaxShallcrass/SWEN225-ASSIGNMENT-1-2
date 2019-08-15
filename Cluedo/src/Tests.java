import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


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
	void test_number_players_3() {		
		Cluedo cluedo = new Cluedo(true,"3 max ferg jam");
		assert(cluedo.getPlayers().size() == 3);
	}
	
	@Test
	void test_number_players_4() {		
		Cluedo cluedo = new Cluedo(true,"4 max ferg jam j");
		assert(cluedo.getPlayers().size() == 4);
	}
	
	@Test
	void test_number_players_5() {		
		Cluedo cluedo = new Cluedo(true,"5 ferg jam max ferg jam");
		assert(cluedo.getPlayers().size() == 5);
	}
	
	@Test
	void test_number_players_6() {		
		Cluedo cluedo = new Cluedo(true,"6 max ferg jam max ferg jam");
		assert(cluedo.getPlayers().size() == 6);
	}
	
	@Test
	void test_number_players_min_boundary() {		
		Cluedo cluedo = new Cluedo(true,"2 3 max ferg jam");
		assert(cluedo.getPlayers().size() == 3);
	}
	
	
	@Test
	void test_incorrect_input() {		
		Cluedo cluedo = new Cluedo(true,"% 3 f m x");
		assert(cluedo.getPlayers().size() == 3);
	}
	
	@Test
	void test_making_suggestion() {		
		RoomCard r = new RoomCard("Dining Room");
		WeaponCard w = new WeaponCard("Lead Pipe");
		CharacterCard c = new CharacterCard("Mr. Green");
		Suggestion ac = new Suggestion(r,w,c);
		System.out.println(ac.toString());
		assert(ac.toString().equals("Room: Dining Room\n" + 
				"Weapon: Lead Pipe\n" + 
				"Character: Mr. Green"));
	}
	
	@Test
	void test_invalid_move_up() {		
		Cluedo cluedoT = new Cluedo(true,"3 f m x");
		Location l = cluedoT.getPlayers().get(0).getLocation();
		Cluedo cluedo = new Cluedo(true,"3 f m x w");
		assert(cluedo.getPlayers().get(0).getLocation().getX() == l.getX());
		assert(cluedo.getPlayers().get(0).getLocation().getY() == l.getY());
	}
	
	@Test
	void test_invalid_move_right() {		
		Cluedo cluedoT = new Cluedo(true,"3 f m x");
		Location l = cluedoT.getPlayers().get(0).getLocation();
		Cluedo cluedo = new Cluedo(true,"3 f m x d");
		assert(cluedo.getPlayers().get(0).getLocation().getX() == l.getX());
		assert(cluedo.getPlayers().get(0).getLocation().getY() == l.getY());
	}
	
	@Test
	void test_invalid_move_left() {		
		Cluedo cluedoT = new Cluedo(true,"3 f m x");
		Location l = cluedoT.getPlayers().get(0).getLocation();
		Cluedo cluedo = new Cluedo(true,"3 f m x w");
		assert(cluedo.getPlayers().get(0).getLocation().getX() == l.getX());
		assert(cluedo.getPlayers().get(0).getLocation().getY() == l.getY());
	}
	
	@Test
	void test_invalid_move_down() {		
		Cluedo cluedoT = new Cluedo(true,"3 f m x s");
		Location l = cluedoT.getPlayers().get(0).getLocation();
		Cluedo cluedo = new Cluedo(true,"3 f m x s s");
		assert(cluedo.getPlayers().get(0).getLocation().getX() == l.getX());
		assert(cluedo.getPlayers().get(0).getLocation().getY() == l.getY());
	}
	
	@Test
	void test_do_nothing() {		
		Cluedo cluedo = new Cluedo(true,"3 max ferg jam s a a s s s s s s s s a a n s d d s s s s s s s s s n");
		System.out.println(cluedo.getPlayers().get(0).getLocation().getY());
		Location l = new Location(9,0);
		assert(cluedo.getPlayers().get(0).getLocation().getX() == 9);
		assert(cluedo.getPlayers().get(0).getLocation().getY() == 0);
	}
	
	@Test
	void test_make_charactercard() {		
		CharacterCard c = new CharacterCard("Mr. Green");
		assert(c.getName().equals("Mr. Green"));
	}
	
	@Test
	void test_make_weaponcard() {		
		CharacterCard c = new CharacterCard("Rope");
		assert(c.getName().equals("Rope"));
	}
	
	@Test
	void test_make_roomcard() {		
		CharacterCard c = new CharacterCard("Ballroom");
		assert(c.getName().equals("Ballroom"));
	}
	
	@Test
	void test_load_board() {		
		Board b = new Board(new ArrayList<Player>(),new ArrayList<Player>());
		assert(true); //no error making
	}
	

	
	
	
	
	

}
