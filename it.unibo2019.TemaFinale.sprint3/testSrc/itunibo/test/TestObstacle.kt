package itunibo.test

import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.After


import alice.tuprolog.SolveInfo
import it.unibo.kactor.sysUtil
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import org.junit.Test
import org.junit.Before


class TestObstacle{
	
	var actor : ActorBasic? = null;
	
	@Before
	fun launchSystem(){
		GlobalScope.launch{
			println("Test Obstacle");
			it.unibo.ctxButler.main()
		}
		
		Thread.sleep(5000)
		actor = sysUtil.getActor("butlerresourcemodel");
	}
	
	@Test
	fun actionTest(){
		movingForward();
		Thread.sleep(1000)
		obstacleFound()
	}
	
	fun movingForward(){
		println("sending movingForward")
		actor!!.scope.launch{
			MsgUtil.sendMsg("modelChangeAction", "modelChangeAction(robot, w)", actor!!);
		}
		Thread.sleep(2000)
		var toSolve = "model(actuator, robot, state(movingForward))"
		actor!!.solve(toSolve);
		var result = actor!!.resVar;
		println("result = $result")
	}
	
	fun obstacleFound(){
		println("Obstacle found")
		actor!!.scope.launch{
			actor!!.emit("sonarRobot", "sonarRobot(6)")
		}
		Thread.sleep(2000)
		var toSolve = "model(actuator, robot, state(stopped))"
		actor!!.solve(toSolve);
		var result = actor!!.resVar;
		println("actor $actor!!, $toSolve result $result")
		assertTrue("", result == "success")
	}
	
}


