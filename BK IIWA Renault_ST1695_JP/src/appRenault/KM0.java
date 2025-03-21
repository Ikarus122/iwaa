package appRenault;

import appShared.Shared;
import appTools.Garra;
import static appUtilities.Utils.isInFrame;
import static appUtilities.Utils.pos_act;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;
import com.kuka.task.ITaskLogger;

/**
 * Clase para llevar el robot al KM0.
 */
public class KM0 extends RoboticsAPIApplication
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
		// se mueve al punto de KM0
		if (Shared.iExtension.getRbtPos1() && !Shared.iExtension.getRbtPos2())
		{
			// Se calcula la posición actual por si se está pulsando origen de máquina desde el principio
			// Si esto ocurre, el Background aún no habrá calculado las coordenadas actuales
			// y dará fallo al intentar reposicionar
			Shared.posAct = pos_act();
			
			// Si está en Home, puede moverse a KM0
			if (isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0))
			{
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.KM0 + "/InterKM0")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(lin(Shared.appData.getFrame(Shared.KM0)).setCartVelocity(60.0));
				// Se para el programa para ver que el robot está correctamente en el punto
				getApplicationUI().displayModalDialog(ApplicationDialogType.INFORMATION
								, "Confirmar que el robot está en el punto KM0 y pulsar continuar para volver a Home."
								, "Continuar");
				Shared.garra.getFrame("/KM0").move(lin(Shared.appData.getFrame(Shared.KM0 + "/InterKM0")).setCartVelocity(80.0));
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome")).setJointVelocityRel(0.8));
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME)).setJointVelocityRel(0.8));
			}
			else
			{
				getAppLogger().error("El robot no está en Home. No puede ir a KM0.");
				return;
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