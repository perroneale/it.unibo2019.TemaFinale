package itunibo.robot

import it.unibo.kactor.ActorBasic
import kotlinx.coroutines.launch

object resourceModelSupport{

	//emit( msgId : String, msg : String)
	
	//aggiorno il modello quando viene eseguito uno specifico task
	fun updateModelTask(actor: ActorBasic, content: String, id : String="", q : String=""){
		actor.solve( "cmd(robot, task($content)" );
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
			actor.emit("modelChanged$content", "modelChanged(robot, $content)");
		}
	}
	
	//aggiorno il modello quando viene eseguita un'azione tra w,a,s,d,h
	fun updateModelAction(actor: ActorBasic, content: String){
		actor.solve("action(robot, move($content))");
		actor.solve("model(actuator, robot, state(S))");
		val currentAction = actor.getCurSol("S");
		println("###CurrentAction : $currentAction");
		//emetto un evento per la mind
		actor.scope.launch{
			actor.emit("modelChangedAction", "modelChangedAction(robot, $content)");
		}
	}
	
	//aggiorno il modello quando viene modificata la current position
	fun updateModelPosition(actor: ActorBasic, content: String){
		actor.solve("updatePosition($content)");
		actor.solve("currentPosition(robot, position(X))");
		val currentPosition = actor.getCurSol("X");
		println("###CurrentPosition : $currentPosition")
		
	}
}

