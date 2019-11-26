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


class TestTask{
	
	var actor : ActorBasic? = null;
	
	@Before
	fun launchSystem(){
		GlobalScope.launch{
			println("###Test Task###");
			it.unibo.ctxButler.main()
		}
		
		Thread.sleep(5000)
		actor = sysUtil.getActor("butlerresourcemodel");
	}
	
	@Test
	fun actionTest(){
		println("###TEST PREPARE");
		prepare();
	}
	
	fun prepare(){
		println("###prepare")
		actor!!.scope.launch{
			MsgUtil.sendMsg("modelChangeTask", "modelChangeTask(robot,preparing,0,0)", actor!!);
		}
		Thread.sleep(700)
		actor!!.solve("currentTask(robot, task(preparing))");
		var result = actor!!.resVar;
		println("###actor $actor!!, currentTask(robot, task(preparing)) result $result")
		assertTrue("", result == "success")
	}
	
}
