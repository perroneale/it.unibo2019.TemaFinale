package itunibo.planner

import aima.core.agent.Action
import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.delay
import itunibo.planner.model.RobotState.Direction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.sysUtil
import itunibo.planner.model.RoomMap

object moveUtils{
    private val actions : List<Action>? = null
    private var existPlan = false

	private var mapDims   : Pair<Int,Int> = Pair(0,0)
	private var curPos    : Pair<Int,Int> = Pair(0,0)
	private var curGoal   : Pair<Int,Int> = Pair(0,0)
	private var direction = "downDir"
	private val PauseTime = 750
	private val ForwardTime = 770
	
	private var MaxX        = 0
	private var MaxY        = 0
	
	
    private fun storeMovesInActor( actor : ActorBasic, actions : List<Action>?  ) {
        if( actions == null ) return
        val iter = actions.iterator()
        while (iter.hasNext()) {
            val a = iter.next()
            actor.solve("assert( move($a) )")
        }
    }
	
	private fun storeMovesInActor(actions : List<Action>?, dest : String  ) {
		var actorDest = sysUtil.getActor(dest)
        if( actions == null ) return
        val iter = actions.iterator()
        while (iter.hasNext()) {
            val a = iter.next()
            actorDest!!.solve("assert( move($a) )")
        }
    }
	
	
	fun loadRoomMap( actor : ActorBasic,  fname : String ){
		val dims = plannerUtil.loadRoomMap( fname )
		println(dims)
		memoMapDims( actor, dims )
 	}
	fun saveMap( actor : ActorBasic, fname : String) {
		val dims = plannerUtil.saveMap( fname )
		memoMapDims( actor, dims )
 	}	
	fun memoMapDims( actor : ActorBasic, dims : Pair<Int,Int> ){
		mapDims = dims
		MaxX    = dims.first
		MaxY    = dims.second
		actor.solve("retract( mapdims(_,_) )")		//remove old data
		actor.solve("assert(  mapdims( ${dims.first},${dims.second} ) )")				
	}
	
 	fun getMapDimX( ) 	: Int{ return mapDims.first }
	fun getMapDimY( ) 	: Int{ return mapDims.second }
 	fun getPosX(actor : ActorBasic)    	  : Int{ setPosition(actor); return curPos.first  } 
	fun getPosY(actor : ActorBasic)    	  : Int{ setPosition(actor); return curPos.second }
	fun getDirection(actor : ActorBasic)  : String{ setPosition(actor);return direction.toString() }
	fun mapIsEmpty() : Boolean{return ((getMapDimX( )==0 &&  getMapDimY( )==0 ) || (getMapDimX( )==1 &&  getMapDimY( )==1 ) )}
	
	
	fun showCurrentRobotState(){
		println("===================================================")
		plannerUtil.showMap()
		direction = plannerUtil.getDirection()
		println("RobotPos=(${curPos.first}, ${curPos.second}) in map($MaxX,$MaxY) direction=$direction")
		println("===================================================")
	}
 	fun setObstacleOnCurrentDirection( actor : ActorBasic ){
		doPlannedMove(actor, direction )
	}
	
	fun setDuration( actor : ActorBasic ){
		val time = plannerUtil.getDuration()
		actor.solve("retract( wduration(_) )")		//remove old data
		actor.solve("assert( wduration($time) )")
 	}
	
	fun setDirection( actor : ActorBasic )  {
		direction = plannerUtil.getDirection()
		//println("moveUtils direction=$direction")
		actor.solve("retract( direction(_) )")		//remove old data
		actor.solve("assert( direction($direction) )")
 	}
	
	
	fun setGoal( actor : ActorBasic, x: String, y: String) {
		val xv = Integer.parseInt(x)
		val yv = Integer.parseInt(y)
		plannerUtil.setGoal( xv,yv )
		curGoal=Pair(xv,yv)
		actor.solve("retract( curGoal(_,_) )")		//remove old data
		actor.solve("assert( curGoal($x,$y) )")
	}
	
	fun setGoal( actor : ActorBasic, x: String, y: String, direction : String) {
		val xv = Integer.parseInt(x)
		val yv = Integer.parseInt(y)
		plannerUtil.setGoal( xv,yv, direction )
		curGoal=Pair(xv,yv)
		actor.solve("retract( curGoal(_,_) )")		//remove old data
		actor.solve("assert( curGoal($x,$y) )")
	}

	fun doPlan(actor : ActorBasic ){
		val plan = plannerUtil.doPlan(  )
		existPlan = plan != null
		if( existPlan ) storeMovesInActor(actor,plan) 
	}
	
