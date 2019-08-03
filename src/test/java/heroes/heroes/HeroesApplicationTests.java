package heroes.heroes;

import heroes.heroes.MatchComponents.Board;
import heroes.heroes.MatchComponents.Field;
import heroes.heroes.MatchComponents.Match;
import heroes.heroes.MatchComponents.Move;
import heroes.heroes.MatchComponents.Units.Archer;
import heroes.heroes.MatchComponents.Units.Soldier;
import heroes.heroes.MatchMaking.SearchQueue;
import heroes.heroes.Security.UserPrinciple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeroesApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Mock
	Principal principal;

	User user1;
	User user2;
	SearchQueue queue;
	Match match;

	@Mock
	Principal principal2;


	@Test
	public void testMovingUnit(){
		Board board = new Board();
		Soldier soldier = new Soldier(new User("name","123"));
		Archer archer = new Archer(new User("name","123"));
		Soldier enemy = new Soldier(new User("enemy","123"));
		board.placeUnit(archer,0,0);
		board.placeUnit(soldier,2,2);
		board.placeUnit(enemy,7,7);
		Move move = new Move(new Field(0,0),new Field(2,2));
		//Assert.assertFalse("same owner",soldier.hasDifferentOwner(archer));
		Assert.assertTrue("same fields",soldier.getPosition()== board.getFieldFromOtherField(move.getEnd()));
		Assert.assertFalse("attacking own unit", soldier.attack(archer.getPosition()));
		Assert.assertFalse("move to field taken", board.executeMove(move));
		move = new Move(new Field(2,2),new Field(7,7));
		Assert.assertFalse("attack out of range", board.executeMove(move));
		Assert.assertTrue("got damaged but shouldn't", enemy.getHealth() == enemy.getMaxHealth());
		move = new Move(new Field(1,1),new Field(3,3));
		Assert.assertFalse("move from empty field",board.executeMove(move));
		move = new Move(new Field(1,1),new Field(9,9));
		Assert.assertFalse("move out of range",board.executeMove(move));
		move = new Move(new Field(2,2),new Field(4,4));
		Assert.assertTrue("normal move",board.executeMove(move));
		Assert.assertEquals("move gone wrong", soldier.getPosition(),new Field(4,4));


	}

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		user1 = new User("user1","1");
		user2 = new User("user2","1");
		when(principal.getName()).thenReturn(user1.getUsername());
		when(principal2.getName()).thenReturn(user2.getUsername());
		queue = new SearchQueue();

		queue.addUser(user1);
		queue.addUser(user2);
		match = new Match(queue);
	}

	@Test
	public void changeTurnAFterAttackTest(){
		Move firstmove = new Move(new Field(2,2),new Field(4,4));
		Assert.assertTrue("normal move",match.executeMove(firstmove, principal));
		Move secondmove = new Move(new Field(4,4),new Field(6,6));
		Assert.assertTrue("second move",match.executeMove(secondmove, principal));
		Move third = new Move(new Field(7,7),new Field(6,6));
		Assert.assertTrue("attack",match.executeMove(third,principal2));
		Assert.assertFalse("didn't change turn after attack",match.getPresentUser().equals(user2.getUsername()));
	}

	@Test
	public void turnChangeTest(){


		Move firstmove = new Move(new Field(2,2),new Field(1,1));
		Assert.assertTrue("normal move",match.executeMove(firstmove, principal));
		Move secondmove = new Move(new Field(1,1),new Field(2,2));
		Assert.assertTrue("second move",match.executeMove(secondmove, principal));
		Assert.assertFalse("present user has not changed",match.getPresentUser().equals(user1.getUsername()));
		Assert.assertTrue("changed to wrong user",match.getPresentUser().equals(user2.getUsername()));
		Assert.assertFalse("using units during enemy's turn",match.executeMove(firstmove,principal));
		Move third = new Move(new Field(7,7),new Field(8,8));
		Assert.assertTrue("normal move",match.executeMove(third,principal2));
		Move fourth = new Move(new Field(8,8),new Field(8,9));
		Assert.assertTrue("second move",match.executeMove(fourth,principal2));
		Assert.assertFalse("present user has not changed 2",match.getPresentUser().equals(user2.getUsername()));
		Assert.assertTrue("changed to wrong user 2",match.getPresentUser().equals(user1.getUsername()));
	}
	@Test
	public void changeTurnBecauseOfTime(){
		Move firstmove = new Move(new Field(2,2),new Field(1,1));
		Assert.assertTrue("normal move",match.executeMove(firstmove, principal));
		Move secondmove = new Move(new Field(1,1),new Field(2,2));
		Assert.assertTrue("second move",match.executeMove(secondmove, principal));
		try {
			Thread.sleep(11000);
			Assert.assertFalse("present user has not changed",match.getPresentUser().equals(user2.getUsername()));
			Assert.assertTrue("changed to wrong user",match.getPresentUser().equals(user1.getUsername()));
			Thread.sleep(11000);
			Assert.assertFalse("present user has not changed",match.getPresentUser().equals(user1.getUsername()));
			Assert.assertTrue("changed to wrong user",match.getPresentUser().equals(user2.getUsername()));
			Thread.sleep(6000);
			Move third = new Move(new Field(7,7),new Field(8,8));
			Assert.assertTrue("normal move",match.executeMove(third,principal2));
			Move fourth = new Move(new Field(8,8),new Field(8,9));
			Assert.assertTrue("second move",match.executeMove(fourth,principal2));
			Assert.assertFalse("present user has not changed",match.getPresentUser().equals(user2.getUsername()));
			Assert.assertTrue("changed to wrong user",match.getPresentUser().equals(user1.getUsername()));
            Thread.sleep(2800);
			Assert.assertFalse("didn't stop based on prevcious turn",match.getPresentUser().equals(user2.getUsername()));
			Assert.assertTrue("changed to wrong user",match.getPresentUser().equals(user1.getUsername()));
			Thread.sleep(10000);
			Assert.assertFalse("didn't stop based on prevcious turn",match.getPresentUser().equals(user1.getUsername()));
			Assert.assertTrue("changed to wrong user",match.getPresentUser().equals(user2.getUsername()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
