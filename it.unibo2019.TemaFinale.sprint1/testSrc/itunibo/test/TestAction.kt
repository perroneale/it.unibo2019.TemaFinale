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


class TestAction{
	
	var actor : ActorBasic? = null;
	
	@Before
	fun launchSystem(){
		GlobalScope.launch{
			println("Test Action");
			it.unibo.ctxButler.main()
		}
		
		Thread.sleep(5000)
		actor = sysUtil.getActor("butlerresourcemodel");
	}
	
	@Test
	fun actionTest(){
		
		println("TEST movingForward");
		moveForward();
		Thread.sleep(1000)
		println("TEST movingBackward");
		movingBackward();
		Thread.sleep(1000)
		println("TEST rotateLeft");
		rotateLeft();
		Thread.sleep(1000)
		println("TEST rotateRight");
		rotateRight();
		Thread.sleep(1000)
		println("TEST stop");
		stop();
	}
	
	fun movingBackward(){
		println("testing movingBackward")
		sendMsg("s")
		Thread.sleep(2000)
		var result = getResult("movingBackward")
		testAssertion(result)
	}
	
	fun rotateLeft(){
		println("testing rotateLeft")
		sendMsg("a")
		Thread.sleep(2000)
		var result = getResult("rotateLeft")
		testAssertion(result)
	}
	
	fun rotateRight(){
		println("testing rotateRight")
		sendMsg("d")
		Thread.sleep(2000)
		var result = getResult("rotateRight")
		testAssertion(result)
	}
	
	fun stop(){
		println("testing stop")
		sendMsg("h")
		Thread.sleep(2000)
		var result = getResult("stopped")
		testAssertion(result)
	}
	
	fun moveForward(){
		println("testing moveForward")
		sendMsg("w")
		Thread.sleep(2000)
		var result = getResult("movingForward")
		testAssertion(result)
	}
	
	fun sendMsg(action : String){
		actor!!.scope.launch{
			MsgUtil.sendMsg("modelChangeAction", "modelChangeAction(robot, ${action})", actor!!);
		}
		
	}
	
	fun getResult(action : String): String{
		var toSolve = "model(actuator, robot, state(${action}))"
		var result = ""
		actor!!.solve(toSolve);
		result = actor!!.resVar;
		println("actor $actor!!, $toSolve result $result")
		return result
	}
	
	fun testAssertion(result : String){
		assertTrue("", result == "success")
	}
	
}
