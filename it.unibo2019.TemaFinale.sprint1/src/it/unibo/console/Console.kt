/* Generated by AN DISI Unibo */ 
package it.unibo.console

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Console ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						itunibo.console.guiSupport.create(myself ,"userCmd" )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t00",targetState="handleUserCmd",cond=whenDispatch("userCmd"))
				}	 
				state("handleUserCmd") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("userCmd(X)"), Term.createTerm("userCmd(CMD)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("modelChangeAction", "modelChangeAction(robot,${payloadArg(0)})" ,"butlerresourcemodel" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}