	fun doPlan(dest : String ){
		val plan = plannerUtil.doPlan(  )
		existPlan = plan != null
		if( existPlan ) storeMovesInActor(plan,dest) 
	}
	
	fun existPlan() : Boolean{ return existPlan }

	fun doPlannedMove(actor : ActorBasic, move: String){
		plannerUtil.doMove( move )
		setPosition(actor)
		//setDirection( actor )
	}
	
	fun setPosition(actor : ActorBasic){
		direction     = plannerUtil.getDirection()
		val posx      = plannerUtil.getPosX()
		val posy      = plannerUtil.getPosY()
		curPos        = Pair( posx,posy )
		
		//println("setPosition curPos=($posx,$posy,$direction)")
		actor.solve("retract( curPos(_,_) )")		//remove old data
		actor.solve("assert( curPos($posx,$posy) )")			
		actor.solve("retract( curPos(_,_,_) )")		//remove old data
		actor.solve("assert( curPos($posx,$posy,$direction) )")			
	}
	
	suspend fun rotate(actor:ActorBasic,move:String,pauseTime:Int=PauseTime){
		when( move ){
			"a" -> rotateLeft(actor, pauseTime)
			"d" -> rotateRight(actor, pauseTime)
			"l" -> rotateLeft90( actor )
		    "r" -> rotateRight90( actor )
			else -> println("rotate $move unknown")
		}
  	}
 	suspend fun rotateRight(actor : ActorBasic, pauseTime : Int = PauseTime){
 		actor.forward("modelChangeAction", "modelChangeAction(robot,d)", "butlerresourcemodel")
 		doPlannedMove(actor, "d" )	    //update map
		delay( pauseTime.toLong() )
		actor.forward("modelChangeAction", "modelChangeAction(robot,h)", "butlerresourcemodel")
		showCurrentRobotState()
	}
	
	suspend fun rotateRight2(actor : ActorBasic, pauseTime : Int = PauseTime){
 		actor.forward("modelChangeAction", "modelChangeAction(robot,d)", "butlerresourcemodel")
 		doPlannedMove(actor, "d" )	    //update map
		delay( pauseTime.toLong() )
		showCurrentRobotState()
	}
 	suspend fun rotateRight90(actor : ActorBasic ){
 		actor.forward("modelChangeAction", "modelChangeAction(robot,r)", "butlerresourcemodel")
		delay( 800 )
 		doPlannedMove(actor, "r" )	    //update map
 	}
 	suspend fun rotateRight90tuning(actor : ActorBasic ){
 		actor.forward("modelChangeAction", "modelChangeAction(robot,r)", "butlerresourcemodel")
		println("TUNING .... ")
 		readLine()
 		doPlannedMove(actor, "r" )	    //update map
 	}
	suspend fun rotateLeft(actor : ActorBasic, pauseTime : Int = PauseTime){
		actor.forward("modelChangeAction", "modelChangeAction(robot,a)", "butlerresourcemodel")
 		doPlannedMove(actor, "a" )	    //update map	
		delay( pauseTime.toLong() )
		actor.forward("modelChangeAction", "modelChangeAction(robot,h)", "butlerresourcemodel")
		showCurrentRobotState()		
	}
	
	suspend fun rotateLeft2(actor : ActorBasic, pauseTime : Int = PauseTime){
		actor.forward("modelChangeAction", "modelChangeAction(robot,a)", "butlerresourcemodel")
 		doPlannedMove(actor, "a" )	    //update map	
		delay( pauseTime.toLong() )
		showCurrentRobotState()
	}
	suspend fun rotateLeft90( actor : ActorBasic ){
		actor.forward("modelChangeAction", "modelChangeAction(robot,l)", "butlerresourcemodel")
		delay( 800 )
 		doPlannedMove(actor, "l" )	    //update map	
 	}
	suspend fun rotateLeft90tuning( actor : ActorBasic ){
		actor.forward("modelChangeAction", "modelChangeAction(robot,l)", "butlerresourcemodel")
		println("TUNING .... ")
 		readLine()
		//delay( 800 )
 		doPlannedMove(actor, "l" )	    //update map	
 	}
 	suspend fun moveAhead(actor:ActorBasic, stepTime:Int, pauseTime:Int = PauseTime, dest:String ="butlerresourcemodel"){
		println("moveUtils moveAhead stepTime=$stepTime")
		actor.forward("modelChangeAction", "modelChangeAction(robot,w)", dest)
		delay( stepTime.toLong() )
		actor.forward("modelChangeAction", "modelChangeAction(robot,h)", dest)
		doPlannedMove(actor, "w" )	//update map	
		showCurrentRobotState()
		//delay( pauseTime.toLong() )
	} 
	suspend fun attemptTomoveAhead(actor:ActorBasic,stepTime:Int, dest:String ="onestep"){
 		//println("moveUtils attemptTomoveAhead stepTime=$stepTime")
		actor.forward("execOneStep", "execOneStep(${stepTime})", dest)
		
   	}
	
