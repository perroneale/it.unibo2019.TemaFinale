/* Generated by AN DISI Unibo */ 
package it.unibo.butler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Butler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var Curmove ="";
			  var nextState =""; 
			  var table = 0;
			  var Position = ""; 
			  var PosTable = "";
			  var Task = "";
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('resourceModelButler.pl')","") //set resVar	
						println("Butler STARTED")
					}
					 transition( edgeName="goto",targetState="waitCmd1", cond=doswitch() )
				}	 
				state("waitCmd1") { //this:State
					action { //it:State
						solve("cmd(robot,task(waiting))","") //set resVar	
					}
					 transition(edgeName="t00",targetState="preparing",cond=whenDispatch("prepare"))
				}	 
				state("preparing") { //this:State
					action { //it:State
						println("---BUTLER in preparing")
						table = 0;
						solve("cmd(robot,task(preparing))","") //set resVar	
						forward("calculateRoute", "calculateRoute(pantry)" ,"butler" ) 
					}
					 transition(edgeName="t01",targetState="planningRoute",cond=whenDispatch("calculateRoute"))
				}	 
				state("planningRoute") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("calculateRoute(X)"), Term.createTerm("calculateRoute(GOAL)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("--BUTLER calculate route to ${payloadArg(0)}")
								forward("exec", "exec" ,"butler" ) 
						}
					}
					 transition(edgeName="t02",targetState="execRoute",cond=whenDispatch("exec"))
				}	 
				state("execRoute") { //this:State
					action { //it:State
						println("--- BUTLER EXEC ROUTE")
						solve("retract(move(M))","") //set resVar	
						if(currentSolution.isSuccess()) { Curmove = getCurSol("M").toString()
						println("Exec move $Curmove")
						 }
						else
						{ Curmove="nomove" 
						 }
						if((Curmove != "nomove")){ forward("nextMove", "nextMove" ,"butler" ) 
						 }
						else
						 { forward("check", "check" ,"butler" ) 
						  }
					}
					 transition(edgeName="t03",targetState="stopApplication",cond=whenDispatch("stop"))
					transition(edgeName="t04",targetState="execRoute",cond=whenDispatch("nextMove"))
					transition(edgeName="t05",targetState="checkPosition",cond=whenDispatch("check"))
				}	 
				state("checkPosition") { //this:State
					action { //it:State
						println("---BUTLER in checkPosition")
						solve("currentTask(robot,task(T))","") //set resVar	
						if(currentSolution.isSuccess()) { Task = getCurSol("T").toString()
						println("task = $Task")
						 }
						solve("currentPosition(robot,position(P))","") //set resVar	
						if(currentSolution.isSuccess()) { Position = getCurSol("P").toString()
						 }
						if((Position == "table")){ table++; PosTable = Position + table
						if((Task == "preparing")){ forward("nearPrepare", "nearPrepare($PosTable)" ,"butler" ) 
						 }
						if((Task == "adding")){ forward("nearClean", "nearClean($PosTable)" ,"butler" ) 
						 }
						if((Task == "cleaning")){ forward("nearAdd", "nearAdd($PosTable)" ,"butler" ) 
						 }
						 }
						else
						 { if((Position == "inRH")){ forward("inRH", "inRH" ,"butler" ) 
						  }
						 else
						  { if((Task == "preparing")){ forward("nearPrepare", "nearPrepare($Position)" ,"butler" ) 
						   }
						  if((Task == "adding")){ forward("nearClean", "nearClean($Position)" ,"butler" ) 
						   }
						  if((Task == "cleaning")){ forward("nearAdd", "nearAdd($Position)" ,"butler" ) 
						   }
						   }
						  }
					}
					 transition(edgeName="t06",targetState="execActionPrepare",cond=whenDispatch("nearPrepare"))
					transition(edgeName="t07",targetState="execActionAdd",cond=whenDispatch("nearAdd"))
					transition(edgeName="t08",targetState="execActionClean",cond=whenDispatch("nearClean"))
					transition(edgeName="t09",targetState="sendAck",cond=whenDispatch("inRH"))
				}	 
				state("execActionPrepare") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("nearPrepare(X)"), Term.createTerm("nearPrepare(pantry)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler takes dishes from pantry")
								forward("calculateRoute", "calculateRoute(table)" ,"butler" ) 
						}
						if( checkMsgContent( Term.createTerm("nearPrepare(X)"), Term.createTerm("nearPrepare(fridge)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler takes food from fridge")
								forward("calculateRoute", "calculateRoute(table)" ,"butler" ) 
						}
						if( checkMsgContent( Term.createTerm("nearPrepare(X)"), Term.createTerm("nearPrepare(table1)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler puts dishes on table")
								forward("calculateRoute", "calculateRoute(fridge)" ,"butler" ) 
						}
						if( checkMsgContent( Term.createTerm("nearPrepare(X)"), Term.createTerm("nearPrepare(table2)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler puts food on table")
								forward("calculateRoute", "calculateRoute(rh)" ,"butler" ) 
						}
					}
					 transition(edgeName="t010",targetState="planningRoute",cond=whenDispatch("calculateRoute"))
				}	 
				state("sendAck") { //this:State
					action { //it:State
						println("Butler finished $Task task")
						forward("completedTask", "completedTask($Task)" ,"maitre" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd1", cond=doswitchGuarded({(Task == "cleaning")}) )
					transition( edgeName="goto",targetState="waitCmd2", cond=doswitchGuarded({! (Task == "cleaning")}) )
				}	 
				state("stopApplication") { //this:State
					action { //it:State
						println("Butler stopping")
					}
					 transition(edgeName="t011",targetState="execRoute",cond=whenDispatch("reactivate"))
				}	 
				state("waitCmd2") { //this:State
					action { //it:State
						solve("cmd(robot,task(waiting))","") //set resVar	
					}
					 transition(edgeName="t012",targetState="adding",cond=whenDispatch("addFood"))
					transition(edgeName="t013",targetState="cleaning",cond=whenDispatch("clean"))
				}	 
				state("cleaning") { //this:State
					action { //it:State
						table = 0;
						solve("cmd(robot,task(cleaning))","") //set resVar	
						forward("calculateRoute", "calculateRoute(table)" ,"butler" ) 
					}
					 transition( edgeName="goto",targetState="planningRoute", cond=doswitch() )
				}	 
				state("execActionClean") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("nearClean(X)"), Term.createTerm("nearClean(dishwasher)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler puts dishes into dishwasher")
								forward("calculateRoute", "calculateRoute(table)" ,"butler" ) 
						}
						if( checkMsgContent( Term.createTerm("nearClean(X)"), Term.createTerm("nearClean(fridge)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler puts food into fridge")
								forward("calculateRoute", "calculateRoute(table)" ,"butler" ) 
						}
						if( checkMsgContent( Term.createTerm("nearClean(X)"), Term.createTerm("nearClean(table1)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler takes dishes from table")
								forward("calculateRoute", "calculateRoute(fridge)" ,"butler" ) 
						}
						if( checkMsgContent( Term.createTerm("nearClean(X)"), Term.createTerm("nearClean(table2)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler takes food from table")
								forward("calculateRoute", "calculateRoute(rh)" ,"butler" ) 
						}
					}
					 transition(edgeName="t014",targetState="planningRoute",cond=whenDispatch("calculateRoute"))
				}	 
				state("adding") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("addFood(C,Q)"), Term.createTerm("addFood(C,Q)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								table = 0;
								solve("cmd(robot,task(cleaning))","") //set resVar	
								forward("foodAvailability", "foodAvailability(${payloadArg(0)},${payloadArg(1)})" ,"fridge" ) 
						}
					}
					 transition(edgeName="t015",targetState="nextStep",cond=whenDispatch("positiveResponse"))
					transition(edgeName="t016",targetState="sendWarning",cond=whenDispatch("negativeResponse"))
				}	 
				state("nextStep") { //this:State
					action { //it:State
						forward("calculateRoute", "calculateRoute(fridge)" ,"butler" ) 
					}
					 transition( edgeName="goto",targetState="planningRoute", cond=doswitch() )
				}	 
				state("sendWarning") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("negativeResponse(C,Q,AQ)"), Term.createTerm("negativeResponse(C,Q,AQ)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("warning", "warning(${payloadArg(0)},${payloadArg(1)},${payloadArg(2)})" ,"maitre" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd2", cond=doswitch() )
				}	 
				state("execActionAdd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("nearAdd(X)"), Term.createTerm("nearAdd(fridge)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler takes food from fridge")
								forward("calculateRoute", "calculateRoute(table)" ,"butler" ) 
						}
						if( checkMsgContent( Term.createTerm("nearAdd(X)"), Term.createTerm("nearAdd(table1)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Butler puts food on table")
								forward("calculateRoute", "calculateRoute(rh)" ,"butler" ) 
						}
					}
					 transition(edgeName="t017",targetState="planningRoute",cond=whenDispatch("calculateRoute"))
				}	 
			}
		}
}