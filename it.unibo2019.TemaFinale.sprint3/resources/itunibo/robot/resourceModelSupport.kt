package itunibo.robot

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch

object resourceModelSupport{

	//emit( msgId : String, msg : String)
	
	//aggiorno il modello quando viene eseguito uno specifico task
	fun updateModelTask(actor: ActorBasic, content: String, id : String="", q : String=""){
		println(content)
		actor.solve( "cmd(robot, task($content))" );
		actor.solve("currentTask(robot, task(TASK))");
		val currentTask = actor.getCurSol("TASK");
		println("###CurrentTask : $currentTask");
		if(content == "adding"){
			actor.solve("updateFood($id, $q)");
			actor.solve("food(ID, Q)");
			val foodID = actor.getCurSol("ID");
			val foodQ = actor.getCurSol("Q");
			println("###Id cibo da aggiungere $foodID, quantità $foodQ")
		}
		//emetto un evento per butlermind
		actor.scope.launch{
			actor.emit("modelChanged$content", "modelChanged$content(robot, $content)");
		}
	}
	
	//aggiorno il modello quando viene eseguita un'azione tra w,a,s,d,h
	fun updateModelAction(actor: ActorBasic, content: String){
		actor.solve("action(robot, move($content))");
		actor.solve("model(actuator, robot, state(S))");
		val currentAction = actor.getCurSol("S").toString();
		println("###CurrentAction : $currentAction");
		actor.solve("robotType(R)")
		println("RRRRR" +actor.getCurSol("R").toString())
		actor.scope.launch{
			actor.emit("modelChangedAction", "modelChangedAction(robot, $content)");
		}
		if(actor.getCurSol("R").toString() == "realnano"){
			if(content == "h"){
				itunibo.test.arduinoConnection.resetCont()
				itunibo.test.arduinoConnection.resetCont()
			}
		}
	}
	
	//aggiorno il modello quando viene modificata la current position
	fun updateModelPosition(actor: ActorBasic, x: String, y : String){
		actor.solve("updatePosition(robot, $x, $y)");
		actor.solve("currentPosition(robot, X, Y)");
		val currentPosition = actor.getCurSol("X");
		val yval = actor.getCurSol("Y")
		println("###CurrentPosition : $currentPosition, $yval")
		actor.scope.launch{
			actor.emit("modelChangedPosition","modelChangedPosition($currentPosition, $yval)")
		}
	}
	
	fun updateModel(actor: ActorBasic, action : String){
		actor.solve("action(robot, move($action))");
		actor.solve("model(actuator, robot, state(S))");
		val currentAction = actor.getCurSol("S");
		println("###CurrentAction : $currentAction");
		actor.solve("robotType(R)")
		if(actor.getCurSol("R").toString() == "realnano"){
			if(action == "h"){
				itunibo.test.arduinoConnection.resetCont()
				itunibo.test.arduinoConnection.resetCont()
			}
		}

	}
}

