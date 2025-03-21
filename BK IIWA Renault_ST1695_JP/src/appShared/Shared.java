package appShared;

import appTools.Garra;

import com.kuka.generated.ioAccess.InputsProfinetIOGroup;
import com.kuka.generated.ioAccess.MediaFlangeIOGroup;
import com.kuka.generated.ioAccess.OutputsProfinetIOGroup;
import com.kuka.roboticsAPI.applicationModel.IApplicationControl;
import com.kuka.roboticsAPI.applicationModel.IApplicationData;
import com.kuka.roboticsAPI.conditionModel.ObserverManager;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.task.ITaskLogger;

/**
 * Clase con las variables globales
 */
public class Shared
{
	/******************************************************************************
	 * Variables
	 *****************************************************************************/
	// Entradas/Salidas
	public static MediaFlangeIOGroup ioFlange;				// Entradas del flange
	public static InputsProfinetIOGroup iExtension;			// Entradas Profinet
	public static OutputsProfinetIOGroup oExtension;		// Salidas Profinet
	
	// Variables generales
	public static LBR robot = null;
	public static Controller controladora = null;
	public static Garra garra = null;						// Garra que se utilizará con el robot
	public static ITaskLogger appLogger = null;				// Para compartir el Logger desde la RobotApplication
	public static IApplicationControl appControl = null;	// Para compartir el ApplicationControl desde la RobotApplication
	public static IApplicationData appData = null;			// Para compartir el ApplicationData desde la RobotApplication
	public static ObserverManager appObserver = null;		// Para compartir el ObserverManager desde la RobotApplication
	
	// Variables de control
	public static boolean appRun = false;					// Para saber cuándo está en marcha la RobotApplication
	public static Frame posAct = null;						// Frame para controlar la posición del robot
	public static double sumTiempo = 0.0;					// Suma de los tiempos de ciclo del robot
	public static int numMotores = 0;						// Número de motores que han pasado para calcular el tiempo medio
	
	// Opciones parametrizables desde ProcessData
	public static int COD_MOT = 0;							// Código del motor
	public static int COD_CON = 0;							// Código del conector
	public static boolean ACT_DEBUG = false;				// Modo debug, para imprimir mensajes de la situación/posición del robot
	public static boolean ACT_REFERENCIADO = false;			// Activa o desactiva el referenciado del robot antes de iniciar secuencia
	
	// Se crean los framesa en Shared para poder crear movimientos desde cualquier clase y no solo
	// desde la clase RoboticsAPIApplication
	// Nombres de los frames
	public static final String WORLD = "/Base";
	public static final String KM0 = WORLD + "/KM0";
	public static final String HOME = KM0 + "/Home";
	public static final String ROBOTINHIBIDO = KM0 + "/RobotInhibido";
	public static final String REFERENCIAR = KM0 + "/Referenciar";
	public static final String TAPON = KM0 + "/Tapon";
	public static final String VACIO = KM0 + "/Vacio";
	public static final String NIVEL = KM0 + "/Nivel";
	
	// Frames
	public static ObjectFrame WORLDFRAME = null;
	public static ObjectFrame KM0FRAME = null;
	public static ObjectFrame HOMEFRAME = null;
	public static ObjectFrame ROBOTINHIBIDOFRAME = null;
	public static ObjectFrame REFERENCIARFRAME = null;
	public static ObjectFrame TAPONFRAME = null;
	public static ObjectFrame VACIOFRAME = null;
	public static ObjectFrame NIVELFRAME = null;
}
