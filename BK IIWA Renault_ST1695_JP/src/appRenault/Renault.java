package appRenault;

import appShared.Shared;
import appTools.Garra;
import appUtilities.RobotReferencing;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;

import static appUtilities.Utils.*;
import static appUtilities.MovimientosRobot.*;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.task.ITaskLogger;

/**
 * Clase principal de la aplicación.
 */
public class Renault extends RoboticsAPIApplication
{
	/******************************************************************************
	 * Variables
	 *****************************************************************************/
	private RobotReferencing robotReferencia = null;			// Clase para referenciar los ejes del robot

	
	/******************************************************************************
	 * Ejecución
	 *****************************************************************************/
	@Override
	public void dispose()
	{
		// Se desactiva la variable de programa iniciado
		Shared.appRun = false;
		super.dispose();
	}
	
	@Override
	public void initialize()
	{
		Shared.controladora = getController("KUKA_Sunrise_Cabinet_1");
		Shared.robot = (LBR) getDevice(Shared.controladora, "LBR_iiwa_14_R820_1");
		robotReferencia = new RobotReferencing();
		
		// Inicializa los valores de las variables globales
		Shared.appRun = false;
		Shared.sumTiempo = 0.0;
		Shared.numMotores = 0;
		Shared.appLogger = getLogger();
		Shared.appControl = getApplicationControl();
		Shared.appData = getApplicationData();
		Shared.appObserver = getObserverManager();
		Shared.WORLDFRAME = getFrame(Shared.WORLD);
		Shared.HOMEFRAME = getFrame(Shared.HOME);
		Shared.ROBOTINHIBIDOFRAME = getFrame(Shared.ROBOTINHIBIDO);
		Shared.REFERENCIARFRAME = getFrame(Shared.REFERENCIAR);
		Shared.TAPONFRAME = getFrame(Shared.TAPON);
		Shared.VACIOFRAME = getFrame(Shared.VACIO);
		Shared.NIVELFRAME = getFrame(Shared.NIVEL);
		
		// Inicializa los valores de la herramienta
		Shared.garra = new Garra("GarraMotor");
		Shared.garra.attachTo(Shared.robot.getFlange());
	}
	
	/**
	 * Auto-generated method stub. Do not modify the contents of this method.
	 */
	public static void main(String[] args)
	{
		Renault app = new Renault();
		app.runApplication();
	}

