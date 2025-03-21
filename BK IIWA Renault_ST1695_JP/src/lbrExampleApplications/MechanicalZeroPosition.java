package lbrExampleApplications;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.motionModel.PTP;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;

public class MechanicalZeroPosition extends RoboticsAPIApplication
{
	private LBR lbr;
	private final static String informationText = "El robot se moverá a la posición 0 mecánicamente (todos los ejes a 0)\n\n"
													+ "Verificar que está en Home para que pueda hacerlo de forma segura";

	public void initialize()
	{
		lbr = getContext().getDeviceFromType(LBR.class);
	}

	public void run()
	{
		getLogger().info("Show modal dialog and wait for user to confirm");
        int isCancel = getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, informationText, "OK", "Cancel");
        if (isCancel == 1)
        {
            return;
        }

		getLogger().info("Move to the mechanical zero position");
		PTP ptpToMechanicalZeroPosition = ptp(0,0,0,0,0,0,0);
		ptpToMechanicalZeroPosition.setJointVelocityRel(0.25);
		
		// Añadido para que pase por el punto intermedio y no golpee
		lbr.move(ptp(getApplicationData().getFrame("/Base/KM0/Home/InterHome")));
		
		lbr.move(ptpToMechanicalZeroPosition);
	}
}
