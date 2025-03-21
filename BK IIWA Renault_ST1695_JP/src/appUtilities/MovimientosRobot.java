package appUtilities;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import static appUtilities.Utils.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import appShared.Shared;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.conditionModel.BooleanIOCondition;
import com.kuka.roboticsAPI.conditionModel.ForceCondition;
import com.kuka.roboticsAPI.conditionModel.ICondition;
import com.kuka.roboticsAPI.geometricModel.CartDOF;
import com.kuka.roboticsAPI.geometricModel.CartPlane;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.math.CoordinateAxis;
import com.kuka.roboticsAPI.geometricModel.math.XyzAbcTransformation;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.PositionHold;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianImpedanceControlMode;
import com.kuka.roboticsAPI.motionModel.controlModeModel.CartesianSineImpedanceControlMode;

/**
 * Clase con los movimientos que puede realizar el robot.
 */
public class MovimientosRobot
{
	/******************************************************************************
	 * Variables
	 *****************************************************************************/
	private static Calendar horaIni, horaFin;							// Para indicar el tiempo de ciclo
	private static boolean cicloIniciado = false;						// Marca para controlar el inicio de ciclo
	private static CartesianImpedanceControlMode impeReposicionar;		// Valores de impedancia para los movimientos de reposicionado
	private static CartesianImpedanceControlMode impeTaponQuitar;		// Valores de impedancia para los movimientos de quitar tap�n
	private static CartesianImpedanceControlMode impeMedirVacio;		// Valores de impedancia para los movimientos de medir vac�o
	private static CartesianImpedanceControlMode impeIrNivel;			// Valores de impedancia para los movimientos de ir a nivel
	private static CartesianImpedanceControlMode impeMedirNivel;		// Valores de impedancia para mantener en medir nivel
	private static CartesianSineImpedanceControlMode impeLissajous;		// ValoresValores de impedancia para los movimientos que utilicen Lissajous
	private static IMotionContainer mantenerImpe;						// Para mantener la impedancia cuando el robot est� parado/esperando
	
	private static boolean irNivel = false;							// Marca para guardar la orden de realizar el test de nivel
	private static boolean irHome = false;								// Marca para guardar la orden de ir a Home
	
	
	/******************************************************************************
	 * Funciones
	 *****************************************************************************/
	/**
	 * Mueve el robot a la posici�n RobotInhibido y activa la salida para indicarlo.
	 */
	public static void movRobotInhibido()
	{
		debug("Moviendo a RobotInhibido");
		
		Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome")).setJointVelocityRel(0.5));
		debugUltimoPunto("RobotInhibido/In1");
		Shared.robot.move(ptp(Shared.appData.getFrame(Shared.ROBOTINHIBIDO)).setJointVelocityRel(0.5));
		debugUltimoPunto("RobotInhibido");
	}
	
