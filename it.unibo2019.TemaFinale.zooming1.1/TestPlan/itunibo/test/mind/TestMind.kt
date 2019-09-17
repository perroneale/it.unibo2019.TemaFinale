package itunibo.test.mind

import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.After
import org.junit.Before

import alice.tuprolog.SolveInfo
import it.unibo.kactor.sysUtil
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import org.junit.Test

class TestMind{
	
	var actor : ActorBasic? = null;
	
	@Before
	fun launchSystem(){
		GlobalScope.launch{
			println("###Test robotMind###");
			it.unibo.ctxButler.main()
		}
		
		Thread.sleep(5000)
		actor = sysUtil.getActor("butlerresourcemodel");
	}
	
	@Test
	fun actionTest(){
		println("###TEST ACTION");
		moveForward();
	}
	
	fun moveForward(){
		println("###moveforward")
		actor!!.scope.launch{
			MsgUtil.sendMsg("modelChangeAction", "modelChangeAction(robot,w)", actor!!);
		}
		Thread.sleep(700)
		actor!!.solve("model(actuator, robot, state(movingForward))");
		var result = actor!!.resVar;
		println("###actor $actor!!, goalTest model(actuator, robot, state(movingForward) result $result")
		assertTrue("", result == "success")
	}
	
	
}