	fun updateMapAfterAheadOk(actor : ActorBasic ){
		doPlannedMove(actor  , "w")
	}
	suspend fun backToCompensate(actor : ActorBasic, stepTime : Int, pauseTime : Int = PauseTime){
		println("moveUtils backToCompensate stepTime=$stepTime")
		actor.forward("modelChangeAction", "modelChangeAction(robot,s)", "butlerresourcemodel")
		delay( stepTime.toLong() )
		actor.forward("modelChangeAction", "modelChangeAction(robot,h)", "butlerresourcemodel")
		delay( pauseTime.toLong() )
   	}

	suspend fun moveBackward(actor : ActorBasic, stepTime : Int){
		println("###move backward, stepTime : $stepTime")
		actor.forward("modelChangeAction", "modelChangeAction(robot,s)", "butlerresourcemodel")
		delay(stepTime.toLong())
		actor.forward("modelChangeAction", "modelChangeAction(robot,h)", "butlerresourcemodel")
	}
	
	var contatore = 0
	
	suspend fun testFunction(actor : ActorBasic){
		if(contatore < 3 ){
			actor.forward("notObstacle", "notObstacle", "roomexploration")
			Thread.sleep(750)
			contatore++
		}else{
			actor.forward("obstacle", "obstacle", "roomexploration")
			Thread.sleep(180)
			contatore = 0
		}
	}
	
	/*
 		esplora tutta la stanza senza trovare il tavolo
	 */
	suspend fun testFunction2(actor : ActorBasic){
		actor.forward("notObstacle", "notObstacle", "roomexploration")
		Thread.sleep(1000)
			
	}
	//funzione di test per trovare il tavolo direzione rightDir
	suspend fun testFunctionRightDir(actor : ActorBasic){
		if(contatore < 2){
			actor.forward("obstacle", "obstacle", "roomexploration")
			Thread.sleep(180)
			contatore ++	
		}else{
			actor.forward("notObstacle", "notObstacle", "roomexploration")
			Thread.sleep(750)
			contatore = 0
		}
	}
	
	//funzione di test per trovare il tavolo direzione leftDir
	suspend fun testFunction3(actor : ActorBasic){
		if(contatore < 3){
			actor.forward("notObstacle", "notObstacle", "roomexploration")
			Thread.sleep(750)
			contatore ++
		}else{
			actor.forward("obstacle", "obstacle", "roomexploration")
			Thread.sleep(180)
			contatore = 0
		}
		
	}
		
	suspend fun testFunction4(actor : ActorBasic){
			if(contatore < 1){
				actor.forward("notObstacle", "notObstacle", "roomexploration")
				Thread.sleep(750)
				
				contatore ++	
			}else{
				actor.forward("obstacle", "obstacle", "roomexploration")
				Thread.sleep(180)
				contatore = 0
			}
				
	}
	
	
	suspend fun execMove(actor:ActorBasic,move:String, stepTime : Int, pauseTime:Int=PauseTime){
		when( move ){
			"a" -> rotateLeft(actor, pauseTime)
			"d" -> rotateRight(actor, pauseTime)
			"w" -> moveAhead( actor, stepTime )
			else -> println("rotate $move unknown")
		}

  	}
	
	suspend fun moveAheadWithoutUpdate(actor:ActorBasic, stepTime:Int, pauseTime:Int = PauseTime, dest:String ="butlerresourcemodel"){
		println("moveUtils moveAhead stepTime=$stepTime")
		actor.forward("modelChangeAction", "modelChangeAction(robot,w)", dest)
		delay( stepTime.toLong() )
		actor.forward("modelChangeAction", "modelChangeAction(robot,h)", dest)	
		delay( pauseTime.toLong() )
	}
	
	fun addTable(actor : ActorBasic){
		for(i in 0..mapDims.first-2){
			println(i)
			for(j in 0..mapDims.second-2){
				println(j)
				if(RoomMap.getRoomMap().isObstacle(i,j)){
					actor.solve("addTable($i,$j)")
				}
			}
		}
		actor.solve("getNearTable")
	}
}