	/**
	 * Mueve el robot a la posici�n Home.
	 */
	public static void movHome()
	{
		debug("Moviendo a Home");
		
		Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME)).setJointVelocityRel(1.0));
		debugUltimoPunto("Home");
	}
	
	/**
	 * Comprueba si desde la posici�n actual del robot es posible realizar un movimiento
	 * a Home de manera segura.
	 * @return True si es posible, False en caso contrario
	 */
	public static boolean checkHomeSeguro()
	{
		/*
		 * Las coordenadas de los puntos seguros se comprueban seg�n el mundo del robot
		 * SIN herramienta
		 * 
		 * Home/InterHome:			358,	-312,	540
		 * Home:					430,	-281,	436
		 * 
		 * Tapon/Quitar/In1:		409,	-289,	421
		 * Tapon/Quitar/In2:		502,	-289,	395
		 * Tapon/Quitar:			502,	-284,	394
		 * Tapon/Quitar/Out1:		502,	-306,	399
		 * 
		 * Vacio/In1:				432,	-333,	297
		 * Vacio/In2:				488,	-337,	284
		 * Vacio:					488,	-310,	277
		 * 
		 * Tapon/Poner/In1:			502,	-305,	400
		 * Tapon/Poner:				502,	-283,	396
		 * Tapon/Poner/Colocar1:	502,	-286,	397
		 * Tapon/Poner/Out1:		387,	-273,	463
		 * 
		 * Nivel/In1:				519,	197,	463
		 * Nivel/In2:				783,	12,		208
		 * Nivel:					791,	-4,		189
		 * 
		 * RobotInhibido:			-403,	0,		923
		 * Referenciar:				0,		0,		1332
		 */

		if (// Zona Tap�n
			(Shared.posAct.getX()>497 && Shared.posAct.getX()<508
			&& Shared.posAct.getY()>-294 && Shared.posAct.getY()<-280
			&& Shared.posAct.getZ()>392 && Shared.posAct.getZ()<402)
			||
			(Shared.posAct.getX()>497 && Shared.posAct.getX()<508
			&& Shared.posAct.getY()>-312 && Shared.posAct.getY()<-295
			&& Shared.posAct.getZ()>392 && Shared.posAct.getZ()<402)
			||
			(Shared.posAct.getX()>381 && Shared.posAct.getX()<498
			&& Shared.posAct.getY()>-751 && Shared.posAct.getY()<-195
			&& Shared.posAct.getZ()>385 && Shared.posAct.getZ()<474)
			// Zona Nivel
			||
			(Shared.posAct.getX()>782 && Shared.posAct.getX()<802
			&& Shared.posAct.getY()>-23 && Shared.posAct.getY()<7
			&& Shared.posAct.getZ()>178 && Shared.posAct.getZ()<197)
			||
			(Shared.posAct.getX()>540 && Shared.posAct.getX()<792
			&& Shared.posAct.getY()>-63 && Shared.posAct.getY()<217
			&& Shared.posAct.getZ()>197 && Shared.posAct.getZ()<532)
			||
			(Shared.posAct.getX()>470 && Shared.posAct.getX()<541
			&& Shared.posAct.getY()>-207 && Shared.posAct.getY()<213
			&& Shared.posAct.getZ()>401 && Shared.posAct.getZ()<490)
			// Zona Vac�o
			||
			(Shared.posAct.getX()>481 && Shared.posAct.getX()<492
			&& Shared.posAct.getY()>-317 && Shared.posAct.getY()<-307
			&& Shared.posAct.getZ()>267 && Shared.posAct.getZ()<288)
			||
			(Shared.posAct.getX()>481 && Shared.posAct.getX()<492
			&& Shared.posAct.getY()>-346 && Shared.posAct.getY()<-318
			&& Shared.posAct.getZ()>267 && Shared.posAct.getZ()<288)
			||
			(Shared.posAct.getX()>417 && Shared.posAct.getX()<482
			&& Shared.posAct.getY()>-359 && Shared.posAct.getY()<-309
			&& Shared.posAct.getZ()>237 && Shared.posAct.getZ()<350)
			// Zona Inhibido/Referenciar
			||
			(Shared.posAct.getX()>-472 && Shared.posAct.getX()<-371
			&& Shared.posAct.getY()>-160 && Shared.posAct.getY()<100
			&& Shared.posAct.getZ()>500 && Shared.posAct.getZ()<1340)
			||
			(Shared.posAct.getX()>-370 && Shared.posAct.getX()<400
			&& Shared.posAct.getY()>-160 && Shared.posAct.getY()<100
			&& Shared.posAct.getZ()>500 && Shared.posAct.getZ()<1340)
			||
			(Shared.posAct.getX()>399 && Shared.posAct.getX()<650
			&& Shared.posAct.getY()>-280 && Shared.posAct.getY()<100
			&& Shared.posAct.getZ()>600 && Shared.posAct.getZ()<1340)
			// Zona Intermedio Home
			||
			(Shared.posAct.getX()>-100 && Shared.posAct.getX()<650
			&& Shared.posAct.getY()>-700 && Shared.posAct.getY()<-281
			&& Shared.posAct.getZ()>340 && Shared.posAct.getZ()<1340)
			)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Funci�n para ir a Home de forma segura desde cualquier punto conocido, para poder recuperar su posici�n
	 * en caso de quedarse a mitad de una secuencia. Las coordenadas de los puntos se comprueban con un margen
	 * de +-10 mm, por si el robot no se qued� en su posici�n exacta.
	 */
	public static void movHomeSeguro()
	{
		debug("Moviendo seguro a Home");

		// Inicializa los valores para el movimiento de impedancia para quitar el tap�n
		impeReposicionar = new CartesianImpedanceControlMode();
		impeReposicionar.parametrize(CartDOF.X).setStiffness(2000);
		impeReposicionar.parametrize(CartDOF.Y).setStiffness(2000);
		impeReposicionar.parametrize(CartDOF.Z).setStiffness(2000);
		impeReposicionar.parametrize(CartDOF.A).setStiffness(250);
		impeReposicionar.parametrize(CartDOF.B).setStiffness(250);
		impeReposicionar.parametrize(CartDOF.C).setStiffness(250);
		impeReposicionar.parametrize(CartDOF.ALL).setDamping(0.9);
		
		
		// Mientras el robot no est� en Home, realiza la secuencia de movimientos
		while (!isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0))
		{
			Shared.posAct = pos_act();

			// Se desactivan las electrov�lvulas del vac�o
			Shared.ioFlange.setActivarVacio(false);
			
						
			// Zona Tap�n
			if (Shared.posAct.getX()>497 && Shared.posAct.getX()<508
				&& Shared.posAct.getY()>-294 && Shared.posAct.getY()<-280
				&& Shared.posAct.getZ()>392 && Shared.posAct.getZ()<402)
			{
				debug("MovHomeSeguro: Tapon");
				Shared.garra.getFrame("/Tapon").move(ptp(Shared.appData.getFrame(Shared.TAPON + "/Quitar/Out1"))
									.setJointVelocityRel(0.4).setMode(impeReposicionar));
				debugUltimoPunto("TaponSeguro/Out1");
			}
			if (Shared.posAct.getX()>497 && Shared.posAct.getX()<508
				&& Shared.posAct.getY()>-312 && Shared.posAct.getY()<-295
				&& Shared.posAct.getZ()>392 && Shared.posAct.getZ()<402)
			{
				debug("MovHomeSeguro: Tapon/In1");
				Shared.garra.getFrame("/Tapon").move(ptp(Shared.appData.getFrame(Shared.TAPON + "/Quitar/In1"))
									.setJointVelocityRel(0.4));
				debugUltimoPunto("TaponSeguro/In1");
			}
			
			// Zona Nivel
			if (Shared.posAct.getX()>782 && Shared.posAct.getX()<802
				&& Shared.posAct.getY()>-23 && Shared.posAct.getY()<7
				&& Shared.posAct.getZ()>178 && Shared.posAct.getZ()<197)
			{
				debug("MovHomeSeguro: Nivel");
				// El movimiento es relativo por si se encuentra dentro de la sonda de nivel y se ha modificado
				// el punto final debido a los giros de la b�squeda
				Shared.garra.getFrame("/Nivel").moveAsync(linRel(0, 0, 20, 0, 0, 0, Shared.NIVELFRAME)
									.setCartVelocity(100.0).setBlendingCart(20).setMode(impeIrNivel));
				Shared.garra.getFrame("/Nivel").move(ptp(Shared.appData.getFrame(Shared.NIVEL + "/In2"))
									.setJointVelocityRel(0.4).setMode(impeReposicionar));
				debugUltimoPunto("NivelSeguro/In2");
			}
			if (Shared.posAct.getX()>540 && Shared.posAct.getX()<792
				&& Shared.posAct.getY()>-63 && Shared.posAct.getY()<217
				&& Shared.posAct.getZ()>197 && Shared.posAct.getZ()<532)
			{
				debug("MovHomeSeguro: Nivel/In1");
				Shared.garra.getFrame("/Nivel").move(ptp(Shared.appData.getFrame(Shared.NIVEL + "/In1"))
									.setJointVelocityRel(0.4));
				debugUltimoPunto("NivelSeguro/In1");
			}
			
			// Zona Vac�o
			if (Shared.posAct.getX()>481 && Shared.posAct.getX()<492
				&& Shared.posAct.getY()>-317 && Shared.posAct.getY()<-307
				&& Shared.posAct.getZ()>267 && Shared.posAct.getZ()<288)
			{
				debug("MovHomeSeguro: Vacio");
				Shared.garra.getFrame("/Vacio").move(ptp(Shared.appData.getFrame(Shared.VACIO + "/In2"))
									.setJointVelocityRel(0.4).setMode(impeReposicionar));
				debugUltimoPunto("VacioSeguro/In2");
			}
			if (Shared.posAct.getX()>481 && Shared.posAct.getX()<492
				&& Shared.posAct.getY()>-346 && Shared.posAct.getY()<-318
				&& Shared.posAct.getZ()>267 && Shared.posAct.getZ()<288)
			{
				debug("MovHomeSeguro: Vacio/In1");
				Shared.garra.getFrame("/Vacio").move(ptp(Shared.appData.getFrame(Shared.VACIO + "/In1"))
									.setJointVelocityRel(0.4));
				debugUltimoPunto("VacioSeguro/In1");
			}
			
			// Zona Inhibido/Referenciar
			if (Shared.posAct.getX()>-472 && Shared.posAct.getX()<-371
				&& Shared.posAct.getY()>-160 && Shared.posAct.getY()<100
				&& Shared.posAct.getZ()>500 && Shared.posAct.getZ()<1340)
			{
				debug("MovHomeSeguro: InterHome");
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome"))
									.setJointVelocityRel(0.4));
				debugUltimoPunto("RobotInhibidoSeguro");
			}
			if (Shared.posAct.getX()>-370 && Shared.posAct.getX()<400
				&& Shared.posAct.getY()>-160 && Shared.posAct.getY()<100
				&& Shared.posAct.getZ()>500 && Shared.posAct.getZ()<1340)
			{
				debug("MovHomeSeguro: InterHome");
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome"))
									.setJointVelocityRel(0.4));
				debugUltimoPunto("ReferenciarSeguro");
			}
			if (Shared.posAct.getX()>399 && Shared.posAct.getX()<650
				&& Shared.posAct.getY()>-280 && Shared.posAct.getY()<100
				&& Shared.posAct.getZ()>600 && Shared.posAct.getZ()<1340)
			{
				debug("MovHomeSeguro: InterHome");
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome"))
									.setJointVelocityRel(0.4));
				debugUltimoPunto("Referenciar2Seguro");
			}
			
			
			// Ir a Home
			if (// Zona Tap�n
				(Shared.posAct.getX()>381 && Shared.posAct.getX()<498
				&& Shared.posAct.getY()>-751 && Shared.posAct.getY()<-195
				&& Shared.posAct.getZ()>385 && Shared.posAct.getZ()<474)
				// Zona Nivel
				||
				(Shared.posAct.getX()>470 && Shared.posAct.getX()<541
				&& Shared.posAct.getY()>-207 && Shared.posAct.getY()<213
				&& Shared.posAct.getZ()>401 && Shared.posAct.getZ()<490)
				// Zona Vac�o
				||
				(Shared.posAct.getX()>417 && Shared.posAct.getX()<482
				&& Shared.posAct.getY()>-359 && Shared.posAct.getY()<-309
				&& Shared.posAct.getZ()>237 && Shared.posAct.getZ()<350)
				// Zona Intermedio Home
				||
				(Shared.posAct.getX()>-100 && Shared.posAct.getX()<650
				&& Shared.posAct.getY()>-700 && Shared.posAct.getY()<-281
				&& Shared.posAct.getZ()>340 && Shared.posAct.getZ()<1340))
			{
				debug("MovHomeSeguro: Home");
				movHome();
			}
		}
	}
	
	/**
	 * Inicia la secuencia para mover el robot.
	 */
	public static void movSecuencia()
	{
		try
		{
			// Se resetean los valores de las marcas para la secuencia
			irNivel = false;
			irHome = false;
			resetearSalidas();
			
			// Condici�n para iniciar el ciclo
			// - Si el ciclo es completo o medir vac�o no est� inhibido, hace el proceso de medir vac�o
			// - Si medir vac�o est� inhibido, se lo salta y comprueba la condici�n de medir nivel
			// - Si se hace petici�n de origen de m�quina, se inhibe el robot o no est� la l�nea en autom�tico,
			// se salta el proceso
			ICondition ciclo1 = new BooleanIOCondition(Shared.iExtension.getInput("QuitarTapon"), true)
				.or(new BooleanIOCondition(Shared.iExtension.getInput("MedirNivel"), true))
				.or(new BooleanIOCondition(Shared.iExtension.getInput("Home"), true))
				.or(new BooleanIOCondition(Shared.iExtension.getInput("RobotInhibido"), true))
				.or(new BooleanIOCondition(Shared.iExtension.getInput("AutomaticoLinea"), false));
			
			waitFor(ciclo1, "Esperando Inicio de Ciclo...", 5);
			// Si no est� inhibido medir vac�o, no hay petici�n de origen de m�quina, no se inhibe
			// el robot y est� la l�nea en autom�tico, se hace el proceso de medir vac�o
			if (Shared.iExtension.getQuitarTapon() && !Shared.iExtension.getHome()
					&& !Shared.iExtension.getRobotInhibido() && Shared.iExtension.getAutomaticoLinea())
			{
				// Se calcula la hora de inicio de la secuencia
				cicloIniciado = true;
				horaIni = GregorianCalendar.getInstance();
				
				taponQuitar();
				
				// Se comprueba que se siguen cumpliendo las condiciones de ejecuci�n
				if (!Shared.iExtension.getRobotInhibido() && Shared.iExtension.getAutomaticoLinea())
				{
					medirVacio();
				}
				
				// Se comprueba que se siguen cumpliendo las condiciones de ejecuci�n
				if (!Shared.iExtension.getRobotInhibido() && Shared.iExtension.getAutomaticoLinea())
				{
					taponPoner();
				}
			}
			
			
			// Condici�n para iniciar la medici�n de nivel
			// - Si medir nivel no est� inhibido, hace el proceso de medir nivel
			// - Si medir nivel est� inhibido, se lo salta y se mueve a Home
			// - Si se hace petici�n de origen de m�quina, se inhibe el robot o no est� la l�nea en autom�tico,
			// se salta el proceso
			ICondition ciclo2 = new BooleanIOCondition(Shared.iExtension.getInput("MedirNivel"), true)
				.or(new BooleanIOCondition(Shared.iExtension.getInput("Home"), true))
				.or(new BooleanIOCondition(Shared.iExtension.getInput("RobotInhibido"), true))
				.or(new BooleanIOCondition(Shared.iExtension.getInput("AutomaticoLinea"), false));
			
			// Si no est� activa ninguna marca del ciclo, comprueba las condiciones para realizar el proceso
			if (!irNivel && !irHome)
			{
				waitFor(ciclo2, "Esperando Permiso Medir Nivel...", 5);
			}
			// Si no est� inhibido medir nivel, no hay petici�n de origen de m�quina, no se inhibe
			// el robot y est� la l�nea en autom�tico, se hace el proceso de medir nivel
			if ((Shared.iExtension.getMedirNivel() || irNivel) && !Shared.iExtension.getHome()
				&& !irHome && !Shared.iExtension.getRobotInhibido() && Shared.iExtension.getAutomaticoLinea())
			{
				// Si no se ha iniciado antes el ciclo, se calcula la hora de inicio
				if (!cicloIniciado)
				{
					cicloIniciado = true;
					horaIni = GregorianCalendar.getInstance();
				}
				
				medirNivel();
			}
			
			
			// Condici�n para ir a posici�n Home
			// - Si termina un ciclo o se hace petici�n de origen de m�quina va a Home
			// - Si se inhibe el robot o no est� la l�nea en autom�tico, sale de la secuencia
			ICondition ciclo3 = new BooleanIOCondition(Shared.iExtension.getInput("Home"), true)
				.or(new BooleanIOCondition(Shared.iExtension.getInput("RobotInhibido"), true))
				.or(new BooleanIOCondition(Shared.iExtension.getInput("AutomaticoLinea"), false));
			
			// Si no est� ya en Home y no est� activa la marca de ciclo, comprueba las condiciones para
			// terminar el proceso
			if (!Shared.oExtension.getPosHome() && !irHome)
			{
				waitFor(ciclo3, "Esperando Fin Ciclo...", 5);
			}
			// Si no se inhibe el robot y est� la l�nea en autom�tico, va a Home
			if ((Shared.iExtension.getHome() || irHome) && !Shared.iExtension.getRobotInhibido()
					&& Shared.iExtension.getAutomaticoLinea())
			{	
				if (Shared.iExtension.getAutomaticoLinea())
				{
					movHome();
				}
				else
				{
					movHomeSeguro();
				}
			}
			
			
			// Si se ha iniciado un ciclo se calcula el tiempo y se muestra en el ProcessData
			if (cicloIniciado)
			{
				cicloIniciado = false;
				horaFin = GregorianCalendar.getInstance();
				Shared.numMotores++;
				Shared.sumTiempo += (horaFin.getTimeInMillis() - horaIni.getTimeInMillis());
				
				Shared.appData.getProcessData("tiempoCiclo").setValue((horaFin.getTimeInMillis() - horaIni.getTimeInMillis()) / 1000.0);
				Shared.appData.getProcessData("tiempoCicloAvg").setValue((Shared.sumTiempo/Shared.numMotores) / 1000.0);
			}
		}
		catch(Exception e)
		{
			// Comentado para evitar que el mensaje sobrecarge el logger al cerrar aplicaci�n
			//Shared.appLogger.error("No se pudo completar la secuencia.\n" + e);
		}
	}
	
	/**
	 * Movimientos para quitar el tap�n del motor.
	 */
	public static void taponQuitar()
	{
		// Inicializa los valores para el movimiento de impedancia para quitar el tap�n
		impeTaponQuitar = new CartesianImpedanceControlMode();
		impeTaponQuitar.parametrize(CartDOF.X).setStiffness(4000);
		impeTaponQuitar.parametrize(CartDOF.Y).setStiffness(4000);
		impeTaponQuitar.parametrize(CartDOF.Z).setStiffness(4000);
		impeTaponQuitar.parametrize(CartDOF.A).setStiffness(300);
		impeTaponQuitar.parametrize(CartDOF.B).setStiffness(300);
		impeTaponQuitar.parametrize(CartDOF.C).setStiffness(300);
		impeTaponQuitar.parametrize(CartDOF.ALL).setDamping(0.8);
		
		// Condici�n para continuar el ciclo
		// - Si se recibe MedirVac�o, continua el ciclo
		// - Si se recibe Home, anula el ciclo y va a origen
		ICondition continuar = new BooleanIOCondition(Shared.iExtension.getInput("MedirVacio"), true)
								.or(new BooleanIOCondition(Shared.iExtension.getInput("Home"), true));
		
		
		debug("Moviendo a Tapon/Quitar");
		
		// 1. Aproximaci�n
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Quitar/In1");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").move(ptp(Shared.appData.getFrame(Shared.TAPON + "/Quitar/In1"))
								.setJointVelocityRel(1.0));
		debugUltimoPunto("Tapon/Quitar/In1");
		
		// 2. B�squeda segura
		waitFor(continuar, "Esperando Permiso Medir Vac�o...", 5);
		while (!Shared.garra.isPinzaAbierta())
		{
			Shared.garra.abrirPinza();
			ThreadUtil.milliSleep(10);
		}
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Quitar/In2");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").moveAsync(ptp(Shared.appData.getFrame(Shared.TAPON + "/Quitar/In2"))
							.setJointVelocityRel(1.0).setBlendingCart(10));
		debugUltimoPunto("Tapon/Quitar/In2");
						
		// 3. Acci�n
		debug("Quitando Tap�n");
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Quitar");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").move(lin(Shared.appData.getFrame(Shared.TAPON + "/Quitar"))
							.setCartVelocity(100.0).setMode(impeTaponQuitar));
		debugUltimoPunto("Tapon/Quitar");
		while (Shared.garra.isPinzaAbierta())
		{
			Shared.garra.cerrarPinza();
			ThreadUtil.milliSleep(10);
		}
		ThreadUtil.milliSleep(100);	// Parada para asegurar que cierra la pinza antes de moverse
		debug("Terminado quitar Tap�n");
		
		// 4. Retirada
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Quitar/Out1");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").moveAsync(ptp(Shared.appData.getFrame(Shared.TAPON + "/Quitar/Out1"))
								.setJointVelocityRel(1.0)/*.setMode(impeTaponQuitar)*/);
		debugUltimoPunto("Tapon/Quitar/Out1");
		debug("Saliendo de Tapon/Quitar");
	}
	
	/**
	 * Movimientos para medir el vac�o del motor.
	 */
	public static void medirVacio()
	{
		// Inicializa los valores para el movimiento de impedancia para medir el vac�o
		impeMedirVacio = new CartesianImpedanceControlMode();
		impeMedirVacio.parametrize(CartDOF.X).setStiffness(2000);
		impeMedirVacio.parametrize(CartDOF.Y).setStiffness(2000);
		impeMedirVacio.parametrize(CartDOF.Z).setStiffness(1000);
		impeMedirVacio.parametrize(CartDOF.A).setStiffness(250);
		impeMedirVacio.parametrize(CartDOF.B).setStiffness(250);
		impeMedirVacio.parametrize(CartDOF.C).setStiffness(250);
		impeMedirVacio.parametrize(CartDOF.ALL).setDamping(0.6);
		impeMedirVacio.setSpringPosition(Shared.garra.getFrame("/GarraMotor/Vacio"));
		
		// Inicializa los valores para el movimiento de impedancia para entrar y salir de la posici�n de medir vac�o 
		impeLissajous = CartesianSineImpedanceControlMode.createLissajousPattern(CartPlane.XY, 15, 0.8, 300);
		impeLissajous.parametrize(CartDOF.X).setStiffness(2800);
		impeLissajous.parametrize(CartDOF.Y).setStiffness(2800);
		impeLissajous.parametrize(CartDOF.Z).setStiffness(3600);
		impeLissajous.parametrize(CartDOF.A).setStiffness(200);
		impeLissajous.parametrize(CartDOF.B).setStiffness(200);
		impeLissajous.parametrize(CartDOF.C).setStiffness(200);
		impeLissajous.parametrize(CartDOF.ALL).setDamping(0.9);
		impeLissajous.setSpringPosition(Shared.garra.getFrame("/GarraMotor/Vacio"));
		
		// Condici�n para continuar el ciclo
		// - Si no est� inhibido medir nivel, espera el permiso para hacer el proceso
		// - Si est� inhibido, espera el permiso para ir a Home
		ICondition continuar = new BooleanIOCondition(Shared.iExtension.getInput("MedirNivel"), true)
								.or(new BooleanIOCondition(Shared.iExtension.getInput("Home"), true));
		
		
		debug("Moviendo a MedirVacio");
		
		// 1. Aproximaci�n
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Vacio/In1 (entrar)");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Vacio").moveAsync(ptp(Shared.appData.getFrame(Shared.VACIO + "/In1"))
								.setJointVelocityRel(1.0).setBlendingCart(10));
		debugUltimoPunto("Vacio/In1 (entrar)");
		
		// 2. B�squeda segura
		while (Shared.garra.isActVacio())
		{
			Shared.garra.desVacio();
			ThreadUtil.milliSleep(10);
		}
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Vacio/In2 (entrar)");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Vacio").move(ptp(Shared.appData.getFrame(Shared.VACIO + "/In2"))
								.setJointVelocityRel(1.0));
		ThreadUtil.milliSleep(50);	// Parada para que el robot no golpee el motor en el cambio de un movimiento sin impedancia a uno con impedancia
		debugUltimoPunto("Vacio/In2 (entrar)");
			
		// 3. Acci�n
		debug("Midiendo Vac�o");
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Vacio");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Vacio").move(lin(Shared.appData.getFrame(Shared.VACIO))
							.setCartVelocity(100.0).setMode(impeLissajous));
		debugUltimoPunto("Vacio");
		mantenerImpe = Shared.robot.moveAsync((new PositionHold(impeMedirVacio, 10, TimeUnit.SECONDS)));
		ThreadUtil.milliSleep(50);
		while (!Shared.garra.isActVacio())
		{
			Shared.garra.actVacio();
			ThreadUtil.milliSleep(10);
		}
		waitFor(continuar, "Esperando fin medir vac�o...", 5);
		// Seg�n la se�al que se reciba, se activa la marca para el siguiente proceso
		if (Shared.iExtension.getHome())
		{
			irHome = true;
			irNivel = false;
		}
		else if (Shared.iExtension.getMedirNivel())
		{
			irHome = false;
			irNivel = true;
		}
		while (Shared.garra.isActVacio())
		{
			Shared.garra.desVacio();
			ThreadUtil.milliSleep(10);
		}
		ThreadUtil.milliSleep(10);	// Esta parada es necesaria para evitar un fallo del robot por posici�n
		mantenerImpe.cancel();
		// Se mueve el robot a su posici�n actual para poder moverse despu�s de haber estado parado en impedancia
		Shared.robot.move(ptp(Shared.robot.getCurrentJointPosition()).setJointVelocityRel(0.1));
		debug("Terminado medir Vac�o");

		// 4. Retirada
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Vacio/In2 (salir)");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		// Para asegurar que el robot sale bien de la posici�n de vac�o
		while (!isInFrame(Shared.garra.getFrame("/Vacio"), Shared.VACIO + "/In2", 5.0))
		{
			Shared.garra.getFrame("/Vacio").move(lin(Shared.appData.getFrame(Shared.VACIO + "/In2"))
									.setCartVelocity(100.0).setMode(impeLissajous));
			ThreadUtil.milliSleep(200);
		}
		ThreadUtil.milliSleep(100);	// Parada para que el robot no golpee el motor en el cambio de un movimiento con impedancia a uno sin impedancia
		debugUltimoPunto("Vacio/In2 (salir)");
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Vacio/In1 (salir)");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Vacio").moveAsync(ptp(Shared.appData.getFrame(Shared.VACIO + "/In1"))
								.setJointVelocityRel(1.0).setBlendingCart(15));
		debugUltimoPunto("Vacio/In1 (salir)");
		debug("Saliendo de MedirVacio");
	}
	
	/**
	 * Movimientos para quitar el tap�n del motor.
	 */
	public static void taponPoner()
	{
		// Inicializa los valores para el movimiento de impedancia para quitar el tap�n
		impeLissajous = CartesianSineImpedanceControlMode.createLissajousPattern(CartPlane.XY, 15, 1.8, 300);
		impeLissajous.parametrize(CartDOF.X).setStiffness(3000);
		impeLissajous.parametrize(CartDOF.Y).setStiffness(3000);
		impeLissajous.parametrize(CartDOF.Z).setStiffness(5000);
		impeLissajous.parametrize(CartDOF.A).setStiffness(300);
		impeLissajous.parametrize(CartDOF.B).setStiffness(300);
		impeLissajous.parametrize(CartDOF.C).setStiffness(200);
		impeLissajous.parametrize(CartDOF.ALL).setDamping(1.0);
		impeLissajous.setSpringPosition(Shared.garra.getFrame("/GarraMotor/Tapon"));
		
		
		debug("Moviendo a Tapon/Poner");
		
		// 1. Aproximaci�n
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Poner/In1");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").moveAsync(ptp(Shared.appData.getFrame(Shared.TAPON + "/Poner/In1"))
								.setJointVelocityRel(1.0));
		debugUltimoPunto("Tapon/Poner/In1");
						
		// 3. Acci�n
		debug("Poniendo Tap�n");
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Poner (1)");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").moveAsync(lin(Shared.appData.getFrame(Shared.TAPON + "/Poner"))
							.setCartVelocity(100.0).setMode(impeLissajous));
		debugUltimoPunto("Tapon/Poner (1)");
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Poner/Colocar1");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").moveAsync(ptp(Shared.appData.getFrame(Shared.TAPON + "/Poner/Colocar1"))
								.setJointVelocityRel(1.0));
		debugUltimoPunto("Tapon/Poner/Colocar1");
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Poner (2)");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").move(ptp(Shared.appData.getFrame(Shared.TAPON + "/Poner"))
							.setJointVelocityRel(1.0));
		debugUltimoPunto("Tapon/Poner (2)");
		while (!Shared.garra.isPinzaAbierta())
		{
			Shared.garra.abrirPinza();
			ThreadUtil.milliSleep(10);
		}
		debug("Terminado poner Tap�n");
						
		// 4. Retirada
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Tapon/Poner/Out1");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Tapon").moveAsync(ptp(Shared.appData.getFrame(Shared.TAPON + "/Poner/Out1"))
								.setJointVelocityRel(1.0).setBlendingCart(20));
		debugUltimoPunto("Tapon/Poner/Out1");
		debug("Saliendo de Tapon/Poner");
	}
	
	/**
	 * Movimientos para medir el nivel de aceite del motor.
	 */
	public static void medirNivel()
	{
		// Inicializa los valores para el movimiento de impedancia para ir a nivel
		impeIrNivel = new CartesianImpedanceControlMode();
		impeIrNivel.parametrize(CartDOF.X).setStiffness(3600);
		impeIrNivel.parametrize(CartDOF.Y).setStiffness(3600);
		impeIrNivel.parametrize(CartDOF.Z).setStiffness(4500);
		impeIrNivel.parametrize(CartDOF.A).setStiffness(300);
		impeIrNivel.parametrize(CartDOF.B).setStiffness(300);
		impeIrNivel.parametrize(CartDOF.C).setStiffness(200);
		impeIrNivel.parametrize(CartDOF.ALL).setDamping(0.9);
		
		// Inicializa los valores para el movimiento de impedancia para medir el nivel
		impeMedirNivel = new CartesianImpedanceControlMode();
		impeMedirNivel.parametrize(CartDOF.X).setStiffness(3000);
		impeMedirNivel.parametrize(CartDOF.Y).setStiffness(3000);
		impeMedirNivel.parametrize(CartDOF.Z).setStiffness(5000);
		impeMedirNivel.parametrize(CartDOF.A).setStiffness(200);
		impeMedirNivel.parametrize(CartDOF.B).setStiffness(200);
		impeMedirNivel.parametrize(CartDOF.C).setStiffness(200);
		impeMedirNivel.parametrize(CartDOF.ALL).setDamping(0.9);
		//impeMedirNivel.setSpringPosition(Shared.garra.getFrame("/GarraMotor/Nivel"));
		
		
		debug("Moviendo a MedirNivel");
		
		// 1. Aproximaci�n
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Nivel/In1");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Nivel").moveAsync(ptp(Shared.appData.getFrame(Shared.NIVEL + "/In1"))
								.setJointVelocityRel(1.0).setBlendingCart(20));
		debugUltimoPunto("Nivel/In1");
		
		// 2. B�squeda segura
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Nivel/In2");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Nivel").moveAsync(ptp(Shared.appData.getFrame(Shared.NIVEL + "/In2"))
									.setJointVelocityRel(1.0));
		debugUltimoPunto("Nivel/In2");
						
		// 3. Acci�n
		debug("Midiendo Nivel");
		// Dependiendo del tipo de conector que se seleccione en pantalla, se hace una b�squeda
		// u otra. Si no se recibe un c�digo de conector conocido, se mueve directamente al punto
		// de medici�n
		if (Shared.COD_CON == 1)
		{
			busquedaNivelRedondo();
		}
		else if (Shared.COD_CON == 2)
		{
			busquedaNivelCuadrado();
		}
		else
		{
			while (!Shared.iExtension.getAutomaticoLinea())
			{
				if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
				{
					debug("Return punto Nivel");
					return;
				}
				ThreadUtil.milliSleep(10);
			}
			Shared.garra.getFrame("/Nivel").move(lin(Shared.appData.getFrame(Shared.NIVEL))
								.setCartVelocity(100.0).setMode(impeIrNivel));
			// Como el robot entra con impedancia, es posible que su posici�n quede m�s elevada antes de hacer el PositionHold
			// Se mueve otra vez al mismo punto para asegurar que est� en una posici�n correcta
			Shared.garra.getFrame("/Nivel").move(lin(Shared.appData.getFrame(Shared.NIVEL))
								.setCartVelocity(100.0));
			debugUltimoPunto("Nivel");
			mantenerImpe = Shared.robot.moveAsync((new PositionHold(impeMedirNivel, -1, TimeUnit.SECONDS)));
			ThreadUtil.milliSleep(50);
			waitFor(new BooleanIOCondition(Shared.iExtension.getInput("Home"), true), "Esperando fin medir nivel...", 5);
			if (Shared.iExtension.getHome())
			{
				irHome = true;
				irNivel = false;
			}
			ThreadUtil.milliSleep(10);	// Esta parada es necesaria para evitar un fallo del robot por posici�n
			mantenerImpe.cancel();
			// Se mueve el robot a su posici�n actual para poder moverse despu�s de haber estado parado en impedancia
			Shared.robot.move(ptp(Shared.robot.getCurrentJointPosition()).setJointVelocityRel(0.1));
		}
		debug("Terminado medir Nivel");

		// 4. Retirada
		if (Shared.COD_CON==1 || Shared.COD_CON==2)
		{
			// El movimiento es relativo porque se ha modificado el punto final debido a los giros de la b�squeda
			while (!Shared.iExtension.getAutomaticoLinea())
			{
				if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
				{
					debug("Return punto Nivel/In2 (salir, rel)");
					return;
				}
				ThreadUtil.milliSleep(10);
			}
			Shared.garra.getFrame("/Nivel").moveAsync(linRel(0, 0, 20, 0, 0, 0, Shared.NIVELFRAME)
									.setCartVelocity(100.0).setBlendingCart(20).setMode(impeIrNivel));
			debugUltimoPunto("Nivel/In2 (salir, rel)");
		}
		else
		{
			while (!Shared.iExtension.getAutomaticoLinea())
			{
				if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
				{
					debug("Return punto Nivel/In2 (salir)");
					return;
				}
				ThreadUtil.milliSleep(10);
			}
			Shared.garra.getFrame("/Nivel").moveAsync(ptp(Shared.appData.getFrame(Shared.NIVEL + "/In2"))
									.setJointVelocityRel(100.0).setBlendingCart(10).setMode(impeIrNivel));
			debugUltimoPunto("Nivel/In2 (salir)");
		}
		ThreadUtil.milliSleep(20);
		Shared.oExtension.setPosFinNivel(true);
		
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Nivel/In1 (salir)");
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Nivel").moveAsync(ptp(Shared.appData.getFrame(Shared.NIVEL + "/In1"))
							.setJointVelocityRel(1.0).setBlendingCart(10));
		debugUltimoPunto("Nivel/In1 (salir)");
		debug("Saliendo de MedirNivel");
	}
	
	/**
	 * B�squeda de la posici�n correcta del nivel utilizando el conector redondo.
	 */
	public static void busquedaNivelRedondo()
	{
		debug("Iniciando b�squeda conector redondo");
		
		// N�mero de grados que se girar�. Se niega para que el giro sea en sentido horario
		int giro = -Shared.iExtension.getGrados();
		
		// N�mero de intentos para mostrar en los mensajes de informaci�n
		int numIntentos = 1;
		
		// Puntos auxiliares para girar en la b�squeda
		Frame pNivelNuevo = Shared.NIVELFRAME.copyWithRedundancy();	// Se modifica el punto final para a�adirle los grados de giro
		Frame pGiro;	// Girar� la posici�n actual para buscar de nuevo la posici�n
		
		// Condici�n para continuar
		// - Si recibe la marca Home, termina el resto del proceso
		// - Si recibe la marca ErrorSonda, reintenta girando la posici�n
		ICondition continuar = new BooleanIOCondition(Shared.iExtension.getInput("ErrorSonda"), true)
								.or(new BooleanIOCondition(Shared.iExtension.getInput("Home"), true));
		
		while (!Shared.iExtension.getAutomaticoLinea())
		{
			if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
			{
				debug("Return punto Nivel/BusquedaRedondo " + numIntentos);
				return;
			}
			ThreadUtil.milliSleep(10);
		}
		Shared.garra.getFrame("/Nivel").move(lin(pNivelNuevo).setCartVelocity(100.0).setMode(impeIrNivel));
		// Como el robot entra con impedancia, es posible que su posici�n quede m�s elevada antes de hacer el PositionHold
		// Se mueve otra vez al mismo punto para asegurar que est� en una posici�n correcta
		Shared.garra.getFrame("/Nivel").move(lin(pNivelNuevo).setCartVelocity(100.0));
		debugUltimoPunto("Nivel (BusquedaRedondo " + numIntentos + ")");
		mantenerImpe = Shared.robot.moveAsync((new PositionHold(impeMedirNivel, 3, TimeUnit.SECONDS)));
		ThreadUtil.milliSleep(50);
		waitFor(continuar, "Esperando fin medir nivel (BusquedaRedondo " + numIntentos + ")...", 5);
		ThreadUtil.milliSleep(10);	// Esta parada es necesaria para evitar un fallo del robot por posici�n
		mantenerImpe.cancel();
		// Se mueve el robot a su posici�n actual para poder moverse despu�s de haber estado parado en impedancia
		Shared.robot.move(ptp(Shared.robot.getCurrentJointPosition()).setJointVelocityRel(0.1));
		
		// Mientras que no se reciba la se�al de Home, si se recibe la se�al ErrorSonda reintentar� la b�squeda
		while (!Shared.iExtension.getHome())
		{
			numIntentos++;
			
			// Si se recibe la se�al de ErrorSonda, se reintenta
			if (Shared.iExtension.getErrorSonda())
			{
				// Condici�n para continuar
				// - Si recibe la marca Home, termina el resto del proceso
				// - Si recibe la marca ErrorSonda, reintenta girando la posici�n
				continuar = new BooleanIOCondition(Shared.iExtension.getInput("ErrorSonda"), true)
										.or(new BooleanIOCondition(Shared.iExtension.getInput("Home"), true));
				
				// Se aleja de la posici�n actual
				pGiro = Shared.robot.getCurrentCartesianPosition(Shared.garra.getFrame("/Nivel")).copyWithRedundancy();
				pGiro = pGiro.transform(XyzAbcTransformation.ofDeg(0, 0, 10, 0, 0, 0));
				while (!Shared.iExtension.getAutomaticoLinea())
				{
					if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
					{
						debug("Return punto Nivel/BusquedaRedondo " + numIntentos + " (rel)");
						return;
					}
					ThreadUtil.milliSleep(10);
				}
				Shared.garra.getFrame("/Nivel").move(ptp(pGiro).setJointVelocityRel(1.0));
				
				// Se gira el punto actual
				pGiro = Shared.robot.getCurrentCartesianPosition(Shared.garra.getFrame("/Nivel")).copyWithRedundancy();
				pGiro = pGiro.transform(XyzAbcTransformation.ofDeg(0, 0, 0, giro, 0, 0));
				while (!Shared.iExtension.getAutomaticoLinea())
				{
					if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
					{
						debug("Return punto Nivel/BusquedaRedondo " + numIntentos + " (giro)");
						return;
					}
					ThreadUtil.milliSleep(10);
				}
				Shared.garra.getFrame("/Nivel").move(ptp(pGiro).setJointVelocityRel(1.0));
				
				// Se a�ade el giro al punto final
				pNivelNuevo = pNivelNuevo.copy().transform(XyzAbcTransformation.ofDeg(0, 0, 0, giro, 0, 0));
				while (!Shared.iExtension.getAutomaticoLinea())
				{
					if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
					{
						debug("Return punto Nivel/BusquedaRedondo " + numIntentos);
						return;
					}
					ThreadUtil.milliSleep(10);
				}
				Shared.garra.getFrame("/Nivel").move(lin(pNivelNuevo).setCartVelocity(100.0).setMode(impeIrNivel));
				
				// Como el robot entra con impedancia, es posible que su posici�n quede m�s elevada antes de hacer el PositionHold
				// Se mueve otra vez al mismo punto para asegurar que est� en una posici�n correcta
				Shared.garra.getFrame("/Nivel").move(lin(pNivelNuevo).setCartVelocity(100.0));
				debugUltimoPunto("Nivel (BusquedaRedondo " + numIntentos + ")");
				mantenerImpe = Shared.robot.moveAsync((new PositionHold(impeMedirNivel, 3, TimeUnit.SECONDS)));
				ThreadUtil.milliSleep(50);
				waitFor(continuar, "Esperando fin medir nivel (BusquedaRedondo " + numIntentos + ")...", 5);
				ThreadUtil.milliSleep(10);	// Esta parada es necesaria para evitar un fallo del robot por posici�n
				mantenerImpe.cancel();
				// Se mueve el robot a su posici�n actual para poder moverse despu�s de haber estado parado en impedancia
				Shared.robot.move(ptp(Shared.robot.getCurrentJointPosition()).setJointVelocityRel(0.1));
			}
		}
		// Si ya se ha recibido la se�al Home deja de reintentarlo y vuelve al punto de inicio
		if (Shared.iExtension.getHome())
		{
			irHome = true;
			irNivel = false;
			Shared.appData.getProcessData("intentoMedirNivel").setValue(numIntentos);
			debug("Fin BusquedaConectorRedondo (intento " + numIntentos + ")");
		}
	}
	
	/**
	 * B�squeda de la posici�n correcta del nivel utilizando el conector cuadrado.
	 */
	public static void busquedaNivelCuadrado()
	{
		debug("Iniciando b�squeda conector cuadrado");

		boolean posicionCorrecta = false;
		int giro = -10;		// N�mero de grados que se girar�. Se niega para que el giro sea en sentido horario
		int numIntentos = 10;	// N�mero de intentos que se realizar�n
		
		// Condici�n de fuerza para parar el movimiento
		ICondition choque = ForceCondition.createNormalForceCondition(Shared.garra.getFrame("Nivel"),
								Shared.WORLDFRAME, CoordinateAxis.Z, 12);
		
		// Puntos auxiliares para girar en la b�squeda
		Frame pNivelNuevo = Shared.NIVELFRAME.copyWithRedundancy();	// Se modifica el punto final para ir a�adi�ndole los grados de giro
		Frame pGiro;	// Gira la posici�n actual para buscar la posici�n

		do
		{
			IMotionContainer busqueda = Shared.garra.getFrame("/Nivel").move(lin(pNivelNuevo).setCartVelocity(80.0).breakWhen(choque));
			ThreadUtil.milliSleep(50);	// Esta parada es para que llegue correctamente a su posici�n antes de hacer la comprobacion
			
			// Estar� en la posici�n correcta si la distancia es menor de los mil�metros indicados
			posicionCorrecta = isInFrame(Shared.garra.getFrame("/Nivel"), Shared.NIVEL, 5.0);

			numIntentos--;
			// Mientras que se dispare la condici�n de fuerza o no est� en la posici�n correcta y a�n queden intentos, se gira
			if((busqueda.hasFired(choque) || !posicionCorrecta) && numIntentos > 0)
			{
				// Se gira desde la posici�n actual
				pGiro = Shared.robot.getCurrentCartesianPosition(Shared.garra.getFrame("/Nivel")).copyWithRedundancy();
				pGiro = pGiro.transform(XyzAbcTransformation.ofDeg(0, 0, 0, giro, 0, 0));
				while (!Shared.iExtension.getAutomaticoLinea())
				{
					if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
					{
						debug("Return punto Nivel/BusquedaCuadrado (giro)");
						return;
					}
					ThreadUtil.milliSleep(10);
				}
				Shared.garra.getFrame("/Nivel").move(ptp(pGiro).setJointVelocityRel(1.0));
				
				// Se a�ade el giro al punto final
				while (!Shared.iExtension.getAutomaticoLinea())
				{
					if (Shared.iExtension.getHome() || Shared.iExtension.getRobotInhibido())
					{
						debug("Return punto Nivel/BusquedaCuadrado");
						return;
					}
					ThreadUtil.milliSleep(10);
				}
				pNivelNuevo = pNivelNuevo.copy().transform(XyzAbcTransformation.ofDeg(0, 0, 0, giro, 0, 0));
			}
		}while (!posicionCorrecta && numIntentos>0);
		debugUltimoPunto("Nivel (BusquedaCuadrado)");
		
		// Si los intentos son 0 y la posici�n no es correcta, muestra un mensaje de informaci�n
		if (numIntentos <= 0 && !posicionCorrecta)
		{
			debug("Se ha superado el n�mero de intentos para el conector cuadrado");
			Shared.oExtension.setBusqCuadNoOK(true);
		}
		mantenerImpe = Shared.robot.moveAsync((new PositionHold(impeMedirNivel, 3, TimeUnit.SECONDS)));
		waitFor(new BooleanIOCondition(Shared.iExtension.getInput("Home"), true), "Esperando fin medir nivel (BusquedaCuadrado)...", 5);
		if (Shared.iExtension.getHome())
		{
			irHome = true;
			irNivel = false;
		}
		ThreadUtil.milliSleep(10);	// Esta parada es necesaria para evitar un fallo del robot por posici�n
		mantenerImpe.cancel();
		// Se mueve el robot a su posici�n actual para poder moverse despu�s de haber estado parado en impedancia
		Shared.robot.move(ptp(Shared.robot.getCurrentJointPosition()).setJointVelocityRel(0.1));
		debug("Fin b�squeda conector cuadrado");
	}
}
