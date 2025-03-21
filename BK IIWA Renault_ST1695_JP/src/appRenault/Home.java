package appRenault;

import static appUtilities.MovimientosRobot.*;
import static appUtilities.Utils.*;
import appShared.Shared;
import appTools.Garra;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.task.ITaskLogger;

/**
 * Clase para mover el robot a Home.
 */
public class Home extends RoboticsAPIApplication
{
	@Override
	public void initialize()
	{
		Shared.controladora = getController("KUKA_Sunrise_Cabinet_1");
		Shared.robot = (LBR) getDevice(Shared.controladora, "LBR_iiwa_14_R820_1");
		
		// Inicializa los valores de las variables globales
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

	@Override
	public void run()
	{
		// Si el robot no está en la posición delantera, se aborta el programa porque está inhibido
		if (!Shared.iExtension.getRbtPos1() || Shared.iExtension.getRbtPos2())
		{
			getAppLogger().error("¡El robot está inhibido, no puede realizar movimientos!");
			return;
		}
		
		// Si el robot está en la posición delantera (trabajo) y no está en la trasera (inhibido),
		// se mueve a Home de forma segura
		if (Shared.iExtension.getRbtPos1() && !Shared.iExtension.getRbtPos2())
		{
			// Se calcula la posición actual por si se está pulsando origen de máquina desde el principio
			// Si esto ocurre, el Background aún no habrá calculado las coordenadas actuales
			// y dará fallo al intentar reposicionar
			Shared.posAct = pos_act();
			
			// Si no está ya en Home, se comprueba si puede ir
			if (!isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0))
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
					return;
				}
				// Si el robot está en una posición controlada, hace una secuencia de movimientos
				// para llegar a Home de forma segura
				else if (checkHomeSeguro())
				{
					movHomeSeguro();
					getAppLogger().info("Robot posicionado en Home");
				}
			}
			else
			{
				getAppLogger().info("El robot ya está en Home");
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