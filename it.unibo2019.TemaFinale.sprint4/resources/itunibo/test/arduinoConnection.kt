package itunibo.test

import itunibo.robot.rotatoryEncoder
import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch

object arduinoConnection{
	lateinit var conn : SerialPortConnSupport
	
	fun connect(actor : ActorBasicFsm){
		val serialConn = JSSCSerialComm()
	    conn = serialConn.connect("/dev/ttyACM0")
		println("arduino connected to conn= $conn")
		resetCont()
		rotatoryEncoder("rotatory", actor, conn)
	}
	
	fun resetCont(){
		conn.sendALine("reset")
	}
}

