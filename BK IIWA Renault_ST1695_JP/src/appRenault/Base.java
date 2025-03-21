package appRenault;

import appShared.Shared;
import appTools.Garra;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;

import static appUtilities.Utils.isInFrame;
import static appUtilities.Utils.pos_act;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;
import com.kuka.task.ITaskLogger;

/**
 * Clase para mover el robot a los puntos de la base.
 */
public class Base extends RoboticsAPIApplication
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
		// Si el robot no est� en la posici�n delantera, se aborta el programa porque est� inhibido
		if (!Shared.iExtension.getRbtPos1() || Shared.iExtension.getRbtPos2())
		{
			getAppLogger().error("�El robot est� inhibido, no puede realizar movimientos!");
			return;
		}
		
		// Si el robot est� en la posici�n delantera (trabajo) y no est� en la trasera (inhibido),
		// se mueve a los puntos de la base
		if (Shared.iExtension.getRbtPos1() && !Shared.iExtension.getRbtPos2())
		{
			// Se calcula la posici�n actual por si se est� pulsando origen de m�quina desde el principio
			// Si esto ocurre, el Background a�n no habr� calculado las coordenadas actuales
			// y dar� fallo al intentar reposicionar
			Shared.posAct = pos_act();
			
			// Si est� en Home, puede moverse a los puntos
			if (isInFrame(Shared.robot.getFlange(), Shared.HOME, 10.0))
			{
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.WORLD + "/InterBase")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.WORLD + "/OrigenBase/In1")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(lin(Shared.appData.getFrame(Shared.WORLD + "/OrigenBase")).setCartVelocity(60.0));
				// Se para el programa para ver que el robot est� correctamente en el punto
				getApplicationUI().displayModalDialog(ApplicationDialogType.INFORMATION
								, "Confirmar que el robot est� en el punto 'Origen' de la base y pulsar continuar para ir al siguiente punto."
								, "Continuar");
				Shared.garra.getFrame("/KM0").move(lin(Shared.appData.getFrame(Shared.WORLD + "/OrigenBase/In1")).setCartVelocity(80.0));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.WORLD + "/PosXBase/In1")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.WORLD + "/PosXBase/In2")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(lin(Shared.appData.getFrame(Shared.WORLD + "/PosXBase")).setCartVelocity(60.0));
				// Se para el programa para ver que el robot est� correctamente en el punto
				getApplicationUI().displayModalDialog(ApplicationDialogType.INFORMATION
								, "Confirmar que el robot est� en el punto 'Posici�n X positiva' de la base y pulsar continuar para ir al siguiente punto."
								, "Continuar");
				Shared.garra.getFrame("/KM0").move(lin(Shared.appData.getFrame(Shared.WORLD + "/PosXBase/In2")).setCartVelocity(80.0));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.WORLD + "/PosXBase/In1")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.WORLD + "/InterBase")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.WORLD + "/PosYBase/In1")).setJointVelocityRel(0.8));
				Shared.garra.getFrame("/KM0").move(lin(Shared.appData.getFrame(Shared.WORLD + "/PosYBase")).setCartVelocity(60.0));
				// Se para el programa para ver que el robot est� correctamente en el punto
				getApplicationUI().displayModalDialog(ApplicationDialogType.INFORMATION
								, "Confirmar que el robot est� en el punto 'Posici�n Y positiva' de la base y pulsar continuar para volver a Home."
								, "Continuar");
				Shared.garra.getFrame("/KM0").move(lin(Shared.appData.getFrame(Shared.WORLD + "/PosYBase/In1")).setCartVelocity(80.0));
				Shared.garra.getFrame("/KM0").move(ptp(Shared.appData.getFrame(Shared.WORLD + "/InterBase")).setJointVelocityRel(0.8));
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome")).setJointVelocityRel(0.8));
				Shared.robot.move(ptp(Shared.appData.getFrame(Shared.HOME)).setJointVelocityRel(0.8));
			}
			else
			{
				getAppLogger().error("El robot no est� en Home. No puede ir a los puntos de la base.");
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