	@Override
	public void run()
	{
		// Se activa la variable de programa iniciado
		Shared.appRun = true;
		// Se resetean las salidas para iniciar desde 0
		resetearSalidas();
		// Se calcula la posición actual por si se está pulsando origen de máquina desde el principio
		// Si esto ocurre, el Background aún no habrá calculado las coordenadas actuales
		// y dará fallo al intentar reposicionar
		Shared.posAct = pos_act();
		
		
		// Si el robot no está en la posición delantera, se aborta el programa porque está inhibido
		if (!Shared.iExtension.getRbtPos1() || Shared.iExtension.getRbtPos2())
		{
			getAppLogger().error("¡El robot está inhibido, no puede realizar movimientos!");
			return;
		}
		

		// Mientras que el robot esté en la posición delantera (trabajo) y no esté en la
		// trasera (inhibido), se realiza el ciclo de trabajo del robot
		while (Shared.iExtension.getRbtPos1() && !Shared.iExtension.getRbtPos2())
		{
			// Si algún eje no está referenciado, está activa la opción de referenciado
			// desde el ProcessData, está en Home y no se ha realizado la referencia,
			// ejecuta la referencia de los ejes
			if (!robotReferencia.getReferenciar() && Shared.ACT_REFERENCIADO
				&& Shared.oExtension.getPosHome())
			{
				robotReferencia.setReferenciar();
			}
			
			// Si se pulsa origen de máquina o está la señal Home y no está en Home, se manda el robot a la
			// posición Home
			if (Shared.iExtension.getHome() && !isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0))
			{
				// Si el robot no está en inhibido ni está en una posición controlada para ir a Home de
				// forma segura, se muestra un mensaje y se aborta el programa porque es necesario recolocar
				// el robot a mano
				if (!isInFrame(Shared.robot.getFlange(), Shared.ROBOTINHIBIDO, 10.0) && !checkHomeSeguro())
				{
					getAppLogger().error("El robot no está en una posición segura para ir a Home.\nEs necesario mover el robot manualmente.\n"
										+ "X: " + Math.round(Shared.posAct.getX())
										+ ", Y: " + Math.round(Shared.posAct.getY())
										+ ", Z: " + Math.round(Shared.posAct.getZ())
										+ ", A: " + Math.round(Math.toDegrees(Shared.posAct.getAlphaRad()))
										+ ", B: " + Math.round(Math.toDegrees(Shared.posAct.getBetaRad()))
										+ ", C: " + Math.round(Math.toDegrees(Shared.posAct.getGammaRad())));
					Shared.oExtension.setReposNoOK(true);
					return;
				}
				// Si el robot está en una posición controlada, hace una secuencia de movimientos
				// para llegar a Home de forma segura
				else if (checkHomeSeguro())
				{
					movHomeSeguro();
				}
			}
			
			
			// Si se inhibe el robot o está la señal RobotInhibido y no está en inhibido, se manda el robot
			// a la posición InhibirRobot pasando por Home
			if (Shared.iExtension.getRobotInhibido() && !isInFrame(Shared.robot.getFlange(), Shared.ROBOTINHIBIDO, 10.0))
			{
				// Si no puede ir a Home de forma segura, hay que moverlo manualmente
				if (!isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0) && !checkHomeSeguro())
				{
					getAppLogger().error("El robot no está en una posición segura para ir a Home.\nEs necesario mover el robot manualmente.\n"
										+ "X: " + Math.round(Shared.posAct.getX())
										+ ", Y: " + Math.round(Shared.posAct.getY())
										+ ", Z: " + Math.round(Shared.posAct.getZ())
										+ ", A: " + Math.round(Math.toDegrees(Shared.posAct.getAlphaRad()))
										+ ", B: " + Math.round(Math.toDegrees(Shared.posAct.getBetaRad()))
										+ ", C: " + Math.round(Math.toDegrees(Shared.posAct.getGammaRad())));
					Shared.oExtension.setReposNoOK(true);
					return;	
				}
				// Si está en una posición segura para ir a Home, pasa por el punto y va a RobotInhibido
				else if (!isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0) && checkHomeSeguro())
				{
					movHomeSeguro();
				}
				// Si el robot está en Home, puede ir a RobotInhibido
				else if (isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0))
				{
					movRobotInhibido();
				}
			}
			

			try
			{				
				// Mientras la línea esté Ready, esté en Automático y en Home, y no se reciba la señal Home
				// ni la señal InhibirRobot, se ejecuta la secuencia
				while (Shared.iExtension.getReadyLinea() && Shared.iExtension.getAutomaticoLinea()
						&& isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0) && !Shared.iExtension.getHome()
						&& !Shared.iExtension.getRobotInhibido())
				{
					debug("Entrando en secuencia");
					
					// Se llama a la función para realizar la secuencia
					movSecuencia();
					
					debug("Terminada secuencia");
				}
			}
			catch (Exception e)
			{
				
			}
		}
	}
	
	
	/******************************************************************************
	 * Funciones
	 *****************************************************************************/
	/**
	 * Comprueba si existe un Logger para imprimir mensajes. Si no existe el logger
	 * en las variables globales, utiliza el de la propia clase.
	 * @return Devuelve el logger encontrado
	 */
	private ITaskLogger getAppLogger()
	{
		if (Shared.appLogger != null)
		{
			return Shared.appLogger;
		}
		else
		{
			return getLogger();
		}
	}
}
